package br.com.lucaophp.cardenetatopografica;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CadastroEstacaActivity extends AppCompatActivity implements LocationListener {
    EditText edtInfo;
    TextView txtLong;
    TextView txtLat;
    TextView txtCota;
    Button btnSalvar;
    private LocationManager locationManager;
    double latitude=0,longitude=0,cota=0;
    public String[] license(){
        return new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    }
    public void cancelGPS(){
        locationManager.removeUpdates(this);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estaca);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 23 && (this.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED) || this.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != PackageManager.PERMISSION_GRANTED){
            this.requestPermissions(license(), 0);
        }
        locationManager.requestLocationUpdates("gps", 50, 0, this);
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        final int projeto_id = bundle.getInt("projeto_id");
        edtInfo = (EditText) findViewById(R.id.edtDescricao);
        txtLong = (TextView) findViewById(R.id.txtLong);
        txtLat = (TextView) findViewById(R.id.txtLat);
        txtCota = (TextView) findViewById(R.id.txtCota);
        btnSalvar = (Button) findViewById(R.id.btnSalvarEstaca);
        btnSalvar.setEnabled(false);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelGPS();
                Estaca e = new Estaca(projeto_id,Double.parseDouble(txtLong.getText().toString().replace(',','.')),Double.parseDouble(txtLat.getText().toString().replace(',','.')),Double.parseDouble(txtCota.getText().toString().replace(',','.')));
                e.setCota(cota);
                e.setInfo(edtInfo.getText().toString());
                String msg = "";
                if(e.insere(e)){
                    msg = "Estaca inserida com sucesso!!!";
                }else{
                    msg = "Falha ao inserir a estaca";
                }
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                Intent it = new Intent(CadastroEstacaActivity.this,EstacaActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("projeto_id",projeto_id);
                it.putExtras(bundle1);
                startActivity(it);
            }
        });



    }
    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double cota = location.getAltitude();
        this.latitude=latitude;
        this.longitude=longitude;
        this.cota=cota;
        this.txtCota.setText(String.format("%.2f",this.cota));
        this.txtLong.setText(String.format("%.3f",this.longitude));
        this.txtLat.setText(String.format("%.3f",this.latitude));
        btnSalvar.setEnabled(true);



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);


    }
}
