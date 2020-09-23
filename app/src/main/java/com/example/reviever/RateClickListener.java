package com.example.reviever;

import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;

public class RateClickListener implements View.OnClickListener {
    String docId;
    String gameTitle;
    Encyclopedia encyclopedia;

    public RateClickListener(String docId, String gameTitle,  Encyclopedia encyclopedia)
    {
        this.docId = docId;
        this.encyclopedia = encyclopedia;
        this.gameTitle = gameTitle;
    }
    @Override
    public void onClick(View v) {
       RateDialogWindow dialog = new RateDialogWindow("Rate " + gameTitle, docId, encyclopedia);
       dialog.show(encyclopedia.manager, "tag");
    }
}
