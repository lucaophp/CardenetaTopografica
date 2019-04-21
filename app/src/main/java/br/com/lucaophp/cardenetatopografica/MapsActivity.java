package br.com.lucaophp.cardenetatopografica;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int projeto_id;
    private List<Estaca> estacas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        projeto_id = bundle.getInt("projeto_id");
        Estaca e = new Estaca();
        estacas = e.buscar(String.format("projeto_id= %d",projeto_id));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(Estaca e:estacas){
            LatLng ponto = new LatLng(e.getLatitude(), e.getLongitude());
            mMap.addMarker(new MarkerOptions().position(ponto).title(e.getCota()+"\n"+e.getInfo()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ponto));
        }

    }
}
