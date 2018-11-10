package chronos.chronos.View.TiposOcorrencias;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import chronos.chronos.Controller.TipoOcorrenciaController;
import chronos.chronos.Model.TipoOcorrencia;
import chronos.chronos.R;

public class ActTiposOcorrencias extends AppCompatActivity {

    private ListView listViewTiposOcorrencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tipos_ocorrencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configurarComponentes();
    }

    @Override
    protected void onResume() {

        atualizarListViewTiposOcorrencias();

        super.onResume();
    }

    //region METODOS

    public void configurarComponentes() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton_act_tipos_ocorrencias);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActTiposOcorrencias.this, ActTiposOcorrenciasCadastro.class);
                startActivity(intent);
            }
        });


        listViewTiposOcorrencias = (ListView) findViewById(R.id.listView_bens_materiais_content_act_bens_materiais);

        listViewTiposOcorrencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TipoOcorrencia tipoOcorrencia =  (TipoOcorrencia) listViewTiposOcorrencias.getAdapter().getItem(i);

                Intent intent = new Intent(ActTiposOcorrencias.this, ActTiposOcorrenciasCadastro.class);
                intent.putExtra("tipoOcorrenciaEdicao",  tipoOcorrencia );

                startActivity(intent);
            }
        });

        configurarArrowBackMenu();

        atualizarListViewTiposOcorrencias();
    }

    public void configurarArrowBackMenu() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void atualizarListViewTiposOcorrencias() {
        ArrayList<TipoOcorrencia> listaTipoOcorrencia = new ArrayList<>();

        try {
            listaTipoOcorrencia = TipoOcorrenciaController.retornaListaTipoOcorrencia(null);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao retornar lista de tipo de ocorrência. Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ActTiposOcorrenciasAdapter actTiposOcorrenciasAdapter = new ActTiposOcorrenciasAdapter(listaTipoOcorrencia, this);
        listViewTiposOcorrencias.setAdapter(actTiposOcorrenciasAdapter);
    }

    //endregion

    //region EVENTOS MENU

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion
}