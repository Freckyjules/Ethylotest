package com.example.ethylotest.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ethylotest.Logic.Person;
import com.example.ethylotest.R;
import com.example.ethylotest.Logic.Sexe;
import com.example.ethylotest.Save.SavePerson;
import com.google.gson.Gson;

public class EditPersonActivity extends AppCompatActivity {

    // Déclaration des variables pour les éléments de l'interface
    private EditText weightEditText;

    // Déclaration des RadioButtons pour le sexe
    private RadioButton manRadioButton;
    private RadioButton womanRadioButton;

    // Déclaration des RadioButtons pour le type de conducteur
    private RadioButton youngRadioButton;
    private RadioButton oldRadioButton;

    // Déclaration du bouton OK
    private Button okButton;

    // Déclaration de l'instance de SavePerson pour la sauvegarde des données
    private SavePerson savePerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_person);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser le champ de texte pour le poids
        weightEditText = findViewById(R.id.volumeEditText);

        // Initialiser les RadioButtons
        manRadioButton = findViewById(R.id.manRadioButton);
        womanRadioButton = findViewById(R.id.womanRadioButton);

        // Initialiser les RadioButtons pour le type de conducteur
        youngRadioButton = findViewById(R.id.youngRadioButton);
        oldRadioButton = findViewById(R.id.oldRadioButton);

        // Initialiser le bouton OK
        okButton = findViewById(R.id.okButton);

        // Initialiser les SharedPreferences pour la sauvegarde des données
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        savePerson = new SavePerson(sharedPreferences);

        // Ajouter un listener pour le bouton OK
        okButton.setOnClickListener(v -> {
            Person person = VueToPerson();
            // Vérifier si un champ obligatoire est manquant
            if ((person.getSexe() == null) || (weightEditText.getText().toString().isEmpty()) || (youngRadioButton.isChecked() && oldRadioButton.isChecked())) {
                messageElementManquant();
            } else {
                // Sauvegarder les données de la personne
                savePerson.savePerson(person);

                // Quitter l'activité
                finish();
            }
        });

        // Récupérer les données sauvegardées
        Person person = savePerson.loadPerson();

        // Mettre à jour l'interface utilisateur avec les données de la personne
        PersonToVue(person);
    }

    /**
     * Affiche un message d'erreur si l'utilisateur n'a pas tout rempli les champs requis.
     */
    private void messageElementManquant() {
        Toast.makeText(this, "Veuillez remplir tous les champs requis.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Récupérer les données de la personne
        Person person = VueToPerson();

        // Sauvegarder les donnée
        savePerson.savePerson(person);
    }

    /**
     * Met à jour l'interface utilisateur avec les données de la personne.
     *
     * @param person L'objet Person contenant les données à afficher.
     */
    public void PersonToVue(Person person) {
        if (person != null) {
            weightEditText.setText(String.valueOf(person.getWeight()));
            if (person.getSexe() == Sexe.MAN) {
                manRadioButton.setChecked(true);
            } else {
                womanRadioButton.setChecked(true);
            }
            if (person.isYoung()) {
                youngRadioButton.setChecked(true);
            } else {
                oldRadioButton.setChecked(true);
            }
        }
    }

    /**
     * Récupère les données de l'interface utilisateur et les convertit en un objet Person.
     * @return Un objet Person contenant les données saisies par l'utilisateur.
     */
    public Person VueToPerson() {
        Person person = new Person();
        person.setWeight(Integer.parseInt(weightEditText.getText().toString()));
        if (manRadioButton.isChecked()) {
            person.setSexe(Sexe.MAN);
        } else if (womanRadioButton.isChecked()) {
            person.setSexe(Sexe.WOMAN);
        }
        if (youngRadioButton.isChecked()) {
            person.setIsYoung(true);
        } else if (oldRadioButton.isChecked()) {
            person.setIsYoung(false);
        }
        return person;
    }
}