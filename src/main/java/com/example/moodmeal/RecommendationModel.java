package com.example.moodmeal;

import org.springframework.stereotype.Component;

@Component
public class RecommendationModel {

    public MoodRecord predict(MoodRequest request) {
        String mood = safe(request.getMood());
        String time = safe(request.getAvailableTime());
        String note = safe(request.getNote()).toLowerCase();
        int stress = Math.max(1, Math.min(10, request.getStressLevel()));

        String risk;
        String meal;
        String advice;

        if (stress >= 8) {
            risk = "High";
            meal = "Warm soup + rice ball + banana";
            advice = "Your stress looks high. Eat something warm, drink water, and take a 10-minute break before continuing.";
        } else if (stress >= 5) {
            risk = "Medium";
            meal = "Chicken bowl + vegetables + unsweetened tea";
            advice = "Your stress is moderate. Choose a balanced meal and avoid skipping dinner.";
        } else {
            risk = "Low";
            meal = "Light sandwich + fruit yogurt";
            advice = "Your current state looks stable. Keep your routine and stay hydrated.";
        }

        if (mood.equalsIgnoreCase("Exciting")) {
            meal = "Korean spicy rice bowl + sparkling water + fruit";
            advice = "You seem energetic today. Try something bold and fun, but remember to stay balanced and hydrated.";
        }


        
        if (time.contains("10")) {
            meal = "Convenience store rice ball + soy milk + fruit";
            advice += " Since you only have a short time, choose a fast but still nutritious option.";
        }

        if (note.contains("tired") || note.contains("sleepy") || note.contains("熬夜") || note.contains("累")) {
            advice += " You also mentioned tiredness, so avoid too much caffeine and try to sleep earlier tonight.";
        }

        if (note.contains("exam") || note.contains("deadline") || note.contains("考試") || note.contains("報告")) {
            advice += " For study pressure, split your task into 25-minute focus blocks.";
        }

        MoodRecord record = new MoodRecord();
        record.setMood(mood);
        record.setStressLevel(stress);
        record.setAvailableTime(time);
        record.setNote(request.getNote());
        record.setRecommendedMeal(meal);
        record.setAdvice(advice);
        record.setRiskLevel(risk);
        return record;
    }

    private String safe(String value) {
        if (value == null || value.isBlank()) return "Not specified";
        return value.trim();
    }
}
