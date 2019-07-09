package com.example.animecenter;

import android.arch.lifecycle.MutableLiveData;

import com.example.animecenter.model.GroImage;

import java.util.ArrayList;
import java.util.List;

public class PlaceRepository {

    private static PlaceRepository instance;
    private ArrayList<GroImage> dataSet = new ArrayList<>();

    public static PlaceRepository getInstance(){
        if(instance == null){
            instance = new PlaceRepository();
        }
        return instance;
    }


    // Pretend to get data from a webservice or online source
    public MutableLiveData<List<GroImage>> getNicePlaces(){
        setNicePlaces();
        MutableLiveData<List<GroImage>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setNicePlaces(){
        dataSet.add(
                new GroImage("https://anime2001.com/wp-content/uploads/33657l.jpg",
                        "Hunter x Hunter")
        );
        dataSet.add(
                new GroImage("https://animespire.com/uploads/6-2016/anime_image/anime_image_3c59dc048e8850243be8079a5c74d07996065.jpg",
                        "akame ga kill")
        );
        dataSet.add(
                new GroImage("http://cimaclub.com/wp-content/uploads/2019/04/59397082_1022550368134896_9020396847428009984_n.jpg",
                        "Attack on Titan")
        );
        dataSet.add(
                new GroImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpL3myORMAqjgg95zamQWm0OFI4NrNex4jKIMuX0W75oAGzlUy",
                        "Naruto")
        );
        dataSet.add(
                new GroImage("https://img1.ak.crunchyroll.com/i/spire4/49d055f0d9b5b191e2dd44a34f890bfb1555368600_full.jpg",
                        "One Piece")
        );
        dataSet.add(
                new GroImage("https://i2.wp.com/www.torreckt.com/wp-content/uploads/2019/07/81ePwl5SmL.jpg",
                        "Vinland Sage")
        );
        dataSet.add(
                new GroImage( "https://www.monstersandcritics.com/wp-content/uploads/2019/03/DanMachi-Arrow-Of-The-Orion-Movie-Key-Visual-Poster.jpg",
                        "danmachi season 2")
        );
        dataSet.add(
                new GroImage("http://www.cima4up.co/wp-content/uploads/2019/07/Dr.Stone_.jpg",
                        "Dr.Stone")
        );
    }
}
