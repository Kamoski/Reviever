package com.example.reviever;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddGame extends Fragment implements View.OnClickListener{

    private AddGameViewModel mViewModel;
    private View view;
    private ImageButton addGame;
    private EditText gameTitle;
    private EditText devStudio;
    private EditText prodYear;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static AddGame newInstance() {
        return new AddGame();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_game_fragment, container, false);
        addGame = (ImageButton) view.findViewById(R.id.btAddGame);
        gameTitle = (EditText) view.findViewById(R.id.etGameTitle);
        devStudio = (EditText) view.findViewById(R.id.etDevStudio);
        prodYear = (EditText) view.findViewById(R.id.etProdYear);
        addGame.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddGameViewModel.class);
        ((MainActivity)getActivity()).setNewPanel("Add game");
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        Map<String, Object> game = new HashMap<>();
        game.put("Developing_studio", devStudio.getText().toString());
        game.put("Title", gameTitle.getText().toString());
        game.put("Year_of_production", prodYear.getText().toString());

        if(devStudio.getText().toString().length() == 0
        || gameTitle.getText().toString().length() == 0
        || prodYear.getText().toString().length() == 0)
        {
            Snackbar.make(view, R.string.empty_field, Snackbar.LENGTH_SHORT)
                    .show();
        }
        else {
            db.collection("Games")
                    .add(game)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Adding success", "Document written with ID: " + documentReference.getId());
                            Snackbar.make(view, R.string.success_adding, Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Adding failure", "Adding error: ", e);
                            Snackbar.make(view, R.string.failure_adding, Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    });
        }

        devStudio.setText("");
        gameTitle.setText("");
        prodYear.setText("");
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }
}