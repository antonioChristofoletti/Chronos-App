package chronos.chronos.Controller;

import java.util.ArrayList;
import java.util.Date;

import chronos.chronos.DAO.OrdemServicoDAO;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.Model.OrdemServico;

public abstract class OrdemServicoController {

    public static void inserir(OrdemServico ordemServico) throws Exception {
        OrdemServicoDAO.inserir(ordemServico);
    }

    public static void editar(OrdemServico ordemServico) throws Exception {
        OrdemServicoDAO.editar(ordemServico);
    }

    public static ArrayList<OrdemServico>  retornalistOrdemServico(Date dataInicialWhere, Date dataFinalWhere) throws Exception {
        return OrdemServicoDAO.retornalistOrdemServico(dataInicialWhere, dataFinalWhere);
    }

    public static ErroValidacao validarDados(OrdemServico ordemServico) throws Exception {

        if (ordemServico.getDataInicial() != null && ordemServico.getDataFinal() != null) {
            if (ordemServico.getDataInicial().after(ordemServico.getDataFinal())) {
                return new ErroValidacao("dataInicial", "A data inicial deve ser menor que a data final");
            }
        }

        if (ordemServico.getBemMaterial() == null) {
            return new ErroValidacao("bemMaterial", "Selecione um bem material para essa ordem de serviço");
        }

        if (ordemServico.getTipoOcorrencia() == null) {
            return new ErroValidacao("tipoOcorrencia", "Selecione um tipo de ocorrência para essa ordem de serviço");
        }

        if (ordemServico.getServico() == null) {
            return new ErroValidacao("servico", "Selecione um serviço para essa ordem de serviço");
        }

        return null;
    }
}