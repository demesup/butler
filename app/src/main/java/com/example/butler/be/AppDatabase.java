package com.example.butler.be;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.butler.be.model.TodoTask;

@Database(entities = {TodoTask.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TodoTaskDao todoTaskDao();
}
