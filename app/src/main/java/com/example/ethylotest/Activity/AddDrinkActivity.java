package com.example.ethylotest.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ethylotest.Logic.Drink;
import com.example.ethylotest.R;
import com.example.ethylotest.Save.SaveParty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDrinkActivity extends AppCompatActivity {

    // Déclaration des variables pour les éléments de l'interface
    private EditText nameEditText;
    private EditText volumeEditText;
    private EditText alcoholEditText;
    private EditText editTextTime;

    // Déclaration des boutons pour les actions
    private Button cancelButton;
    private Button okButton;

    // Déclaration des boutons pour les types de boissons
    private Button beerButton;
    private Button wineButton;
    private Button wiskeyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_drink);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser les TextViews
        nameEditText = findViewById(R.id.nameEditText);
        volumeEditText = findViewById(R.id.volumeEditText);
        alcoholEditText = findViewById(R.id.alcoholEditText);
        editTextTime = findViewById(R.id.editTextTime);

        // affiche la date de consommation
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        editTextTime.setText(sdf.format(new java.util.Date()));

        // Configurer le sélecteur de date et d'heure
        editTextTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            //calendar.setTime(oldDrink.getDate());

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddDrinkActivity.this, (view, year, month, dayOfMonth) -> {
                // Sélecteur d'heure
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddDrinkActivity.this, (timeView, hourOfDay, minute) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    Calendar now = Calendar.getInstance();
                    if (calendar.after(now)) {
                        new AlertDialog.Builder(AddDrinkActivity.this)
                                .setTitle(R.string.Error)
                                .setMessage(R.string.YouCannotSelectAFutureDateAndTime)
                                .setPositiveButton(R.string.Ok, null)
                                .show();
                        return;
                    }

                    editTextTime.setText(sdf.format(calendar.getTime()));

                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                timePickerDialog.show();

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        // Initialiser les boutons
        cancelButton = findViewById(R.id.cancelButton2);
        okButton = findViewById(R.id.okButton3);
        beerButton = findViewById(R.id.beerButton);
        wineButton = findViewById(R.id.wineButton);
        wiskeyButton = findViewById(R.id.whiskeyButton);

        // Initialise les sharedPreferences et la logique de sauvegarde
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SaveParty saveParty = new SaveParty(sharedPreferences);

        // Configurer les actions des boutons
        beerButton.setOnClickListener(v -> {
            // Action pour le bouton Bière
            nameEditText.setText(R.string.Beer);
            volumeEditText.setText("25"); // Volume par défaut pour une bière
            alcoholEditText.setText("6"); // Taux d'alcool par défaut pour une bière
        });

        wineButton.setOnClickListener(v -> {
            // Action pour le bouton Vin
            nameEditText.setText(R.string.Wine);
            volumeEditText.setText("12"); // Volume par défaut pour un verre de vin
            alcoholEditText.setText("13"); // Taux d'alcool par défaut pour un verre de vin
        });

        wiskeyButton.setOnClickListener(v -> {
            // Action pour le bouton Whisky
            nameEditText.setText(R.string.Whiskey);
            volumeEditText.setText("2"); // Volume par défaut pour un verre de whisky
            alcoholEditText.setText("40"); // Taux d'alcool par défaut pour un verre de whisky
        });


        cancelButton.setOnClickListener(v -> {
            // Action pour le bouton Annuler
            finish(); // Ferme l'activité actuelle
        });

        okButton.setOnClickListener(v -> {

            // Récupérer les valeurs des champs de saisie
            String name = nameEditText.getText().toString();
            String volumeStr = volumeEditText.getText().toString();
            String alcoholStr = alcoholEditText.getText().toString();
            Date date = null;
            try {
                date = sdf.parse(editTextTime.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // Vérifier que les champs ne sont pas vides
            if (name.isEmpty() || volumeStr.isEmpty() || alcoholStr.isEmpty()) {
                messageElementManquant(); // Afficher un message d'erreur
            } else {
                // Convertir les valeurs en types appropriés
                int volume = Integer.parseInt(volumeStr);
                double alcohol = Double.parseDouble(alcoholStr);

                // Créer un objet Drink et le sauvegarder
                Drink drink = new Drink(volume, alcohol, name, date);
                saveParty.saveOneDrink(drink);

                // Fermer l'activité après la sauvegarde
                finish();
            }
        });
    }

    /**
     * Affiche un message d'erreur si l'utilisateur n'a pas tout rempli les champs requis.
     */
    private void messageElementManquant() {
        Toast.makeText(this, "Veuillez remplir tous les champs requis.", Toast.LENGTH_SHORT).show();
    }
}