// WorkoutHistoryActivity

package kr.ac.dankook.jeong.workoutapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutHistoryActivity extends AppCompatActivity {

    RecyclerView rvDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        rvDates = findViewById(R.id.rvDates);

        List<String> dateList =
                AppDatabase.getInstance(this)
                        .resultDao()
                        .getAllDates();

        if (dateList == null || dateList.isEmpty()) {
            Toast.makeText(this, "저장된 운동 기록이 없습니다", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        HistoryDateAdapter adapter =
                new HistoryDateAdapter(this, dateList);

        rvDates.setLayoutManager(new LinearLayoutManager(this));
        rvDates.setAdapter(adapter);
    }
}
