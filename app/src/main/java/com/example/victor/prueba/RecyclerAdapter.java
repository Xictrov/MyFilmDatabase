package com.example.victor.prueba;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Toast;


import java.util.Collections;
import java.util.List;


/**
 * Created by Adrian on 03/01/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private List<Film> films = Collections.emptyList();
    private FilmData filmData;
    //private boolean showMore;

    public RecyclerAdapter(Context context, List<Film> filmList, FilmData filmData){
        inflater=LayoutInflater.from(context);
        this.films = filmList;
        this.context = context;
        this.filmData = filmData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.content_format,parent,false);
        final MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Toco", Toast.LENGTH_SHORT).show();
                holder.changeShowMore();
                showMoreInfo(holder);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Film current = films.get(position);
        holder.title.setText(String.valueOf(current.getTitle()));
        holder.year.setText(Integer.toString(current.getYear()));
        holder.director.setText(current.getDirector());
        holder.country.setText(current.getCountry());
        holder.protagonist.setText(current.getProtagonist());
        holder.rate.setRating(current.getCritics_rate());
        //Paràmetres originalment ocults
        holder.country.setVisibility(View.GONE);
        holder.director.setVisibility(View.GONE);
        holder.protagonist.setVisibility(View.GONE);
        holder.rate.setVisibility(View.GONE);
        holder.button.setVisibility(View.GONE);



    }

    @Override
    public int getItemCount() {

        return films.size();
    }

    public void delete(int position, long Id){
        films.remove(position);
        filmData.deleteFilm(Id);
        notifyItemRemoved(position);
    }

    public void changeRating(int position, long Id, Float rating){
        filmData.changeCriticsRate(Id,rating);

    }

    public void showMoreInfo(MyViewHolder holder){
        if(holder.showMoreActivated()){
            holder.country.setVisibility(View.VISIBLE);
            holder.director.setVisibility(View.VISIBLE);
            holder.protagonist.setVisibility(View.VISIBLE);
            holder.rate.setVisibility(View.VISIBLE);
            holder.button.setVisibility(View.VISIBLE);
        }
        else {
            holder.country.setVisibility(View.GONE);
            holder.director.setVisibility(View.GONE);
            holder.protagonist.setVisibility(View.GONE);
            holder.rate.setVisibility(View.GONE);
            holder.button.setVisibility(View.GONE);
        }

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title,director,country,year,protagonist;
        public RatingBar rate;
        public ImageButton button;
        public boolean showMore = false;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleR);
            year = (TextView) itemView.findViewById(R.id.yearR);
            showMore = false;
            director = (TextView) itemView.findViewById(R.id.directorR);
            country = (TextView) itemView.findViewById(R.id.countryR);
            protagonist = (TextView) itemView.findViewById(R.id.protagonistR);
            rate = (RatingBar) itemView.findViewById(R.id.ratingR);
            rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    changeRating(getPosition(),films.get(getPosition()).getId(),v);
                }
            });
            button = (ImageButton) itemView.findViewById(R.id.deleteB);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("DELETE FILM");
                    builder.setIcon(R.drawable.ic_report_problem_black_24dp);
                    builder.setMessage("Do you really want to delete this film?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delete(getPosition(),films.get(getPosition()).getId());

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

        }
        public void changeShowMore(){
            showMore = !(showMore);
        }
        public boolean showMoreActivated(){
            return showMore;
        }

    }

}