package chronos.chronos.View.Servicos;

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
import chronos.chronos.Controller.ServicoController;
import chronos.chronos.DAO.BemMaterialDAO;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.Model.Servico;
import chronos.chronos.R;

public class ActServicosCadastro extends AppCompatActivity {

    private Servico servicoEdicao;

    private EditText txtDescricao;
    private Spinner spinnerStatusServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_servicos_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        servicoEdicao = (Servico) this.getIntent().getSerializableExtra("servicoEdicao");

        configurarComponentes();

        if (servicoEdicao != null) {

            this.setTitle("Edição");

            carregarDadosParaCampos(servicoEdicao);
        }
    }

    //region METODOS

    public void configurarComponentes() {
        txtDescricao = (EditText) findViewById(R.id.txtDescricao_content_act_servicos_cadastro);

        configurarspinnerStatus();
    }

    public void configurarspinnerStatus(){
        spinnerStatusServico = (Spinner) findViewById(R.id.spinnerStatusServico_content_act_servicos_cadastro);
        String[] itens = new String[]{"Ativo", "Cancelado"};
        ArrayAdapter adapterTurno = new ArrayAdapter(this, R.layout.spinner_layout, itens);
        spinnerStatusServico.setAdapter(adapterTurno);
    }

    public void carregarDadosParaCampos(Servico servico) {
        txtDescricao.setText(servico.getDescricao());

        for (int i = 0; i < spinnerStatusServico.getAdapter().getCount(); i++) {

            if (servico.getStatus().equals(spinnerStatusServico.getAdapter().getItem(i))) {
                spinnerStatusServico.setSelection(i);
                break;
            }
        }
    }

    public void salvarServico() {
        try {
            Servico servico = new Servico();

            servico.setDescricao(txtDescricao.getText().toString());
            servico.setStatus(spinnerStatusServico.getSelectedItem().toString());

            if (servicoEdicao != null)
                servico.setId(servicoEdicao.getId());

            ErroValidacao ev = ServicoController.validarDados(servico);

            if (ev != null) {
                Toast.makeText(this, ev.getErroCampo(), Toast.LENGTH_LONG).show();

                if (ev.getNomeCampo().equals("descricao"))
                    txtDescricao.requestFocus();

                return;
            }

            if (servicoEdicao == null)
                ServicoController.inserir(servico);
            else
                ServicoController.editar(servico);

            finish();
        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", "Erro ao salvar o serviço. Erro: " + ex.getMessage());
        }
    }

    //endregion

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_cadastro_geral, menu);

        if(servicoEdicao != null) {
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
                salvarServico();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion
}