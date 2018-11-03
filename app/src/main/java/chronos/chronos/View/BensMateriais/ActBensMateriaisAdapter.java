package chronos.chronos.View.BensMateriais;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.TipoOcorrencia;
import chronos.chronos.R;

public class ActBensMateriaisAdapter extends BaseAdapter{

    private ArrayList<BemMaterial> listaBensMateriais;
    private AppCompatActivity activity;

    public ActBensMateriaisAdapter(ArrayList<BemMaterial> listaBensMateriais, AppCompatActivity activity) {
        this.listaBensMateriais = listaBensMateriais;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaBensMateriais.size();
    }

    @Override
    public Object getItem(int position) {
        return listaBensMateriais.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  Integer.parseInt(listaBensMateriais.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = this.activity.getLayoutInflater().inflate(R.layout.content_act_bens_materiais_list_view, null);

        TextView txtDescricao = (TextView) convertView.findViewById(R.id.txtDescricaoBemMaterial_content_act_bens_material_list_view);
        TextView txtLocalizacao = (TextView) convertView.findViewById(R.id.txtLocalizacaoBemMaterial_content_act_bens_material_list_view);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.txtStatusBemMaterial_content_act_bens_material_list_view);

        BemMaterial bemMaterial = listaBensMateriais.get(position);

        txtDescricao.setText(bemMaterial.getDescricao());

        txtLocalizacao.setText("Localização: " + bemMaterial.getLocalizacao());

        txtStatus.setText(bemMaterial.getStatus());

        if (bemMaterial.getStatus().equals("Ativo"))
            txtStatus.setTextColor(ContextCompat.getColor(this.activity, R.color.colorGreen_status_ativo));
        else
            txtStatus.setTextColor(Color.RED);

        return convertView;
    }
}