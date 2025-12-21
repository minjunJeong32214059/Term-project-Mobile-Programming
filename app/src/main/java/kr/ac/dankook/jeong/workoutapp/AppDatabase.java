// AppDataBase

package kr.ac.dankook.jeong.workoutapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {
                WorkoutSessionEntity.class,
                WorkoutSummaryEntity.class,
                WorkoutSetEntity.class
        },
        version = 5
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract WorkoutResultDao resultDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "workout_result_db"
                    )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
