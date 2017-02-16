package appewtc.masterung.bsrufriend;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {
    //ewplicit
    private GoogleMap mMap;
    private double uesrLatDouble = 13.733030, userLngDouble = 100.489406;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }//main method


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //setup center of map
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(uesrLatDouble,userLngDouble),16));
    }// on mapready
} //Main class
