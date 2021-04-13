package com.example.roomtestapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText eTextName;
    private EditText eTextAge;
    private Button btnInsert;
    private Button btnRetrieve;
    private TextView tViewEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTextAge = findViewById(R.id.eTextAge);
        eTextName = findViewById(R.id.eTextName);
        btnInsert = findViewById(R.id.btnInsert);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        tViewEntries = findViewById(R.id.tViewEntries);


        //Once we click on the insert button
        btnInsert.setOnClickListener(v -> {

            //First we retrieve the data from the view
            String name = eTextName.length() > 0 ? eTextName.getText().toString() : "";
            int age = eTextAge.getText().length() > 0 ? Integer.parseInt(eTextAge.getText().toString()) : 0;

            //Then we initiate the database
            //Think of this as the main database connector
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "AppDatabase").build();

            //Then create the Database Access Object for the entries that we want to access
            //in this case we only have one table and we've defined only one DAO for it
            //The DAO contains all the methods of things that you can do with the object created from it's class
            //eg. inserting, selecting, updating data
            UserDao userDao = db.userDao();

            //Then we create an object that will be inserted in the table "User" of the database
            User user = new User(name, age);
            eTextAge.setText("");
            eTextName.setText("");

            new Thread() {
                @Override
                public void run(){
                    //Yeah, we need to run this in a separate thread because Android doesn't like it
                    //when you lock the Main UI Thread :/
                    userDao.insertAll(user);
                }
            }.start();
            db.close();
        });

        btnRetrieve.setOnClickListener(v -> {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "AppDatabase").build();
            UserDao userDao = db.userDao();
            new Thread() {
                @Override
                public void run(){
                    tViewEntries.setText("");
                    List<User> retrievedUsers = userDao.getAll();
                    if(retrievedUsers.size() > 0) {
                        for(User u : retrievedUsers)
                            tViewEntries.append("UID: " + u.uid + "\nName: " + u.name + "\nAge: " + u.age + "\n\n");
                    } else {
                        tViewEntries.setText("No users Found!");
                    }
                }
            }.start();
            db.close();
        });
    }
}