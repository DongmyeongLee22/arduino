package com.example.windowproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WindowState {
    OPEN("창문 열림"),
    CLOSE("창문 닫힘"),
    OPENING("창문 여는중"),
    CLOSING("창문 닫는중"),
    ERROR("창문 버그 확인 요망");

    String message;

    public static WindowState findBy(String message) {
        for (WindowState state : WindowState.values()) {
            if (state.getMessage().endsWith(message))
                return state;
        }
        throw new IllegalArgumentException("message는 Window State의 message 중 하나여야 합니다. mssage: " + message);
    }
}
