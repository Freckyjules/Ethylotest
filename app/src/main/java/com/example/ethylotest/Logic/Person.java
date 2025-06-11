package com.example.ethylotest.Logic;

import com.example.ethylotest.R;

import java.io.Serializable;

/**
 * Classe représentant une personne.
 * Implémente Serializable pour permettre la sauvegarde et le transfert d'objets.
 */
public class Person implements Serializable {

    /* Poids de la personne, exprimé en kilogrammes */
    private Integer weight;

    /* Sexe de la personne, défini à l'aide de l'énumération Sexe (HOMME ou FEMME) */
    private Sexe sexe;

    /* Indique si la personne est un jeune conducteur (true = jeune conducteur) */
    private boolean isYoung;

    /**
     * Définit le poids de la personne.
     * @param weight le poids en kilogrammes
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * Retourne le poids de la personne.
     * @return le poids en kilogrammes
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Définit le sexe de la personne.
     * @param sexe la valeur de l'énumération Sexe (HOMME ou FEMME)
     */
    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    /**
     * Retourne le sexe de la personne.
     * @return la valeur de l'énumération Sexe
     */
    public Sexe getSexe() {
        return sexe;
    }

    /**
     * Définit si la personne est un jeune conducteur.
     * @param isYoung true si jeune conducteur, false sinon
     */
    public void setIsYoung(boolean isYoung) {
        this.isYoung = isYoung;
    }

    /**
     * Indique si la personne est un jeune conducteur.
     * @return true si jeune conducteur, false sinon
     */
    public boolean isYoung() {
        return isYoung;
    }
}
