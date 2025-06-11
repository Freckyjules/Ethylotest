package com.example.ethylotest.Activity;

import android.app.AlertDialog;
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
import com.example.ethylotest.Save.SaveDrinks;
import com.example.ethylotest.Save.SavePerson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button editPerson;
    private Button addDrinkButton;

    private ListView beerListView;

    private TextView dataText;
    private TextView tauxText;
    private TextView driveTextView;

    private SharedPreferences sharedPreferences;

    private ArrayList<Drink> beerList;
    private ArrayAdapter<Drink> adapter;

    private SavePerson savePerson;
    private SaveDrinks saveDrinks;

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

        // Initialiser les SharedPreferences and Save classes
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        savePerson = new SavePerson(sharedPreferences);
        saveDrinks = new SaveDrinks(sharedPreferences);

        // Initialiser le TextView
        dataText = findViewById(R.id.dataText);
        tauxText = findViewById(R.id.TauxText);
        driveTextView = findViewById(R.id.driveTextView);

        // Mettre à jour le taux d'alcool
        updateTauxText();

        // Initialiser le ListView
        beerList = new ArrayList<>();

        // Mettre à jour la liste des boissons
        beerListView = findViewById(R.id.beerListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, beerList);
        beerListView.setAdapter(adapter);
        updateTotalDrinkDisplay();

        beerListView.setOnItemLongClickListener((parent, view, position, id) -> {
            // Créer un AlertDialog pour la confirmation
            new AlertDialog.Builder(this)
                    .setTitle(R.string.Confirmation)
                    .setMessage(R.string.Are_you_sure_you_want_to_delete_this_drink)
                    .setPositiveButton(R.string.Yes, (dialog, which) -> {
                        // Supprimer l'élément de la liste
                        saveDrinks.saveOneRemoveDrink(position);

                        // Mettre à jour l'affichage de la liste
                        updateTotalDrinkDisplay();
                    })
                    .setNegativeButton(R.string.No, null)
                    .show();
            return true; // Indiquer que l'événement est consommé
        });

        // Bouton pour éditer la personne
        editPerson = findViewById(R.id.editPerson);
        editPerson.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditPersonActivity.class);
            startActivity(intent);
        });

        // Bouton pour ajouter une boisson
        addDrinkButton = findViewById(R.id.addDrinkButton);
        addDrinkButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddDrinkActivity.class);
            startActivity(intent);
        });

        // Mettre a jour toutes les minutes le taux d'alcool
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    // Mettre à jour le taux d'alcool
                    updateTauxText();
                });
            }
        }, 0,60000); // Mettre à jour toutes les minutes

    }

    /**
     * Met à jour l'affichage de la liste des boissons.
     * Cette méthode est appelée pour rafraîchir la liste des boissons affichées dans le ListView.
     */
    private void updateTotalDrinkDisplay() {
        beerList = saveDrinks.loadDrinks().getDrinks();

        if (beerList != null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, beerList);
            beerListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Met à jour le TextView affichant le taux d'alcool.
     * Cette méthode calcule le taux d'alcool en fonction des boissons consommées et de la personne définie.
     */
    private void updateTauxText() {
        // Calculer le taux d'alcool
        double taux = 0.0;
        Person person = savePerson.loadPerson();
        if (person == null) {
            tauxText.setText(R.string.Please_register);
        } else {
            taux = saveDrinks.loadDrinks().getTotalAlcohol(savePerson.loadPerson());
            tauxText.setText(String.format("%.2f", taux));
        }
        // Mettre à jour le TextView pour savoir si on peut conduire
        if (person != null) {
            if ((person.isYoung() && taux > 0.2) || taux > 0.5) {
                driveTextView.setText(R.string.You_can_t_drive);
            } else {
                driveTextView.setText(R.string.You_can_drive);
            }
        } else {
            driveTextView.setText(R.string.Please_register);
        }
    }


    /**
     * Méthode appelée lorsque l'activité est reprise.
     * Elle récupère les données sauvegardées et met à jour l'interface utilisateur.
     */
    @Override
    protected void onResume() {
        super.onResume();
        updatePersonDisplay(savePerson.loadPerson());
        updateTotalDrinkDisplay();
        updateTauxText();
    }

    private void updatePersonDisplay(Person person) {
        if (person != null) {
            String texte = getString(R.string.Data_taken_into_account) + " : \n" + person.getWeight() + " kg\n" + person.getSexe() + "\n" + (person.isYoung() ? getString(R.string.Young_Driver) : getString(R.string.Confirmed_Driver));
            dataText.setText(texte);
        } else {
            dataText.setText("Aucune donnée trouvée");
        }
    }
}