package chronos.chronos.View.Principal.OrdemServico;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.OrdemServico;
import chronos.chronos.R;

public class FragActPrincipalosAdapter extends BaseAdapter {

    ArrayList<OrdemServico> listOrdemServico;

    AppCompatActivity activity;

    public FragActPrincipalosAdapter(ArrayList<OrdemServico> listOrdemServico, AppCompatActivity activity) {
        this.activity = activity;

        this.listOrdemServico = listOrdemServico;
    }

    @Override
    public int getCount() {
        return listOrdemServico.size();
    }

    @Override
    public Object getItem(int position) {
        return listOrdemServico.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(listOrdemServico.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = this.activity.getLayoutInflater().inflate(R.layout.frag_act_principal_os_list_view, null);

        TextView txtDescricaoBemMaterial = (TextView) convertView.findViewById(R.id.txtDescricaoBemMaterial_frag_act_principal_os_list_view);
        TextView txtTipoOcorrencia = (TextView) convertView.findViewById(R.id.txtTipoOcorrenciaBemMaterial_frag_act_principal_os_list_view);
        TextView txtStatusOS = (TextView) convertView.findViewById(R.id.txtStatusOS_frag_act_principal_os_list_view);
        TextView txtPeriodoOS = (TextView) convertView.findViewById(R.id.txtPeriodoOS_content_act_bens_material_list_view);

        OrdemServico ordemServico = listOrdemServico.get(position);

        txtDescricaoBemMaterial.setText(ordemServico.getBemMaterial().getDescricao());

        txtTipoOcorrencia.setText(ordemServico.getTipoOcorrencia().getDescricao());

        txtStatusOS.setTextColor(ContextCompat.getColor(activity, R.color.ItemCancelado));

        String status = "Cancelado";
        String periodo = "";
        if(ordemServico.getStatus().equals("Ativo")) {
            if (ordemServico.getDataInicial() == null && ordemServico.getDataFinal() == null) {
                status = "Pendente";
                txtStatusOS.setTextColor(ContextCompat.getColor(activity, R.color.ItemPendente));
            }

            if (ordemServico.getDataInicial() != null && ordemServico.getDataFinal() == null) {
                status = "Em Execução";
                periodo = Geral.formataData("dd/MM/yyyy HH:mm", ordemServico.getDataInicial());
                txtStatusOS.setTextColor(ContextCompat.getColor(activity, R.color.ItemEmExecucao));
            }

            if (ordemServico.getDataInicial() != null && ordemServico.getDataFinal() != null) {
                status = "Finalizado";
                periodo = Geral.formataData("dd/MM/yyyy HH:mm", ordemServico.getDataInicial()) + " - " + Geral.formataData("dd/MM/yyyy HH:mm", ordemServico.getDataFinal());
                txtStatusOS.setTextColor(ContextCompat.getColor(activity, R.color.ItemFinalizado));
            }
        }

        txtStatusOS.setText(status);


        txtPeriodoOS.setText(periodo);

        return convertView;
    }
}
