package br.com.lucaophp.cardenetatopografica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BD<T extends BDInterface> {
    private final SQLiteDatabase bd;
    public T obj;
    public static String TABLENAME = "";
    public static String[] TABLECOL = {};

    public BD(Context context){
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();

    }
    public void setObj(T obj){
        this.obj = obj;
    }

    public boolean insere(T objeto){
        ContentValues c = objeto.getContent();

        boolean res = bd.insert(TABLENAME,null,c)>0;
        bd.close();
        return res;
    }
    public boolean delete(T objeto){
        final int delete = bd.delete(TABLENAME, String.format("_id = %d", + objeto.getId()) , null);
        Log.i("DELETE",objeto.getId()+"-"+delete);

        boolean res =  delete>0;

        return res;
    }
    public boolean delete(String where){

        final int delete = bd.delete(TABLENAME, where , null);
        boolean res =  delete>0;
        return res;
    }

    public List<T> buscar(){
        List<T> list = new ArrayList<>();


        Cursor cursor = bd.query(TABLENAME, TABLECOL, null, null, null, null, "_id ASC");

        list = obj.buscar(cursor);


        return(list);
    }
    public List<T> buscar(String where){
        List<T> list = new ArrayList<>();


        Cursor cursor = bd.query(TABLENAME, TABLECOL,where, null, null, null, "_id ASC");

        list = obj.buscar(cursor);


        return(list);
    }
}
