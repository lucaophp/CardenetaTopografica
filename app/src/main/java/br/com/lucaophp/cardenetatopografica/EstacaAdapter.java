package br.com.lucaophp.cardenetatopografica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lucao on 16/09/2017.
 */

public class EstacaAdapter extends BaseAdapter {
    private Context context;
    private List<Estaca> listEstacas;
    public EstacaAdapter(Context context, List<Estaca> listEstaca){
        this.context = context;
        this.listEstacas = listEstaca;
    }
    @Override
    public int getCount() {
        return listEstacas.size();
    }

    @Override
    public Object getItem(int position) {
        return listEstacas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Estaca estaca = listEstacas.get(position);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_estacas, null);
        TextView text = (TextView)view.findViewById(R.id.text);
        text.setText(estaca.getInfo()+"\n"+"("+estaca.getLatitude()+","+estaca.getLongitude()+","+estaca.getCota()+")");


        return view;
    }
}
