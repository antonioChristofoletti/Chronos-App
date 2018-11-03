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
        sql.append("       turno            VARCHAR(30) NOT NULL)");

        return sql.toString();
    }

    public static void inserir(Usuario usuario) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", usuario.getNome());
        contentValues.put("numeroTelefone", usuario.getNumeroTelefone());
        contentValues.put("email", usuario.getEmail());
        contentValues.put("turno", usuario.getTurno());

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

            Usuario usuario = new Usuario();

            usuario.setId(resultado.getString(resultado.getColumnIndexOrThrow("id")));
            usuario.setEmail(resultado.getString(resultado.getColumnIndexOrThrow("email")));
            usuario.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
            usuario.setNumeroTelefone(resultado.getString(resultado.getColumnIndexOrThrow("numeroTelefone")));

            usuario.setTurno(resultado.getString(resultado.getColumnIndexOrThrow("turno")));

            return usuario;
        }catch (Exception ex){
            throw new Exception("Erro ao retornar usuário. Erro: " + ex.getMessage());
        }
    }

    public static void editar(Usuario usuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", usuario.getNome());
        contentValues.put("numeroTelefone", usuario.getNumeroTelefone());
        contentValues.put("email", usuario.getEmail());
        contentValues.put("turno", usuario.getTurno());

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(usuario.getId());

        DadosOpenHelper.getConexao().update("Usuario", contentValues, "id = ?", parametros);
    }
}