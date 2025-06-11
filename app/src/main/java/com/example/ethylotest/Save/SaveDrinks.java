package com.example.ethylotest.Save;

import android.content.SharedPreferences;

import com.example.ethylotest.Logic.Drink;
import com.example.ethylotest.Logic.Drinks;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SaveDrinks {
    private SharedPreferences sharedPreferences;

    public SaveDrinks(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Sauvegarde les boissons dans les SharedPreferences.
     * @param totalDrink L'objet Drinks contenant la liste des boissons à sauvegarder.
     */
    private void saveDrinks(Drinks totalDrink) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String str = gson.toJson(totalDrink);
        editor.putString("totalDrink", str);
        editor.apply();
    }

    /**
     * Ajoute une boisson à la liste des boissons et la sauvegarde.
     * @param drink La boisson à ajouter.
     */
    public void saveOneDrink(Drink drink) {
        Drinks totalDrink = loadDrinks();
        totalDrink.AddDrink(drink);
        saveDrinks(totalDrink);
    }

    /**
     * Supprime une boisson de la liste des boissons et met à jour la sauvegarde.
     * @param index L'index de la boisson à supprimer.
     */
    public void saveOneRemoveDrink(int index) {
        Drinks totalDrink = loadDrinks();
        totalDrink.RemoveDrink(index);
        saveDrinks(totalDrink);
    }

    /**
     * Load les boissons depuis les SharedPreferences.
     * @return L'objet Drinks contenant la liste des boissons chargées.
     */
    public Drinks loadDrinks() {
        Gson gson = new Gson();
        String totalDrinkStr = sharedPreferences.getString("totalDrink", "");
        Drinks totalDrinkSave = gson.fromJson(totalDrinkStr, Drinks.class);
        if (totalDrinkSave == null) {
            totalDrinkSave = new Drinks();
        }
        return totalDrinkSave;
    }
}
