package chronos.chronos.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.Usuario;

public abstract  class UsuarioDAO {

    public static String getTableUsuario() {

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS Usuario ( ");
        sql.append("       id               INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("       nome             VARCHAR(100) NOT NULL, ");
        sql.append("       numeroTelefone   VARCHAR(20) NOT NULL, ");
        sql.append("       email            VARCHAR(30) NOT NULL, ");
        sql.append("       turno            VARCHAR(1) NOT NULL)");

        return sql.toString();
    }

    public static void inserir(Usuario u) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", u.getNome());
        contentValues.put("numeroTelefone", u.getNumeroTelefone());
        contentValues.put("email", u.getEmail());
        contentValues.put("turno", u.getTurno().substring(0,1));

        DadosOpenHelper.getConexao().insertOrThrow("Usuario",null,contentValues);
    }

    public static Usuario retornaUsuario() throws Exception {
        try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM usuario");

            Cursor resultado = DadosOpenHelper.getConexao().rawQuery(sql.toString(), null);

            if(resultado.getCount() <= 0) {
                return null;
            }

            resultado.moveToFirst();

            Usuario usuarioCliente = new Usuario();

            usuarioCliente.setId(resultado.getString(resultado.getColumnIndexOrThrow("id")));
            usuarioCliente.setEmail(resultado.getString(resultado.getColumnIndexOrThrow("email")));
            usuarioCliente.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));

            usuarioCliente.setTurno(resultado.getString(resultado.getColumnIndexOrThrow("turno")));

            return usuarioCliente;
        }catch (Exception ex){
            throw new Exception("Erro ao retornar usuário. Erro: " + ex.getMessage());
        }
    }

    /*
    public static void alterar(UsuarioCliente uc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", uc.getId());
        contentValues.put("nome", uc.getNome());
        contentValues.put("cpf", uc.getCpf());
        contentValues.put("dataNascimento", uc.getDataNascimento().toString());

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(uc.getId());

        DadosOpenHelper.getConexao().update("UsuarioCliente", contentValues, "id = ?", parametros);
    }

   */
}