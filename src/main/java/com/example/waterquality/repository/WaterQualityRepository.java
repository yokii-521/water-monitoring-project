package com.example.waterquality.repository;

import com.example.waterquality.model.WaterQuality;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterQualityRepository extends MongoRepository<WaterQuality, String> {
    List<WaterQuality> findByStatus(String status);
}
