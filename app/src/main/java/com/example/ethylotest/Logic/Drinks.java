package com.example.ethylotest.Logic;

import java.io.Serializable;
import java.util.ArrayList;

import kotlin.experimental.ExperimentalObjCName;

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
     * @param index l'index de la boisson à supprimer
     */
    public void RemoveDrink(int index) {

        this.drinks.remove(index);

    }

    /**
     * Retourne la liste des boissons consommées.
     * @return la liste des boissons
     */
    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    /**
     * Définit la liste des boissons consommées.
     * @param drinks la nouvelle liste de boissons
     */
    public void setDrinks(ArrayList<Drink> drinks) {
        if (drinks != null) {
            this.drinks = drinks;
        } else {
            this.drinks = new ArrayList<>();
        }
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

    /**
     * Calcule le taux d'alcool total dans le sang d'une personne en fonction de ses boissons consommées.
     * @param person la personne pour laquelle calculer le taux d'alcool
     * @return le taux d'alcool total dans le sang
     */
    public double getTotalAlcohol(Person person) {
        if (person == null || person.getWeight() == null || person.getWeight() <= 0) {
            throw new IllegalArgumentException("Person or weight is invalid.");
        }

        double totalAlcohol = 0.0;
        double eliminationRate = 0.15; // g/l par heure
        double coefficient = person.getSexe() == Sexe.MAN ? 0.7 : 0.8; // Coefficient K

        for (int i = 0; i < drinks.size(); i++) {
            Drink drink = drinks.get(i);

            // Calcul du taux d'alcool pour la boisson
            double alcohol = (drink.getVolume() * 0.1 * drink.getPercentageAlcohol() * 0.8) / (coefficient * person.getWeight());

            // Calculer le temps écoulé entre la boisson actuelle et celle d'après
            long timeDifference;
            if (i < drinks.size() - 1) {
                Drink nextDrink = drinks.get(i + 1);
                timeDifference = nextDrink.getDate().getTime() - drink.getDate().getTime(); // Différence en millisecondes
            } else {
                timeDifference = System.currentTimeMillis() - drink.getDate().getTime(); // Utiliser la date actuelle
            }

            double hoursElapsed = timeDifference / (1000.0 * 60 * 60); // Convertir en heures

            // Réduire l'alcool en fonction du temps écoulé
            totalAlcohol -= eliminationRate * hoursElapsed;
            if (totalAlcohol < 0) {
                totalAlcohol = 0; // Le taux d'alcool ne peut pas être négatif
            }

            // Ajouter l'alcool de la boisson actuelle
            totalAlcohol += alcohol;
        }

        return totalAlcohol;
    }
}
