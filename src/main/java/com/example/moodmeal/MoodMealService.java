package com.example.moodmeal;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoodMealService {
    private final RecommendationModel model;
    private final MoodMealRepository repository;

    public MoodMealService(RecommendationModel model, MoodMealRepository repository) {
        this.model = model;
        this.repository = repository;
    }

    public MoodRecord recommend(MoodRequest request) {
        MoodRecord prediction = model.predict(request);
        return repository.save(prediction);
    }

    public List<MoodRecord> getRecords() {
        return repository.findAll();
    }

    public void clearRecords() {
        repository.deleteAll();
    }
}
