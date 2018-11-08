package chronos.chronos.View.Servicos;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.Servico;
import chronos.chronos.R;

public class ActServicosAdapter extends BaseAdapter{

    private ArrayList<Servico> listaServico;
    private AppCompatActivity activity;

    public ActServicosAdapter(ArrayList<Servico> listaServico, AppCompatActivity activity) {
        this.listaServico = listaServico;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaServico.size();
    }

    @Override
    public Object getItem(int position) {
        return listaServico.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  Integer.parseInt(listaServico.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = this.activity.getLayoutInflater().inflate(R.layout.content_act_servicos_cadastro_list_view, null);

        TextView txtDescricao = (TextView) convertView.findViewById(R.id.txtDescricaoServico_content_act_servicos_cadastro_list_view);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.txtStatusServico_content_act_servicos_cadastro_list_view);

        Servico servico = listaServico.get(position);

        txtDescricao.setText(servico.getDescricao());

        txtStatus.setText(servico.getStatus());

        if (servico.getStatus().equals("Ativo"))
            txtStatus.setTextColor(ContextCompat.getColor(this.activity, R.color.colorGreen_status_ativo));
        else
            txtStatus.setTextColor(ContextCompat.getColor(this.activity, R.color.ItemCancelado));

        return convertView;
    }
}