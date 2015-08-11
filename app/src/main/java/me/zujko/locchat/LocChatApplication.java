package me.zujko.locchat;

import android.app.Application;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;

public class LocChatApplication extends Application {
    public static Firebase FIREBASE;
    public static GeoFire GEOFIRE;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        FIREBASE = new Firebase("https://<YOUR-FIREBASE-APP>.firebaseio.com/");
        GEOFIRE = new GeoFire(FIREBASE);
    }
}
