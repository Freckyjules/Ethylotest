package com.example.ethylotest.Save;

import com.example.ethylotest.Logic.Person;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SavePerson {

    // Classe pour sauvegarder et charger les informations d'une personne
    private SharedPreferences sharedPreferences;

    /**
     * Constructeur de la classe SavePerson.
     * @param sharedPreferences Les SharedPreferences utilisées pour la sauvegarde.
     */
    public SavePerson(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Sauvegarde les informations d'une personne dans les SharedPreferences.
     * @param person L'objet Person à sauvegarder.
     */
    public void savePerson(Person person) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String str = gson.toJson(person);
        editor.putString("person",str);
        editor.apply();
    }

    /**
     * Charge les informations d'une personne depuis les SharedPreferences.
     * @return L'objet Person chargé, ou null si aucune donnée n'est trouvée.
     */
    public Person loadPerson() {
        String str = sharedPreferences.getString("person", "");
        Gson gson = new Gson();
        return gson.fromJson(str,Person.class);
    }
}
