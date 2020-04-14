package com.example.windowproject;


public class Item {
    private String temperature;
    private String airQuality;
    private String fineDust;
    private String windowState;
    private String pdlcState;

    public Item(String temperature, String airQuality, String fineDust, String windowState, String pdlcState) {
        this.temperature = temperature;
        this.airQuality = airQuality;
        this.fineDust = fineDust;
        this.windowState = windowState;
        this.pdlcState = pdlcState;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public String getFineDust() {
        return fineDust;
    }

    public String getWindowState() {
        return windowState;
    }

    public String getPdlcState() {
        return pdlcState;
    }
}
