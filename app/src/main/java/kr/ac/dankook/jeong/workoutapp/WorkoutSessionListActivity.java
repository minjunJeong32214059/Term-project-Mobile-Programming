// WorkoutSessionListActivity

package kr.ac.dankook.jeong.workoutapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutSessionListActivity extends AppCompatActivity {

    RecyclerView rvSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        rvSessions = findViewById(R.id.rvSessions);

        String date = getIntent().getStringExtra("date");

        AppDatabase db = AppDatabase.getInstance(this);
        List<WorkoutSessionEntity> sessions =
                db.resultDao().getSessionsByDate(date);

        SessionAdapter adapter =
                new SessionAdapter(this, sessions);

        rvSessions.setLayoutManager(
                new LinearLayoutManager(this)
        );
        rvSessions.setAdapter(adapter);
    }
}
