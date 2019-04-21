package br.com.lucaophp.cardenetatopografica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SobreActivity extends AppCompatActivity {
    String sobre;
    TextView txtSobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        sobre = bundle.getString("sobre");
        txtSobre = (TextView) findViewById(R.id.txtSobre);
        txtSobre.setText(sobre);

    }

}
