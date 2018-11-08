package chronos.chronos.View.Principal.Cadastros;

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

import chronos.chronos.View.BensMateriais.ActBensMateriais;
import chronos.chronos.View.TiposOcorrencias.ActTiposOcorrencias;
import chronos.chronos.R;

public class FragActPrincipalCadastros extends Fragment {

    private View view;
    private AppCompatActivity appCompatActivity;

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_act_principal_cadastros, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view = view;

        this.appCompatActivity = (AppCompatActivity) this.getActivity();

        configuraComponentes();
    }

    public void configuraComponentes() {
        listView = (ListView) view.findViewById(R.id.listView_frag_act_principal_cadastros);

        final FragActPrincipalCadastrosAdapter fragActPrincipalCadastrosAdapter = new FragActPrincipalCadastrosAdapter(this.appCompatActivity);

        listView.setAdapter(fragActPrincipalCadastrosAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String titulo = fragActPrincipalCadastrosAdapter.titulos[position];

                switch (titulo) {
                    case "Tipos Ocorrências": {
                        Intent intent = new Intent(appCompatActivity, ActTiposOcorrencias.class);
                        startActivity(intent);
                        break;
                    }

                    case "Bens Materiais": {
                        Intent intent = new Intent(appCompatActivity, ActBensMateriais.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }
}
