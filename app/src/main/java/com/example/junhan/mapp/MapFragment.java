package com.example.junhan.mapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;


/**
 * A placeholder fragment containing a simple view.
 */

public class MapFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ArrayList<EventsItem> eventList;

    private int level = 5;

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void changeLevelView(){
        googleMap.clear();
        Context context = getActivity().getApplicationContext();
        Drawable drawable = context.getResources().getDrawable(R.drawable.b2l5fixlres);
        if(level==5) {
            drawable = context.getResources().getDrawable(R.drawable.b2l5fixlres);
        }
        else if(level==4){
            drawable = context.getResources().getDrawable(R.drawable.b2l5fixlres);
        }
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        LatLngBounds Building2Bounds = new LatLngBounds(
                new LatLng(1.340542, 103.961791),       // South west corner
                new LatLng(1.341437, 103.963324));

        googleMap.setLatLngBoundsForCameraTarget(Building2Bounds);
        GroundOverlayOptions SUTDMAP = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromBitmap(bitmap))
                .positionFromBounds(Building2Bounds);
        googleMap.addGroundOverlay(SUTDMAP);
    }

    Dictionary<String, LatLng> dict = new Dictionary<String, LatLng>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Enumeration<String> keys() {
            return null;
        }

        @Override
        public Enumeration<LatLng> elements() {
            return null;
        }

        @Override
        public LatLng get(Object key) {
            return null;
        }

        @Override
        public LatLng put(String key, LatLng value) {
            return null;
        }

        @Override
        public LatLng remove(Object key) {
            return null;
        }
    };
    MapView mMapView;
    private GoogleMap googleMap;
    private LatLngBounds SUTD = new LatLngBounds(
            new LatLng(1.339845, 103.961356), new LatLng(1.342966, 103.965465));


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        eventList = new ArrayList<>();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        dict.put("LT5",new LatLng(1.340928, 103.962480));
        dict.put("CC13",new LatLng(1.340765, 103.962012));
        dict.put("CC14",new LatLng(1.340746, 103.961899));
        dict.put("CP9",new LatLng(1.340907, 103.961929));
        dict.put("CP10",new LatLng(1.341028, 103.961959));
        dict.put("Tech Office",new LatLng(1.341100, 103.962117));
        dict.put("ChemBio Lab",new LatLng(1.341146, 103.962294));
        dict.put("Electric Design Lab",new LatLng(1.341149, 103.962442));
        dict.put("TT26",new LatLng(1.341074, 103.962445));
        dict.put("Studio3",new LatLng(1.341270, 103.962960));
        dict.put("Studio4",new LatLng(1.341072, 103.963051));
        dict.put("Office of Campus Infrastructure",new LatLng(1.340865, 103.963024));
        dict.put("Fab Lab Sat 4",new LatLng(1.340817, 103.962753));
        dict.put("TT24",new LatLng(1.340820, 103.962651));
        dict.put("TT25",new LatLng(1.340812, 103.962587));
        dict.put("Mini TT13",new LatLng(1.340701, 103.962088));

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.clear();
                googleMap.setMinZoomPreference(19.0f);
                googleMap.setMaxZoomPreference(20.0f);
                googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                    @Override
                    public void onCameraChange(CameraPosition arg0) {
                        // Move camera.
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(SUTD, 10));
                        // Remove listener to prevent position reset on camera move.
                        googleMap.setOnCameraChangeListener(null);
                    }
                });

                // For zooming automatically to the location of the marker

                Context context = getActivity().getApplicationContext();

                LatLngBounds Building2Bounds = new LatLngBounds(
                        new LatLng(1.340542, 103.961791),       // South west corner
                        new LatLng(1.341437, 103.963324));

                googleMap.setLatLngBoundsForCameraTarget(Building2Bounds);

            }
        });



        return rootView;
    }

    public void addMarker(String location){
        googleMap.clear();
        Context context = getActivity().getApplicationContext();
        Drawable drawable = context.getResources().getDrawable(R.drawable.b2l5fixlres);
        if(level==5) {
            drawable = context.getResources().getDrawable(R.drawable.b2l5fixlres);
        }
        else if(level==4){
            drawable = context.getResources().getDrawable(R.drawable.b2l5fixlres);
        }
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        LatLngBounds Building2Bounds = new LatLngBounds(
                new LatLng(1.340542, 103.961791),       // South west corner
                new LatLng(1.341437, 103.963324));

        googleMap.setLatLngBoundsForCameraTarget(Building2Bounds);
        GroundOverlayOptions SUTDMAP = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromBitmap(bitmap))
                .positionFromBounds(Building2Bounds);
        googleMap.addMarker(new MarkerOptions().position(dict.get(location)).title("location"));
        googleMap.addGroundOverlay(SUTDMAP);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
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
}