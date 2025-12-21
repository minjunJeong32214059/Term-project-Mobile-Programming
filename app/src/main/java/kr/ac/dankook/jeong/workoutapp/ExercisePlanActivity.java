// ExercisePlanActivity
package kr.ac.dankook.jeong.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;


import java.util.ArrayList;

public class ExercisePlanActivity extends AppCompatActivity {

    private RecyclerView rvSelected;
    private SelectedExercisesAdapter adapter;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_plan);

        rvSelected = findViewById(R.id.rvSelectedExercises);
        btnStart = findViewById(R.id.btnStart);

        ArrayList<Exercise> list = SelectedExercises.getExercises();

        adapter = new SelectedExercisesAdapter(this, list);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        rvSelected.setAdapter(adapter);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(ExercisePlanActivity.this, ExercisePerformActivity.class);
            startActivity(intent);
            finish();
        });


        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        0 //
                ) {
                    @Override
                    public boolean onMove(
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            RecyclerView.ViewHolder target) {

                        int fromPos = viewHolder.getAdapterPosition();
                        int toPos = target.getAdapterPosition();

                        adapter.moveItem(fromPos, toPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(
                            RecyclerView.ViewHolder viewHolder,
                            int direction) {
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rvSelected);


    }
}
