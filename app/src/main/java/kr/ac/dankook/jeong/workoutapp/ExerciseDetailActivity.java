//ExerciseDetailActivity

package kr.ac.dankook.jeong.workoutapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDetailActivity extends AppCompatActivity {

    private TextView tvName, tvMuscle, tvDesc;
    private ImageView ivExercise;
    private Button btnAddExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        tvName = findViewById(R.id.tvName);
        tvMuscle = findViewById(R.id.tvMuscle);
        tvDesc = findViewById(R.id.tvDesc);
        ivExercise = findViewById(R.id.ivExercise);
        btnAddExercise = findViewById(R.id.btnAddExercise);

        String name = getIntent().getStringExtra("name");
        String desc = getIntent().getStringExtra("desc");
        String muscle = getIntent().getStringExtra("muscle");

        int imageResId = getIntent().getIntExtra(
                "imageResId",
                R.drawable.ic_launcher_foreground
        );

        tvName.setText(name);
        tvMuscle.setText("타겟 근육: " + muscle);
        tvDesc.setText(desc);
        ivExercise.setImageResource(imageResId);

        btnAddExercise.setOnClickListener(v -> {
            Exercise exercise = new Exercise(name, desc, muscle, imageResId);
            SelectedExercises.addExercise(exercise);

            btnAddExercise.setText("추가됨 ✓");
            btnAddExercise.setEnabled(false);
        });

    }
}