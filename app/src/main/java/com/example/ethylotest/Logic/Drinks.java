package com.example.ethylotest.Logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Drinks implements Serializable {
    /* Liste des boissons consommées */
    private ArrayList<Drink> drinks;

    /**
     * Constructeur de la classe TotalDrink.
     * Initialise la liste des boissons.
     */
    public Drinks() {
        this.drinks = new ArrayList<>();
    }

    /**
     * Ajoute une boisson à la liste des boissons consommées.
     * @param drink la boisson à ajouter
     */
    public void AddDrink(Drink drink) {
        if (drink != null) {
            this.drinks.add(drink);
        }
    }

    /**
     * Supprime une boisson de la liste des boissons consommées.
     * @param drink la boisson à supprimer
     */
    public void RemoveDrink(Drink drink) {
        if (drink != null) {
            this.drinks.remove(drink);
        }
    }

    /**
     * Retourne la liste des boissons consommées.
     * @return la liste des boissons
     */
    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Drink drink : drinks) {
            sb.append("Volume: ").append(drink.getVolume())
              .append(" cl, Alcool: ").append(drink.getPercentageAlcohol())
              .append("%, Date: ").append(drink.getDate()).append("\n");
        }
        return sb.toString();
    }


}
