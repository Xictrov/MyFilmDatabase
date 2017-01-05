package com.example.victor.prueba;

import android.media.Rating;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RatingBar;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.support.v7.widget.AppCompatButton;
import android.widget.Toast;


public class AddFragment extends Fragment implements View.OnClickListener {
    private FilmData filmData;
    public Button button;

    EditText title, director, country, year, protagonist;
    RatingBar rate;

    public AddFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filmData = new FilmData(getActivity().getApplicationContext());
        filmData.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_add, container, false);
        button = (Button) v.findViewById(R.id.btnOk);
        button.setOnClickListener(this);
        title = (EditText) v.findViewById(R.id.addTitle);
        director = (EditText) v.findViewById(R.id.addDirector);
        country = (EditText) v.findViewById(R.id.addCountry);
        year = (EditText) v.findViewById(R.id.addYear);
        protagonist = (EditText) v.findViewById(R.id.addProtagonist);
        rate = (RatingBar) v.findViewById(R.id.addRating);
        return v;
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
    public void onClick(View v) {
        filmData.createFilm(title.getText().toString(), director.getText().toString(),
                country.getText().toString(), Integer.parseInt(year.getText().toString()),
                protagonist.getText().toString(), rate.getRating());
    }
}