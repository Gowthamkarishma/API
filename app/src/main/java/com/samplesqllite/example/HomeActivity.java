package com.samplesqllite.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class HomeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000", "ravi@gmail.com"));
        db.addContact(new Contact("Srinivas", "9199999999", "srinivas@gmail.com"));
        db.addContact(new Contact("Tommy", "9522222222", "tommy@gmail.com"));
        db.addContact(new Contact("Karthik", "9533333333", "karthick@gmail.com"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber() + " ,Mail: " + cn.get_email_id();
            // Writing Contacts to log
            Log.d("Name: ", log);

        }
    }
}