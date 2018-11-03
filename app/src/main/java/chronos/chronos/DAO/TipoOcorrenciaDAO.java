package chronos.chronos.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import chronos.chronos.Model.TipoOcorrencia;

public abstract  class TipoOcorrenciaDAO {

    public static String getTableTipoocorrencia() {

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS TipoOcorrencia ( ");
        sql.append("       id           INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("       descricao    VARCHAR(100) NOT NULL, ");
        sql.append("       status       VARCHAR(1) NOT NULL) ");

        return sql.toString();
    }

    public static void inserir(TipoOcorrencia tipoOcorrencia) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("descricao", tipoOcorrencia.getDescricao());
        contentValues.put("status", tipoOcorrencia.getStatus().substring(0, 1));

        DadosOpenHelper.getConexao().insertOrThrow("TipoOcorrencia", null, contentValues);
    }

    public static ArrayList<TipoOcorrencia> retornaListaTipoOcorrencia() throws Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM TipoOcorrencia ORDER BY status, descricao COLLATE LOCALIZED;");

            Cursor resultado = DadosOpenHelper.getConexao().rawQuery(sql.toString(), null);

            ArrayList<TipoOcorrencia> listaTipoOcorrencia = new ArrayList<>();

            if(resultado.moveToFirst()) {
                do {
                    TipoOcorrencia tipoOcorrencia = new TipoOcorrencia();

                    tipoOcorrencia.setId(resultado.getString(resultado.getColumnIndexOrThrow("id")));
                    tipoOcorrencia.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricao")));

                    String status = resultado.getString(resultado.getColumnIndexOrThrow("status"));
                    if (status.equals("A"))
                        tipoOcorrencia.setStatus("Ativo");
                    else
                        tipoOcorrencia.setStatus("Cancelado");

                    listaTipoOcorrencia.add(tipoOcorrencia);
                } while (resultado.moveToNext());
            }

            return listaTipoOcorrencia;
        } catch (Exception ex) {
            throw new Exception("Erro ao retornar lista de tipos de ocorrência. Erro: " + ex.getMessage());
        }
    }

    public static boolean descricaoExiste(TipoOcorrencia tipoOcorrencia) throws Exception {
        try {
            String query = "SELECT COUNT(*) quantidade FROM TipoOcorrencia WHERE descricao='@@descricao'";

            if(tipoOcorrencia.getId() != null)
                query += " AND id <> " + tipoOcorrencia.getId();

            query = query.replace("@@descricao", tipoOcorrencia.getDescricao());

            Cursor resultado = DadosOpenHelper.getConexao().rawQuery(query, null);

            ArrayList<TipoOcorrencia> listaTipoOcorrencia = new ArrayList<>();

            resultado.moveToFirst();

            int quantidade = resultado.getInt(resultado.getColumnIndexOrThrow("quantidade"));

            if(quantidade >= 1)
                return true;

            return false;
        } catch (Exception ex) {
            throw new Exception("Erro ao verificar existência de descrição de tipo de ocorrência. Erro: " + ex.getMessage());
        }
    }

    public static void editar(TipoOcorrencia tipoOcorrencia) throws Exception {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("descricao", tipoOcorrencia.getDescricao());
            contentValues.put("status", tipoOcorrencia.getStatus().substring(0, 1));

            String[] parametros = new String[1];
            parametros[0] = String.valueOf(tipoOcorrencia.getId());

            DadosOpenHelper.getConexao().update("TipoOcorrencia", contentValues, "id = ?", parametros);
        } catch (Exception ex) {
            throw new Exception("Erro ao editar tipo de ocorrência. Erro: " + ex.getMessage());
        }
    }
}