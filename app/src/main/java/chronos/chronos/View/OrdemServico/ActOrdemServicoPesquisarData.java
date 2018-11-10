package chronos.chronos.View.OrdemServico;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import chronos.chronos.Geral.Geral;
import chronos.chronos.Geral.MaskWatcher;
import chronos.chronos.Interface.InterfaceInjecaoDependenciaOrdemServicoPesquisa;
import chronos.chronos.R;

public abstract class ActOrdemServicoPesquisarData {

    public static Date dataInicial;
    public static Date dataFinal;

    public static void invocarModalPesquisarOS(final View view, final FragmentActivity fragmentActivity,
                                               final InterfaceInjecaoDependenciaOrdemServicoPesquisa interfaceOrdemServicoPesquisa) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
        View mview = fragmentActivity.getLayoutInflater().inflate(R.layout.act_ordem_servico_pesquisar_data, null);
        mBuilder.setView(mview);
        mBuilder.setCancelable(false);

        Button btnPesquisar = (Button) mview.findViewById(R.id.btnPesquisar_act_ordem_servico_pesquisar_data);
        Button btnCancelar = (Button) mview.findViewById(R.id.btnCancelar_act_ordem_servico_pesquisar_data);

        final EditText txtDataInicial = (EditText) mview.findViewById(R.id.txtDataInicial_act_ordem_servico_pesquisar_data);
        final EditText txtDataFinal = (EditText) mview.findViewById(R.id.txtDataFinal_act_ordem_servico_pesquisar_data);

        if(dataInicial != null)
            txtDataInicial.setText(Geral.formataData("dd/MM/yyyy", dataInicial));

        if(dataFinal != null)
            txtDataFinal.setText(Geral.formataData("dd/MM/yyyy", dataFinal));

        txtDataInicial.addTextChangedListener(new MaskWatcher(MaskWatcher.FORMAT_DATE));
        txtDataFinal.addTextChangedListener(new MaskWatcher(MaskWatcher.FORMAT_DATE));

        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataInicial = Geral.geraData("dd/MM/yyyy", txtDataInicial.getText().toString());
                dataFinal = Geral.geraData("dd/MM/yyyy", txtDataFinal.getText().toString());

                if (dataInicial == null && !txtDataInicial.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "A data inicial é inválida", Toast.LENGTH_LONG).show();
                    txtDataInicial.requestFocus();
                    return;
                }

                if (dataFinal == null && !txtDataFinal.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "A data final é inválida", Toast.LENGTH_LONG).show();
                    txtDataFinal.requestFocus();
                    return;
                }

                if(dataInicial != null && dataFinal != null && dataInicial.after(dataFinal))
                {
                    Toast.makeText(view.getContext(), "A data inicial é maior que a data final", Toast.LENGTH_LONG).show();
                    txtDataInicial.requestFocus();
                    return;
                }

                interfaceOrdemServicoPesquisa.atualizarOrdemServico(dataInicial, dataFinal);
                dialog.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
