package my.company.projetorotisseriejavafx.Objects;

import java.util.List;

public class Relatorio {

    List<Pedido> pedidos;

    public Relatorio(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public int getTotalPedidos() {
        return pedidos.size();
    }

    public double getValorFaturamento() {
        return pedidos.stream().filter(p -> !p.getTipoPagamento().equals("Pagar depois")).mapToDouble(Pedido::getValorTotal).sum();
    }

    public double getValorTotalPedidos() {
        return pedidos.stream().mapToDouble(Pedido::getValorTotal).sum();
    }

    public int getTotalEntrega() {
        return pedidos.stream().filter(p -> p.getTipoPedido().equals("Entrega")).toList().size();
    }

    public double getValorTotalEntrega() {
        return pedidos.stream().filter(p -> p.getTipoPedido().equals("Entrega")).mapToDouble(Pedido::getValorTotal).sum();
    }

    public int getPorcentoEntrega() {
        return (int) ((getValorTotalEntrega() / getValorFaturamento()) * 100);
    }

    public int getTotalBalcao() {
        return pedidos.stream().filter(p -> p.getTipoPedido().equals("Balcao")).toList().size();
    }

    public double getValorTotalBalcao() {
        return pedidos.stream().filter(p -> p.getTipoPedido().equals("Balcao")).mapToDouble(Pedido::getValorTotal).sum();
    }

    public int getPorcentoBalcao() {
        return (int) ((getValorTotalBalcao() / getValorFaturamento()) * 100);
    }

    public int getTotalPagamentos() {
        return pedidos.stream().filter(p -> !p.getTipoPagamento().equals("Pagar depois")).toList().size();
    }

    public double getValorTotalPagamentos() {
        return pedidos.stream().filter(p -> !p.getTipoPagamento().equals("Pagar depois")).mapToDouble(Pedido::getValorTotal).sum();
    }

    public int getTotalDinheiro() {
        return pedidos.stream().filter(p -> p.getTipoPagamento().equals("Dinheiro")).toList().size();
    }

    public double getValorTotalDinheiro() {
        return pedidos.stream().filter(p -> p.getTipoPagamento().equals("Dinheiro")).mapToDouble(Pedido::getValorTotal).sum();
    }

    public int getPorcentoDinheiro() {
        return (int) ((getValorTotalDinheiro() / getValorFaturamento()) * 100);
    }

    public int getTotalCredito() {
        return pedidos.stream().filter(p -> p.getTipoPagamento().equals("Credito")).toList().size();
    }

    public double getValorTotalCredito() {
        return pedidos.stream().filter(p -> p.getTipoPagamento().equals("Credito")).mapToDouble(Pedido::getValorTotal).sum();
    }

    public int getPorcentoCredito() {
        return (int) ((getValorTotalCredito() / getValorFaturamento()) * 100);
    }

    public int getTotalDebito() {
        return pedidos.stream().filter(p -> p.getTipoPagamento().equals("Debito")).toList().size();
    }

    public double getValorTotalDebito() {
        return pedidos.stream().filter(p -> p.getTipoPagamento().equals("Debito")).mapToDouble(Pedido::getValorTotal).sum();
    }

    public int getPorcentoDebito() {
        return (int) ((getValorTotalDebito() / getValorFaturamento()) * 100);
    }

    public int getTotalPix() {
        return pedidos.stream().filter(p -> p.getTipoPagamento().equals("Pix")).toList().size();
    }

    public double getValorTotalPix() {
        return pedidos.stream().filter(p -> p.getTipoPagamento().equals("Pix")).mapToDouble(Pedido::getValorTotal).sum();
    }

    public int getPorcentoPix() {
        return (int) ((getValorTotalPix() / getValorFaturamento()) * 100);
    }
}
