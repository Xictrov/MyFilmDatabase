package com.example.victor.prueba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;


import java.util.Collections;
import java.util.List;


/**
 * Created by Adrian on 03/01/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    Context context;
    List<Film> films = Collections.emptyList();

    public RecyclerAdapter(Context context, List<Film> filmList){
        inflater=LayoutInflater.from(context);
        this.films = filmList;
        this.context = context;

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
        holder.director.setText(current.getDirector());
        holder.country.setText(current.getCountry());
        holder.year.setText(Integer.toString(current.getYear()));
        holder.protagonist.setText(current.getProtagonist());
        holder.rate.setRating(current.getCritics_rate());

    }

    @Override
    public int getItemCount() {

        return films.size();
    }

    public void delete(int position){
        films.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView title,director,country,year,protagonist;
        public RatingBar rate;
        public Button button;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleR);
            director = (TextView) itemView.findViewById(R.id.directorR);
            country = (TextView) itemView.findViewById(R.id.countryR);
            year = (TextView) itemView.findViewById(R.id.yearR);
            protagonist = (TextView) itemView.findViewById(R.id.protagonistR);
            rate = (RatingBar) itemView.findViewById(R.id.ratingR);
            button = (Button) itemView.findViewById(R.id.deleteB);
            button.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            delete(getPosition());
        }
    }
}
