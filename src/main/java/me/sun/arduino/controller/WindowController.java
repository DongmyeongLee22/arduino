package me.sun.arduino.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.sun.arduino.domain.WindowState;
import me.sun.arduino.service.WindowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/window")
public class WindowController {

    private final WindowService windowService;

    @GetMapping("/set")
    public MessageDto setWindowSate(@RequestParam("windowState") WindowState windowState) {
        return new MessageDto(windowService.saveAndUpdate(windowState));
    }

    @GetMapping
    public MessageDto getWindowState() {
        return new MessageDto(windowService.getWindowState());
    }

    @GetMapping("/state")
    public MessageDto queryByArduinoForWindowOperation(@RequestParam("name") String name) {
        return new MessageDto(windowService.tellToArduinoNeedToBehavior());
    }

    @AllArgsConstructor
    @Data
    static class MessageDto {
        private String windowMessage;
    }
}
