// GeminiBottomSheet
package kr.ac.dankook.jeong.workoutapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GeminiBottomSheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(
                R.layout.bottomsheet_gemini,
                container,
                false
        );

        EditText etQuestion = v.findViewById(R.id.etQuestion);
        TextView tvAnswer = v.findViewById(R.id.tvAnswer);
        Button btnAsk = v.findViewById(R.id.btnAsk);
        Button btnClose = v.findViewById(R.id.btnClose);

        btnAsk.setOnClickListener(view -> {
            String question = etQuestion.getText().toString().trim();

            if (question.isEmpty()) {
                tvAnswer.setText("질문을 입력해주세요.");
                return;
            }

            tvAnswer.setText("Gemini 응답 생성 중... ");

            new Thread(() -> {
                try {
                    String answer = GeminiApi.ask(question);

                    requireActivity().runOnUiThread(() ->
                            tvAnswer.setText(answer)
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                    requireActivity().runOnUiThread(() ->
                            tvAnswer.setText("오류가 발생했습니다 😥")
                    );
                }
            }).start();
        });

        btnClose.setOnClickListener(view -> dismiss());

        return v;
    }
}
