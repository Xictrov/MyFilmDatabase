package com.example.victor.prueba;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.Button;
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
    private boolean showMore = false;

    public RecyclerAdapter(Context context, List<Film> filmList, FilmData filmData){
        inflater=LayoutInflater.from(context);
        this.films = filmList;
        this.context = context;
        this.filmData = filmData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.content_format,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
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


    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView title,director,country,year,protagonist;
        public RatingBar rate;
        public Button button;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Toast.makeText(context, "Clicked item ", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            title = (TextView) itemView.findViewById(R.id.titleR);
            year = (TextView) itemView.findViewById(R.id.yearR);

            director = (TextView) itemView.findViewById(R.id.directorR);
            country = (TextView) itemView.findViewById(R.id.countryR);
            protagonist = (TextView) itemView.findViewById(R.id.protagonistR);
            rate = (RatingBar) itemView.findViewById(R.id.ratingR);
            button = (Button) itemView.findViewById(R.id.deleteB);
            button.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
    }

}