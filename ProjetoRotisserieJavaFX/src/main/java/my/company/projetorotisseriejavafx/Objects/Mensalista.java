package my.company.projetorotisseriejavafx.Objects;

public class Mensalista {
       
    private int id;
    private String nome;
    private double conta;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setConta(double conta) {
        this.conta = conta;
    }

    public double getConta() {
        return conta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormattedConta() {
        return "R$ " + String.valueOf(conta).replace(".", ",");
    }

    @Override
    public String toString() {
        return nome;
    }
}
