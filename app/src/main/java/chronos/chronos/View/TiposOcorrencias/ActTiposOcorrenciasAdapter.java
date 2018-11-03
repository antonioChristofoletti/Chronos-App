package chronos.chronos.View.TiposOcorrencias;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import chronos.chronos.Model.TipoOcorrencia;

import chronos.chronos.R;

public class ActTiposOcorrenciasAdapter extends BaseAdapter{

    private ArrayList<TipoOcorrencia> listaTipoOcorrencia;
    private AppCompatActivity activity;

    public ActTiposOcorrenciasAdapter(ArrayList<TipoOcorrencia> listaTipoOcorrencia, AppCompatActivity activity) {
        this.listaTipoOcorrencia = listaTipoOcorrencia;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaTipoOcorrencia.size();
    }

    @Override
    public Object getItem(int position) {
        return listaTipoOcorrencia.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  Integer.parseInt(listaTipoOcorrencia.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = this.activity.getLayoutInflater().inflate(R.layout.act_tipos_ocorrencias_list_view, null);

        TextView txtDescricao = (TextView) convertView.findViewById(R.id.txtDescricaoTipoOcoorrencia_act_tipos_ocorrencias_list_view);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.txtstatus_act_tipos_ocorrencias_list_view);

        TipoOcorrencia tipoOcorrencia = listaTipoOcorrencia.get(position);

        txtDescricao.setText(tipoOcorrencia.getDescricao());

        txtStatus.setText(tipoOcorrencia.getStatus());

        if (tipoOcorrencia.getStatus().equals("Ativo"))
            txtStatus.setTextColor(ContextCompat.getColor(this.activity, R.color.colorGreen_status_ativo));
        else
            txtStatus.setTextColor(Color.RED);

        return convertView;
    }

}