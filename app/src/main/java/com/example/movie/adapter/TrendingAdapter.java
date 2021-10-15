package com.example.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.example.movie.model.Trenting;
import com.example.movie.ui.Detailed;

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
    public void onBindViewHolder(@NonNull final TrendingAdapter.Trendingview holder, final int position) {
        try {
            //holder.mtitle.setText(trentingList.get(position).getTitle());
            //String url="https://image.tmdb.org/t/p/w500"+trentingList.get(position).getPoster();
            Glide.with(context).load(trentingList.get(position).getPoster()).into(holder.poster);
            //holder.poster.setImageResource(R.drawable.ic_launcher_background);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ImageView iv=v.findViewById();
                    Intent intent=new Intent(context, Detailed.class);
                    intent.putExtra("title",trentingList.get(position).getTitle());
                    intent.putExtra("release",trentingList.get(position).getReleasedate());
                    intent.putExtra("overview",trentingList.get(position).getOverview());
                    intent.putExtra("id",trentingList.get(position).getId());
                    intent.putExtra("poster",trentingList.get(position).getPoster());
                    intent.putExtra("rate",trentingList.get(position).getRate());
                    intent.putExtra("lang",trentingList.get(position).getLang());
                    intent.putExtra("age",trentingList.get(position).getAge());
                    context.startActivity(intent);
                    //holder.poster.setImageBitmap(trentingList.get(position).getPoster());
                }
            });
        }catch (Exception e){
            //Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
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
            cardView=itemView.findViewById(R.id.detailview);
            //mtitle=itemView.findViewById(R.id.movietitle);
        }
    }

}
