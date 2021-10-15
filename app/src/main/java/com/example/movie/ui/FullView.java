package com.example.movie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movie.adapter.MovieAdapter;
import com.example.movie.R;
import com.example.movie.model.Trenting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FullView extends AppCompatActivity {
    RecyclerView fullrecycler;
    List<Trenting> fullList=new ArrayList<>();
    Trenting trenting;
    MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);
        fullrecycler=findViewById(R.id.fullview);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        fetchdet(url,fullrecycler,fullList);
        //Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }
    public void fetchdet(String url, RecyclerView recyclerView, final List<Trenting> trentingList){

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        String title=object.getString("title");
                        long id=object.getLong("id");
                        //if(z==0) {
                        //    movid = id;
                         //   z=1;
                          //  String relatedurl="https://api.themoviedb.org/3/movie/"+String.valueOf(movid)+"/similar?api_key=81152650175a579f1997f9f742ed686b&language=en-US&page=1";
                          //  fetchdetrelated(relatedurl,relatedrecycler,relatedList);
                            //Toast.makeText(Search.this, "called related", Toast.LENGTH_SHORT).show();
                        //}
                        String releasedate=object.getString("release_date");
                        String overview=object.getString("overview");
                        String poster=object.getString("poster_path");
                        Boolean adult=object.getBoolean("adult");
                        String post="https://image.tmdb.org/t/p/w500"+poster;
                        String lang=object.getString("original_language");
                        double rate=object.getDouble("vote_average");
                        String age;
                        if(adult){
                            age="18+";
                        }
                        else {
                            age="13+";
                        }
                        trenting=new Trenting(post,title,id,releasedate,overview,adult,age,rate,lang);
                        trentingList.add(trenting);
                        //Toast.makeText(Search.this, post, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Search.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        movieAdapter=new MovieAdapter(trentingList,FullView.this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(movieAdapter);
        //movid=trentingList.get(0).getId();
    }
}