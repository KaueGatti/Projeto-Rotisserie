package my.company.projetorotisserie.Objects;

import my.company.projetorotisserie.DAO.BairroDAO;

public class Endereco {
    
    private String rua;
    private String avenida;
    private String numero;
    private Bairro bairro;

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getAvenida() {
        return avenida;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(String nomeBairro) {
        Bairro bairro = BairroDAO.read(nomeBairro);
        this.bairro = bairro;
    }
}
