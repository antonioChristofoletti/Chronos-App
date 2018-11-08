package chronos.chronos.View.Principal.Cadastros;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import chronos.chronos.R;

public class FragActPrincipalCadastrosAdapter extends BaseAdapter {
    String[] titulos = {"Bens Materiais", "Serviços", "Tipos Ocorrências"};

    int[] imagens = {R.drawable.ic_robot_industrial_grey600_24dp,
            R.drawable.ic_worker_grey600_24dp,
            R.drawable.ic_truck_delivery_grey600_24dp};

    AppCompatActivity activity;

    public FragActPrincipalCadastrosAdapter(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = this.activity.getLayoutInflater().inflate(R.layout.frag_act_principal_cadastros_list_view, null);

        ImageView imagemDescricao = (ImageView) convertView.findViewById(R.id.ImgItemLista_frag_act_principal_cadastros_list_view);
        TextView txtDescricaoItemLista = (TextView) convertView.findViewById(R.id.txtDescricaoItemLista_frag_act_principal_cadastros_list_view);

        imagemDescricao.setImageResource(imagens[position]);
        txtDescricaoItemLista.setText(titulos[position]);

        return convertView;
    }
}