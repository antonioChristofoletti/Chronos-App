package chronos.chronos.Controller;

import java.util.ArrayList;

import chronos.chronos.DAO.TipoOcorrenciaDAO;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.Model.TipoOcorrencia;

public abstract class TipoOcorrenciaController {
    public static void inserir(TipoOcorrencia tipoOcorrencia) throws Exception {
        TipoOcorrenciaDAO.inserir(tipoOcorrencia);
    }

    public static void editar(TipoOcorrencia tipoOcorrencia) throws Exception {
        TipoOcorrenciaDAO.editar(tipoOcorrencia);
    }

    public static ArrayList<TipoOcorrencia>  retornaListaTipoOcorrencia(String whereStatus) throws Exception {
        return TipoOcorrenciaDAO.retornaListaTipoOcorrencia(whereStatus);
    }

    public static ErroValidacao validarDados(TipoOcorrencia tipoOcorrencia) throws Exception {

        if (Geral.isCampoVazio(tipoOcorrencia.getDescricao())) {
            return new ErroValidacao("descricao", "Informe uma descrição válida");
        }

        if(TipoOcorrenciaDAO.descricaoExiste(tipoOcorrencia)){
            return new ErroValidacao("descricao", "A descrição informada já existe");
        }

        return null;
    }
}
