// SelectedExercisesAdapter

package kr.ac.dankook.jeong.workoutapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class SelectedExercisesAdapter
        extends RecyclerView.Adapter<SelectedExercisesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Exercise> selectedList;

    public SelectedExercisesAdapter(Context context, ArrayList<Exercise> selectedList) {
        this.context = context;
        this.selectedList = selectedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_selected_exercise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise ex = selectedList.get(position);

        holder.tvName.setText(ex.getName());
        holder.tvTarget.setText("타겟 근육: " + ex.getTargetMuscle());

        holder.btnRemove.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {
                Exercise removed = selectedList.remove(pos);
                notifyItemRemoved(pos);

                SelectedExercises.removeExercise(removed);
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedList.size();
    }

    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition < 0 || toPosition < 0) return;

        Collections.swap(selectedList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvTarget, btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvTarget = itemView.findViewById(R.id.tvTarget);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
