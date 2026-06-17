package com.example.moodmeal;

public class MoodRecord {
    private long id;
    private String mood;
    private int stressLevel;
    private String availableTime;
    private String note;
    private String recommendedMeal;
    private String advice;
    private String riskLevel;
    private String createdAt;

    public MoodRecord() {}

    public MoodRecord(long id, String mood, int stressLevel, String availableTime, String note,
                      String recommendedMeal, String advice, String riskLevel, String createdAt) {
        this.id = id;
        this.mood = mood;
        this.stressLevel = stressLevel;
        this.availableTime = availableTime;
        this.note = note;
        this.recommendedMeal = recommendedMeal;
        this.advice = advice;
        this.riskLevel = riskLevel;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }

    public int getStressLevel() { return stressLevel; }
    public void setStressLevel(int stressLevel) { this.stressLevel = stressLevel; }

    public String getAvailableTime() { return availableTime; }
    public void setAvailableTime(String availableTime) { this.availableTime = availableTime; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getRecommendedMeal() { return recommendedMeal; }
    public void setRecommendedMeal(String recommendedMeal) { this.recommendedMeal = recommendedMeal; }

    public String getAdvice() { return advice; }
    public void setAdvice(String advice) { this.advice = advice; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
