package chronos.chronos.View.Principal.OrdemServico;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chronos.chronos.Controller.OrdemServicoController;
import chronos.chronos.Model.OrdemServico;
import chronos.chronos.R;
import chronos.chronos.View.OrdemServico.ActOrdemServicoCadastro;

public class FragActPrincipalos extends Fragment {

    private View view;
    private AppCompatActivity appCompatActivity;

    FloatingActionButton floatingActionButtonCadastrarOS;
    ListView listViewOS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_act_principal_os, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.view = view;
        this.appCompatActivity = (AppCompatActivity)this.getActivity();

        configurarComponentes(this.view);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        carregarOrdensDeServicoListView();
        super.onResume();
    }

    public void carregarOrdensDeServicoListView() {
        ArrayList<OrdemServico> listaOrdemServico = null;
        try {
            listaOrdemServico = OrdemServicoController.retornalistOrdemServico();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao retornar a lista de O.S. Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        FragActPrincipalosAdapter fragActPrincipalosAdapter = new FragActPrincipalosAdapter(listaOrdemServico, this.appCompatActivity);

        listViewOS.setAdapter(fragActPrincipalosAdapter);
    }

    public void configurarComponentes(View view) {
        floatingActionButtonCadastrarOS = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_frag_ordemServico_frag_act_principal_os);

        floatingActionButtonCadastrarOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActOrdemServicoCadastro.class);
                startActivity(intent);
            }
        });

        listViewOS = (ListView) view.findViewById(R.id.listView_ordemServico_frag_act_principal_os);

        carregarOrdensDeServicoListView();
    }
}