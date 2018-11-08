package chronos.chronos.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.BemMaterial;

public abstract  class BemMaterialDAO {

    public static String getTableBemMaterial() {

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS BemMaterial ( ");
        sql.append("       id           INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("       descricao    VARCHAR(100) NOT NULL, ");
        sql.append("       localizacao  VARCHAR(100) NOT NULL, ");
        sql.append("       status       VARCHAR(1) NOT NULL) ");

        return sql.toString();
    }

    public static void inserir(BemMaterial bemMaterial) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("descricao", bemMaterial.getDescricao());
        contentValues.put("localizacao", bemMaterial.getLocalizacao());
        contentValues.put("status", bemMaterial.getStatus().substring(0, 1));

        DadosOpenHelper.getConexao().insertOrThrow("BemMaterial", null, contentValues);
    }

    public static ArrayList<BemMaterial> retornaListaBemMaterial(String whereStatus) throws Exception {
        try {
            String sql = "SELECT * FROM BemMaterial WHERE @WHERE ORDER BY status, localizacao, descricao";

            String where = "";
            if(!Geral.isCampoVazio(whereStatus))
                where += whereStatus + " AND ";

            where += " 1=1";

            sql = sql.replace("@WHERE", where);

            Cursor resultado = DadosOpenHelper.getConexao().rawQuery(sql.toString(), null);

            ArrayList<BemMaterial> listaBemMaterial = new ArrayList<>();

            if (resultado.moveToFirst()) {
                do {
                    BemMaterial bemMaterial = new BemMaterial();

                    bemMaterial.setId(resultado.getString(resultado.getColumnIndexOrThrow("id")));
                    bemMaterial.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricao")));
                    bemMaterial.setLocalizacao(resultado.getString(resultado.getColumnIndexOrThrow("localizacao")));

                    String status = resultado.getString(resultado.getColumnIndexOrThrow("status"));
                    if (status.equals("A"))
                        bemMaterial.setStatus("Ativo");
                    else
                        bemMaterial.setStatus("Cancelado");

                    listaBemMaterial.add(bemMaterial);
                } while (resultado.moveToNext());
            }
            return listaBemMaterial;
        } catch (Exception ex) {
            throw new Exception("Erro ao retornar lista de bens materiais. Erro: " + ex.getMessage());
        }
    }

    public static boolean descricaoExiste(BemMaterial bemMaterial) throws Exception {
        try {
            String query = "SELECT COUNT(*) quantidade FROM BemMaterial WHERE descricao='@@descricao' AND localizacao='@@localizacao'";

            if(bemMaterial.getId() != null)
                query += " AND id <> " + bemMaterial.getId();

            query = query.replace("@@descricao", bemMaterial.getDescricao());

            query = query.replace("@@localizacao", bemMaterial.getLocalizacao());

            Cursor resultado = DadosOpenHelper.getConexao().rawQuery(query, null);

            resultado.moveToFirst();

            int quantidade = resultado.getInt(resultado.getColumnIndexOrThrow("quantidade"));

            if(quantidade >= 1)
                return true;

            return false;
        } catch (Exception ex) {
            throw new Exception("Erro ao verificar existência de descrição do bem material. Erro: " + ex.getMessage());
        }
    }

    public static void editar(BemMaterial bemMaterial) throws Exception {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("descricao", bemMaterial.getDescricao());
            contentValues.put("localizacao", bemMaterial.getLocalizacao());
            contentValues.put("status", bemMaterial.getStatus().substring(0, 1));

            String[] parametros = new String[1];
            parametros[0] = String.valueOf(bemMaterial.getId());

            DadosOpenHelper.getConexao().update("BemMaterial", contentValues, "id = ?", parametros);
        } catch (Exception ex) {
            throw new Exception("Erro ao editar bem material. Erro: " + ex.getMessage());
        }
    }
}