// WorkoutSet

package kr.ac.dankook.jeong.workoutapp;

public class WorkoutSet {

    public String exerciseName;
    public int weight;
    public int reps;
    public int setNumber;

    public boolean isCompleted = false;
    public boolean isResting = false;
    public int restSeconds = 60;

    public WorkoutSet() {
        this.exerciseName = "";
        this.weight = 0;
        this.reps = 0;
        this.setNumber = 1;
    }

    public WorkoutSet(String exerciseName, int weight, int reps, int setNumber) {
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.reps = reps;
        this.setNumber = setNumber;
    }
}
