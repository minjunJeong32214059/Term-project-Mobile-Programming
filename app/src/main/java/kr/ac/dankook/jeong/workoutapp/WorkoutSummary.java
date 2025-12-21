// WorkoutSummary

package kr.ac.dankook.jeong.workoutapp;

public class WorkoutSummary {

    public String exerciseName;
    public int setCount;
    public int totalReps;
    public int totalWeight;

    public WorkoutSummary(String exerciseName, int setCount, int totalReps, int totalWeight) {
        this.exerciseName = exerciseName;
        this.setCount = setCount;
        this.totalReps = totalReps;
        this.totalWeight = totalWeight;
    }
}
