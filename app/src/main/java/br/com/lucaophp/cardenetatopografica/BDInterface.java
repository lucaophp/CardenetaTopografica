package br.com.lucaophp.cardenetatopografica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

/**
 * Created by lucao on 16/09/2017.
 */

public interface BDInterface<T> {
    public ContentValues getContent();
    public List<T> buscar(Cursor cursor);
    public int getId();

}
