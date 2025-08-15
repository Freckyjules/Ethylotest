package com.example.ethylotest.Logic;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

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
     * Constructeur de copie pour créer une nouvelle boisson à partir d'une boisson existante.
     * @param drink la boisson à copier
     */
    public Drink(Drink drink) {
        this.volume = drink.volume;
        this.percentageAlcohol = drink.percentageAlcohol;
        this.date = drink.date;
        this.name = drink.name;
    }

    /**
     * Constructeur de la classe Drink.
     * @param volume le volume de la boisson en centilitres
     * @param percentageAlcohol le pourcentage d'alcool dans la boisson
     * @param name le nom de la boisson
     * @param date la date de consommation de la boisson
     */
    public Drink(int volume, double percentageAlcohol, String name, Date date) {
        setVolume(volume);
        setPercentageAlcohol(percentageAlcohol);
        setName(name);
        setDate(date);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Drink other = (Drink) obj;
        return volume == other.volume &&
                Double.compare(other.percentageAlcohol, percentageAlcohol) == 0 &&
                name.equals(other.name) &&
                date.equals(other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume, percentageAlcohol, name, date);
    }

}
