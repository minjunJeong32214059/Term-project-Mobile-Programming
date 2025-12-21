// WorkoutSetEntity

package kr.ac.dankook.jeong.workoutapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_set")
public class WorkoutSetEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int sessionId;
    public String exerciseName;
    public int setNumber;
    public int weight;
    public int reps;
}
