package my.company.projetorotisseriejavafx.Util;

import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;

import javax.print.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Printer {

    public static void printOrder(Pedido pedido, List<MarmitaVendida> marmitas, List<ProdutoVendido> produtos) {

        Map<String, String> dadosPedidos = new HashMap<>();

        dadosPedidos.put("nomeCliente", pedido.getNomeCliente() != null ? pedido.getNomeCliente().toUpperCase() : "");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dadosPedidos.put("data", LocalDate.now().format(dtf));

        StringBuilder marmitasText = new StringBuilder();

        for (int i = 0; i < marmitas.size(); i++) {
            marmitasText.append("[bold]");
            marmitasText.append(marmitas.get(i).getNome()).append("\n");
            marmitasText.append("[/bold]");
            marmitasText.append(marmitas.get(i).getDetalhes()).append("\n");
            marmitasText.append("Observacao: ");
            marmitasText.append(marmitas.get(i).getObservacao() != null ? marmitas.get(i).getObservacao() : "");

            if (i != marmitas.size() - 1) {
                marmitasText.append("\n\n");
            }
        }

        StringBuilder produtosText = new StringBuilder();

        for (int i = 0; i < produtos.size(); i++) {
            produtosText.append(produtos.get(i).getNome()).append(" ");
            produtosText.append("x").append(produtos.get(i).getQuantidade()).append("\n");
        }

        dadosPedidos.put("marmitas", marmitasText.toString());
        dadosPedidos.put("produtos", produtosText.toString());
        dadosPedidos.put("endereco", pedido.getEndereco() != null ? pedido.getEndereco() : "");

        dadosPedidos.put("bairro", pedido.getBairro() != null ? pedido.getBairro().getNome() : "");
        dadosPedidos.put("motoboy", pedido.getMotoboy() != null ? pedido.getMotoboy().getNome() : "");
        dadosPedidos.put("pagamento", pedido.getTipoPagamento());
        dadosPedidos.put("total", pedido.getFormattedValorPedido());
        dadosPedidos.put("observacao", pedido.getObservacoes() != null ? pedido.getObservacoes() : "");
        dadosPedidos.put("entrega", pedido.getFormattedValorEntrega() != null ? pedido.getFormattedValorEntrega() : "R$ 0,00");
        try {
            String model_order = new String(Files.readAllBytes(Paths.get("src/main/resources/modelo_pedido.txt")));

            for (Map.Entry<String, String> entry : dadosPedidos.entrySet()) {
                String variavel = "${" + entry.getKey() + "}";
                String valor = entry.getValue() != null ? entry.getValue() : "?";
                model_order = model_order.replace(variavel, valor);
            }

            model_order = normalizar(model_order);

            /*ByteArrayOutputStream baos = new ByteArrayOutputStream();

            baos.write(new byte[]{0x1B, 0x61, 0x01});*/

            byte[] bytes = PosFormatter.process(model_order);

            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            DocPrintJob job = service.createPrintJob();

            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(bytes, flavor, null);

            job.print(doc, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String normalizar(String texto) {
        return texto
                .replace("á", "a")
                .replace("à", "a")
                .replace("ã", "a")
                .replace("â", "a")
                .replace("Á", "A")
                .replace("é", "e")
                .replace("ê", "e")
                .replace("É", "E")
                .replace("í", "i")
                .replace("Í", "I")
                .replace("ó", "o")
                .replace("ô", "o")
                .replace("õ", "o")
                .replace("Ó", "O")
                .replace("ú", "u")
                .replace("Ú", "U")
                .replace("ç", "c")
                .replace("Ç", "C");
    }
}
