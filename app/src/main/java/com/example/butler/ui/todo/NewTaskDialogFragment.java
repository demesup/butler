package com.example.butler.ui.todo;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.butler.MainActivity;
import com.example.butler.R;
import com.example.butler.be.TodoTaskDao;
import com.example.butler.be.model.TodoTask;
import com.example.butler.ui.DialogCloseListener;

public class NewTaskDialogFragment extends DialogFragment {
    private TodoTaskDao db;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_task, null);
        builder.setView(view);
        db = MainActivity.getAppDatabase().todoTaskDao();

        manageDialog(view);

        return builder.create();
    }

    private void manageDialog(View view) {
        Button saveButton = getSaveButton(view);
        EditText editText = getEditText(view);

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        isUpdate = controlButtonColor(saveButton, editText, isUpdate, bundle);

        TextWatcher textWatcher = createTextWatcher(saveButton);
        editText.addTextChangedListener(textWatcher);

        final boolean finalIsUpdate = isUpdate;
        saveButton.setOnClickListener(v -> {
            String text = editText.getText().toString();
            if (finalIsUpdate) {
                db.updateTask(bundle.getInt("id"), text);
            } else {
                TodoTask task = TodoTask.create(text);
                db.insert(task);
            }
            dismiss();
        });
    }

    private TextWatcher createTextWatcher(Button saveButton) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    saveButton.setEnabled(false);
                    saveButton.setTextColor(Color.GRAY);
                } else {
                    saveButton.setEnabled(true);
                    saveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    private boolean controlButtonColor(Button saveButton, EditText editText, boolean isUpdate, Bundle bundle) {
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            editText.setText(task);
            assert task != null;
            if (task.length() > 0)
                saveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200));
        }
        return isUpdate;
    }

    private static EditText getEditText(View view) {
        return view.findViewById(R.id.newTaskText);
    }

    @NonNull
    private static Button getSaveButton(View view) {
        Button saveButton = view.findViewById(R.id.addTaskButton);
        saveButton.setEnabled(false);
        saveButton.setTextColor(Color.GRAY);
        return saveButton;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener)
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        TodoFragment.loadTasks();
    }
}
