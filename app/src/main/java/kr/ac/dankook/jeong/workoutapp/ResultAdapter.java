// ResultAdapter

package kr.ac.dankook.jeong.workoutapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private Context context;
    private ArrayList<WorkoutSummary> list;
    private int sessionId;

    public ResultAdapter(Context context,
                         ArrayList<WorkoutSummary> list,
                         int sessionId) {
        this.context = context;
        this.list = list;
        this.sessionId = sessionId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WorkoutSummary summary = list.get(position);
        holder.tvName.setText(summary.exerciseName);

        holder.layoutSets.removeAllViews();

        WorkoutResultDao dao =
                AppDatabase.getInstance(context).resultDao();

        List<WorkoutSetEntity> sets =
                dao.getSetsByExercise(
                        sessionId,
                        summary.exerciseName
                );

        for (WorkoutSetEntity set : sets) {
            TextView tv = new TextView(context);
            tv.setText(
                    set.setNumber + "세트: " +
                            set.weight + "kg × " +
                            set.reps + "회"
            );
            tv.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
            tv.setTextSize(14);
            tv.setTextSize(16);
            tv.setPadding(0, 4, 0, 4);
            holder.layoutSets.addView(tv);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        LinearLayout layoutSets;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            layoutSets = itemView.findViewById(R.id.layoutSets);
        }
    }
}
