package com.example.animecenter.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.animecenter.ActivityViewModel;
import com.example.animecenter.R;
import com.example.animecenter.adapters.RecyclerAdapter;
import com.example.animecenter.model.GroImage;

import java.util.List;

public class ImaActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private ActivityViewModel mActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ima);
        mFab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);

        mActivityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);

        mActivityViewModel.init();

        mActivityViewModel.getNicePlaces().observe(this, new Observer<List<GroImage>>() {
            @Override
            public void onChanged(@Nullable List<GroImage> nicePlaces) {
                mAdapter.notifyDataSetChanged();
            }
        });

        mActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    showProgressBar();
                }
                else{
                    hideProgressBar();
                    mRecyclerView.smoothScrollToPosition(mActivityViewModel.getNicePlaces().getValue().size()-1);
                }
            }
        });


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivityViewModel.addNewValue(
                        new GroImage(
                                "https://i.imgur.com/ZcLLrkY.jpg",
                                "Washington"
                        )
                );
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView(){
        mAdapter = new RecyclerAdapter(this, mActivityViewModel.getNicePlaces().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }
}


