package br.com.lucaophp.cardenetatopografica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by lucao on 16/09/2017.
 */

public class Estaca extends BD<Estaca> implements BDInterface<Estaca> {
    private int id;
    private double longitude;
    private double latitude;
    private double cota;
    private String info="";

    private int projeto_id;
    public static final String TABLE_NAME = "estaca";
    public static Context context;
    public static final String[] TABLE_COL = {
            "_id",
            "projeto_id",
            "latitude",
            "longitude",
            "cota",
            "info"
    };

    public int getProjeto_id() {
        return projeto_id;
    }

    public void setProjeto_id(int projeto_id) {
        this.projeto_id = projeto_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estaca(){
        super(context);
        super.TABLENAME = TABLE_NAME;
        super.TABLECOL = TABLE_COL;
        this.setObj(this);

    }

    public Estaca(int projeto_id, double longitude, double latitude, double cota) {
        this();
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.setCota(cota);
        this.setProjeto_id(projeto_id);

    }
    public Estaca(int projeto_id,double longitude, double latitude, double cota,String info){
        this(projeto_id,longitude,latitude,cota);
        this.setInfo(info);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getCota() {
        return cota;
    }

    public void setCota(double cota) {
        this.cota = cota;
    }
    public ContentValues getContent(){
        ContentValues c = new ContentValues();
        c.put("projeto_id",this.getProjeto_id());
        c.put("info",this.getInfo());
        c.put("latitude",this.getLatitude());
        c.put("cota",this.getCota());
        c.put("longitude",this.getLongitude());
        return c;
    }
    public boolean delete(int project_id){
        super.TABLENAME = this.TABLE_NAME;
        super.TABLECOL = this.TABLE_COL;
        return super.delete(String.format("projeto_id=%d",project_id));
    }
    public List<Estaca> buscar(Cursor cursor){
        List<Estaca> list=new ArrayList<>();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{

                Estaca e = new Estaca();
                e.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                e.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
                e.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
                e.setCota(cursor.getDouble(cursor.getColumnIndex("cota")));
                e.setInfo(cursor.getString(cursor.getColumnIndex("info")));
                e.setProjeto_id(cursor.getInt(cursor.getColumnIndex("projeto_id")));
                list.add(e);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
