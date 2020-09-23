package com.example.reviever;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class GameAdapter extends ArrayAdapter<Game>
{
    public GameAdapter(Context context, ArrayList<Game> games)
    {
        super(context, 0 , games);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Game game = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element, parent, false);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvDeveloper = (TextView) convertView.findViewById(R.id.tvDeveloper);
        TextView tvYearOfProduction = (TextView) convertView.findViewById(R.id.tvYearOfProduction);
        ImageButton ibRate = (ImageButton) convertView.findViewById(R.id.ibRate);

        tvTitle.setText(game.title);
        tvDeveloper.setText(game.developer);
        tvYearOfProduction.setText(game.yearOfProduction);
        ibRate.setOnClickListener(game.onClick);

        return convertView;
    }
}
