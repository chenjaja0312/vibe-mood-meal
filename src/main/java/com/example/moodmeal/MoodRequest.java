package com.example.moodmeal;

public class MoodRequest {
    private String mood;
    private int stressLevel;
    private String availableTime;
    private String note;

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }

    public int getStressLevel() { return stressLevel; }
    public void setStressLevel(int stressLevel) { this.stressLevel = stressLevel; }

    public String getAvailableTime() { return availableTime; }
    public void setAvailableTime(String availableTime) { this.availableTime = availableTime; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
