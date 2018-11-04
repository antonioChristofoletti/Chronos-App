package chronos.chronos.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.OrdemServico;

public abstract  class OrdemServicoDAO {

    public static String getTableOrdemServico() {

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS OrdemServico ( ");
        sql.append("       id               INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("       dataInicial      DATETIME NOT NULL, ");
        sql.append("       dataFinal        DATETIME NOT NULL, ");
        sql.append("       observacao       VARCHAR(200) NOT NULL,");
        sql.append("       idTipoOcorrencia INTEGER NOT NULL,");
        sql.append("       idUsuario        INTEGER NOT NULL,");
        sql.append("       idBemMaterial    INTEGER NOT NULL,");
        sql.append("       status           VARCHAR(1) NOT NULL)");

        return sql.toString();
    }

    public static void inserir(OrdemServico ordemServico) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("dataInicial", ordemServico.getDataInicial().toString());
        contentValues.put("dataFinal", ordemServico.getDataFinal().toString());
        contentValues.put("observacao", ordemServico.getObservacao());
        contentValues.put("idTipoOcorrencia", ordemServico.getTipoOcorrencia().getId());
        contentValues.put("idUsuario", ordemServico.getUsuario().getId());
        contentValues.put("idBemMaterial", ordemServico.getBemMaterial().getId());
        contentValues.put("status", ordemServico.getStatus());

        DadosOpenHelper.getConexao().insertOrThrow("OrdemServico", null, contentValues);
    }

    public static ArrayList<BemMaterial> retornaListaBemMaterial() throws Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM BemMaterial ORDER BY status, localizacao, descricao");

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