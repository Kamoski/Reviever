package com.example.reviever;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class Encyclopedia extends ListFragment implements OnItemClickListener {

    private EncyclopediaViewModel mViewModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Game> arrayOfGames = new ArrayList<>();
    GameAdapter adapter;
    public FragmentManager manager;
    private Encyclopedia self;

    public static Encyclopedia newInstance() {
        return new Encyclopedia();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.encyclopedia_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EncyclopediaViewModel.class);
        ((MainActivity)getActivity()).setNewPanel("Encyclopedia");
        self = this;
        getData();
        adapter =  new GameAdapter(this.getContext(), arrayOfGames);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPause() {
        if(!arrayOfGames.isEmpty())
        {
            arrayOfGames.clear();
        }
        super.onPause();
    }

    public void fillArray(ArrayList<Game> list)
    {
        arrayOfGames.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void setManager( FragmentManager manager)
    {
        this.manager = manager;
    }

    void getData()
    {
        db.collection("Games")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Game> list  = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(new Game(document.get("Title").toString(),
                                        document.get("Year_of_production").toString(),
                                        document.get("Developing_studio").toString(),
                                        new RateClickListener(document.getId(), document.get("Title").toString() ,  self)));
                            }
                            fillArray(list);
                        } else {
                            Log.d("FAILED", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
