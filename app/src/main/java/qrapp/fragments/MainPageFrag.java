package qrapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qrapp.activities.R;

/**
 * Created by Magnus on 27-12-2016.
 */

public class MainPageFrag extends MasterFrag {


    public MainPageFrag(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void addEntry(String data) {

    }
}
