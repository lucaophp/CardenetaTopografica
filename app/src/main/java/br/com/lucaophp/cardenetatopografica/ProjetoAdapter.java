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

public class ProjetoAdapter extends BaseAdapter {
    private Context context;
    private List<Projeto> listProjeto;
    public ProjetoAdapter(Context context, List<Projeto> listProjeto){
        this.context = context;
        this.listProjeto= listProjeto;
    }
    @Override
    public int getCount() {
        return this.listProjeto.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listProjeto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Projeto projeto = this.listProjeto.get(position);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_projects, null);
        TextView tv = (TextView) view.findViewById(R.id.textProject);
        tv.setText(projeto.getName());


        return view;
    }
}
