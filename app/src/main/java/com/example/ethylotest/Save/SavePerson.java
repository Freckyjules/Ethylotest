package com.example.ethylotest.Save;

import com.example.ethylotest.Logic.Person;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SavePerson {
    private SharedPreferences sharedPreferences;

    public SavePerson(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void savePerson(Person person) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String str = gson.toJson(person);
        editor.putString("person",str);
        editor.apply();
    }

    public Person loadPerson() {
        String str = sharedPreferences.getString("person", "");
        Gson gson = new Gson();
        return gson.fromJson(str,Person.class);
    }
}
