package chronos.chronos.View.Principal.OrdemServico;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Date;

import chronos.chronos.Controller.OrdemServicoController;
import chronos.chronos.Interface.InterfaceInjecaoDependenciaOrdemServicoPesquisa;
import chronos.chronos.Model.OrdemServico;
import chronos.chronos.R;
import chronos.chronos.View.OrdemServico.ActOrdemServicoCadastro;
import chronos.chronos.View.OrdemServico.ActOrdemServicoPesquisarData;

public class FragActPrincipalos extends Fragment implements InterfaceInjecaoDependenciaOrdemServicoPesquisa {

    private View view;
    private AppCompatActivity appCompatActivity;

    FloatingActionMenu floatingActionButton_frag_ordemServico_frag_act_principal_os;
    com.github.clans.fab.FloatingActionButton floatingActionMenuInserir_frag_ordemServico_frag_act_principal_os;
    com.github.clans.fab.FloatingActionButton floatingActionMenuFiltra_frag_ordemServico_frag_act_principal_os;
    ListView listViewOS;

    Date dataInicial, dataFinal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_act_principal_os, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.view = view;
        this.appCompatActivity = (AppCompatActivity) this.getActivity();

        configurarComponentes(this.view);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        carregarOrdensDeServicoListView(dataInicial, dataFinal);
        super.onResume();
    }

    public void carregarOrdensDeServicoListView(Date dataInicialWhere, Date dataFinalWhere) {
        ArrayList<OrdemServico> listaOrdemServico = null;
        try {
            listaOrdemServico = OrdemServicoController.retornalistOrdemServico(dataInicialWhere, dataFinalWhere);
            FragActPrincipalosAdapter fragActPrincipalosAdapter = new FragActPrincipalosAdapter(listaOrdemServico, this.appCompatActivity);

            listViewOS.setAdapter(fragActPrincipalosAdapter);
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao retornar a lista de O.S. Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void configurarComponentes(View view) {
        listViewOS = (ListView) view.findViewById(R.id.listView_ordemServico_frag_act_principal_os);
        listViewOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OrdemServico ordemServico = (OrdemServico) listViewOS.getAdapter().getItem(i);

                Intent intent = new Intent(view.getContext(), ActOrdemServicoCadastro.class);
                intent.putExtra("ordemServicoEdicao", ordemServico);

                startActivity(intent);
            }
        });

        carregarOrdensDeServicoListView(dataInicial, dataFinal);

        configurar_floatingActionButton_frag_ordemServico_frag_act_principal_os();
    }

    //region CONFIGURACAO FLOATING ACTION BUTTON

    public void configurar_floatingActionButton_frag_ordemServico_frag_act_principal_os() {
        floatingActionButton_frag_ordemServico_frag_act_principal_os =
                (FloatingActionMenu) view.findViewById(R.id.floatingActionButton_frag_ordemServico_frag_act_principal_os);

        floatingActionMenuFiltra_frag_ordemServico_frag_act_principal_os =
                (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.floatingActionMenuFiltra_frag_ordemServico_frag_act_principal_os);

        floatingActionMenuFiltra_frag_ordemServico_frag_act_principal_os.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenuInserir_frag_ordemServico_frag_act_principal_os_onClick();
            }
        });

        floatingActionMenuInserir_frag_ordemServico_frag_act_principal_os =
                (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.floatingActionMenuInserir_frag_ordemServico_frag_act_principal_os);

        floatingActionMenuInserir_frag_ordemServico_frag_act_principal_os.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenuFiltra_frag_ordemServico_frag_act_principal_os_onClick();
            }
        });
    }

    public void floatingActionMenuInserir_frag_ordemServico_frag_act_principal_os_onClick() {
        ActOrdemServicoPesquisarData.invocarModalPesquisarOS(view, getActivity(), this);
    }

    public void floatingActionMenuFiltra_frag_ordemServico_frag_act_principal_os_onClick() {
        Intent intent = new Intent(view.getContext(), ActOrdemServicoCadastro.class);
        startActivity(intent);
    }

    //endregion

    //region INTERFACE

    @Override
    public void atualizarOrdemServico(Date dataInicialWhere, Date dataFinalWhere) {
        dataInicial = dataInicialWhere;
        dataFinal = dataFinalWhere;

        carregarOrdensDeServicoListView(dataInicialWhere, dataFinalWhere);
    }

    //endregion
}