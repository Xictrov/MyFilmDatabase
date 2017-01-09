package com.example.victor.prueba;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerFragment extends Fragment {
    private RecyclerView MyRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Film> mFilmList = new ArrayList<>();
    private static FilmData filmData;

    public RecyclerFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        filmData = new FilmData(getActivity().getApplicationContext());
        filmData.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        MyRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        MyRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        MyRecyclerView.setLayoutManager(mLayoutManager);

        List<Film> films = filmData.getAllFilms();
        RecyclerFragment.orderByYear(films);


        mFilmList.clear();
        for (int i=0; i<films.size(); ++i) {
            Film filmi = new Film();
            filmi.setId(films.get(i).getId());
            filmi.setTitle(films.get(i).getTitle());
            filmi.setDirector(films.get(i).getDirector());
            filmi.setCountry(films.get(i).getCountry());
            filmi.setYear(films.get(i).getYear());
            filmi.setProtagonist(films.get(i).getProtagonist());
            filmi.setCritics_rate(films.get(i).getCritics_rate());
            mFilmList.add(filmi);
        }

        mAdapter = new RecyclerAdapter(getActivity(),mFilmList,filmData);
        MyRecyclerView.setAdapter(mAdapter);
        return v;
    }

    private static void orderByYear(List<Film> lf) {
        class ComparatorFilms implements Comparator<Film> {
            public int compare(Film a, Film b) {
                return b.getYear()-a.getYear();
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
}
