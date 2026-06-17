package com.example.moodmeal;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MoodMealController {
    private final MoodMealService service;

    public MoodMealController(MoodMealService service) {
        this.service = service;
    }

    @PostMapping("/recommend")
    public MoodRecord recommend(@RequestBody MoodRequest request) {
        return service.recommend(request);
    }

    @GetMapping("/records")
    public List<MoodRecord> records() {
        return service.getRecords();
    }

    @DeleteMapping("/records")
    public Map<String, String> clear() {
        service.clearRecords();
        return Map.of("message", "All records have been cleared.");
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "OK");
    }
}
