package chronos.chronos.Controller;

import java.util.ArrayList;

import chronos.chronos.DAO.BemMaterialDAO;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.BemMaterial;
import chronos.chronos.Model.ErroValidacao;

public abstract class BemMaterialController {

    public static void inserir(BemMaterial bemMaterial) throws Exception {
        BemMaterialDAO.inserir(bemMaterial);
    }

    public static void editar(BemMaterial bemMaterial) throws Exception {
        BemMaterialDAO.editar(bemMaterial);
    }

    public static ArrayList<BemMaterial>  retornaListaBemMaterial(String whereStatus) throws Exception {
        return BemMaterialDAO.retornaListaBemMaterial(whereStatus);
    }

    public static ErroValidacao validarDados(BemMaterial bemMaterial) throws Exception {

        if (Geral.isCampoVazio(bemMaterial.getDescricao())) {
            return new ErroValidacao("descricao", "Informe uma descrição válida");
        }

        if(Geral.isCampoVazio(bemMaterial.getLocalizacao()))
        {
            return new ErroValidacao("localizacao", "Informe uma localização válida");
        }

        if(BemMaterialDAO.descricaoExiste(bemMaterial)){
            return new ErroValidacao("descricao", "A descrição informada já existe nessa localização");
        }

        return null;
    }
}