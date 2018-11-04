package chronos.chronos.View.Principal.OrdemServico;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chronos.chronos.R;
import chronos.chronos.View.OrdemServico.ActOrdemServicoCadastro;

public class FragActPrincipalos extends Fragment {

    private View view;
    FloatingActionButton floatingActionButtonCadastrarOS;
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

        configurarComponentes(this.view);

        super.onViewCreated(view, savedInstanceState);
    }

    public void configurarComponentes(View view){
        floatingActionButtonCadastrarOS = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_frag_ordemServico_frag_act_principal_os);

        floatingActionButtonCadastrarOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActOrdemServicoCadastro.class);
                startActivity(intent);
            }
        });
    }
}