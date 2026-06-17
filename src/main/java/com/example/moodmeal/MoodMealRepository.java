package com.example.moodmeal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MoodMealRepository {
    private static final String DB_URL = "jdbc:sqlite:moodmeal.db";

    @PostConstruct
    public void init() {
        String sql = """
                CREATE TABLE IF NOT EXISTS mood_records (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    mood TEXT NOT NULL,
                    stress_level INTEGER NOT NULL,
                    available_time TEXT,
                    note TEXT,
                    recommended_meal TEXT NOT NULL,
                    advice TEXT NOT NULL,
                    risk_level TEXT NOT NULL,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                )
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize SQLite database", e);
        }
    }

    public MoodRecord save(MoodRecord record) {
        String sql = """
                INSERT INTO mood_records
                (mood, stress_level, available_time, note, recommended_meal, advice, risk_level)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, record.getMood());
            ps.setInt(2, record.getStressLevel());
            ps.setString(3, record.getAvailableTime());
            ps.setString(4, record.getNote());
            ps.setString(5, record.getRecommendedMeal());
            ps.setString(6, record.getAdvice());
            ps.setString(7, record.getRiskLevel());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) record.setId(keys.getLong(1));
            }
            return findById(record.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save record", e);
        }
    }

    public MoodRecord findById(long id) {
        String sql = "SELECT * FROM mood_records WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find record", e);
        }
        return null;
    }

    public List<MoodRecord> findAll() {
        List<MoodRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM mood_records ORDER BY id DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) records.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read records", e);
        }
        return records;
    }

    public void deleteAll() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM mood_records");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete records", e);
        }
    }

    private MoodRecord mapRow(ResultSet rs) throws SQLException {
        return new MoodRecord(
                rs.getLong("id"),
                rs.getString("mood"),
                rs.getInt("stress_level"),
                rs.getString("available_time"),
                rs.getString("note"),
                rs.getString("recommended_meal"),
                rs.getString("advice"),
                rs.getString("risk_level"),
                rs.getString("created_at")
        );
    }
}
