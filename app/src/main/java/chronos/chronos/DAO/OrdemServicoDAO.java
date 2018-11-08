package chronos.chronos.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;

import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.OrdemServico;
import chronos.chronos.Model.TipoOcorrencia;
import chronos.chronos.Model.Usuario;

public abstract  class OrdemServicoDAO {

    public static String getTableOrdemServico() {

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS OrdemServico ( ");
        sql.append("       id               INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("       dataInicial      DATETIME, ");
        sql.append("       dataFinal        DATETIME, ");
        sql.append("       observacao       VARCHAR(200),");
        sql.append("       idTipoOcorrencia INTEGER NOT NULL,");
        sql.append("       idUsuario        INTEGER NOT NULL,");
        sql.append("       idBemMaterial    INTEGER NOT NULL,");
        sql.append("       status           VARCHAR(1) NOT NULL)");

        return sql.toString();
    }

    public static void inserir(OrdemServico ordemServico) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("dataInicial", Geral.formataData("yyyy-MM-dd HH:mm:ss", ordemServico.getDataInicial()));
        contentValues.put("dataFinal", Geral.formataData("yyyy-MM-dd HH:mm:ss", ordemServico.getDataFinal()));
        contentValues.put("observacao", ordemServico.getObservacao());
        contentValues.put("idTipoOcorrencia", ordemServico.getTipoOcorrencia().getId());
        contentValues.put("idUsuario", ordemServico.getUsuario().getId());
        contentValues.put("idBemMaterial", ordemServico.getBemMaterial().getId());
        contentValues.put("status", ordemServico.getStatus().substring(0,1));

        DadosOpenHelper.getConexao().insertOrThrow("OrdemServico", null, contentValues);
    }

    public static ArrayList<OrdemServico> retornalistOrdemServico() throws Exception {
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT a.* FROM (SELECT os.id idOS, (strftime('%d/%m/%Y %H:%M:%S', datetime(os.dataInicial))) dataInicial, (strftime('%d/%m/%Y %H:%M:%S', datetime(os.dataFinal))) dataFinal, os.observacao, os.status statusOS, ");

            sql.append("bm.id idBemMaterial, bm.descricao descricaoBemMaterial, bm.localizacao localizacaoBemMaterial, bm.status statusBemMaterial,");

            sql.append("tio.id idTipoOcorrencia, tio.descricao descricaoTipoOcorrencia, tio.status statusTipoOcorrencia,");

            sql.append("u.id idUsuario, u.nome nomeUsuario, ");

            sql.append("CASE WHEN os.status = 'C' THEN 1 " +
                            "WHEN os.dataInicial IS NOT NULL AND os.dataFinal IS NOT NULL THEN 2 " +
                            "WHEN os.dataInicial IS NOT NULL AND os.dataFinal IS NULL THEN 3 " +
                            "ELSE 4 END ordenacao FROM OrdemServico os ");

            sql.append("INNER JOIN TipoOcorrencia tio ON tio.id = os.idTipoOcorrencia ");
            sql.append("INNER JOIN BemMaterial bm ON bm.id = os.idBemMaterial ");
            sql.append("INNER JOIN Usuario u ON u.id = os.idUsuario) a ORDER BY a.ordenacao DESC");

            Cursor resultado = DadosOpenHelper.getConexao().rawQuery(sql.toString(), null);

            ArrayList<OrdemServico> listaOrdemServico = new ArrayList<>();

            if (resultado.moveToFirst()) {
                do {
                    //region OS

                    OrdemServico os = new OrdemServico();

                    os.setId(resultado.getString(resultado.getColumnIndexOrThrow("idOS")));

                    String dataInicial = resultado.getString(resultado.getColumnIndexOrThrow("dataInicial"));
                    os.setDataInicial(Geral.geraData("dd/MM/yyyy HH:mm:ss", dataInicial));

                    String dataFinal = resultado.getString(resultado.getColumnIndexOrThrow("dataFinal"));
                    os.setDataFinal(Geral.geraData("dd/MM/yyyy HH:mm:ss", dataFinal));

                    os.setObservacao(resultado.getString(resultado.getColumnIndexOrThrow("observacao")));

                    os.setId(resultado.getString(resultado.getColumnIndexOrThrow("idOS")));

                    String statusOS = resultado.getString(resultado.getColumnIndexOrThrow("statusOS"));
                    if (statusOS.equals("A"))
                        os.setStatus("Ativo");
                    else
                        os.setStatus("Cancelado");

                    //endregion

                    //region BEM MATERIAL

                    BemMaterial bemMaterial = new BemMaterial();
                    bemMaterial.setId(resultado.getString(resultado.getColumnIndexOrThrow("idBemMaterial")));
                    bemMaterial.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricaoBemMaterial")));
                    bemMaterial.setLocalizacao(resultado.getString(resultado.getColumnIndexOrThrow("localizacaoBemMaterial")));
                    String statusBemMaterial = resultado.getString(resultado.getColumnIndexOrThrow("statusBemMaterial"));
                    if (statusBemMaterial.equals("A"))
                        bemMaterial.setStatus("Ativo");
                    else
                        bemMaterial.setStatus("Cancelado");

                    //endregion

                    //region TIPO OCORRENCIA

                    TipoOcorrencia tipoOcorrencia = new TipoOcorrencia();
                    tipoOcorrencia.setId(resultado.getString(resultado.getColumnIndexOrThrow("idTipoOcorrencia")));
                    tipoOcorrencia.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricaoTipoOcorrencia")));
                    String status = resultado.getString(resultado.getColumnIndexOrThrow("statusTipoOcorrencia"));
                    if (status.equals("A"))
                        tipoOcorrencia.setStatus("Ativo");
                    else
                        tipoOcorrencia.setStatus("Cancelado");

                    os.setTipoOcorrencia(tipoOcorrencia);

                    //endregion

                    //region USUARIO

                    Usuario usuario = new Usuario();

                    usuario.setId(resultado.getString(resultado.getColumnIndexOrThrow("idUsuario")));
                    usuario.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nomeUsuario")));

                    //endregion

                    os.setBemMaterial(bemMaterial);

                    os.setTipoOcorrencia(tipoOcorrencia);

                    os.setUsuario(usuario);

                    listaOrdemServico.add(os);

                } while (resultado.moveToNext());
            }

            return listaOrdemServico;
        } catch (Exception ex) {
            throw new Exception("Erro ao retornar lista de ordens de serviço. Erro: " + ex.getMessage());
        }
    }

    /*public static void editar(BemMaterial bemMaterial) throws Exception {
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
    }*/
}