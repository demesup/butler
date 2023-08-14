package com.example.butler.be.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TodoTask {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String task;
    private boolean completed = false;

    public TodoTask(String task, boolean completed) {
        this.task = task;
        this.completed = completed;
    }

    public TodoTask(int id, String task, boolean completed) {
        this.id = id;
        this.task = task;
        this.completed = completed;
    }

    public TodoTask() {

    }

    public static TodoTask create(String task) {
        return new TodoTask(task, false);
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
