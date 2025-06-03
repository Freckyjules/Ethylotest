package com.example.ethylotest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private Button editPerson;

    private TextView testText;

    private SharedPreferences sharedPreferences;

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

        // Initialiser le TextView
        testText = findViewById(R.id.testText);

        editPerson = findViewById(R.id.editPerson);
        editPerson.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditPersonActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Accéder aux SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);


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
    }
}