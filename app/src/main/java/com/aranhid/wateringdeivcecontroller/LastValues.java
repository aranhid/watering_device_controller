package com.aranhid.wateringdeivcecontroller;

import androidx.annotation.NonNull;

public class LastValues {
    private static final String TAG = "LastValues";
    public Integer lastHumidity;
    public String lastHumidityCheckTime;
    public String lastWateringTime;

    public LastValues() {

    }

    public LastValues(Integer lastHumidity, String lastHumidityCheckTime, String lastWateringTime) {
        this.lastHumidity = lastHumidity;
        this.lastHumidityCheckTime = lastHumidityCheckTime;
        this.lastWateringTime = lastWateringTime;
    }

    public String getFormattedHumidityCheckTime() {
        return lastHumidityCheckTime.replace("T", " ").substring(0, lastHumidityCheckTime.length() - 1);
    }

    public String getFormattedWateringTime() {
        return lastWateringTime.replace("T", " ").substring(0, lastHumidityCheckTime.length() - 1);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("lastHumidity: %s, lastHumidityCheckTime: %s, lastWateringTime: %s",
                lastHumidity, lastHumidityCheckTime, lastWateringTime);
    }
}
