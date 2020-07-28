package com.example.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView search;
    EditText searchtex;
    Animation animation;
    RecyclerView nowplayrecyclerView;
    MovieAdapter movieAdapter;
    RecyclerView poprecyclerView;
    RecyclerView upcomerecyclerView;
    RecyclerView topratedrecyclerView;
    //RecyclerView latestrecyclerView;
    public static List<Trenting> trentingList =new ArrayList<>();
    public static List<Trenting> nowplay =new ArrayList<>();
    public static List<Trenting> poplist =new ArrayList<>();
    public static List<Trenting> upcomelist =new ArrayList<>();
    public static List<Trenting> topratedlist =new ArrayList<>();
    //public static List<Trenting> latestlist =new ArrayList<>();
    Trenting trenting;
    TrendingAdapter trendingAdapter;
    ViewPager2 viewPager2;
    int t=0;
    public void searchclicked(View view){
        if(t==0){
            //searchtex.setAnimation(animation);
            searchtex.setAlpha((float) 1.0);
            searchtex.setEnabled(true);
            t=1;
        }
        else{
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            t=0;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search=findViewById(R.id.search);
        searchtex=findViewById(R.id.stext);
        viewPager2=findViewById(R.id.viewpager);
        animation= AnimationUtils.loadAnimation(this,R.anim.anim);
        nowplayrecyclerView=findViewById(R.id.nowplayingrecycler);
        poprecyclerView=findViewById(R.id.popularrecycler);
        upcomerecyclerView=findViewById(R.id.upcomingrecycler);
        topratedrecyclerView=findViewById(R.id.topratedrecycler);
        //latestrecyclerView=findViewById(R.id.latestrecycler);
        fetchdet();
        String nowplayurl="https://api.themoviedb.org/3/movie/now_playing?api_key=81152650175a579f1997f9f742ed686b&language=en-US&page=1";
        fetchdetAll(nowplayurl,nowplayrecyclerView,nowplay);
        String popurl="https://api.themoviedb.org/3/movie/popular?api_key=81152650175a579f1997f9f742ed686b&language=en-US&page=1";
        fetchdetAll(popurl,poprecyclerView,poplist);
        String upcomeurl="https://api.themoviedb.org/3/movie/upcoming?api_key=81152650175a579f1997f9f742ed686b&language=en-US&page=1";
        fetchdetAll(upcomeurl,upcomerecyclerView,upcomelist);
        String topratedurl="https://api.themoviedb.org/3/movie/top_rated?api_key=81152650175a579f1997f9f742ed686b&language=en-US&page=1";
        fetchdetAll(topratedurl,topratedrecyclerView,topratedlist);
        //String latesturl="https://api.themoviedb.org/3/movie/latest?api_key=81152650175a579f1997f9f742ed686b&language=en-US";
        //fetchdetAll(latesturl,latestrecyclerView,latestlist);
        //searchtex.setAnimation(animation);
    }
    public void fetchdet() {
        String url = "https://api.themoviedb.org/3/trending/movie/day?api_key=81152650175a579f1997f9f742ed686b";
        //String url="https://api.themoviedb.org/3/movie/now_playing?api_key=81152650175a579f1997f9f742ed686b&language=en-US&page=1";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        String title=object.getString("title");
                        String id=object.getString("id");
                        String releasedate=object.getString("release_date");
                        String overview=object.getString("overview");
                        String poster=object.getString("poster_path");
                        Boolean adult=object.getBoolean("adult");
                        //String post="https://image.tmdb.org/t/p/w500"+poster;
                        trenting=new Trenting(poster,title,id,releasedate,overview,adult);
                        trentingList.add(trenting);

                    }
                    //trendingAdapter=new TrendingAdapter(trentingList,MainActivity.this,viewPager2);
                    //viewPager2.setAdapter(trendingAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "error from main", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }catch (Exception e){
            e.printStackTrace();
        }

        viewPager2.setAdapter(new TrendingAdapter(trentingList,MainActivity.this,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r =1-Math.abs(position);
                page.setScaleY(.85f + r * 0.15f);

            }
        }));
        viewPager2.setPageTransformer(compositePageTransformer);
    }
    public void fetchdetAll(String url, RecyclerView recyclerView, final List<Trenting> trentingList){
        //String Url=url;
        //final RecyclerView recyclerView1=recyclerView;
        //final List<Trenting> trentingList1=trentingList;
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object=jsonArray.getJSONObject(i);
                    String title=object.getString("title");
                    String id=object.getString("id");
                    String releasedate=object.getString("release_date");
                    String overview=object.getString("overview");
                    String poster=object.getString("poster_path");
                    Boolean adult=object.getBoolean("adult");
                    String post="https://image.tmdb.org/t/p/w500"+poster;
                    trenting=new Trenting(post,title,id,releasedate,overview,adult);
                    trentingList.add(trenting);
                    //Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        movieAdapter=new MovieAdapter(trentingList,MainActivity.this);
        recyclerView.setAdapter(movieAdapter);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
    }

}