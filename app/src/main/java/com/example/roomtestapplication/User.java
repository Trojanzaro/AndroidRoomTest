package com.example.roomtestapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

/*
* Here we've defined a database table called User
*
* This follows something that you would find in any standard ORM framework
* AKA all the columns of the database are defined here as well as their datatypes and
* The relationships of the data (eg. primary keys, foreign keys, etc)
*
* Anything the is defined here can be inserted in a database class like "AppDatabase"
* */
@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "age")
    public int age;

    User(String name, int age) {
        this.uid = UUID.randomUUID().toString();
        this.age = age;
        this.name = name;
    }
}