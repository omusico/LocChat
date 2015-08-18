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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zujko.locchat.R;
import me.zujko.locchat.Utils;

public class LocationFragment extends Fragment {

    @Bind(R.id.mapview) MapView mMapView;

    private GoogleMap mGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, rootView);

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
        mGoogleMap = mMapView.getMap();
        MapsInitializer.initialize(getActivity());
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Utils.CURRENT_LOCATION.getLatitude(), Utils.CURRENT_LOCATION.getLongitude())));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Utils.CURRENT_LOCATION.getLatitude(), Utils.CURRENT_LOCATION.getLongitude()), 11);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    private void setLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if(Utils.CURRENT_LOCATION == null) {
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(lastLocation != null) {
                    Utils.CURRENT_LOCATION = lastLocation;
                } else {
                    Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    Utils.CURRENT_LOCATION = location;
                }
            }
        }
    }
}
