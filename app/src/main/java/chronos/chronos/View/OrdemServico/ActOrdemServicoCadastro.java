package chronos.chronos.View.OrdemServico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import chronos.chronos.Controller.OrdemServicoController;
import chronos.chronos.Controller.TipoOcorrenciaController;
import chronos.chronos.Controller.UsuarioController;
import chronos.chronos.DAO.OrdemServicoDAO;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Geral.MaskWatcher;
import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.Model.OrdemServico;
import chronos.chronos.Model.TipoOcorrencia;
import chronos.chronos.R;
import chronos.chronos.View.BensMateriais.ActBensMateriaisPesquisar;

public class ActOrdemServicoCadastro extends AppCompatActivity {

    private OrdemServico ordemServicoEdicao;
    private BemMaterial bemMaterialPesquisado;
    private ArrayList<TipoOcorrencia> listaTipoOcorrencia;

    private EditText txtDataInicial;
    private EditText txtDataFinal;
    private EditText txtBemMaterial;
    private EditText txtObservacao;

    private Spinner spinnerStatusOS;
    private Spinner spinnerTipoOcorrencia;

    private ImageView imageViewButton_pesquisarBemMaterial;

    private String aguardandoRetorno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ordem_servico_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ordemServicoEdicao = (OrdemServico) this.getIntent().getSerializableExtra("ordemServicoEdicao");

        aguardandoRetorno = "";

        configurarComponentes();

        if (ordemServicoEdicao != null) {

            this.setTitle("Edição");

            //  carregarDadosParaCampos(ordemServicoEdicao);
        }
    }

    @Override
    protected void onResume() {

        if (aguardandoRetorno == null)
            return;

        switch (aguardandoRetorno) {
            case "Bem Material Pesquisa": {

                if (ActBensMateriaisPesquisar.bemMaterialSelecionado != null) {

                    this.bemMaterialPesquisado = ActBensMateriaisPesquisar.bemMaterialSelecionado;

                    txtBemMaterial.setText(this.bemMaterialPesquisado.getDescricao());

                    ActBensMateriaisPesquisar.bemMaterialSelecionado = null;
                }

                break;
            }
        }

        aguardandoRetorno = null;

        super.onResume();
    }

    //region METODOS

    public void configurarComponentes() {
        txtDataInicial = (EditText) findViewById(R.id.txtDataInicial_act_ordem_servico_cadastro);
        txtDataInicial.addTextChangedListener(new MaskWatcher(MaskWatcher.FORMAT_DATE));

        txtDataFinal = (EditText) findViewById(R.id.txtDataFinal_act_ordem_servico_cadastro);
        txtDataFinal.addTextChangedListener(new MaskWatcher(MaskWatcher.FORMAT_DATE));

        txtBemMaterial = (EditText) findViewById(R.id.txtBemMaterial_content_act_ordem_servico_cadastro);

        txtObservacao = (EditText) findViewById(R.id.txtObservacao_content_act_ordem_servico_cadastro);

        imageViewButton_pesquisarBemMaterial = (ImageView) findViewById(R.id.imageView_pesquisarBemMaterial_content_act_ordem_servico_cadastro);
        imageViewButton_pesquisarBemMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aguardandoRetorno = "Bem Material Pesquisa";
                Intent intent = new Intent(ActOrdemServicoCadastro.this, ActBensMateriaisPesquisar.class);
                startActivity(intent);
            }
        });

        configurarspinnerStatus();
        configurarspinnerTipoOcorrencia();
    }

    public void configurarspinnerStatus() {
        spinnerStatusOS = (Spinner) findViewById(R.id.spinnerStatus_content_act_ordem_servico_cadastro);
        String[] itens = new String[]{"Ativo", "Cancelado"};
        ArrayAdapter adapterTurno = new ArrayAdapter(this, R.layout.spinner_layout, itens);
        spinnerStatusOS.setAdapter(adapterTurno);
    }

    public void configurarspinnerTipoOcorrencia() {
        try {
            spinnerTipoOcorrencia = (Spinner) findViewById(R.id.spinnerTipoOcorrencia_content_act_ordem_servico_cadastro);

            listaTipoOcorrencia = TipoOcorrenciaController.retornaListaTipoOcorrencia("status = 'A'");

            if (listaTipoOcorrencia.size() == 0)
                Geral.chamarAlertDialog(this, "Aviso", "Não há tipos de ocorrências cadastradas como ativas. Não será possível cadastrar ordens de serviço.");

            String[] itens = new String[listaTipoOcorrencia.size()];
            int iTipoOcorrencia = 0;
            for (TipoOcorrencia tipoOcorrencia : listaTipoOcorrencia)
                itens[iTipoOcorrencia++] = tipoOcorrencia.getDescricao();

            ArrayAdapter adapterTurno = new ArrayAdapter(this, R.layout.spinner_layout, itens);
            spinnerTipoOcorrencia.setAdapter(adapterTurno);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao carregar os tipos de ocorrência. Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /*public void carregarDadosParaCampos(BemMaterial bemMaterial) {
        txtDescricao.setText(bemMaterial.getDescricao());

        txtLocalizacao.setText(bemMaterial.getLocalizacao());

        for (int i = 0; i < spinnerStatusMaterialEdicao.getAdapter().getCount(); i++) {

            if (bemMaterial.getStatus().equals(spinnerStatusMaterialEdicao.getAdapter().getItem(i))) {
                spinnerStatusMaterialEdicao.setSelection(i);
                break;
            }
        }
    }*/

    public void salvarOrdemServico() {
        try {
            OrdemServico ordemServico = new OrdemServico();

            ordemServico.setDataInicial(Geral.geraData("dd/MM/yyyy HH:mm", txtDataInicial.getText().toString()));

            ordemServico.setDataFinal(Geral.geraData("dd/MM/yyyy HH:mm", txtDataFinal.getText().toString()));

            ordemServico.setBemMaterial(bemMaterialPesquisado);

            ordemServico.setObservacao(txtObservacao.getText().toString());

            ordemServico.setStatus(spinnerStatusOS.getSelectedItem().toString());

            if (listaTipoOcorrencia.size() != 0)
                ordemServico.setTipoOcorrencia(listaTipoOcorrencia.get(spinnerTipoOcorrencia.getSelectedItemPosition()));

            ordemServico.setUsuario(UsuarioController.retornaUsuario());

            if (ordemServicoEdicao != null)
                ordemServico.setId(ordemServicoEdicao.getId());

            ErroValidacao ev = OrdemServicoController.validarDados(ordemServico);

            if (ev != null) {
                Toast.makeText(this, ev.getErroCampo(), Toast.LENGTH_LONG).show();

                if (ev.getNomeCampo().equals("dataInicial"))
                    txtDataInicial.requestFocus();

                if (ev.getNomeCampo().equals("bemMaterial"))
                    txtBemMaterial.requestFocus();

                return;
            }

            if (ordemServicoEdicao == null)
                OrdemServicoController.inserir(ordemServico);
           /* else
                OrdemServicoController.editar(ordemServico);*/

            finish();
        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", "Erro ao salvar a ordem de serviço. Erro: " + ex.getMessage());
        }
    }

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_cadastro_geral, menu);

        if (ordemServicoEdicao != null) {
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
                salvarOrdemServico();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion
}