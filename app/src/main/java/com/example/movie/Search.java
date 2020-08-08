package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class Search extends AppCompatActivity {
    String reque;
    long movid;
    Trenting trenting;
    MovieAdapter movieAdapter;
    int z;
    public static List<Trenting> searchList;
    public static List<Trenting> relatedList;
    RecyclerView searchrecycler;
    RecyclerView relatedrecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent=getIntent();
        z=0;
        searchList=new ArrayList<>();
        relatedList=new ArrayList<>();
        reque=intent.getStringExtra("req");
        searchrecycler=findViewById(R.id.searched);
        relatedrecycler=findViewById(R.id.related);
        String searchurl="https://api.themoviedb.org/3/search/movie?api_key=81152650175a579f1997f9f742ed686b&language=en-US&query="+reque+"'&page=1&include_adult=false";
        fetchdet(searchurl,searchrecycler,searchList);
        //String relatedurl="https://api.themoviedb.org/3/movie/"+movid+"/similar?api_key=81152650175a579f1997f9f742ed686b&language=en-US&page=1";
        //fetchdetrelated(relatedurl,relatedrecycler,relatedList);
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
                        if(z==0) {
                            movid = id;
                            z=1;
                            String relatedurl="https://api.themoviedb.org/3/movie/"+String.valueOf(movid)+"/similar?api_key=81152650175a579f1997f9f742ed686b&language=en-US&page=1";
                            fetchdetrelated(relatedurl,relatedrecycler,relatedList);
                            //Toast.makeText(Search.this, "called related", Toast.LENGTH_SHORT).show();
                        }
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
        movieAdapter=new MovieAdapter(trentingList,Search.this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(movieAdapter);
        //movid=trentingList.get(0).getId();
    }
    public void fetchdetrelated(String url, RecyclerView recyclerView, final List<Trenting> trentingList){
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
                        //Toast.makeText(Search.this, movid, Toast.LENGTH_SHORT).show();
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
        movieAdapter=new MovieAdapter(trentingList,Search.this);
        recyclerView.setAdapter(movieAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Search.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}