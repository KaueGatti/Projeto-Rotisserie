package my.company.projetorotisserie.Objects;

public class Mensalista {

    private int id;
    private String nome;
    private String cpf;
    private String endereco;
    private Bairro bairro;
    private double conta;
    private String status;

    public Mensalista() {
    }

    public Mensalista(int id, String nome, String cpf, String endereco, Bairro bairro, double conta, String status) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.bairro = bairro;
        this.conta = conta;
        this.status = status;
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public double getConta() {
        return conta;
    }

    public void setConta(double conta) {
        this.conta = conta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
