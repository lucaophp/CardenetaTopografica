package br.com.lucaophp.cardenetatopografica;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class EstacaActivity extends AppCompatActivity {
    ListView listEstacas;
    FloatingActionButton btnAdd;
    int projeto_id;
    private Button btnMap;
    public void delete(Estaca estaca){
        estaca.delete(estaca);
        Intent it = new Intent(EstacaActivity.this,EstacaActivity.class);
        Bundle b = new Bundle();
        b.putInt("projeto_id",projeto_id);
        it.putExtras(b);
        startActivity(it);
    }

    public void confirm(final List<Estaca> estacas, final int cod){
        new AlertDialog.Builder(this)
                .setMessage("Realmente deseja excluir está estaca?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete(estacas.get(cod));
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaca);


        listEstacas = (ListView) findViewById(R.id.listEstacasProjeto);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnMap = (Button) findViewById(R.id.btnMap);
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        projeto_id = bundle.getInt("projeto_id");

        Estaca e = new Estaca();
        final List<Estaca> estacas = e.buscar(String.format("projeto_id= %d",projeto_id));
        EstacaAdapter ea = new EstacaAdapter(getApplicationContext(),estacas);
        listEstacas.setAdapter(ea);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EstacaActivity.this,CadastroEstacaActivity.class);
                Bundle nb = new Bundle();
                nb.putInt("projeto_id",projeto_id);
                it.putExtras(nb);
                startActivity(it);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EstacaActivity.this,MapsActivity.class);
                Bundle nb = new Bundle();
                nb.putInt("projeto_id",projeto_id);
                it.putExtras(nb);
                startActivity(it);

            }
        });
        listEstacas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirm(estacas,position);
                return true;
            }
        });

    }


}
