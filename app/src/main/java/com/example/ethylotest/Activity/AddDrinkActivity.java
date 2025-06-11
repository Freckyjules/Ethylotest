package com.example.ethylotest.Activity;

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
import com.example.ethylotest.Save.SaveDrinks;

public class AddDrinkActivity extends AppCompatActivity {

    // Déclaration des variables pour les éléments de l'interface
    private EditText nameEditText;
    private EditText volumeEditText;
    private EditText alcoholEditText;

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

        // Initialiser les boutons
        cancelButton = findViewById(R.id.cancelButton2);
        okButton = findViewById(R.id.okButton2);
        beerButton = findViewById(R.id.beerButton);
        wineButton = findViewById(R.id.wineButton);
        wiskeyButton = findViewById(R.id.whiskeyButton);

        // Initialise les sharedPreferences et la logique de sauvegarde
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SaveDrinks saveDrinks = new SaveDrinks(sharedPreferences);

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

            // Vérifier que les champs ne sont pas vides
            if (name.isEmpty() || volumeStr.isEmpty() || alcoholStr.isEmpty()) {
                messageElementManquant(); // Afficher un message d'erreur
            } else {
                // Convertir les valeurs en types appropriés
                int volume = Integer.parseInt(volumeStr);
                double alcohol = Double.parseDouble(alcoholStr);

                // Créer un objet Drink et le sauvegarder
                Drink drink = new Drink(volume, alcohol, name);
                saveDrinks.saveOneDrink(drink);

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