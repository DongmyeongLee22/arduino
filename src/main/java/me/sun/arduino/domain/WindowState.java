package me.sun.arduino.domain;

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
}
