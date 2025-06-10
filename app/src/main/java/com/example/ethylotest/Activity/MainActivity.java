package com.example.ethylotest.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ethylotest.Logic.Drink;
import com.example.ethylotest.Logic.Person;
import com.example.ethylotest.R;
import com.example.ethylotest.Logic.Drinks;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button editPerson;
    private Button addDrinkButton;

    private ListView beerListView;

    private TextView testText;
    private TextView testText2;

    private SharedPreferences sharedPreferences;

    private Drinks totalDrink;

    private ArrayList<String> beerList;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser les SharedPreferences
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);

        totalDrink = new Drinks();

        // Initialiser le TextView
        testText = findViewById(R.id.testText);
        testText2 = findViewById(R.id.testText2);

        // Initialiser le ListView
        beerList = new ArrayList<>();

        beerListView = findViewById(R.id.beerListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, beerList);
        beerListView.setAdapter(adapter);

        editPerson = findViewById(R.id.editPerson);
        editPerson.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditPersonActivity.class);
            startActivity(intent);
        });

        addDrinkButton = findViewById(R.id.addDrinkButton);
        addDrinkButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddDrinkActivity.class);
            startActivity(intent);
        });
        /*beerButton.setOnClickListener(v -> {

            Drink drink = new Drink();
            drink.setVolume(25);
            drink.setPercentageAlcohol(6.0);
            drink.setDate(new java.util.Date());
            drink.setName("Bière");

            totalDrink.AddDrink(drink);

            // Sauvegarder les données
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Gson gson = new Gson();
            String str = gson.toJson(totalDrink);
            editor.putString("totalDrink", str);

            editor.apply();

            updateTotalDrinkDisplay();
        });*/
    }

    protected void updateTotalDrinkDisplay() {
        // Récupérer les données de TotalDrink
        Gson gson = new Gson();
        String totalDrinkStr = sharedPreferences.getString("totalDrink", "");
        Drinks totalDrinkSave = gson.fromJson(totalDrinkStr, Drinks.class);

        if (totalDrinkSave != null) {
            totalDrink = totalDrinkSave;
            // Nettoyer l’ancienne liste
            beerList.clear();

            // Ajouter chaque boisson à la liste affichée
            for (Drink drink : totalDrink.getDrinks()) {
                beerList.add(drink.toString());
            }
            // Prévenir l’adaptateur que les données ont changé
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Récupérer les données sauvegardées
        String str = sharedPreferences.getString("person", "");
        Gson gson = new Gson();
        Person person = gson.fromJson(str,Person.class);

        // Mettre à jour le TextView avec la valeur récupérée
        if (person != null) {
            testText.setText(person.toString());
        } else {
            testText.setText("Aucune donnée trouvée");
        }
        updateTotalDrinkDisplay();
    }
}