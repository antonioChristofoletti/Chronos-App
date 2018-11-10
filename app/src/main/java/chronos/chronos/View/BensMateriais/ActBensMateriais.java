package chronos.chronos.View.BensMateriais;

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
import chronos.chronos.Model.BemMaterial;
import chronos.chronos.R;

public class ActBensMateriais extends AppCompatActivity {

    private ListView listViewBensMateriais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bens_materiais);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configurarComponentes();
    }

    @Override
    protected void onResume() {

        atualizarListViewBensMateriais();

        super.onResume();
    }

    //region METODOS

    public void configurarComponentes() {
        listViewBensMateriais = (ListView) findViewById(R.id.listView_bens_materiais_content_act_bens_materiais);

        listViewBensMateriais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                BemMaterial bemMaterial = (BemMaterial) listViewBensMateriais.getAdapter().getItem(i);

                Intent intent = new Intent(ActBensMateriais.this, ActBensMateriaisCadastro.class);
                intent.putExtra("bemMaterialEdicao", bemMaterial);

                startActivity(intent);
            }
        });

        atualizarListViewBensMateriais();

        configurarArrowBackMenu();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton_act_bens_Materiais);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActBensMateriais.this, ActBensMateriaisCadastro.class);
                startActivity(intent);
            }
        });
    }

    public void atualizarListViewBensMateriais() {
        ArrayList<BemMaterial> listaBensMateriais = new ArrayList<>();

        try {
            listaBensMateriais = BemMaterialController.retornaListaBemMaterial(null);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao retornar lista de bens materiais. Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ActBensMateriaisAdapter actBensMateriaisAdapter = new ActBensMateriaisAdapter(listaBensMateriais, this);
        listViewBensMateriais.setAdapter(actBensMateriaisAdapter);
    }

    public void configurarArrowBackMenu() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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