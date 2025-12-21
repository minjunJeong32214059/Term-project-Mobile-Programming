// SelectedExercises

package kr.ac.dankook.jeong.workoutapp;

import java.util.ArrayList;

public class SelectedExercises {

    private static ArrayList<Exercise> selectedList = new ArrayList<>();

    public static void addExercise(Exercise exercise) {
        for (Exercise e : selectedList) {
            if (e.getName().equals(exercise.getName())) {
                return;
            }
        }
        selectedList.add(exercise);
    }

    public static void removeExercise(Exercise exercise) {
        selectedList.remove(exercise);
    }


    public static ArrayList<Exercise> getExercises() {
        return selectedList;
    }


    public static void clear() {
        selectedList.clear();
    }
}
