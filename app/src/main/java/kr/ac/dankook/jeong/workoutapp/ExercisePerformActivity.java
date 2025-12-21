// ExercisePerformActivity
package kr.ac.dankook.jeong.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ExercisePerformActivity extends AppCompatActivity {

    TextView tvExerciseName, tvTimer, tvRestTimer, tvEmpty;
    RecyclerView rvSets;
    Button btnAddSet, btnNext, btnPause;
    Button btnRestMinus, btnRestPlus, btnGemini;
    LinearLayout layoutRest;

    ArrayList<Exercise> exerciseList;
    ArrayList<WorkoutSet> setList = new ArrayList<>();
    SetAdapter adapter;

    int currentIndex = 0;
    int currentSessionId;

    AppDatabase db;
    WorkoutResultDao dao;

    long startMillis;
    long pausedMillis = 0;
    boolean isPaused = false;

    Handler workoutHandler = new Handler();
    Runnable workoutRunnable;

    int restSeconds = 60;
    Handler restHandler = new Handler();
    Runnable restRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_perform);

        tvExerciseName = findViewById(R.id.tvExerciseName);
        tvTimer = findViewById(R.id.tvTimer);
        tvRestTimer = findViewById(R.id.tvRestTimer);
        tvEmpty = findViewById(R.id.tvEmpty);

        layoutRest = findViewById(R.id.layoutRest);

        btnRestMinus = findViewById(R.id.btnRestMinus);
        btnRestPlus = findViewById(R.id.btnRestPlus);
        btnPause = findViewById(R.id.btnPause);

        btnPause.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_pause, 0, 0, 0
        );
        btnGemini = findViewById(R.id.btnGemini);

        rvSets = findViewById(R.id.rvSets);
        btnAddSet = findViewById(R.id.btnAddSet);
        btnNext = findViewById(R.id.btnNext);

        exerciseList = SelectedExercises.getExercises();
        if (exerciseList == null || exerciseList.isEmpty()) {
            finish();
            return;
        }

        db = AppDatabase.getInstance(this);
        dao = db.resultDao();

        WorkoutSessionEntity session = new WorkoutSessionEntity();
        session.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        session.startTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        currentSessionId = (int) dao.insertSession(session);

        adapter = new SetAdapter(this, setList);
        rvSets.setLayoutManager(new LinearLayoutManager(this));
        rvSets.setAdapter(adapter);

        updateEmptyState();

        adapter.setOnSetCompleteListener(this::startRestTimer);

        loadExercise();


        btnRestPlus.setOnClickListener(v -> {
            restSeconds += 5;
            tvRestTimer.setText("휴식 " + restSeconds + "초");
        });

        btnRestMinus.setOnClickListener(v -> {
            if (restSeconds > 5) {
                restSeconds -= 5;
                tvRestTimer.setText("휴식 " + restSeconds + "초");
            }
        });


        btnGemini.setOnClickListener(v -> {
            GeminiBottomSheet sheet = new GeminiBottomSheet();
            sheet.show(getSupportFragmentManager(), "GeminiSheet");
        });


        btnPause.setOnClickListener(v -> {
            if (!isPaused) {
                isPaused = true;
                pausedMillis = System.currentTimeMillis();
                btnPause.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_play, 0, 0, 0
                );
            } else {
                isPaused = false;
                long pauseDuration = System.currentTimeMillis() - pausedMillis;
                startMillis += pauseDuration;
                btnPause.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_pause, 0, 0, 0
                );
            }
        });



        startMillis = System.currentTimeMillis();
        workoutRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused) {
                    long elapsed = System.currentTimeMillis() - startMillis;
                    tvTimer.setText(formatTime(elapsed));
                }
                workoutHandler.postDelayed(this, 1000);
            }
        };
        workoutHandler.post(workoutRunnable);


        btnAddSet.setOnClickListener(v -> {
            setList.add(new WorkoutSet());
            adapter.notifyItemInserted(setList.size() - 1);
            updateEmptyState();
        });


        btnNext.setOnClickListener(v -> {
            Exercise cur = exerciseList.get(currentIndex);

            int setNumber = 1;
            for (WorkoutSet set : setList) {
                set.exerciseName = cur.getName();
                WorkoutHistory.completedWorkouts.add(set);

                WorkoutSetEntity e = new WorkoutSetEntity();
                e.sessionId = currentSessionId;
                e.exerciseName = cur.getName();
                e.setNumber = setNumber++;
                e.weight = set.weight;
                e.reps = set.reps;

                dao.insertSet(e);
            }

            currentIndex++;

            if (currentIndex >= exerciseList.size()) {
                workoutHandler.removeCallbacks(workoutRunnable);
                restHandler.removeCallbacks(restRunnable);

                long totalDuration = System.currentTimeMillis() - startMillis;
                dao.updateSessionDuration(currentSessionId, totalDuration);
                dao.updateSessionEndTime(
                        currentSessionId,
                        new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date())
                );

                Intent intent = new Intent(this, WorkoutResultActivity.class);
                intent.putExtra("sessionId", currentSessionId);
                startActivity(intent);
                finish();

            } else {
                setList.clear();
                adapter.notifyDataSetChanged();
                updateEmptyState();
                loadExercise();
            }
        });
    }

    private void loadExercise() {
        tvExerciseName.setText(exerciseList.get(currentIndex).getName());
    }


    private void updateEmptyState() {
        if (setList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvSets.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvSets.setVisibility(View.VISIBLE);
        }
    }


    private void startRestTimer() {
        restHandler.removeCallbacks(restRunnable);
        restSeconds = 60;
        layoutRest.setVisibility(View.VISIBLE);

        restRunnable = new Runnable() {
            @Override
            public void run() {
                if (isPaused) {
                    restHandler.postDelayed(this, 1000);
                    return;
                }

                tvRestTimer.setText("휴식 " + restSeconds + "초");

                if (restSeconds <= 0) {
                    layoutRest.setVisibility(View.GONE);
                    Toast.makeText(
                            ExercisePerformActivity.this,
                            "휴식 시간이 끝났습니다!",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                restSeconds--;
                restHandler.postDelayed(this, 1000);
            }
        };

        restHandler.post(restRunnable);
    }

    private String formatTime(long millis) {
        int sec = (int) (millis / 1000);
        return String.format(Locale.getDefault(), "%02d:%02d", sec / 60, sec % 60);
    }
}
