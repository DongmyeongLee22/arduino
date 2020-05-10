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
  private final MeasureValueService measureValueService;

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

  public String selectWindowStateForOperationBy(String name) {
    User user = userService.findUser("이혜은");
    Window window = windowRepository.findById(1L)
        .orElseThrow(() -> new IllegalArgumentException("windowState가 존재해야 한다."));
    MeasureValue latestValue = measureValueService.findLatestValue(user.getId());
    return selectWindowState(user, window, latestValue);
  }

  private String selectWindowState(User user, Window window, MeasureValue measureValue) {
    WindowState windowState = window.getWindowState();
    if (windowState == WindowState.CLOSING)
      return "CLOSE";
    if (windowState == WindowState.OPENING)
      // 비가오면 창문을 열고 있더라도 강제로 닫게한다.
      return measureValue.isRain() ? "CLOSE" : "OPEN";

    if (windowState == WindowState.OPEN) {
      if (needClose(user, measureValue)) {
        window.setWindowState(WindowState.CLOSING);
        return "CLOSE";
      }
      return "NONE";
    }

    if (windowState == WindowState.CLOSE) {
      if (needOpen(user, measureValue)) {
        window.setWindowState(WindowState.OPENING);
        return "OPEN";
      }
      return "NONE";
    }
    throw new IllegalArgumentException("windowstate 확인 요망. " + windowState);
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
