package br.com.lucaophp.cardenetatopografica;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listEstacas;
    List<Projeto> projetoList;

    public void delete(Projeto projeto){
        /*List<Estaca> listaEstacas = new Estaca().buscar(String.format("projeto_id=%d",projeto.getId()));
        for(Estaca e:listaEstacas){
            e.delete(e);
        }*/

        projeto.delete();
        Intent it = new Intent(MainActivity.this,MainActivity.class);
        startActivity(it);
    }

    public void confirm(final List<Projeto> projetoList, final int cod){
        new AlertDialog.Builder(this)
                .setMessage("Realmente deseja excluir este projeto?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete(projetoList.get(cod));
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= 23 && (this.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) || this.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED){
            this.requestPermissions(license(), 0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Projeto.context = getApplicationContext();
        Estaca.context = getApplicationContext();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastrar = new Intent(MainActivity.this, CadastroProjetoActivity.class);
                startActivity(cadastrar);

                //Activity cadastrar = new Activity(CadastroProjetoActivity.this);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listEstacas = (ListView) findViewById(R.id.listProjects);
        ArrayAdapter<String> list = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1);
        Projeto projeto = new Projeto();
        projeto.context = getApplicationContext();
        projetoList = projeto.buscar();
        ProjetoAdapter pa = new ProjetoAdapter(getApplicationContext(),projetoList);
        listEstacas.setAdapter(pa);
        listEstacas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirm(projetoList,position);

                return true;
            }
        });
        listEstacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Projeto p = projetoList.get(position);

                Intent it = new Intent(MainActivity.this,EstacaActivity.class);
                Bundle b = new Bundle();
                b.putInt("projeto_id",p.getId());
                it.putExtras(b);
                startActivity(it);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public String[] license(){
        return new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    }
    public void transferencia(){
        Intent it = new Intent(MainActivity.this,TransferenciaActivity.class);
        startActivity(it);
    }

    public void backup(){
        for(Projeto p:projetoList){
            try {
                File sd = Environment.getExternalStorageDirectory();
                File file = new File(sd,"TOPO/");
                file.mkdirs();
                String path = file.getPath();
                path += "/projeto_"+p.getId()+".txt";
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path,false));
                Estaca e = new Estaca();
                List<Estaca> estacas = e.buscar("projeto_id="+p.getId());
                String conteudo = "";
                for(Estaca estaca: estacas){
                    conteudo+=String.format("%s;%f;%f;%f\n",estaca.getInfo().replace('\n',' '),estaca.getLatitude(),estaca.getLongitude(),estaca.getCota());
                }
                osw.write(conteudo);
                osw.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_backup) {
            backup();

            return true;
        }else if(id == R.id.action_sobre){
            String sobre = "Projeto desenvolvido para auxiliar os alunos a coleta de dados topograficos, com baixo investimento.\n" +
                    "Desenvolvido por: Lucas Batista Fialho\n" +
                    "Orientado por: Prof. Dr. Reynaldo Furtado Faria\n"+
                    "Financiado pela: CAPES/CNPQ";
            Bundle bundle = new Bundle();
            bundle.putString("sobre",sobre);
            Intent it = new Intent(MainActivity.this,SobreActivity.class);
            it.putExtras(bundle);
            startActivity(it);

            return true;
        }else if(id == R.id.action_transferencia){
            transferencia();
        }

        return super.onOptionsItemSelected(item);
    }
}
