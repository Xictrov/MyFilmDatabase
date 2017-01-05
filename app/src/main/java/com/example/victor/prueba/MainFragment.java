package com.example.victor.prueba;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends ListFragment {
    private FilmData filmData;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filmData = new FilmData(getActivity().getApplicationContext());
        filmData.open();

        List<Film> values = filmData.getAllFilms();
        List<String> titles = new ArrayList<>();
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, titles);
        setListAdapter(adapter);

        if (values.size() == 0) {
            String[] newFilm = new String[]{"Toy Story", "John Lasseter", "Rocky Horror Picture Show", "Jim Sharman", "The Godfather", "Francis Ford Coppola", "Blade Runner", "Ridley Scott"};
            for (int i = 0; i < newFilm.length; i += 2) {
                // save the new film to the database
                filmData.createFilm(newFilm[i], newFilm[i + 1], "Spain", 2014, "Victor Hervas", (float) 0.5);
            }
         }

        values = filmData.getAllFilms();
        MainFragment.orderByTitle(values);
        adapter.clear();
        for (int i=0; i<values.size(); ++i) {
            adapter.add(values.get(i).getTitle());
        }

    }

    private static void orderByTitle(List<Film> lf) {
        class ComparatorFilms implements Comparator<Film> {
            public int compare(Film a, Film b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        }
        Collections.sort(lf, new ComparatorFilms());
    }

    @Override
    public void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        filmData.close();
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
