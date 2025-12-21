// SessionAdapter

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

public class SessionAdapter
        extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    Context context;
    List<WorkoutSessionEntity> sessions;

    public SessionAdapter(Context context, List<WorkoutSessionEntity> sessions) {
        this.context = context;
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_session, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {

        WorkoutSessionEntity session = sessions.get(position);

        String text =
                (position + 1) + "번째 운동 (" +
                        session.startTime + " ~ " +
                        session.endTime + ")";

        holder.tvTime.setText(text);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(
                    context,
                    WorkoutResultActivity.class
            );
            intent.putExtra("sessionId", session.sessionId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
