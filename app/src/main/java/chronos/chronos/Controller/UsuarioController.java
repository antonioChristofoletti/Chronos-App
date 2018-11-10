package chronos.chronos.Controller;

import chronos.chronos.DAO.UsuarioDAO;
import chronos.chronos.Geral.Geral;
import chronos.chronos.Model.ErroValidacao;
import chronos.chronos.Model.Usuario;

public abstract class UsuarioController {

    public static void inserir(Usuario u) throws Exception {
        UsuarioDAO.inserir(u);
    }

    public static void editar(Usuario u) throws Exception {
        UsuarioDAO.editar(u);
    }

    public static Usuario retornaUsuario() throws Exception {
        return UsuarioDAO.retornaUsuario();
    }

    public static ErroValidacao validarDados(Usuario u) {

        if (Geral.isCampoVazio(u.getNome())) {
            return new ErroValidacao("nome", "Informe um nome válido");
        }

        if (u.getNumeroTelefone().length() != 12) {
            return new ErroValidacao("numeroTelefone", "Informe um telefone válido");
        }

        if (Geral.isEmailValido(u.getEmail())) {
            return new ErroValidacao("email", "Informe um e-mail válido");
        }

        return null;
    }
}
