package br.com.lucaophp.cardenetatopografica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by lucao on 16/09/2017.
 */

public class Projeto extends BD<Projeto> implements BDInterface<Projeto> {
    private int id;
    private String name;
    public static Context context;
    public static String TABLE_NAME = "projeto";
    public static String[] TABLE_COL = {
            "_id",
            "name"
    };
    public Projeto(){
        super(context);
        super.TABLENAME = TABLE_NAME;
        super.TABLECOL = TABLE_COL;
        this.setObj(this);
    }

    public Projeto(int id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentValues getContent(){
        ContentValues c = new ContentValues();
        c.put("name",this.getName());

        return c;
    }
    public boolean delete(){
        Estaca e = new Estaca();
        if(e.delete(this.getId())){
            Log.i("ERRO","ERRO AO EXCLUIR");
        }
        super.TABLENAME = this.TABLE_NAME;
        super.TABLECOL = this.TABLE_COL;
        return super.delete(this);
    }
    public List<Projeto> buscar(Cursor cursor){
        List<Projeto> list=new ArrayList<>();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Projeto p = new Projeto();
                p.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                p.setName(cursor.getString(cursor.getColumnIndex("name")));

                list.add(p);

            }while(cursor.moveToNext());
        }
        return list;
    }
}
