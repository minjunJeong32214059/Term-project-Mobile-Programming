//SetAdapter

package kr.ac.dankook.jeong.workoutapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {

    public interface OnSetCompleteListener {
        void onSetCompleted();
    }

    private OnSetCompleteListener listener;
    private final Context context;
    private final ArrayList<WorkoutSet> setList;

    public SetAdapter(Context context, ArrayList<WorkoutSet> setList) {
        this.context = context;
        this.setList = setList;
    }

    public void setOnSetCompleteListener(OnSetCompleteListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_set, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutSet set = setList.get(position);

        holder.tvSetNumber.setText((position + 1) + " 세트");

        if (holder.weightWatcher != null)
            holder.etWeight.removeTextChangedListener(holder.weightWatcher);
        if (holder.repsWatcher != null)
            holder.etReps.removeTextChangedListener(holder.repsWatcher);

        holder.etWeight.setText(set.weight == 0 ? "" : String.valueOf(set.weight));
        holder.etReps.setText(set.reps == 0 ? "" : String.valueOf(set.reps));

        holder.btnComplete.setText(set.isCompleted ? "✔" : "완료");
        holder.etWeight.setEnabled(!set.isCompleted);
        holder.etReps.setEnabled(!set.isCompleted);

        holder.weightWatcher = new SimpleWatcher(s -> {
            try { set.weight = Integer.parseInt(s); } catch (Exception e) {}
        });

        holder.repsWatcher = new SimpleWatcher(s -> {
            try { set.reps = Integer.parseInt(s); } catch (Exception e) {}
        });

        holder.etWeight.addTextChangedListener(holder.weightWatcher);
        holder.etReps.addTextChangedListener(holder.repsWatcher);

        holder.btnComplete.setOnClickListener(v -> {
            if (set.isCompleted) return;

            set.isCompleted = true;
            notifyItemChanged(position);

            if (listener != null) listener.onSetCompleted();
        });

        holder.btnDelete.setOnClickListener(v -> {
            setList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, setList.size());
        });
    }

    @Override
    public int getItemCount() {
        return setList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSetNumber, btnDelete;
        EditText etWeight, etReps;
        Button btnComplete;

        TextWatcher weightWatcher, repsWatcher;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSetNumber = itemView.findViewById(R.id.tvSetNumber);
            etWeight = itemView.findViewById(R.id.etWeight);
            etReps = itemView.findViewById(R.id.etReps);
            btnComplete = itemView.findViewById(R.id.btnComplete);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    static class SimpleWatcher implements TextWatcher {
        interface Callback { void onChange(String s); }
        Callback cb;
        SimpleWatcher(Callback cb) { this.cb = cb; }

        @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
        @Override public void onTextChanged(CharSequence s, int a, int b, int c) {
            cb.onChange(s.toString());
        }
        @Override public void afterTextChanged(Editable s) {}
    }
}
