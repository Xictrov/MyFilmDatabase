package com.example.victor.prueba;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

        vFilmTitle.setText(mTitleRatingList.get(position).getFilmTitle());
        vFilmRating.setRating(mTitleRatingList.get(position).getRate());

        vFilmRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                filmdata.changeCriticsRate(mTitleRatingList.get(position).getFilmId(),rating);
            }
        });

        v.setTag(mTitleRatingList.get(position).getItemId());
        return v;
    }
}
