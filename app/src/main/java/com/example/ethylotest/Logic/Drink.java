package com.example.ethylotest.Logic;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Classe représentant une boisson.
 * Contient des informations sur le volume et le pourcentage d'alcool.
 */
public class Drink implements Serializable {
    /* Volume de la boisson en centilitres */
    private int volume;

    /* Pourcentage d'alcool dans la boisson */
    private double percentageAlcohol;

    /* Date de consommation de la boisson */
    private Date date;

    /* Nom de la boisson */
    private String name;

    /**
     * Constructeur de la classe Drink.
     * Initialise le volume, le pourcentage d'alcool, la date et le nom de la boisson.
     * @param volume le volume en centilitres
     * @param percentageAlcohol le pourcentage d'alcool
     * @param name le nom de la boisson
     */
    public Drink(int volume, double percentageAlcohol, String name) {
        setVolume(volume);
        setPercentageAlcohol(percentageAlcohol);
        setDate(new java.util.Date()); // Définit la date à la date actuelle
        setName(name);
    }

    /**
     * Définit le volume de la boisson.
     * @param volume le volume en centilitres
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * Retourne le volume de la boisson.
     * @return le volume en centilitres
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Définit le pourcentage d'alcool dans la boisson.
     * @param percentageAlcohol le pourcentage d'alcool
     */
    public void setPercentageAlcohol(double percentageAlcohol) {
        if (percentageAlcohol < 0 || percentageAlcohol > 100) {
            throw new IllegalArgumentException("Le pourcentage d'alcool doit être compris entre 0 et 100.");
        }
        this.percentageAlcohol = percentageAlcohol;
    }

    /**
     * Retourne le pourcentage d'alcool dans la boisson.
     * @return le pourcentage d'alcool
     */
    public double getPercentageAlcohol() {
        return percentageAlcohol;
    }

    /**
     * Définit la date de consommation de la boisson.
     * @param date la date de consommation
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Retourne la date de consommation de la boisson.
     * @return la date de consommation
     */
    public Date getDate() {
        return date;
    }

    /**
     * Définit le nom de la boisson.
     * @param name le nom de la boisson
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le nom de la boisson.
     * @return le nom de la boisson
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la boisson.
     * @return une chaîne de caractères représentant la boisson
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
        return name + " " + volume + "ml, " + percentageAlcohol + "%, " + dateFormat.format(date);
    }
}
