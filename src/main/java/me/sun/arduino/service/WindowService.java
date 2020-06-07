package me.sun.arduino.service;

import lombok.RequiredArgsConstructor;
import me.sun.arduino.domain.MeasureValue;
import me.sun.arduino.domain.User;
import me.sun.arduino.domain.Window;
import me.sun.arduino.domain.WindowState;
import me.sun.arduino.repo.WindowRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class WindowService {

  private final WindowRepository windowRepository;
  private final UserService userService;

  public String saveAndUpdate(@RequestParam(name = "windowState") WindowState windowState) {
    Optional<Window> optionalWindow = windowRepository.findById(1L);
    if (optionalWindow.isPresent()) {
      optionalWindow.get().setWindowState(windowState);
    } else {
      windowRepository.save(new Window(windowState));
    }
    return windowState.getMessage();
  }

  public String getWindowState() {
    Optional<Window> byId = windowRepository.findById(1L);
    if (!byId.isPresent())
      return windowRepository.save(new Window(WindowState.CLOSE)).getWindowState().getMessage();

    return byId.get().getWindowState().getMessage();
  }

  public void changeWindowStateByMeasureValue(MeasureValue latestValue) {
    User user = userService.findUser("이혜은");
    Optional<Window> byId = windowRepository.findById(1L);
    Window window = null;
    window = byId.orElseGet(() -> windowRepository.save(new Window(WindowState.CLOSE)));
    changeWindowState(user, window, latestValue);
  }

  // 아두이노에게 해야할 행동을 알려줌
  public String tellToArduinoNeedToBehavior() {
    Window window = windowRepository.findById(1L)
        .orElseThrow(() -> new IllegalArgumentException("windowState가 존재해야 한다."));
    switch (window.getWindowState()) {
      case OPENING:
        return "OPEN";
      case CLOSING:
        return "CLOSE";
      default:
        return "NONE";
    }
  }

  private void changeWindowState(User user, Window window, MeasureValue measureValue) {
    WindowState windowState = window.getWindowState();
    if (windowState == WindowState.OPEN) {
      if (needClose(user, measureValue)) {
        window.setWindowState(WindowState.CLOSING);
        windowRepository.save(window);
      }
    }

    if (windowState == WindowState.CLOSE) {
      if (needOpen(user, measureValue)) {
        window.setWindowState(WindowState.OPENING);
        windowRepository.save(window);
      }
    }
  }

  private boolean needOpen(User user, MeasureValue currentValue) {
    boolean openByTemperature = false, openByFineDust = false, openByHumidity = false;
    if (user.isCheckedTemp()) {
      // 현재 온도가 설정온도보다 높으면 연다.
      openByTemperature = Integer.parseInt(currentValue.getTemperature()) >= user.getOpenWindowTemp();
    }
    if (user.isCheckedFineDust()) {
      // 현재 미세먼지가 설정보다 낮으면 연다.
      openByFineDust = Integer.parseInt(currentValue.getFineDust()) <= user.getOpenWindowFineDust();
    }
    if (user.isCheckedHumidity()) {
      // 현재 습도가 설정보다 높으면 연다.
      openByHumidity = Integer.parseInt(currentValue.getHumidity()) >= user.getOpenWindowHumidity();
    }
    return openByTemperature || openByFineDust || openByHumidity;
  }

  private boolean needClose(User user, MeasureValue measureValue) {
    boolean closeByTemperature = false, closeByFineDust = false, closeByHumidity = false;
    if (user.isCheckedTemp()) {
      // 현재 온도가 온도보다 낮으면 닫는다.
      closeByTemperature = Integer.parseInt(measureValue.getTemperature()) <= user.getCloseWindowTemp();
    }
    if (user.isCheckedFineDust()) {
      // 현재 먼지가 설정보다 높으면 닫는다.
      closeByFineDust = Integer.parseInt(measureValue.getFineDust()) >= user.getCloseWindowFineDust();
    }
    if (user.isCheckedHumidity()) {
      // 현재 습도가 설정보다 낮으면 닫는다.
      closeByHumidity = Integer.parseInt(measureValue.getHumidity()) <= user.getCloseWindowHumidity();
    }
    return measureValue.isRain() || closeByTemperature || closeByFineDust || closeByHumidity;
  }
}
