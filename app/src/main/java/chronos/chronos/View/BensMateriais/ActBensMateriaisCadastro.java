package chronos.chronos.View.BensMateriais;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import chronos.chronos.Controller.BemMaterialController;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.R;

public class ActBensMateriaisCadastro extends AppCompatActivity {

    private BemMaterial bemMaterialEdicao;

    private EditText txtDescricao;
    private EditText txtLocalizacao;
    private Spinner spinnerStatusMaterialEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_bens_materiais_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bemMaterialEdicao = (BemMaterial) this.getIntent().getSerializableExtra("bemMaterialEdicao");

        configurarComponentes();

        if (bemMaterialEdicao != null) {

            this.setTitle("Edição");

            carregarDadosParaCampos(bemMaterialEdicao);
        }
    }

    //region METODOS

    public void configurarComponentes() {
        txtDescricao = (EditText) findViewById(R.id.txtDescricao_content_act_bens_materiais_cadastro);

        txtLocalizacao = (EditText) findViewById(R.id.txtLocalizacao_content_act_tipos_ocorrencias_cadastro);

        configurarspinnerStatus();

        configurarArrowBackMenu();
    }

    public void configurarspinnerStatus(){
        spinnerStatusMaterialEdicao = (Spinner) findViewById(R.id.spinnerStatusBemMaterial_content_act_tipos_ocorrencias_cadastro);
        String[] itens = new String[]{"Ativo", "Cancelado"};
        ArrayAdapter adapterTurno = new ArrayAdapter(this, R.layout.spinner_layout, itens);
        spinnerStatusMaterialEdicao.setAdapter(adapterTurno);
    }

    public void configurarArrowBackMenu() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void carregarDadosParaCampos(BemMaterial bemMaterial) {
        txtDescricao.setText(bemMaterial.getDescricao());

        txtLocalizacao.setText(bemMaterial.getLocalizacao());

        for (int i = 0; i < spinnerStatusMaterialEdicao.getAdapter().getCount(); i++) {

            if (bemMaterial.getStatus().equals(spinnerStatusMaterialEdicao.getAdapter().getItem(i))) {
                spinnerStatusMaterialEdicao.setSelection(i);
                break;
            }
        }
    }

    public void salvarBemMaterial() {
        try {
            BemMaterial bemMaterial = new BemMaterial();

            bemMaterial.setDescricao(txtDescricao.getText().toString());
            bemMaterial.setLocalizacao(txtLocalizacao.getText().toString());
            bemMaterial.setStatus(spinnerStatusMaterialEdicao.getSelectedItem().toString());

            if (bemMaterialEdicao != null)
                bemMaterial.setId(bemMaterialEdicao.getId());

            ErroValidacao ev = BemMaterialController.validarDados(bemMaterial);

            if (ev != null) {
                Toast.makeText(this, ev.getErroCampo(), Toast.LENGTH_LONG).show();

                if (ev.getNomeCampo().equals("descricao"))
                    txtDescricao.requestFocus();

                if (ev.getNomeCampo().equals("localizacao"))
                    txtLocalizacao.requestFocus();

                return;
            }

            if (bemMaterialEdicao == null)
                BemMaterialController.inserir(bemMaterial);
            else
                BemMaterialController.editar(bemMaterial);

            finish();
        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", "Erro ao salvar o bem material. Erro: " + ex.getMessage());
        }
    }

    //endregion

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_cadastro_geral, menu);

        if(bemMaterialEdicao != null) {
            menu.getItem(0).setTitle("Editar");

            menu.getItem(1).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.act_autenticacao_menu_title_cancelar: {
                finish();
                break;
            }

            case R.id.act_autenticacao_menu_title_salvar: {
                salvarBemMaterial();
                break;
            }

            case android.R.id.home: {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion
}