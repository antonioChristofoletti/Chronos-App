package chronos.chronos.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.Servico;

public abstract  class ServicoDAO {

    public static String getTableServico() {

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS Servico ( ");
        sql.append("       id           INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("       descricao    VARCHAR(100) NOT NULL, ");
        sql.append("       status       VARCHAR(1) NOT NULL) ");

        return sql.toString();
    }

    public static void inserir(Servico servico) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("descricao", servico.getDescricao());
        contentValues.put("status", servico.getStatus().substring(0, 1));

        DadosOpenHelper.getConexao().insertOrThrow("Servico", null, contentValues);
    }

    public static ArrayList<Servico> retornaListaServico(String whereStatus) throws Exception {
        try {
            String sql = "SELECT * FROM Servico WHERE @WHERE ORDER BY status, descricao";

            String where = "";
            if(!Geral.isCampoVazio(whereStatus))
                where += whereStatus + " AND ";

            where += " 1=1";

            sql = sql.replace("@WHERE", where);

            Cursor resultado = DadosOpenHelper.getConexao().rawQuery(sql, null);

            ArrayList<Servico> listaServico = new ArrayList<>();

            if (resultado.moveToFirst()) {
                do {
                    Servico servico = new Servico();

                    servico.setId(resultado.getString(resultado.getColumnIndexOrThrow("id")));
                    servico.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricao")));

                    String status = resultado.getString(resultado.getColumnIndexOrThrow("status"));
                    if (status.equals("A"))
                        servico.setStatus("Ativo");
                    else
                        servico.setStatus("Cancelado");

                    listaServico.add(servico);
                } while (resultado.moveToNext());
            }
            return listaServico;
        } catch (Exception ex) {
            throw new Exception("Erro ao retornar lista de serviços. Erro: " + ex.getMessage());
        }
    }

    public static boolean descricaoExiste(Servico servico) throws Exception {
        try {
            String query = "SELECT COUNT(*) quantidade FROM Servico WHERE descricao='@@descricao'";

            if(servico.getId() != null)
                query += " AND id <> " + servico.getId();

            query = query.replace("@@descricao", servico.getDescricao());

            Cursor resultado = DadosOpenHelper.getConexao().rawQuery(query, null);

            resultado.moveToFirst();

            int quantidade = resultado.getInt(resultado.getColumnIndexOrThrow("quantidade"));

            if(quantidade >= 1)
                return true;

            return false;
        } catch (Exception ex) {
            throw new Exception("Erro ao verificar existência da descrição do serviço. Erro: " + ex.getMessage());
        }
    }

    public static void editar(Servico servico) throws Exception {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("descricao", servico.getDescricao());
            contentValues.put("status", servico.getStatus().substring(0, 1));

            String[] parametros = new String[1];
            parametros[0] = String.valueOf(servico.getId());

            DadosOpenHelper.getConexao().update("Servico", contentValues, "id = ?", parametros);
        } catch (Exception ex) {
            throw new Exception("Erro ao editar serviço. Erro: " + ex.getMessage());
        }
    }
}