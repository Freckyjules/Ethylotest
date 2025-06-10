package com.example.ethylotest.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ethylotest.R;

public class AddDrinkActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText volumeEditText;
    private EditText alcoholEditText;
    private EditText dateEditText;

    private Button cancelButton;
    private Button okButton;

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
        dateEditText = findViewById(R.id.dateEditText);

        // Initialiser les boutons
        cancelButton = findViewById(R.id.cancelButton2);
        okButton = findViewById(R.id.okButton2);
        beerButton = findViewById(R.id.beerButton);
        wineButton = findViewById(R.id.wineButton);
        wiskeyButton = findViewById(R.id.whiskeyButton);

        // préparer la date actuelle
        // dateEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        // Configurer les actions des boutons
        beerButton.setOnClickListener(v -> {
            // Action pour le bouton Bière
            nameEditText.setText("Bière");
            volumeEditText.setText("33"); // Volume par défaut pour une bière
            alcoholEditText.setText("5"); // Taux d'alcool par défaut pour une bière
        });

        wineButton.setOnClickListener(v -> {
            // Action pour le bouton Vin
            nameEditText.setText("Vin");
            volumeEditText.setText("15"); // Volume par défaut pour un verre de vin
            alcoholEditText.setText("12"); // Taux d'alcool par défaut pour un verre de vin
        });

        wiskeyButton.setOnClickListener(v -> {
            // Action pour le bouton Whisky
            nameEditText.setText("Whisky");
            volumeEditText.setText("4"); // Volume par défaut pour un verre de whisky
            alcoholEditText.setText("40"); // Taux d'alcool par défaut pour un verre de whisky
        });


        cancelButton.setOnClickListener(v -> {
            // Action pour le bouton Annuler
            finish(); // Ferme l'activité actuelle
        });
    }
}