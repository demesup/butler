package com.example.butler.be.model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.butler.R;
import com.example.butler.be.TodoTaskDao;
import com.example.butler.ui.todo.NewTaskDialogFragment;
import com.example.butler.ui.todo.TodoFragment;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<TodoTask> todoList;
    private final TodoFragment todoFragment;
    private final TodoTaskDao dao;

    public TodoAdapter(TodoTaskDao dao, TodoFragment todoFragment) {
        this.todoFragment = todoFragment;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final TodoTask item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(item.isCompleted());
        holder.task.setOnCheckedChangeListener((buttonView, isChecked) ->
                dao.updateTask(item.getId(), isChecked));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public Context getContext() {
        return todoFragment.getContext();
    }

    public void setTasks(List<TodoTask> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        TodoTask item = todoList.get(position);
        dao.delete(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        TodoTask item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        NewTaskDialogFragment fragment = new NewTaskDialogFragment();
        fragment.setArguments(bundle);
        fragment.show(todoFragment.getChildFragmentManager(), "Test");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
}
