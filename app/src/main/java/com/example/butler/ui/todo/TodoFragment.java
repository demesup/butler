package com.example.butler.ui.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.butler.MainActivity;
import com.example.butler.R;
import com.example.butler.be.TodoTaskDao;
import com.example.butler.be.model.TodoTask;
import com.example.butler.databinding.FragmentTodoBinding;
import com.example.butler.ui.DialogCloseListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class TodoFragment extends Fragment implements DialogCloseListener {

    private FragmentTodoBinding binding;
    private static TodoAdapter adapter;
    private static TodoTaskDao dao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodoBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        dao = MainActivity.getAppDatabase().todoTaskDao();

        RecyclerView recyclerView = binding.todoContent;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TodoAdapter(dao, TodoFragment.this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        loadTasks();
        FloatingActionButton addTodoButton = rootView.findViewById(R.id.add_todo_item_btn);
        addTodoButton.setOnClickListener(this::openNewTaskDialog);

        return rootView;
    }

    public static void loadTasks() {
        List<TodoTask> todoTasks = dao.getAllTasks();
        Collections.reverse(todoTasks);
        adapter.setTasks(todoTasks);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        loadTasks();
    }

    public void openNewTaskDialog(View view) {
        NewTaskDialogFragment dialogFragment = new NewTaskDialogFragment();
        dialogFragment.show(getChildFragmentManager(), "NewTaskDialogFragment");

    }
}
