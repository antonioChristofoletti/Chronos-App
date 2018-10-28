package chronos.chronos.Model;

public class ErroValidacao {

    private String nomeCampo;
    private String erroCampo;

    public ErroValidacao(String nomeCampo, String erroCampo) {
        this.nomeCampo = nomeCampo;
        this.erroCampo = erroCampo;
    }

    public String getNomeCampo() {
        return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }

    public String getErroCampo() {
        return erroCampo;
    }

    public void setErroCampo(String erroCampo) {
        this.erroCampo = erroCampo;
    }
}
