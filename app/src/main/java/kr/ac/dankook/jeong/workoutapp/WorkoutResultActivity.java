// WorkoutResultActivity

package kr.ac.dankook.jeong.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutResultActivity extends AppCompatActivity {

    RecyclerView rvResult;
    TextView tvTotalSets, tvTotalReps, tvTotalWeight, tvTotalTime;
    Button btnGoHome;

    ArrayList<WorkoutSummary> summaryList = new ArrayList<>();

    AppDatabase db;
    int sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        sessionId = getIntent().getIntExtra("sessionId", -1);
        if (sessionId == -1) {
            finish();
            return;
        }


        db = AppDatabase.getInstance(this);


        rvResult = findViewById(R.id.rvResult);
        tvTotalSets = findViewById(R.id.tvTotalSets);
        tvTotalReps = findViewById(R.id.tvTotalReps);
        tvTotalWeight = findViewById(R.id.tvTotalWeight);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        btnGoHome = findViewById(R.id.btnGoHome);


        WorkoutSessionEntity session =
                db.resultDao().getSessionById(sessionId);

        if (session != null) {
            tvTotalTime.setText(
                    "총 운동 시간: " + formatTime(session.totalDuration)
            );
        }


        makeSummaryList();
        if (!summaryList.isEmpty()) {
            saveResultToDB();
        }
        updateSessionEndTime();
        loadResultFromSession();

        WorkoutHistory.completedWorkouts.clear();
        SelectedExercises.clear();

        btnGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void makeSummaryList() {
        summaryList.clear();

        for (WorkoutSet set : WorkoutHistory.completedWorkouts) {
            boolean found = false;

            for (WorkoutSummary s : summaryList) {
                if (s.exerciseName.equals(set.exerciseName)) {
                    s.setCount++;
                    s.totalReps += set.reps;
                    s.totalWeight += set.weight * set.reps;
                    found = true;
                    break;
                }
            }

            if (!found) {
                summaryList.add(new WorkoutSummary(
                        set.exerciseName,
                        1,
                        set.reps,
                        set.weight * set.reps
                ));
            }
        }
    }

    private void saveResultToDB() {
        WorkoutResultDao dao = db.resultDao();

        for (WorkoutSummary s : summaryList) {
            WorkoutSummaryEntity e = new WorkoutSummaryEntity();
            e.sessionId = sessionId;
            e.exerciseName = s.exerciseName;
            e.setCount = s.setCount;
            e.totalReps = s.totalReps;
            e.totalWeight = s.totalWeight;
            dao.insertSummary(e);
        }
    }

    private void loadResultFromSession() {
        List<WorkoutSummaryEntity> list =
                db.resultDao().getSummariesBySession(sessionId);

        summaryList.clear();

        int totalSets = 0;
        int totalReps = 0;
        int totalWeight = 0;

        for (WorkoutSummaryEntity e : list) {
            summaryList.add(new WorkoutSummary(
                    e.exerciseName,
                    e.setCount,
                    e.totalReps,
                    e.totalWeight
            ));

            totalSets += e.setCount;
            totalReps += e.totalReps;
            totalWeight += e.totalWeight;
        }

        tvTotalSets.setText("총 세트: " + totalSets + "세트");
        tvTotalReps.setText("총 반복수: " + totalReps + "회");
        tvTotalWeight.setText("총 중량: " + totalWeight + "kg");

        rvResult.setLayoutManager(new LinearLayoutManager(this));
        rvResult.setAdapter(
                new ResultAdapter(this, summaryList, sessionId)
        );
    }

    private void updateSessionEndTime() {
        String endTime = new SimpleDateFormat(
                "HH:mm", Locale.getDefault()
        ).format(new Date());

        db.resultDao().updateSessionEndTime(sessionId, endTime);
    }

    private String formatTime(long millis) {
        int totalSeconds = (int) (millis / 1000);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}
