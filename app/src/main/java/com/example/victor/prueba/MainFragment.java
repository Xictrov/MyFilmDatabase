package com.example.victor.prueba;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
        System.out.println(values.size());

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);


        Film film;
        int size = values.size();
        for (int i=0; i<size; ++i) {
            if (getListAdapter().getCount() > 0) {
                film = (Film) getListAdapter().getItem(0);
                filmData.deleteFilm(film);
                adapter.remove(film);
            }
        }

        String[] newFilm = new String[] { " ", " ", "Toy Story", "John Lasseter", "Rocky Horror Picture Show", "Jim Sharman", "The Godfather", "Francis Ford Coppola", "Blade Runner", "Ridley Scott" };
        for (int i=0; i<newFilm.length; i+=2) {
            // save the new film to the database
            filmData.createFilm(newFilm[i], newFilm[i+1]);
        }

        values = filmData.getAllFilms();
        class ComparatorFilms implements Comparator<Film> {
            public int compare(Film a, Film b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        }
        Collections.sort(values, new ComparatorFilms());
        for (int i=0; i<values.size(); ++i) {
            // save the new film to the database
            adapter.add(values.get(i));
        }



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
