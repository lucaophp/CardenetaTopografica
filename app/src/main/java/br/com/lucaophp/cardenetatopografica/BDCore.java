package br.com.lucaophp.cardenetatopografica;

/**
 * Created by lucao on 16/09/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by lucao on 01/08/2017.
 */

public class BDCore extends SQLiteOpenHelper {
    public static final String NOME_BD ="CARDENETA_TOPOGRAFIA_DB";
    private static final int VERSAO_BD = 7;
    public BDCore(Context ctx){
        super(ctx, NOME_BD, null, VERSAO_BD);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE projeto(" +
                "_id integer primary key autoincrement," +
                "name text not null" +
                ")");
        db.execSQL("CREATE TABLE estaca(" +
                "_id integer primary key autoincrement," +
                "info text," +
                "longitude double," +
                "latitude double," +
                "cota double,"+
                "projeto_id int," +
                "foreign key (projeto_id) REFERENCES projeto(id)" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table projeto;drop table estaca;");
        onCreate(db);

    }
}

