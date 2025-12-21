// HistoryDateAdapter
package kr.ac.dankook.jeong.workoutapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryDateAdapter
        extends RecyclerView.Adapter<HistoryDateAdapter.ViewHolder> {

    Context context;
    List<String> dates;

    public HistoryDateAdapter(Context context, List<String> dates) {
        this.context = context;
        this.dates = dates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_history_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date = dates.get(position);
        holder.tvDate.setText(date);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(
                    context,
                    WorkoutSessionListActivity.class
            );
            intent.putExtra("date", date);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
