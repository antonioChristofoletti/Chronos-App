package chronos.chronos.View.Servicos;

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

import chronos.chronos.Controller.BemMaterialController;
import chronos.chronos.Controller.ServicoController;
import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.Servico;
import chronos.chronos.R;
import chronos.chronos.View.BensMateriais.ActBensMateriaisAdapter;

public class ActServicos extends AppCompatActivity {

    private ListView listViewServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_servicos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configurarComponentes();
    }

    @Override
    protected void onResume() {

        atualizarListViewBensServicos();

        super.onResume();
    }

    public void configurarComponentes(){
        listViewServico = (ListView) findViewById(R.id.listView_servicos_content_act_servicos);

        listViewServico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Servico servico = (Servico) listViewServico.getAdapter().getItem(i);

                Intent intent = new Intent(ActServicos.this, ActServicosCadastro.class);
                intent.putExtra("servicoEdicao", servico);

                startActivity(intent);
            }
        });

        atualizarListViewBensServicos();

        configurarArrowBackMenu();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton_act_servicos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActServicos.this, ActServicosCadastro.class);
                startActivity(intent);
            }
        });
    }

    public void configurarArrowBackMenu() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void atualizarListViewBensServicos() {
        ArrayList<Servico> listaServicos = new ArrayList<>();

        try {
            listaServicos = ServicoController.retornaListaServico(null);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao retornar lista de serviços. Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ActServicosAdapter actServicosAdapter = new ActServicosAdapter(listaServicos, this);
        listViewServico.setAdapter(actServicosAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}