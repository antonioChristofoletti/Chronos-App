package chronos.chronos.View.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import chronos.chronos.Controller.UsuarioController;
import chronos.chronos.DAO.DadosOpenHelper;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Geral.MaskWatcher;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.Model.Usuario;
import chronos.chronos.R;
import chronos.chronos.View.Principal.ActPrincipal;

public class ActCadastroUsuario extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtNumeroTelefone;
    private EditText txtEmail;
    private Spinner spinnerTurno;

    private Usuario usuarioEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuarioEdicao = (Usuario) this.getIntent().getSerializableExtra("usuarioEdicao");

        if (usuarioEdicao == null) {

            this.setTitle("Cadastre-se");

            DadosOpenHelper.criarconexao(this);

            Usuario u = null;
            try {
                u = UsuarioController.retornaUsuario();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            if (u != null) {
                Intent intent = new Intent(this, ActPrincipal.class);
                startActivity(intent);
                finish();
            }

            configurarComponentes();
        }

        if (usuarioEdicao != null) {

            this.setTitle("Meu Perfil");

            configurarComponentes();

            carregarDadosParaCampos(usuarioEdicao);
        }
    }

    //region METODOS

    public void configurarComponentes() {
        txtNome = (EditText) findViewById(R.id.txtNome);

        txtEmail = (EditText) findViewById(R.id.txtEmail);

        txtNumeroTelefone = (EditText) findViewById(R.id.txtNumeroTelefone);
        txtNumeroTelefone.addTextChangedListener(new MaskWatcher(MaskWatcher.FORMAT_FONE));

        configurarSpinnerTurno();

        configurarArrowBackMenu();
    }

    public void configurarSpinnerTurno(){
        spinnerTurno = (Spinner) findViewById(R.id.spinnerTurno);
        String[] itemsTurno = new String[]{"A - Diurno", "B - Diurno", "C - Noturno", "D - Noturno"};
        ArrayAdapter adapterTurno = new ArrayAdapter(ActCadastroUsuario.this, R.layout.spinner_layout, itemsTurno);
        spinnerTurno.setAdapter(adapterTurno);
    }

    public void configurarArrowBackMenu() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void carregarDadosParaCampos(Usuario u) {
        txtEmail.setText(u.getEmail());

        txtNumeroTelefone.setText(Geral.setaMascara(u.getNumeroTelefone(), MaskWatcher.FORMAT_FONE));

        txtNome.setText(u.getNome());

        for (int i = 0; i < spinnerTurno.getAdapter().getCount(); i++) {

            if (u.getTurno().equals(spinnerTurno.getAdapter().getItem(i))) {
                spinnerTurno.setSelection(i);
                break;
            }
        }
    }

    public void salvarUsuario() {
        try {
            Usuario u = new Usuario();

            u.setNome(txtNome.getText().toString());
            u.setNumeroTelefone(Geral.removerMascara(txtNumeroTelefone.getText().toString(), MaskWatcher.FORMAT_FONE));
            u.setEmail(txtEmail.getText().toString());
            u.setTurno(spinnerTurno.getSelectedItem().toString());

            ErroValidacao ev = UsuarioController.validarDados(u);

            if(ev != null) {
                Toast.makeText(this, ev.getErroCampo(), Toast.LENGTH_LONG).show();

                if (ev.getNomeCampo().equals("nome"))
                    txtNome.requestFocus();

                if(ev.getNomeCampo().equals("email"))
                    txtEmail.requestFocus();

                if (ev.getNomeCampo().equals("numeroTelefone"))
                    txtNumeroTelefone.requestFocus();

                return;
            }

            if(usuarioEdicao == null) {
                UsuarioController.inserir(u);
                Intent intent = new Intent(this, ActPrincipal.class);
                startActivity(intent);
            }
            else {
                u.setId(usuarioEdicao.getId());

                UsuarioController.editar(u);
            }
            finish();
        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", "Erro ao salvar o usuário. Erro: " + ex.getMessage());
        }
    }

    //endregion

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_cadastro_geral, menu);

        if(usuarioEdicao != null) {
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
                salvarUsuario();
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