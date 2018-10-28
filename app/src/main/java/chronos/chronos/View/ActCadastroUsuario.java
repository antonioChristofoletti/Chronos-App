package chronos.chronos.View;

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
import chronos.chronos.DAO.UsuarioDAO;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("Cadastre-se");

        DadosOpenHelper.criarconexao(this);

        Usuario u = null;
        try {
            u = UsuarioController.retornaUsuario();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if(u != null){
            finish();
            Intent intent = new Intent(this, ActPrincipal.class);
            startActivity(intent);
        }

        configurarComponentes();
    }

    //region METODOS

    public void configurarComponentes() {
        txtNome = (EditText) findViewById(R.id.txtNome);

        txtEmail = (EditText) findViewById(R.id.txtEmail);

        txtNumeroTelefone = (EditText) findViewById(R.id.txtNumeroTelefone);
        txtNumeroTelefone.addTextChangedListener(new MaskWatcher(MaskWatcher.FORMAT_FONE));

        configurarSpinnerTurno();
    }

    public void configurarSpinnerTurno(){
        spinnerTurno = (Spinner) findViewById(R.id.spinnerTurno);
        String[] itemsTurno = new String[]{"A - Diurno", "B - Diurno", "C - Noturno", "D - Noturno"};
        ArrayAdapter adapterTurno = new ArrayAdapter(ActCadastroUsuario.this, R.layout.spinner_layout, itemsTurno);
        spinnerTurno.setAdapter(adapterTurno);
    }

    public void salvarUsuario() {
        try {
            Usuario u = new Usuario();

            u.setNome(txtNome.getText().toString());
            u.setNumeroTelefone(Geral.removerMascara(txtNumeroTelefone.getText().toString()));
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

            UsuarioDAO.inserir(u);

            finish();
        } catch (Exception ex) {
            Geral.chamarAlertDialog(this, "Erro", "Erro ao inserir usuário. Erro: " + ex.getMessage());
        }
    }

    //endregion

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.act_act_inc_alt, menu);

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
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion

}