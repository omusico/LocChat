package me.zujko.locchat.fragments;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zujko.locchat.R;
import me.zujko.locchat.Utils;

public class LocationFragment extends Fragment implements DiscreteSeekBar.OnProgressChangeListener {

    @Bind(R.id.mapview) MapView mMapView;
    @Bind(R.id.radius_seekbar) DiscreteSeekBar mSeekbar;
    Circle mCircleRadius;

    private GoogleMap mGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, rootView);
        mSeekbar.setOnProgressChangeListener(this);

        createMap(savedInstanceState);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLocation();
        super.onCreate(savedInstanceState);
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
        LatLng currLocation = new LatLng(Utils.CURRENT_LOCATION.getLatitude(), Utils.CURRENT_LOCATION.getLongitude());
        mGoogleMap = mMapView.getMap();
        MapsInitializer.initialize(getActivity());
        mGoogleMap.addMarker(new MarkerOptions().position(currLocation));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currLocation, 11);
        mGoogleMap.animateCamera(cameraUpdate);
        mCircleRadius = mGoogleMap.addCircle(new CircleOptions().center(currLocation).radius(1000));
    }

    private void setLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if(Utils.CURRENT_LOCATION == null) {
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(lastLocation != null) {
                    Utils.CURRENT_LOCATION = lastLocation;
                } else {
                    Utils.CURRENT_LOCATION = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                }
            }
        }
    }

    @Override
    public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean b) {
        mCircleRadius.remove();
        mCircleRadius = mGoogleMap.addCircle(new CircleOptions().center(new LatLng(Utils.CURRENT_LOCATION.getLatitude(),Utils.CURRENT_LOCATION.getLongitude())).radius(i*1000));
    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {

    }
}
