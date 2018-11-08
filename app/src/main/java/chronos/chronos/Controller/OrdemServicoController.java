package chronos.chronos.Controller;

import java.util.ArrayList;

import chronos.chronos.DAO.BemMaterialDAO;
import chronos.chronos.DAO.OrdemServicoDAO;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.Model.OrdemServico;

public class OrdemServicoController {
    public static void inserir(OrdemServico ordemServico) throws Exception {
        OrdemServicoDAO.inserir(ordemServico);
    }

    public static ErroValidacao validarDados(OrdemServico ordemServico) throws Exception {

        if(ordemServico.getDataInicial() != null && ordemServico.getDataFinal() != null)
        {
            if(ordemServico.getDataInicial().after(ordemServico.getDataFinal()))
            {
                return new ErroValidacao("dataInicial", "A data inicial deve ser menor que a data final");
            }
        }

        if(ordemServico.getBemMaterial() == null)
        {
            return new ErroValidacao("bemMaterial", "Selecione um bem material para essa ordem de serviço");
        }

        if (ordemServico.getTipoOcorrencia() == null) {
            return new ErroValidacao("tipoOcorrencia", "Selecione um tipo de ocorrência para essa ordem de serviço");
        }

        return null;
    }

   /* public static void editar(OrdemServico ordemServico) throws Exception {
        OrdemServicoDAO.editar(ordemServico);
    }*/

    public static ArrayList<OrdemServico>  retornalistOrdemServico() throws Exception {
        return OrdemServicoDAO.retornalistOrdemServico();
    }
}