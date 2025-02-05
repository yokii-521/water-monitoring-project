package com.example.waterquality.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "water_quality")
public class WaterQuality {
    @Id
    private String id;
    private double waterLevel;
    private double pH;
    private double dissolvedOxygen;
    private double temperature;
    private double turbidity;
    private double tds;
    private boolean isPH;
    private boolean isDissolvedOxygen;
    private boolean isTemperature;
    private boolean isTurbidity;
    private boolean isTds;
    private String status;
    private String msg;
    private String date;
    private String time;

    // Setter
    public WaterQuality(double waterLevel, double pH, double dissolvedOxygen, double temperature, double turbidity,
            double tds, String status, String msg) {
        this.waterLevel = waterLevel;
        this.pH = pH;
        this.dissolvedOxygen = dissolvedOxygen;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.tds = tds;
        this.status = status;
        this.msg = msg;
        LocalDateTime now = LocalDateTime.now();
        this.date = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setIsPH(boolean isPH) {
        this.isPH = isPH;
    }

    public void setIsDissolvedOxygen(boolean isDissolvedOxygen) {
        this.isDissolvedOxygen = isDissolvedOxygen;
    }

    public void setIsTemperature(boolean isTemperature) {
        this.isTemperature = isTemperature;
    }

    public void setIsTurbidity(boolean isTurbidity) {
        this.isTurbidity = isTurbidity;
    }

    public void setIsTds(boolean isTds) {
        this.isTds = isTds;
    }
    
    // Getter
    public String getId() {
        return id;
    }

    public double getWaterLevel() {
        return waterLevel;
    }
    
    public double getpH() {
        return pH;
    }

    public double getDissolvedOxygen() {
        return dissolvedOxygen;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTurbidity() {
        return turbidity;
    }

    public double getTds() {
        return tds;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "WaterQuality{" +
                "id='" + id + '\'' +
                ", waterLevel=" + waterLevel +
                ", pH=" + pH +
                ", dissolvedOxygen=" + dissolvedOxygen +
                ", temperature=" + temperature +
                ", turbidity=" + turbidity +
                ", tds=" + tds +
                ", status='" + status + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

    public boolean isPH() {
        return isPH;
    }

    public boolean isDissolvedOxygen() {
        return isDissolvedOxygen;
    }

    public boolean isTemperature() {
        return isTemperature;
    }

    public boolean isTurbidity() {
        return isTurbidity;
    }

    public boolean isTds() {
        return isTds;
    }
}