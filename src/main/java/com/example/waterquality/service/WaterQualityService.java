package com.example.waterquality.service;

import com.example.waterquality.model.WaterQuality;
import com.example.waterquality.repository.WaterQualityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class WaterQualityService {

    @Autowired
    private WaterQualityRepository repository;
    private final Random random = new Random();

    public List<WaterQuality> getAllData() {
        return repository.findAll();
    }

    public List<WaterQuality> getDataByStatus(String status) {
        return repository.findByStatus(status);
    }

    public WaterQuality generateRandomData() {
        double waterLevel = Math.round(random.nextDouble() * 1000) / 100.0;
        double pH = Math.round(6.5 + random.nextDouble() * 1500) / 100.0;
        double dissolvedOxygen = Math.round(5 + random.nextDouble() * 500) / 100.0;
        double temperature = Math.round(10 + random.nextDouble() * 2000) / 100.0;
        double turbidity = Math.round(random.nextDouble() * 1000) / 100.0;
        double tds = Math.round(100 + random.nextDouble() * 90000) / 100.0;
        List<String> analyse = analyzeWaterQuality(new WaterQuality(waterLevel, pH, dissolvedOxygen, temperature, turbidity, tds, "", ""));
        String status = analyse.get(0);
        String msg = analyse.get(1);

        WaterQuality data = new WaterQuality(waterLevel, pH, dissolvedOxygen, temperature, turbidity, tds, status, msg);
        return repository.save(data);
    }

    public List<String> analyzeWaterQuality(WaterQuality data) {
        String status = "Safe";
        StringBuilder msg = new StringBuilder();

        if (data.getpH() < 6.5 || data.getpH() > 8.5) {
            status = "Unsafe";
            msg.append("pH out of range\n");
        } 
        if (data.getDissolvedOxygen() < 5) {
            status = "Unsafe";
            msg.append("Low Dissolved Oxygen\n");
        }
        if (data.getTemperature() < 10 || data.getTemperature() > 25) {
            status = "Unsafe";
            msg.append("Temperature out of range\n");
        }
        if (data.getTurbidity() > 1) {
            status = "Unsafe";
            msg.append("High Turbidity\n");
        }
        if (data.getTds() > 500) {
            status = "Unsafe";
            msg.append("High TDS\n");
        }
        
        return List.of(status, msg.toString());
    }
}
