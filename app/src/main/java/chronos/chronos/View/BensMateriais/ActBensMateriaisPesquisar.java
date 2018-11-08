package chronos.chronos.View.BensMateriais;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chronos.chronos.Controller.BemMaterialController;
import chronos.chronos.Model.BemMaterial;
import chronos.chronos.R;

public class ActBensMateriaisPesquisar extends AppCompatActivity {

    private ListView listViewBensMateriais;
    private ArrayList<BemMaterial> listaBemMaterial;

    private AppCompatActivity activityCompat;

    public static BemMaterial bemMaterialSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bens_materiais_pesquisar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bemMaterialSelecionado = null;
        activityCompat = this;

        configurarComponentes();
    }

    public void configurarComponentes() {
        listViewBensMateriais = (ListView) findViewById(R.id.listView_bensMateriais_act_bens_materiais_pesquisar);

        listaBemMaterial = new ArrayList<>();
        try {
            listaBemMaterial = BemMaterialController.retornaListaBemMaterial("status='A'");
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao pesquisar os bens materiais. Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ActBensMateriaisAdapter actBensMateriaisAdapter = new ActBensMateriaisAdapter(listaBemMaterial, this);

        listViewBensMateriais.setAdapter(actBensMateriaisAdapter);

        listViewBensMateriais.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                bemMaterialSelecionado = (BemMaterial) listViewBensMateriais.getAdapter().getItem(i);

                finish();

                return false;
            }
        });
    }

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_pesquisa_geral, menu);

        MenuItem item = menu.findItem(R.id.itemMenuPesquisar_menu_act_pesquisa_geral);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<BemMaterial> listAuxiliar = new ArrayList<>();

                for (BemMaterial bemMaterial: listaBemMaterial) {

                    if(bemMaterial.getDescricao().contains(newText))
                        listAuxiliar.add(bemMaterial);
                }

                ActBensMateriaisAdapter actBensMateriaisAdapter = new ActBensMateriaisAdapter(listAuxiliar,activityCompat);
                listViewBensMateriais.setAdapter(actBensMateriaisAdapter);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //endregion
}