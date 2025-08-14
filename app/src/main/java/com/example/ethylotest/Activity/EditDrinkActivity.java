package com.example.ethylotest.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditDrinkActivity extends AppCompatActivity {

    // Déclaration des variables pour les éléments de l'interface
    private EditText nameEditText;
    private EditText volumeEditText;
    private EditText alcoholEditText;
    private EditText editTextTime;

    // Déclaration des boutons pour les actions
    private Button cancelButton;
    private Button okButton;
    private Button deleteButton;

    // Déclaration de l'instance de SaveParty pour la sauvegarde des données
    private SaveParty saveParty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_drink);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Récupérer les données de l'intent
        Drink oldDrink = (Drink) getIntent().getSerializableExtra("drink");
        if (oldDrink == null){
            new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Aucune boisson à éditer.")
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
        }
        else {
            // Initialiser les TextViews
            nameEditText = findViewById(R.id.nameEditText);
            volumeEditText = findViewById(R.id.volumeEditText);
            alcoholEditText = findViewById(R.id.alcoholEditText);
            editTextTime = findViewById(R.id.editTextTime);

            // Remplir les TextViews avec les données de la boisson
            nameEditText.setText(oldDrink.getName());
            volumeEditText.setText(String.valueOf(oldDrink.getVolume()));
            alcoholEditText.setText(String.valueOf(oldDrink.getPercentageAlcohol()));

            // affiche la date de consommation
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            editTextTime.setText(sdf.format(oldDrink.getDate()));

            // copie de la boisson pour la sauvegarde
            Drink drinkCopy = new Drink(oldDrink);

            // Configurer le sélecteur de date et d'heure
            editTextTime.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(oldDrink.getDate());

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDrinkActivity.this, (view, year, month, dayOfMonth) -> {
                    // Sélecteur d'heure
                    TimePickerDialog timePickerDialog = new TimePickerDialog(EditDrinkActivity.this, (timeView, hourOfDay, minute) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        Calendar now = Calendar.getInstance();
                        if (calendar.after(now)) {
                            new AlertDialog.Builder(EditDrinkActivity.this)
                                    .setTitle(R.string.Error)
                                    .setMessage(R.string.YouCannotSelectAFutureDateAndTime)
                                    .setPositiveButton(R.string.Ok, null)
                                    .show();
                            return;
                        }

                        drinkCopy.setDate(calendar.getTime());
                        editTextTime.setText(sdf.format(calendar.getTime()));

                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                    timePickerDialog.show();

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            });


            // Initialiser les boutons
            cancelButton = findViewById(R.id.cancelButton3);
            okButton = findViewById(R.id.okButton3);
            deleteButton = findViewById(R.id.deleteButton);

            // Initialiser les SharedPreferences pour la sauvegarde des données
            saveParty = new SaveParty(getSharedPreferences("UserPreferences", MODE_PRIVATE));

            // Configurer les actions des boutons
            cancelButton.setOnClickListener(v -> {
                // Action pour le bouton Annuler
                finish(); // Ferme l'activité actuelle
            });

            // Supprimer la boisson
            deleteButton.setOnClickListener(v -> {
                saveParty.saveOneRemoveDrink(oldDrink);
                finish(); // Ferme l'activité actuelle
            });

            // Appliquer les modifications à la boisson
            okButton.setOnClickListener(v -> {
                // Récupérer les valeurs des champs de saisie
                String name = nameEditText.getText().toString();
                String volumeStr = volumeEditText.getText().toString();
                String alcoholStr = alcoholEditText.getText().toString();
                // le champ de date est déjà mis à jour dans l'éditeur de texte

                // Vérifier que les champs ne sont pas vides
                if (name.isEmpty() || volumeStr.isEmpty() || alcoholStr.isEmpty()) {
                    Toast.makeText(this, "Veuillez remplir tous les champs requis.", Toast.LENGTH_SHORT).show();
                } else {
                    // Convertir les valeurs en types appropriés
                    int volume = Integer.parseInt(volumeStr);
                    double alcohol = Double.parseDouble(alcoholStr);

                    // Mettre à jour l'objet Drink avec les nouvelles valeurs
                    drinkCopy.setName(name);
                    drinkCopy.setVolume(volume);
                    drinkCopy.setPercentageAlcohol(alcohol);

                    // Sauvegarder la boisson modifiée
                    saveParty.saveOneEditDrink(oldDrink, drinkCopy);

                    // Fermer l'activité
                    finish();
                }
            });







        }
    }
}