package com.example.ethylotest.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.example.ethylotest.Logic.Party;
import com.example.ethylotest.Logic.Person;
import com.example.ethylotest.R;
import com.example.ethylotest.Save.SaveParty;
import com.example.ethylotest.Save.SavePerson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Build;
import androidx.core.app.NotificationCompat;


public class MainActivity extends AppCompatActivity {

    // Déclaration des Boutons
    private Button editPerson;
    private Button addDrinkButton;

    // Déclaration des ListView et TextViews
    private ListView beerListView;
    private TextView dataText;
    private TextView tauxText;
    private TextView driveTextView;

    // Déclaration des SharedPreferences et des classes de sauvegarde
    private SharedPreferences sharedPreferences;
    private SavePerson savePerson;
    private SaveParty saveParty;

    // Déclaration des variables pour la liste des boissons et l'adaptateur
    private ArrayList<Drink> beerList;
    private ArrayAdapter<Drink> adapter;

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
        saveParty = new SaveParty(sharedPreferences);

        // Initialiser les TextView
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

        // Permettre la suppression d'une boisson en maintenant appuyé sur l'élément de la liste
        beerListView.setOnItemLongClickListener((parent, view, position, id) -> {
            // Créer un AlertDialog pour la confirmation
            new AlertDialog.Builder(this)
                    .setTitle(R.string.Confirmation)
                    .setMessage(R.string.Are_you_sure_you_want_to_delete_this_drink)
                    .setPositiveButton(R.string.Yes, (dialog, which) -> {
                        // Supprimer l'élément de la liste
                        saveParty.saveOneRemoveDrink(beerList.size() - 1 - position); // Ajuster l'index pour la suppression sur la liste inversée

                        // Mettre à jour l'affichage de la liste
                        updateTotalDrinkDisplay();
                        updateTauxText();
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
        }, 0,2000); // Mettre à jour toutes les 2 secondes

        // Vérifier les permissions pour les notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    /**
     * Met à jour l'affichage de la liste des boissons.
     * Cette méthode est appelée pour rafraîchir la liste des boissons affichées dans le ListView.
     */
    private void updateTotalDrinkDisplay() {
        beerList = saveParty.loadParty().getDrinks();

        if (beerList != null) {
            Collections.reverse(beerList);
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
        Party party = saveParty.loadParty();
        if (person == null) {
            tauxText.setText(R.string.Please_register);
        } else {
            taux = party.getTotalAlcohol(savePerson.loadPerson());
            tauxText.setText(String.format("%.4f", taux) + "g/L");
        }
        // Mettre à jour le TextView pour savoir si on peut conduire
        if (person != null) {
            if ((person.isYoung() && taux > 0.2) || taux > 0.5) {
                String formattedTime = formatHoursToString(party.getTimeYouCanDrive(person));
                driveTextView.setText(getString(R.string.You_can_t_drive) + "\n" + getString(R.string.You_will_be_able_to_drive_in) + " " + formattedTime);

                //sendNotification(getString(R.string.You_drank_too_much), getString(R.string.You_can_t_drive));
            } else {
                driveTextView.setText(R.string.You_can_drive);
            }
        } else {
            driveTextView.setText(R.string.Please_register);
        }
    }

    /**
     * Convertit un double représentant des heures en une chaîne formatée.
     * @param hours Le nombre d'heures en double.
     * @return Une chaîne formatée du type "1h 30min".
     */
    public String formatHoursToString(double hours) {
        int fullHours = (int) hours; // Partie entière des heures
        int minutes = (int) ((hours - fullHours) * 60); // Conversion de la partie décimale en minutes
        return fullHours + "h " + minutes + "min";
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

    /**
     * Met à jour l'affichage des données de la personne.
     * @param person L'objet Person contenant les données à afficher.
     */
    private void updatePersonDisplay(Person person) {
        if (person != null) {
            String texte = getString(R.string.Data_taken_into_account) + " : \n" + person.getWeight() + " kg  " + person.getSexe() + "\n" + (person.isYoung() ? getString(R.string.Young_Driver) : getString(R.string.Confirmed_Driver));
            dataText.setText(texte);
        } else {
            dataText.setText("Aucune donnée trouvée");
        }
    }

    /**
     * Permet d'envoyer une notification.
     * @param title Le titre de la notification
     * @param content Le contenu de la notification
     */
    private void sendNotification(String title, String content) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "ethylotest_channel";

        // Créer le canal pour Android 8+ (SDK 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Ethylotest Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications d'alerte éthylotest");
            manager.createNotificationChannel(channel);
        }

        // Intent pour lancer l'activité lors du clic sur la notif
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        // Créer la notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.icon_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Afficher la notification
        manager.notify(100, builder.build());
    }
}