package chronos.chronos.Model;

import java.io.Serializable;
import java.util.Date;

public class OrdemServico implements Serializable {

    private String id;
    private Date dataInicial;
    private Date dataFinal;
    private String observacao;
    private TipoOcorrencia tipoOcorrencia;
    private BemMaterial bemMaterial;
    private Servico servico;
    private Usuario usuario;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoOcorrencia getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public BemMaterial getBemMaterial() {
        return bemMaterial;
    }

    public void setBemMaterial(BemMaterial bemMaterial) {
        this.bemMaterial = bemMaterial;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}