package my.company.projetorotisseriejavafx.Util;

import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;

import javax.print.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Printer {

    public static void printOrder(Pedido pedido, List<MarmitaVendida> marmitas, List<ProdutoVendido> produtos) {

        /*Map<String, String> dadosPedidos = new HashMap<>();

        dadosPedidos.put("nomeCliente", pedido.getNomeCliente() != null ? pedido.getNomeCliente() : "");
        dadosPedidos.put("data", LocalDate.now().toString());*/

        StringBuilder marmitasText = new StringBuilder();

        for (int i = 0; i < marmitas.size(); i++) {
            marmitasText.append(marmitas.get(i).getNome()).append("\n");
            marmitasText.append(marmitas.get(i).getDetalhes()).append("\n");
            marmitasText.append("Observacao: ");
            marmitasText.append(marmitas.get(i).getObservacao());

            if (i != marmitas.size() - 1) {
                marmitasText.append("\n\n");
            }
        }

        StringBuilder produtosText = new StringBuilder();

        for (int i = 0; i < produtos.size(); i++) {
            produtosText.append(produtos.get(i).getNome()).append(" ");
            produtosText.append("x").append(produtos.get(i).getQuantidade()).append("\n");

            if (i != produtos.size() - 1) {
                produtosText.append("\n\n");
            }
        }

        /*dadosPedidos.put("marmitas", marmitasText.toString());
        dadosPedidos.put("produtos", "");
        dadosPedidos.put("endereco", pedido.getEndereco() != null ? pedido.getEndereco() : "");

        dadosPedidos.put("bairro", pedido.getBairro() != null ? pedido.getBairro().getNome() : "");
        dadosPedidos.put("motoboy", pedido.getMotoboy() != null ? pedido.getMotoboy().getNome() : "");
        dadosPedidos.put("entrega", pedido.getFormattedValorEntrega() != null ? pedido.getFormattedValorEntrega() : "R$ 0,00");
        dadosPedidos.put("total", pedido.getFormattedValorTotal());
        dadosPedidos.put("pagamento", pedido.getTipoPagamento() + "\n\n\n");

        try {
            String model_order = new String(Files.readAllBytes(Paths.get("src/main/resources/modelo_pedido.txt")));

            for (Map.Entry<String, String> entry : dadosPedidos.entrySet()) {
                String variavel = "${" + entry.getKey() + "}";
                String valor = entry.getValue() != null ? entry.getValue() : "?";
                model_order = model_order.replace(variavel, valor);
            }

            byte[] bytes = model_order.getBytes(StandardCharsets.UTF_8);

            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            DocPrintJob job = service.createPrintJob();

            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(bytes, flavor, null);

            job.print(doc, null);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            Path modelo = Paths.get("src/main/resources/modelo_pedido.odt");
            Path saida = Paths.get("src/main/resources/modelo_preenchido.odt");

            Files.copy(modelo, saida, StandardCopyOption.REPLACE_EXISTING);

            try (ZipFile zipFile = new ZipFile(saida.toFile())) {
                ZipEntry contentEntry = zipFile.getEntry("content.xml");
                String content;
                try (InputStream is = zipFile.getInputStream(contentEntry)) {
                    content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                }

                content = content
                        .replace("${nomeCliente}", pedido.getNomeCliente() != null ? pedido.getNomeCliente() : "")
                        .replace("${data}", LocalDate.now().toString())
                        .replace("${marmitas}", marmitasText)
                        .replace("${produtos}", produtosText)
                        .replace("${endereco}", pedido.getEndereco() != null ? pedido.getEndereco() : "")
                        .replace("${bairro}", pedido.getBairro() != null ? pedido.getBairro().getNome() : "")
                        .replace("${total}", pedido.getFormattedValorPedido())
                        .replace("${pagamento}", pedido.getTipoPagamento())
                        .replace("${entrega}", pedido.getFormattedValorEntrega());

                try (ZipFile originalZip = new ZipFile(modelo.toFile());
                     ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(saida.toFile()))) {

                    Enumeration<? extends ZipEntry> entries = originalZip.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        zos.putNextEntry(new ZipEntry(entry.getName()));
                        if (entry.getName().equals("content.xml")) {
                            zos.write(content.getBytes(StandardCharsets.UTF_8));
                        } else {
                            try (InputStream is = originalZip.getInputStream(entry)) {
                                is.transferTo(zos);
                            }
                        }
                        zos.closeEntry();
                    }
                }
            }

            String impressora = "POS-80";

            String libreOfficePath = System.getenv("ProgramFiles") + "\\LibreOffice\\program\\soffice.exe";
            if (!new File(libreOfficePath).exists()) {
                libreOfficePath = System.getenv("ProgramFiles(x86)") + "\\LibreOffice\\program\\soffice.exe";
            }

            ProcessBuilder pb = new ProcessBuilder(
                    libreOfficePath,
                    "--headless",
                    "--pt", impressora,
                    saida.toAbsolutePath().toString()
            );
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();

            System.out.println("Arquivo preenchido e enviado para impressão!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
