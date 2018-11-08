package chronos.chronos.View.TiposOcorrencias;

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

import chronos.chronos.Controller.TipoOcorrenciaController;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.Model.TipoOcorrencia;
import chronos.chronos.R;

public class ActTiposOcorrenciasCadastro extends AppCompatActivity {

    private TipoOcorrencia tipoOcorrenciaEdicao;

    private EditText txtDescricao;
    private Spinner spinnerStatusTipoOcorrencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tipos_ocorrencias_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tipoOcorrenciaEdicao = (TipoOcorrencia) this.getIntent().getSerializableExtra("tipoOcorrenciaEdicao");

        configurarComponentes();

        if (tipoOcorrenciaEdicao != null) {

            this.setTitle("Edição");

            carregarDadosParaCampos(tipoOcorrenciaEdicao);
        }
    }

    //region METODOS

    public void configurarComponentes() {
        txtDescricao = (EditText) findViewById(R.id.txtDescricao_content_act_tipos_ocorrencias_cadastro);

        configurarSpinnerTurno();
    }

    public void configurarSpinnerTurno(){
        spinnerStatusTipoOcorrencia = (Spinner) findViewById(R.id.spinnerStatusTipoOcorrencia_content_act_tipos_ocorrencias_cadastro);
        String[] itens = new String[]{"Ativo", "Cancelado"};
        ArrayAdapter adapterTurno = new ArrayAdapter(ActTiposOcorrenciasCadastro.this, R.layout.spinner_layout, itens);
        spinnerStatusTipoOcorrencia.setAdapter(adapterTurno);
    }

    public void carregarDadosParaCampos(TipoOcorrencia tipoOcorrencia) {
        txtDescricao.setText(tipoOcorrencia.getDescricao());

        for (int i = 0; i < spinnerStatusTipoOcorrencia.getAdapter().getCount(); i++) {

            if (tipoOcorrencia.getStatus().equals(spinnerStatusTipoOcorrencia.getAdapter().getItem(i))) {
                spinnerStatusTipoOcorrencia.setSelection(i);
                break;
            }
        }
    }

    public void salvarTipoOcorrencia() {
        try {
            TipoOcorrencia tipoOcorrencia = new TipoOcorrencia();

            tipoOcorrencia.setDescricao(txtDescricao.getText().toString());
            tipoOcorrencia.setStatus(spinnerStatusTipoOcorrencia.getSelectedItem().toString());

            if (tipoOcorrenciaEdicao != null)
                tipoOcorrencia.setId(tipoOcorrenciaEdicao.getId());

            ErroValidacao ev = TipoOcorrenciaController.validarDados(tipoOcorrencia);

            if (ev != null) {
                Toast.makeText(this, ev.getErroCampo(), Toast.LENGTH_LONG).show();

                if (ev.getNomeCampo().equals("descricao"))
                    txtDescricao.requestFocus();

                return;
            }

            if (tipoOcorrenciaEdicao == null)
                TipoOcorrenciaController.inserir(tipoOcorrencia);
            else
                TipoOcorrenciaController.editar(tipoOcorrencia);

            finish();
        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", "Erro ao salvar o tipo de ocorrência. Erro: " + ex.getMessage());
        }
    }

    //endregion

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_cadastro_geral, menu);

        if(tipoOcorrenciaEdicao != null) {
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
                salvarTipoOcorrencia();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion
}