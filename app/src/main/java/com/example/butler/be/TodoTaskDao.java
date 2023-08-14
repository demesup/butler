package com.example.butler.be;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.butler.be.model.TodoTask;

import java.util.List;

@Dao
public interface TodoTaskDao {

    @Insert
    void insert(TodoTask task);

    @Query("SELECT * FROM TodoTask")
    List<TodoTask> getAllTasks();

    @Query("UPDATE TodoTask SET task = :newTask WHERE id = :taskId")
    void updateTask(int taskId, String newTask);

    @Query("UPDATE TodoTask SET completed = :completed WHERE id = :taskId")
    void updateTask(int taskId, boolean completed);
    @Query("SELECT * FROM TodoTask WHERE id = :taskId")
    TodoTask findTaskById(long taskId);
    @Query("DELETE FROM TodoTask WHERE id = :id")
    void delete(int id);

    // Add other database operations here
}
