package br.com.lucaophp.cardenetatopografica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroProjetoActivity extends AppCompatActivity {
    EditText edtNome;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_projeto);
        edtNome = (EditText) findViewById(R.id.edtNomeProjeto);
        btnSalvar = (Button) findViewById(R.id.btnSalvarProjeto);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString();
                Projeto p = new Projeto(-1,nome);
                String msg = "";
                if(p.insere(p)){
                    msg = "Projeto inserido com sucesso!!!";
                }else{
                    msg = "Falha ao inserir o projeto";
                }
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                startActivity(new Intent(CadastroProjetoActivity.this,MainActivity.class));
            }
        });
    }
}
