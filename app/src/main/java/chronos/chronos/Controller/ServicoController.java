package chronos.chronos.Controller;

import java.util.ArrayList;

import chronos.chronos.DAO.ServicoDAO;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.Model.Servico;

public class ServicoController {
    public static void inserir(Servico servico) throws Exception {
        ServicoDAO.inserir(servico);
    }

    public static void editar(Servico servico) throws Exception {
        ServicoDAO.editar(servico);
    }

    public static ArrayList<Servico>  retornaListaServico(String whereStatus) throws Exception {
        return ServicoDAO.retornaListaServico(whereStatus);
    }

    public static ErroValidacao validarDados(Servico servico) throws Exception {

        if (Geral.isCampoVazio(servico.getDescricao())) {
            return new ErroValidacao("descricao", "Informe uma descrição válida");
        }

        if(ServicoDAO.descricaoExiste(servico)){
            return new ErroValidacao("descricao", "A descrição informada já existe");
        }

        return null;
    }
}