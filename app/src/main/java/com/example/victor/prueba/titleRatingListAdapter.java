package com.example.victor.prueba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Victor on 05/01/2017.
 */

public class titleRatingListAdapter extends BaseAdapter {
    private Context mContext;
    private List<titleRating> mTitleRatingList;
    private FilmData filmdata;

    //Constructor

    public titleRatingListAdapter(Context mcontext, List<titleRating> mTitleRatingList, FilmData filmdata) {
        this.mContext = mcontext;
        this.mTitleRatingList = mTitleRatingList;
        this.filmdata = filmdata;
    }

    @Override
    public int getCount() {
        return mTitleRatingList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTitleRatingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_list, null);
        TextView vFilmTitle = (TextView) v.findViewById(R.id.filmTitle);
        RatingBar vFilmRating = (RatingBar) v.findViewById(R.id.filmRating);
        ImageButton vDeleteFilm = (ImageButton) v.findViewById(R.id.deleteBtn);
        final TextView vFilmRateNumer = (TextView) v.findViewById(R.id.filmRateNumber);

        vFilmTitle.setText(mTitleRatingList.get(position).getFilmTitle());
        vFilmRating.setRating(mTitleRatingList.get(position).getRate());
        Float notaPeli = (mTitleRatingList.get(position).getRate()*2);
        vFilmRateNumer.setText(notaPeli.toString());

        vFilmRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                filmdata.changeCriticsRate(mTitleRatingList.get(position).getFilmId(),rating);
                Float aux = rating*2;
                vFilmRateNumer.setText(aux.toString());
            }
        });

        vDeleteFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("DELETE FILM");
                builder.setIcon(R.drawable.ic_report_problem_black_24dp);
                builder.setMessage("Do you really want to delete this film?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                filmdata.deleteFilm(mTitleRatingList.get(position).getFilmId());
                                mTitleRatingList.remove(position);
                                titleRatingListAdapter.this.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });

        v.setTag(mTitleRatingList.get(position).getItemId());
        return v;
    }
}
