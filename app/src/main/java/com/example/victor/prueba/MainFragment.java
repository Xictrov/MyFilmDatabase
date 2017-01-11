package com.example.victor.prueba;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private FilmData filmData;
    private ListView lw;
    private titleRatingListAdapter adapter;
    private List<titleRating> mTitleRatingList;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filmData = new FilmData(getActivity().getApplicationContext());
        filmData.open();

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
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        lw = (ListView) v.findViewById(R.id.listHome);

        mTitleRatingList = new ArrayList<>();

        List<Film> values = filmData.getAllFilms();

        if (values.size() == 0) {
            filmData.createFilm("V for Vendetta", "James McTeigue", "United States", 2005, "Natalie Portman", (float) 5);
            filmData.createFilm("Hachi: A Dog's Tale", "Lasse Hällstrom", "United States", 2009, "Richard Gere", (float) 5);
            filmData.createFilm("Mr. Nobody", "Jaco Van Dormael", "Belgium", 2009, "Jared Leto", (float) 4);
            filmData.createFilm("Dr. Strange", "Scott Derrickson", "United States", 2016, "Benedict Cumberbatch", (float) 4.5);
        }

        values = filmData.getAllFilms();
        MainFragment.orderByTitle(values);
        mTitleRatingList.clear();
        for (int i=0; i<values.size(); ++i) {
            mTitleRatingList.add(new titleRating(i+1, values.get(i).getTitle(), (float) values.get(i).getCritics_rate(), values.get(i).getId()));
        }

        adapter = new titleRatingListAdapter(getActivity(), mTitleRatingList, filmData);

        lw.setAdapter(adapter);

        /*lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Clicked" + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });*/

        return v;
    }

}
