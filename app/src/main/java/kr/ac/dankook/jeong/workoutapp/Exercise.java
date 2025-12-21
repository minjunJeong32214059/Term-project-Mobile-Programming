// Exercise

package kr.ac.dankook.jeong.workoutapp;

public class Exercise {

    private String name;
    private String description;
    private String targetMuscle;
    private int imageResId;
    public Exercise(String name, String description, String targetMuscle, int imageResId) {
        this.name = name;
        this.description = description;
        this.targetMuscle = targetMuscle;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTargetMuscle() {
        return targetMuscle;
    }

    public int getImageResId() {
        return imageResId;
    }

}
