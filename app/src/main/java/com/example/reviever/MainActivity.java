package com.example.reviever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public ImageButton encButton;
    public ImageButton homeButton;
    public ImageButton addGame;
    public TextView actualPanel;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Home homeFragment = new Home();
    Encyclopedia encFragment = new Encyclopedia();
    AddGame addGameFragment = new AddGame();
    Map<String, Object> users = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encFragment.setManager(fragmentManager);
        actualPanel = findViewById(R.id.tvActualPanel);

        encButton = findViewById(R.id.encButton);
        homeButton = findViewById(R.id.homeButton);
        addGame = findViewById(R.id.addGame);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction anotherOne = fragmentManager.beginTransaction();
                anotherOne.replace(R.id.fragmentContainer, homeFragment);
                anotherOne.addToBackStack(null);
                anotherOne.commit();
            }
        });
        encButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction anotherOne = fragmentManager.beginTransaction();
                anotherOne.replace(R.id.fragmentContainer, encFragment);
                anotherOne.addToBackStack(null);
                anotherOne.commit();
            }
        });

        addGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction anotherOne = fragmentManager.beginTransaction();
                anotherOne.replace(R.id.fragmentContainer, addGameFragment);
                anotherOne.addToBackStack(null);
                anotherOne.commit();
            }
        });


        fragmentTransaction.add(R.id.fragmentContainer, homeFragment);
        fragmentTransaction.commit();

    }

    public void setNewPanel(String panelName)
    {
        actualPanel.setText(panelName);
    }


}
