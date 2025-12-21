// WorkoutSessionEntity

package kr.ac.dankook.jeong.workoutapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "workout_session")
public class WorkoutSessionEntity {

    @PrimaryKey(autoGenerate = true)
    public int sessionId;

    public String date;       // yyyy-MM-dd (운동 수행 날짜)
    public String startTime;  // HH:mm (운동 시작 시간)
    public String endTime;    // HH:mm (운동 종료 시간)

    public long totalDuration;

}
