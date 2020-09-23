package com.example.reviever;

import android.view.View;

public class Game {
    public String title;
    public String developer;
    public String yearOfProduction;

    public View.OnClickListener onClick;

    public Game(String title, String developer, String yearOfProduction, View.OnClickListener click)
    {
        this.title = title;
        this.developer = developer;
        this.yearOfProduction = yearOfProduction;
        this.onClick = click;
    }

    public Game(Game copy)
    {
        this.title = copy.title;
        this.developer = copy.developer;
        this.yearOfProduction = copy.yearOfProduction;
        this.onClick = copy.onClick;
    }
}
