// ExerciseListActivity

package kr.ac.dankook.jeong.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseListActivity extends AppCompatActivity {

    RecyclerView rvExercises;
    ExerciseAdapter adapter;
    EditText etSearch;
    LinearLayout categoryLayout;
    Button btnGoPlan;

    ArrayList<Exercise> allExercises = new ArrayList<>();
    ArrayList<Exercise> filteredExercises = new ArrayList<>();

    String selectedCategory = "전체";
    String searchKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        rvExercises = findViewById(R.id.rvExercises);
        etSearch = findViewById(R.id.etSearch);
        categoryLayout = findViewById(R.id.layoutCategory);
        btnGoPlan = findViewById(R.id.btnGoPlan);

        allExercises.addAll(ExerciseData.getExercises());
        filteredExercises.addAll(allExercises);

        adapter = new ExerciseAdapter(this, filteredExercises);
        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchKeyword = s.toString().trim();
                applyFilter();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        etSearch.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchKeyword = etSearch.getText().toString().trim();
                applyFilter();
                return true;
            }
            return false;
        });

        for (int i = 0; i < categoryLayout.getChildCount(); i++) {
            View v = categoryLayout.getChildAt(i);
            if (v instanceof Button) {
                Button btn = (Button) v;
                btn.setOnClickListener(view -> {

                    for (int j = 0; j < categoryLayout.getChildCount(); j++) {
                        View other = categoryLayout.getChildAt(j);
                        if (other instanceof Button) {
                            other.setSelected(false);
                        }
                    }

                    btn.setSelected(true);

                    selectedCategory = btn.getTag().toString();
                    applyFilter();
                });
            }
        }
        for (int i = 0; i < categoryLayout.getChildCount(); i++) {
            View v = categoryLayout.getChildAt(i);
            if (v instanceof Button) {
                Button btn = (Button) v;
                if ("전체".equals(btn.getTag())) {
                    btn.setSelected(true);
                    break;
                }
            }
        }


        btnGoPlan.setOnClickListener(v -> {

            if (SelectedExercises.getExercises().isEmpty()) {
                Toast.makeText(
                        ExerciseListActivity.this,
                        "담은 운동이 없습니다",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent intent =
                    new Intent(
                            ExerciseListActivity.this,
                            ExercisePlanActivity.class
                    );
            startActivity(intent);
        });
    }

    private void applyFilter() {
        filteredExercises.clear();

        for (Exercise e : allExercises) {
            boolean categoryMatch =
                    selectedCategory.equals("전체") ||
                            e.getTargetMuscle().contains(selectedCategory);

            boolean searchMatch =
                    searchKeyword.isEmpty() ||
                            e.getName().contains(searchKeyword);

            if (categoryMatch && searchMatch) {
                filteredExercises.add(e);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
