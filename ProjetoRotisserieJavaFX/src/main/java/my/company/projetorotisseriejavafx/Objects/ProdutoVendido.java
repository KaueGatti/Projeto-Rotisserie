package my.company.projetorotisseriejavafx.Objects;

public class ProdutoVendido {

    private int id;
    private Produto produto;
    private Pedido pedido;
    private int quantidade;
    private String deletar = "-";

    public String getDeletar() {
        return deletar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return produto.getValor() * quantidade;
    }
    
    public String getDescricao() {
        return produto.getDescricao();
    }
}
