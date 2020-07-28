package com.example.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.Trendingview>{
    private List<Trenting> trentingList =new ArrayList<>();
    Context context;
    ViewPager2 viewPager2;
    public TrendingAdapter(List<Trenting> trentingList, Context context,ViewPager2 viewPager2) {
        this.trentingList = trentingList;
        this.context = context;
        this.viewPager2=viewPager2;
    }
    @NonNull
    @Override
    public Trendingview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trending,parent,false);
        return new Trendingview(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TrendingAdapter.Trendingview holder, int position) {
        try {
            //holder.mtitle.setText(trentingList.get(position).getTitle());
            String url="https://image.tmdb.org/t/p/w500"+trentingList.get(position).getPoster();
            Glide.with(context).load(url).into(holder.poster);
            //holder.poster.setImageResource(R.drawable.ic_launcher_background);
        }catch (Exception e){
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        //return trentingList.size();
        return 20;
    }

    public static class Trendingview extends RecyclerView.ViewHolder{
        ImageView poster;
        //TextView mtitle;
        CardView cardView;
        public Trendingview(@NonNull View itemView) {
            super(itemView);
            poster=itemView.findViewById(R.id.movieposter);
            //mtitle=itemView.findViewById(R.id.movietitle);
        }
    }

}
