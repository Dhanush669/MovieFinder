package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class Detailed extends AppCompatActivity {
    //ImageView detimage;
    RecyclerView similarrecycler;
    List<Trenting> similarlist;
    Trenting trenting;
    MovieAdapter movieAdapter;
    ImageView poster;
    ImageView background;
    TextView title;
    TextView releasedate;
    TextView age;
    TextView rate;
    TextView lang;
    TextView overview;
    CardView cardView;
    long id;
    int f=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        similarlist=new ArrayList<>();
        similarrecycler=findViewById(R.id.similarrecycler);
        poster=findViewById(R.id.poster);
        cardView=findViewById(R.id.background);
        background=findViewById(R.id.posterbackground);
        title=findViewById(R.id.mtitle);
        age=findViewById(R.id.ageval);
        rate=findViewById(R.id.rateval);
        lang=findViewById(R.id.langval);
        releasedate=findViewById(R.id.mreleasedate);
        overview=findViewById(R.id.overview);
        try {
            Intent intent = getIntent();
            title.setText(intent.getStringExtra("title"));
            releasedate.setText(intent.getStringExtra("release"));
            overview.setText(intent.getStringExtra("overview"));
            Glide.with(Detailed.this).load(intent.getStringExtra("poster")).into(poster);
            Glide.with(Detailed.this).load(intent.getStringExtra("poster")).centerCrop().transform(new BlurTransformation(7)).into(background);
            id = intent.getLongExtra("id",0);
            rate.setText(String.valueOf(intent.getDoubleExtra("rate",0)));
            lang.setText(intent.getStringExtra("lang"));
            age.setText(intent.getStringExtra("age"));
            String relatedurl="https://api.themoviedb.org/3/movie/"+String.valueOf(id)+"/similar?api_key=81152650175a579f1997f9f742ed686b&language=en-US&page=1";
            fetchdetrelated(relatedurl,similarrecycler,similarlist);
        }catch (Exception e){
            e.printStackTrace();
        }

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
                        //Toast.makeText(Detailed.this, releasedate, Toast.LENGTH_SHORT).show();
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
        movieAdapter=new MovieAdapter(trentingList,Detailed.this);
        recyclerView.setAdapter(movieAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Detailed.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}