package com.example.roomtestapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;


/*
* This is the class for the entire database
*
* Here you can Insert your tables and their relations between them and stuff
*
* e.g. Right now we've created a database called "AppDatabase" and inside it we have one table
* called "User" which we've defined as it's own different class
* */
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}