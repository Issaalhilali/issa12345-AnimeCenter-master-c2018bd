package com.example.animecenter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.animecenter.model.GroImage;

import java.util.List;

public class ActivityViewModel extends ViewModel {

    private MutableLiveData<List<GroImage>> mNicePlaces;
    private PlaceRepository mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init(){
        if(mNicePlaces != null){
            return;
        }
        mRepo = PlaceRepository.getInstance();
        mNicePlaces = mRepo.getNicePlaces();
    }

    @SuppressLint("StaticFieldLeak")
    public void addNewValue(final GroImage nicePlace){
        mIsUpdating.setValue(true);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<GroImage> currentPlaces = mNicePlaces.getValue();
                currentPlaces.add(nicePlace);
                mNicePlaces.postValue(currentPlaces);
                mIsUpdating.postValue(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public LiveData<List<GroImage>> getNicePlaces(){
        return mNicePlaces;
    }


    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }
}

