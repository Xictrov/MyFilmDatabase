package com.example.victor.prueba;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private FilmData filmData;
    private ListView lw;
    private titleRatingListAdapter adapter;
    private List<titleRating> mTitleRatingList;
    private RelativeLayout layout;

    public SearchFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        filmData = new FilmData(getActivity().getApplicationContext());
        filmData.open();

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        lw = (ListView) view.findViewById(R.id.listsearch);
        layout = (RelativeLayout) view.findViewById(R.id.searchLayout);
        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });

        setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getText(R.string.hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                List<Film> films = filmData.searchByActor(query);
                mTitleRatingList = new ArrayList<>();

                SearchFragment.orderByTitle(films);
                mTitleRatingList.clear();
                for (int i=0; i<films.size(); ++i) {
                    //System.out.println((float) films.get(i).getCritics_rate());
                    mTitleRatingList.add(new titleRating(i+1, films.get(i).getTitle(), (float) films.get(i).getCritics_rate(), films.get(i).getId()));
                }

                adapter = new titleRatingListAdapter(getActivity(), mTitleRatingList, filmData);

                lw.setAdapter(adapter);

                searchView.setQuery("", false);
                //searchView.setIconified(true);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //textView.setText(newText);
                return true;
            }
        });



        //check http://stackoverflow.com/questions/11085308/changing-the-background-drawable-of-the-searchview-widget
        //View searchPlate = (View) searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        //searchPlate.setBackgroundResource(R.mipmap.textfield_custom);



    }

    private static void orderByTitle(List<Film> lf) {
        class ComparatorFilms implements Comparator<Film> {
            public int compare(Film a, Film b) {
                return a.getTitle().compareTo(b.getTitle());
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

