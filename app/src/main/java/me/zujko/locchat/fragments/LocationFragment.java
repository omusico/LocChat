package me.zujko.locchat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zujko.locchat.R;
import me.zujko.locchat.activities.MainActivity;

public class LocationFragment extends Fragment {

    @Bind(R.id.mapview) MapView mMapView;

    private GoogleMap mGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, rootView);

        MainActivity activity = (MainActivity) getActivity();


        createMap(savedInstanceState);

        return rootView;
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



    private void createMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        mGoogleMap = mMapView.getMap();
        MapsInitializer.initialize(getActivity());

    }


}
