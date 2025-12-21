// WorkoutResultDao

package kr.ac.dankook.jeong.workoutapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkoutResultDao {
    @Insert
    long insertSession(WorkoutSessionEntity session);

    @Query("SELECT * FROM workout_session WHERE sessionId = :sessionId")
    WorkoutSessionEntity getSessionById(int sessionId);

    @Query("SELECT * FROM workout_session WHERE date = :date ORDER BY sessionId ASC")
    List<WorkoutSessionEntity> getSessionsByDate(String date);

    @Query("SELECT DISTINCT date FROM workout_session ORDER BY date DESC")
    List<String> getAllDates();

    @Query("UPDATE workout_session SET endTime = :endTime WHERE sessionId = :sessionId")
    void updateSessionEndTime(int sessionId, String endTime);

    @Query("UPDATE workout_session SET totalDuration = :duration WHERE sessionId = :sessionId")
    void updateSessionDuration(int sessionId, long duration);

    @Insert
    void insertSummary(WorkoutSummaryEntity summary);

    @Query("SELECT * FROM workout_summary WHERE sessionId = :sessionId")
    List<WorkoutSummaryEntity> getSummariesBySession(int sessionId);


    @Insert
    void insertSet(WorkoutSetEntity set);

    @Query(
            "SELECT * FROM workout_set " +
                    "WHERE sessionId = :sessionId " +
                    "AND exerciseName = :exerciseName " +
                    "ORDER BY setNumber ASC"
    )
    List<WorkoutSetEntity> getSetsByExercise(
            int sessionId,
            String exerciseName
    );

}
