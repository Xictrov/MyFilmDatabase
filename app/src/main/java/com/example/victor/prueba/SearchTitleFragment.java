package com.example.victor.prueba;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTitleFragment extends Fragment {
    private FilmData filmData;
    private RecyclerView MyRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Film> mFilmList = new ArrayList<>();
    private RelativeLayout layout;
    private boolean submitted = false;
    private int sizeBefore;
    private int querySize;

    public SearchTitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        filmData = new FilmData(getActivity().getApplicationContext());
        filmData.open();


        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        MyRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        MyRecyclerView.setHasFixedSize(true);


        setHasOptionsMenu(true);

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Film title");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                querySize = query.length();
                if (mFilmList.size() == 0) {
                    Toast.makeText(getActivity(), "Your search has no result!", Toast.LENGTH_SHORT).show();
                }
                searchView.setQuery("", false);
                //searchView.setIconified(true);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                submitted = true;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                submitted = false;
                if (!Objects.equals(newText, "") && !submitted) {
                    querySize=0;
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    MyRecyclerView.setLayoutManager(mLayoutManager);

                    List<Film> films = filmData.searchByTitle(newText);
                    SearchTitleFragment.orderByYear(films);

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
                }

                if (Objects.equals(newText, "") && !submitted && (newText.length() == querySize)) {
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    MyRecyclerView.setLayoutManager(mLayoutManager);
                    mFilmList.clear();
                    mAdapter = new RecyclerAdapter(getActivity(),mFilmList,filmData);
                    MyRecyclerView.setAdapter(mAdapter);
                }
                return true;
            }
        });
    }



    private static void orderByYear(List<Film> lf) {
        class ComparatorFilms implements Comparator<Film> {
            public int compare(Film a, Film b) {
                return b.getYear()-a.getYear();
            }
        }
        Collections.sort(lf, new ComparatorFilms());
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


}

