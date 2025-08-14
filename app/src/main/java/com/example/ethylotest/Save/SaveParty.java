package com.example.ethylotest.Save;

import android.content.SharedPreferences;

import com.example.ethylotest.Logic.Drink;
import com.example.ethylotest.Logic.Party;
import com.google.gson.Gson;

public class SaveParty {
    // Classe pour sauvegarder et charger les boissons
    private SharedPreferences sharedPreferences;

    /**
     * Constructeur de la classe SaveDrinks.
     * @param sharedPreferences Les SharedPreferences utilisées pour la sauvegarde des boissons.
     */
    public SaveParty(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Sauvegarde les boissons dans les SharedPreferences.
     * @param totalDrink L'objet Drinks contenant la liste des boissons à sauvegarder.
     */
    private void saveDrinks(Party totalDrink) {
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
        Party totalDrink = loadParty();
        totalDrink.AddDrink(drink);
        saveDrinks(totalDrink);
    }

    /**
     * Supprime une boisson spécifique de la liste des boissons et met à jour la sauvegarde.
     * @param drink La boisson à supprimer.
     */
    public void saveOneRemoveDrink(Drink drink) {
        Party totalDrink = loadParty();
        totalDrink.RemoveDrink(drink);
        saveDrinks(totalDrink);
    }

    /**
     * Modifie une boisson dans la liste des boissons et met à jour la sauvegarde.
     * @param oldDrink La boisson à modifier.
     * @param newDrink La nouvelle boisson.
     */
    public void saveOneEditDrink(Drink oldDrink, Drink newDrink) {
        Party totalDrink = loadParty();
        totalDrink.EditDrink(oldDrink, newDrink);
        saveDrinks(totalDrink);
    }

    /**
     * Load les boissons depuis les SharedPreferences.
     * @return L'objet Drinks contenant la liste des boissons chargées.
     */
    public Party loadParty() {
        Gson gson = new Gson();
        String totalDrinkStr = sharedPreferences.getString("totalDrink", "");
        Party totalDrinkSave = gson.fromJson(totalDrinkStr, Party.class);
        if (totalDrinkSave == null) {
            totalDrinkSave = new Party();
        }
        return totalDrinkSave;
    }
}
