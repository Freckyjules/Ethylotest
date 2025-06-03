package com.example.ethylotest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

public class EditPersonActivity extends AppCompatActivity {

    // Déclaration des variables pour les éléments de l'interface
    private EditText weightEditText;

    private RadioButton manRadioButton;
    private RadioButton womanRadioButton;

    private RadioButton youngRadioButton;
    private RadioButton oldRadioButton;

    private Button okButton;

    // Déclaration des variables pour les SharedPreferences
    private SharedPreferences sharedPreferences;


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
        weightEditText = findViewById(R.id.weightEditText);

        // Initialiser les RadioButtons
        manRadioButton = findViewById(R.id.manRadioButton);
        womanRadioButton = findViewById(R.id.womanRadioButton);

        // Initialiser les RadioButtons pour le type de conducteur
        youngRadioButton = findViewById(R.id.youngRadioButton);
        oldRadioButton = findViewById(R.id.oldRadioButton);

        // Initialiser le bouton OK
        okButton = findViewById(R.id.okButton);

        // Initialiser les SharedPreferences
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);

        // Ajouter un listener pour le bouton OK
        okButton.setOnClickListener(v -> {

            // Créér une instance de la classe Person
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

            // Sauvegarder les données
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Gson gson = new Gson();
            String str = gson.toJson(person);
            editor.putString("person",str);

            editor.apply();

            // Quitter l'activité
            finish();
        });

        // Récupérer les données sauvegardées
        String str = sharedPreferences.getString("person", "");
        Gson gson = new Gson();
        Person person = gson.fromJson(str,Person.class);

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

    @Override
    protected void onPause() {
        super.onPause();

        // Créér une instance de la classe Person
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

        // Sauvegarder les données
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String str = gson.toJson(person);
        editor.putString("person",str);

        editor.apply();
    }
}