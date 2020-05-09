package me.sun.arduino.service;

import lombok.RequiredArgsConstructor;
import me.sun.arduino.domain.Window;
import me.sun.arduino.domain.WindowState;
import me.sun.arduino.repo.WindowRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class WindowService {

    private final WindowRepository windowRepository;

    public String saveAndUpdate(WindowState windowState) {
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
}
