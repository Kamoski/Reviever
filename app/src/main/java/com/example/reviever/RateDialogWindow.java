package com.example.reviever;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RateDialogWindow extends DialogFragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView tvHeader;
    private EditText etWrittenBy;
    private EditText etOpinion;

    private String gameTitle;
    private String docId;
    Encyclopedia encyclopedia;
    public RateDialogWindow(String gameTitle, String docId, Encyclopedia encyclopedia)
    {
        this.gameTitle = gameTitle;
        this.docId = docId;
        this.encyclopedia = encyclopedia;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.dialog_layout, null);
        tvHeader = dialogLayout.findViewById(R.id.tvHeader);
        tvHeader.setText(gameTitle);
        etOpinion = dialogLayout.findViewById(R.id.etOpinion);
        etWrittenBy = dialogLayout.findViewById(R.id.etWrittenBy);

        builder.setView(dialogLayout)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int id) {
                        Map<String, Object> opinion = new HashMap<>();
                        opinion.put("Name", etWrittenBy.getText().toString());
                        opinion.put("Opinion", etOpinion.getText().toString());
                        db.collection("Games")
                                .document(docId)
                                .collection("opinions")
                                .add(opinion)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Snackbar.make(encyclopedia.getView(), "Successfully added opinion!", Snackbar.LENGTH_SHORT)
                                                .show();
                                    }

                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(encyclopedia.getView(), R.string.failure_adding, Snackbar.LENGTH_SHORT)
                                                .show();
                                    }
                                });
                    }
                });
        return builder.create();
    }

    public void setData(View v)
    {
        tvHeader = v.findViewById(R.id.tvHeader);
        tvHeader.setText(gameTitle);
    }
}
