package com.example.victor.prueba;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {
    public List<String> textos;

    public HelpFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        textos = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, textos);
        ListView viewL = (ListView) view.findViewById(R.id.listHelp);
        viewL.setAdapter(adapter);
        adapter.add("Welcome to the Help option of the app. The following content explains the features of this app.");
        adapter.add("-In option Home, you can view all films ordered alphabetically. For each film, you can see the Title and its critic's rate");
        adapter.add("-In the Film Data Base option, you can view all films ordered by year. You will see films' Title and Year of release. If you click in one of them, all of its information will be shown");
        adapter.add("-To create a new film, select the Add new film option and enter the corresponding information");
        //
        adapter.add("Film fields and more:");
        adapter.add("-The film Title field corresponds on the title of the film");
        adapter.add("-The film Year field corresponds on the release year of the film");
        adapter.add("-The film Director field corresponds on the film director");
        adapter.add("-The film Protagonist field corresponds on the main actor/actress of the film");
        adapter.add("-The film Critics Rate is shown in 5 stars, with values from 0 to 10, and it corresponds on the valoration given. You can change the valoration in any moment");
        adapter.add("-The Critics Rate can be modified clicking on the stars. There are 5 stars, but you can click half of the star.");
        //
        adapter.add("Search features:");
        adapter.add("-To search films by title, use the Search Film by Title option. You will see films' Title and Year of release. If you click on a film, you will see all of its information.");
        adapter.add("-To search films by main/protagonist actor/actress, use the Search Film by Actor/Actress. For each film, you can see the Title and the valoration");
        adapter.add("Other features:");
        adapter.add("-To delete a film from the data base, use the trash can icon in the film view (it's accesible in the searchs, the film data basa and the home menu.");
        adapter.add("-If you press back, you will go to the Home film view, the view given default when you open the app");
        adapter.add("-To know more about the app, use the About option of the menu.");
        adapter.add("");
        adapter.add("For any other question, contact us: MyFilmDataBaseContact@gmail.com");
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
