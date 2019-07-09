package com.example.animecenter.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.animecenter.account.LoginActivity;
import com.example.animecenter.account.SettingActivity;
import com.example.animecenter.R;
import com.example.animecenter.adapters.RecyclerViewAdapter;
import com.example.animecenter.model.Anime;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    private final  String JSON_URL = "https://gist.githubusercontent.com/issa12345/1a1b9879f0bb365ed21b6dcf270f774a/raw/2ce6afbe43a9c1bbf361507fbef8ab42f2636237/jsonMyAppAnimeCenter";
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private static final String SORT_FAVORITE = "favorite";
    private ArrayList<Anime> lstAnime ;
    private RecyclerView recyclerView ;
    private ShareActionProvider mShareActionProvider;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,getString(R.string.ads_banner));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (savedInstanceState != null){
            lstAnime = savedInstanceState.getParcelableArrayList("STATE_SCORE");
        }else {
            jsonrequest();
        }

        NavigationView navigationView = findViewById(R.id.navigationId);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawe);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lstAnime = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);
        jsonrequest();

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("STATE_SCORE", lstAnime);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lstAnime = savedInstanceState.getParcelableArrayList("STATE_SCORE");
    }



    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject  = null ;

                for (int i = 0 ; i < response.length(); i++ ) {


                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Anime anime = new Anime() ;
                        anime.setName(jsonObject.getString("name"));
                        anime.setDescription(jsonObject.getString("description"));
                        anime.setRating(jsonObject.getString("Rating"));
                        anime.setCategorie(jsonObject.getString("categorie"));
                        anime.setNb_episode(jsonObject.getInt("episode"));
                        anime.setStudio(jsonObject.getString("studio"));
                        anime.setImage_url(jsonObject.getString("img"));
                        lstAnime.add(anime);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                setuprecyclerview(lstAnime);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request) ;


    }

    private void setuprecyclerview(List<Anime> lstAnime) {


        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, (ArrayList<Anime>) lstAnime) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                Intent i = new Intent(Intent.ACTION_SEND);

                i.setType("text/plain");
                String sharebody = " Anime Center app you can get now on link https://play.google.com/store";
                String shareSub = "get App Anime Center";
                i.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                i.putExtra(Intent.EXTRA_TEXT, sharebody);
                startActivity(Intent.createChooser(i, "Share using"));
        }
        Toast.makeText(getApplicationContext(), "you click on menu share", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId()==R.id.db)
        {
            Toast.makeText(this,"this is Home",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else if (menuItem.getItemId()==R.id.imagegroup){
            Toast.makeText(this,"this is Group image",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ImaActivity.class);
            startActivity(intent);
        }
        else if (menuItem.getItemId()==R.id.about)
        {
            Toast.makeText(this,"this is about",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }
        else if (menuItem.getItemId()==R.id.settings)
        {
            Toast.makeText(this,"this is settings",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        else if (menuItem.getItemId()==R.id.logout)
        {
            Toast.makeText(this,"this is logout",Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent LoginActivity = new Intent(this, LoginActivity.class);
            startActivity(LoginActivity);
        }
        return false;
    }
    public class JSONTask extends AsyncTask<URI, String, String> {

        @Override
        protected String doInBackground(URI... uris) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}