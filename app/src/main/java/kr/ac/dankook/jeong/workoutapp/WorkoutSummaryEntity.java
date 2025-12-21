// WorkoutSummaryEntity

package kr.ac.dankook.jeong.workoutapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_summary")
public class WorkoutSummaryEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int sessionId;
    public String exerciseName;

    public int setCount;
    public int totalReps;
    public int totalWeight;
}

