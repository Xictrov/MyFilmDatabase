package com.example.victor.prueba;

import android.content.Context;
import android.media.Rating;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.support.v7.widget.AppCompatButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class AddFragment extends Fragment implements View.OnClickListener {
    private FilmData filmData;
    public Button button;

    EditText title, director, country, year, protagonist;
    RatingBar rate;
    TextView rateNumber;
    RelativeLayout layout;

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
        title.requestFocus();
        title.setFocusable(true);
        director = (EditText) v.findViewById(R.id.addDirector);
        country = (EditText) v.findViewById(R.id.addCountry);
        year = (EditText) v.findViewById(R.id.addYear);
        protagonist = (EditText) v.findViewById(R.id.addProtagonist);
        rate = (RatingBar) v.findViewById(R.id.addRating);
        rateNumber = (TextView) v.findViewById(R.id.addRateNumber);

        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Float aux = rating*2;
                rateNumber.setText(aux.toString());
            }
        });

        layout = (RelativeLayout) v.findViewById(R.id.addLayout);
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
        String eTitle = title.getText().toString();
        String eDirector = director.getText().toString();
        String eCountry = country.getText().toString();
        String eYear = year.getText().toString();
        String eProtagonist = protagonist.getText().toString();
        if (Objects.equals(eTitle, "")) {
            title.setError("The film must have a title!");
        }
        if (Objects.equals(eDirector, "")) {
            director.setError("Nobody directed the film?");
        }
        if (Objects.equals(eCountry, "")) {
            country.setError("Where was the film released?");
        }
        if (Objects.equals(eYear, "")) {
            year.setError("In which year came out?");
        }
        if (Objects.equals(eProtagonist, "")) {
            protagonist.setError("Who was the main actor/actress?");
        }
        if (!Objects.equals(eTitle, "") && !Objects.equals(eDirector, "") &&
                !Objects.equals(eCountry, "") && !Objects.equals(eYear, "") &&
                !Objects.equals(eProtagonist, "")) {
            filmData.createFilm(eTitle, eDirector,
                    eCountry, Integer.parseInt(eYear),
                    eProtagonist, rate.getRating());
            title.setText("");
            director.setText("");
            country.setText("");
            year.setText("");
            protagonist.setText("");
            rate.setRating(0);
            title.requestFocus();
            Toast.makeText(getActivity(), "Film added to the database!", Toast.LENGTH_SHORT).show();
        }

    }

    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity(). getSystemService(Activity.INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    public void showInputMethod() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

}
