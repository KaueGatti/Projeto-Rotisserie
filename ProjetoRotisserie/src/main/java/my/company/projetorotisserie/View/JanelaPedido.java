package my.company.projetorotisserie.View;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import my.company.projetorotisserie.DAO.BairroDAO;
import my.company.projetorotisserie.DAO.MarmitasCadastradasDAO;
import my.company.projetorotisserie.DAO.MarmitasVendidasDAO;
import my.company.projetorotisserie.DAO.PedidoDAO;
import my.company.projetorotisserie.Model.MarmitaPedidoTableModel;
import my.company.projetorotisserie.Objects.Bairro;
import my.company.projetorotisserie.Objects.Endereco;
import my.company.projetorotisserie.Objects.Marmita;
import my.company.projetorotisserie.Objects.Pedido;

public class JanelaPedido extends javax.swing.JFrame {

    private List<String> misturas = new ArrayList();
    private List<String> guarnicoes = new ArrayList();
    private List<String> principal = new ArrayList();
    private String salada = "Não";
    private double total = 0;
    private double entrega = 0;

    MarmitaPedidoTableModel model = new MarmitaPedidoTableModel();

    public JanelaPedido() {
        initComponents();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setLocationRelativeTo(null);

        carregarMarmitas();
        carregarBairros();

        jRBBalcao.setSelected(true);
        enableEndereco(false);

        settingTableMarmitas();

        addNamejTB();

        ActionListener jTBListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JToggleButton jTB) {
                    if (jTB.isSelected()) {
                        jTB.setBackground(Color.green);
                        if (jTB.getName().contains("jTBMistura")) {
                            misturas.add(jTB.getText());
                        } else if (jTB.getName().contains("jTBGuarnicao")) {
                            guarnicoes.add(jTB.getText());
                        } else if (jTB.getName().contains("jTBPrincipal")) {
                            principal.add("Sim");
                            System.out.println(principal.toString());
                        } else {
                            salada = jTB.getText();
                        }
                    } else {
                        jTB.setBackground(Color.LIGHT_GRAY);
                        if (jTB.getName().contains("jTBMistura")) {
                            misturas.remove(jTB.getText());
                        } else if (jTB.getName().contains("jTBGuarnicao")) {
                            guarnicoes.remove(jTB.getText());
                        } else if (jTB.getName().contains("jTBPrincipal")) {
                            principal.remove(jTB.getText());
                        } else {
                            salada = "Não";
                        }
                    }
                    if (misturas.size() >= maxMisturas()) {
                        for (Component comp : jPMarmita.getComponents()) {
                            if (comp instanceof JToggleButton jTBMistura && jTBMistura.getName().contains("jTBMistura") && !jTBMistura.isSelected()) {
                                jTBMistura.setEnabled(false);
                            }
                        }
                    } else {
                        for (Component comp : jPMarmita.getComponents()) {
                            if (comp instanceof JToggleButton jTBMistura && jTBMistura.getName().contains("jTBMistura")) {
                                jTBMistura.setEnabled(true);
                            }
                        }
                    }
                    if (guarnicoes.size() >= maxGuarnicoes()) {
                        for (Component comp : jPMarmita.getComponents()) {
                            if (comp instanceof JToggleButton jTBGuarnicao && jTBGuarnicao.getName().contains("jTBGuarnicao") && !jTBGuarnicao.isSelected()) {
                                jTBGuarnicao.setEnabled(false);
                            }
                        }
                    } else {
                        for (Component comp : jPMarmita.getComponents()) {
                            if (comp instanceof JToggleButton jTBGuarnicao && jTBGuarnicao.getName().contains("jTBGuarnicao")) {
                                jTBGuarnicao.setEnabled(true);
                            }
                        }
                    }
                }
            }
        };

        addListeners(jTBListener);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGTipoPedido = new javax.swing.ButtonGroup();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTMarmitas = new javax.swing.JTable();
        jPMarmita = new javax.swing.JPanel();
        jCBMarmita = new javax.swing.JComboBox<>();
        jTBPrincipal2 = new javax.swing.JToggleButton();
        jTBPrincipal1 = new javax.swing.JToggleButton();
        jTBMistura3 = new javax.swing.JToggleButton();
        jTBMistura1 = new javax.swing.JToggleButton();
        jTBGuarnicao2 = new javax.swing.JToggleButton();
        jTBGuarnicao1 = new javax.swing.JToggleButton();
        jTBGuarnicao4 = new javax.swing.JToggleButton();
        jTBGuarnicao3 = new javax.swing.JToggleButton();
        jTBMistura2 = new javax.swing.JToggleButton();
        jTBMistura4 = new javax.swing.JToggleButton();
        jTBSalada = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jBAdicionar = new javax.swing.JButton();
        jPInformacoes = new javax.swing.JPanel();
        jBCancelar = new javax.swing.JButton();
        jBFinalizar = new javax.swing.JButton();
        jRBBalcao = new javax.swing.JRadioButton();
        jRBRetirada = new javax.swing.JRadioButton();
        jRBEntrega = new javax.swing.JRadioButton();
        jPEndereço = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTFRua = new javax.swing.JTextField();
        jTFAvenida = new javax.swing.JTextField();
        jTFNumero = new javax.swing.JTextField();
        jCBBairro = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLTotal = new javax.swing.JLabel();
        jLable11 = new javax.swing.JLabel();
        jLEntrega = new javax.swing.JLabel();
        jTFCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jBRemover = new javax.swing.JButton();

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTMarmitas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTMarmitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTMarmitas);

        jPMarmita.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPMarmita.setForeground(new java.awt.Color(0, 0, 0));
        jPMarmita.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jCBMarmita.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCBMarmita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTBPrincipal2.setBackground(java.awt.Color.lightGray);
        jTBPrincipal2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBPrincipal2.setForeground(new java.awt.Color(0, 0, 0));
        jTBPrincipal2.setText("FEIJÃO");

        jTBPrincipal1.setBackground(java.awt.Color.lightGray);
        jTBPrincipal1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBPrincipal1.setForeground(new java.awt.Color(0, 0, 0));
        jTBPrincipal1.setText("ARROZ");

        jTBMistura3.setBackground(java.awt.Color.lightGray);
        jTBMistura3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBMistura3.setForeground(new java.awt.Color(0, 0, 0));
        jTBMistura3.setText("MISTURA 3");

        jTBMistura1.setBackground(java.awt.Color.lightGray);
        jTBMistura1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBMistura1.setForeground(new java.awt.Color(0, 0, 0));
        jTBMistura1.setText("MISTURA 1");

        jTBGuarnicao2.setBackground(java.awt.Color.lightGray);
        jTBGuarnicao2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBGuarnicao2.setForeground(new java.awt.Color(0, 0, 0));
        jTBGuarnicao2.setText("GUARNIÇÃO 2");

        jTBGuarnicao1.setBackground(java.awt.Color.lightGray);
        jTBGuarnicao1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBGuarnicao1.setForeground(new java.awt.Color(0, 0, 0));
        jTBGuarnicao1.setText("GUARNIÇÃO 1");

        jTBGuarnicao4.setBackground(java.awt.Color.lightGray);
        jTBGuarnicao4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBGuarnicao4.setForeground(new java.awt.Color(0, 0, 0));
        jTBGuarnicao4.setText("GUARNIÇÃO 4");

        jTBGuarnicao3.setBackground(java.awt.Color.lightGray);
        jTBGuarnicao3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBGuarnicao3.setForeground(new java.awt.Color(0, 0, 0));
        jTBGuarnicao3.setText("GUARNIÇÃO 3");

        jTBMistura2.setBackground(java.awt.Color.lightGray);
        jTBMistura2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBMistura2.setForeground(new java.awt.Color(0, 0, 0));
        jTBMistura2.setText("MISTURA 2");

        jTBMistura4.setBackground(java.awt.Color.lightGray);
        jTBMistura4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBMistura4.setForeground(new java.awt.Color(0, 0, 0));
        jTBMistura4.setText("MISTURA 4");

        jTBSalada.setBackground(java.awt.Color.lightGray);
        jTBSalada.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTBSalada.setForeground(new java.awt.Color(0, 0, 0));
        jTBSalada.setText("SALADA");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Principal");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Misturas");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Guanições");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MARMITA");

        javax.swing.GroupLayout jPMarmitaLayout = new javax.swing.GroupLayout(jPMarmita);
        jPMarmita.setLayout(jPMarmitaLayout);
        jPMarmitaLayout.setHorizontalGroup(
            jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMarmitaLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPMarmitaLayout.createSequentialGroup()
                        .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTBSalada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTBPrincipal2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTBPrincipal1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTBMistura2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTBMistura1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTBMistura3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTBMistura4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jCBMarmita, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTBGuarnicao2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTBGuarnicao1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTBGuarnicao3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTBGuarnicao4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPMarmitaLayout.setVerticalGroup(
            jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPMarmitaLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jCBMarmita, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTBMistura1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBPrincipal1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBGuarnicao1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTBGuarnicao2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTBMistura2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTBPrincipal2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTBMistura3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBGuarnicao3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPMarmitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTBMistura4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBGuarnicao4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBSalada, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        jBAdicionar.setBackground(new java.awt.Color(0, 150, 75));
        jBAdicionar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jBAdicionar.setForeground(new java.awt.Color(0, 0, 0));
        jBAdicionar.setText("Adicionar");
        jBAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAdicionarActionPerformed(evt);
            }
        });

        jPInformacoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jBCancelar.setBackground(new java.awt.Color(255, 0, 0));
        jBCancelar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jBCancelar.setForeground(new java.awt.Color(0, 0, 0));
        jBCancelar.setText("Cancelar");
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jBFinalizar.setBackground(new java.awt.Color(0, 150, 75));
        jBFinalizar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jBFinalizar.setForeground(new java.awt.Color(0, 0, 0));
        jBFinalizar.setText("Finalizar");
        jBFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFinalizarActionPerformed(evt);
            }
        });

        BGTipoPedido.add(jRBBalcao);
        jRBBalcao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jRBBalcao.setText("Balcão");
        jRBBalcao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRBBalcaoMouseClicked(evt);
            }
        });

        BGTipoPedido.add(jRBRetirada);
        jRBRetirada.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jRBRetirada.setText("Retirada");
        jRBRetirada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRBRetiradaMouseClicked(evt);
            }
        });

        BGTipoPedido.add(jRBEntrega);
        jRBEntrega.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jRBEntrega.setText("Entrega");
        jRBEntrega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRBEntregaMouseClicked(evt);
            }
        });

        jPEndereço.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Rua");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Número");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Avenida");

        jTFRua.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jTFAvenida.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jTFNumero.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jCBBairro.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCBBairro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBBairroActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Bairro");

        javax.swing.GroupLayout jPEndereçoLayout = new javax.swing.GroupLayout(jPEndereço);
        jPEndereço.setLayout(jPEndereçoLayout);
        jPEndereçoLayout.setHorizontalGroup(
            jPEndereçoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPEndereçoLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPEndereçoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPEndereçoLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPEndereçoLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPEndereçoLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFAvenida, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPEndereçoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFRua, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        jPEndereçoLayout.setVerticalGroup(
            jPEndereçoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEndereçoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPEndereçoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPEndereçoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFAvenida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(14, 14, 14)
                .addGroup(jPEndereçoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTFNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPEndereçoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Total:");

        jLTotal.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLable11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLable11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLable11.setText("Entrega:");

        jLEntrega.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLEntrega.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jTFCliente.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Cliente");

        javax.swing.GroupLayout jPInformacoesLayout = new javax.swing.GroupLayout(jPInformacoes);
        jPInformacoes.setLayout(jPInformacoesLayout);
        jPInformacoesLayout.setHorizontalGroup(
            jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPInformacoesLayout.createSequentialGroup()
                        .addGroup(jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLable11, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPInformacoesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(jPInformacoesLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPEndereço, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPInformacoesLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPInformacoesLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPInformacoesLayout.createSequentialGroup()
                        .addComponent(jRBBalcao)
                        .addGap(18, 18, 18)
                        .addComponent(jRBRetirada)
                        .addGap(18, 18, 18)
                        .addComponent(jRBEntrega)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPInformacoesLayout.setVerticalGroup(
            jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPInformacoesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRBBalcao)
                    .addComponent(jRBEntrega)
                    .addComponent(jRBRetirada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPEndereço, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLable11)
                    .addComponent(jLEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBRemover.setBackground(new java.awt.Color(255, 0, 0));
        jBRemover.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jBRemover.setForeground(new java.awt.Color(0, 0, 0));
        jBRemover.setText("Remover");
        jBRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPMarmita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jBRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 477, Short.MAX_VALUE)
                .addComponent(jPInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPMarmita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRBBalcaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRBBalcaoMouseClicked
        enableEndereco(false);
    }//GEN-LAST:event_jRBBalcaoMouseClicked

    private void jRBRetiradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRBRetiradaMouseClicked
        enableEndereco(false);
    }//GEN-LAST:event_jRBRetiradaMouseClicked

    private void jRBEntregaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRBEntregaMouseClicked
        enableEndereco(true);
    }//GEN-LAST:event_jRBEntregaMouseClicked

    private void jBAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAdicionarActionPerformed
        Marmita marmita = MarmitasCadastradasDAO.read(jCBMarmita.getSelectedItem().toString());
        ajustarArraysSize();

        marmita.setPrincipal(new ArrayList<>(principal));
        marmita.setMisturas(new ArrayList<>(misturas));
        marmita.setGuarnicoes(new ArrayList<>(guarnicoes));
        marmita.setSalada(salada);
        model.addRow(marmita);

        total += marmita.getValor();
        jLTotal.setText(String.valueOf(total));

        this.principal.clear();
        this.misturas.clear();
        this.guarnicoes.clear();

        for (Component comp : jPMarmita.getComponents()) {
            if (comp instanceof JToggleButton jTB) {
                jTB.setBackground(Color.LIGHT_GRAY);
                jTB.setEnabled(true);
                jTB.setSelected(false);
            }
        }
    }//GEN-LAST:event_jBAdicionarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed

    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFinalizarActionPerformed
        Pedido pedido = newPedido();
        PedidoDAO.create(pedido);
        int idPedido = PedidoDAO.read().getLast().getId();
        MarmitasVendidasDAO.create(model.getMarmitas(), idPedido);
    }//GEN-LAST:event_jBFinalizarActionPerformed

    private void jBRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverActionPerformed
        if (jTMarmitas.getSelectedRow() != -1) {
            total -= model.getRowData(jTMarmitas.getSelectedRow()).getValor();
            model.deleteRow(jTMarmitas.getSelectedRow());
            jLTotal.setText(String.valueOf(total));
        }
    }//GEN-LAST:event_jBRemoverActionPerformed

    private void jCBBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBBairroActionPerformed
        if (jRBEntrega.isSelected()) {
            total -= entrega;
            entrega = BairroDAO.read(jCBBairro.getSelectedItem().toString()).getValorEntrega();
            total += entrega;
            jLEntrega.setText(String.valueOf(entrega));
            jLTotal.setText(String.valueOf(total));
        }
    }//GEN-LAST:event_jCBBairroActionPerformed

    public Pedido newPedido() {
        Pedido pedido = new Pedido();

        pedido.setCliente(jTFCliente.getText());
        pedido.setMarmitas(model.getMarmitas());
        pedido.setTipo(tipoPedido());
        pedido.setValorTotal(total);
        pedido.setValorEntrega(entrega);
        pedido.setEndereco(getEndereco());
        pedido.setData(LocalDate.now());
        pedido.setHorário(LocalTime.now());
        return pedido;
    }

    public String haveSalada() {
        String salada = jTBSalada.isSelected() ? "Sim" : "Não";
        return salada;
    }

    public void enableEndereco(boolean bool) {
        if (bool) {
            jTFRua.setEnabled(true);
            jTFAvenida.setEnabled(true);
            jTFNumero.setEnabled(true);
            jCBBairro.setSelectedIndex(0);
            jCBBairro.setEnabled(true);
        } else {
            jTFRua.setEnabled(false);
            jTFAvenida.setEnabled(false);
            jTFNumero.setEnabled(false);
            jCBBairro.setSelectedItem(null);
            jCBBairro.setEnabled(false);
            total -= entrega;
            entrega = 0;
            jLEntrega.setText(String.valueOf(entrega));
            jLTotal.setText(String.valueOf(total));
        }
    }

    public String tipoPedido() {
        if (jRBBalcao.isSelected()) {
            return jRBBalcao.getText();
        } else if (jRBRetirada.isSelected()) {
            return jRBRetirada.getText();
        } else {
            return jRBBalcao.getText();
        }
    }
    
    public Endereco getEndereco() {
        if (jRBEntrega.isSelected()) {
            Endereco endereco = new Endereco();
            endereco.setRua(jTFRua.getText());
            endereco.setAvenida(jTFAvenida.getText());
            endereco.setNumero(jTFNumero.getText());
            endereco.setBairro(jCBBairro.getSelectedItem().toString());
            return endereco;
        }
        return null;
    }

    public void addListeners(ActionListener... actionListeners) {
        for (Component comp : jPMarmita.getComponents()) {
            if (comp instanceof JToggleButton jTB) {
                jTB.addActionListener(actionListeners[0]);
            }
        }
    }

    public void addNamejTB() {
        jTBPrincipal1.setName("jTBPrincipal1");
        jTBPrincipal2.setName("jTBPrincipal2");
        jTBSalada.setName("jTBSalada");
        jTBMistura1.setName("jTBMistura1");
        jTBMistura2.setName("jTBMistura2");
        jTBMistura3.setName("jTBMistura3");
        jTBMistura4.setName("jTBMistura4");
        jTBGuarnicao1.setName("jTBGuarnicao1");
        jTBGuarnicao2.setName("jTBGuarnicao2");
        jTBGuarnicao3.setName("jTBGuarnicao3");
        jTBGuarnicao4.setName("jTBGuarnicao4");
    }

    public int maxMisturas() {
        Marmita marmita = MarmitasCadastradasDAO.read(jCBMarmita.getSelectedItem().toString());
        switch (marmita.getTamanho()) {
            case "Mini":
                return 1;
            case "Executiva":
                return 1;
            case "P":
                return 1;
            case "M":
                return 2;
            case "G":
                return 3;
            case "Combo":
                return 3;
            case "Peso":
                return 4;
        }
        return 0;
    }

    public int maxGuarnicoes() {
        Marmita marmita = MarmitasCadastradasDAO.read(jCBMarmita.getSelectedItem().toString());
        switch (marmita.getTamanho()) {
            case "Mini":
                return 1;
            case "Executiva":
                return 1;
            case "P":
                return 2;
            case "M":
                return 2;
            case "G":
                return 3;
            case "Combo":
                return 3;
            case "Peso":
                return 4;
        }
        return 0;
    }

    public void carregarMarmitas() {
        jCBMarmita.removeAllItems();
        for (Marmita marmita : MarmitasCadastradasDAO.read()) {
            jCBMarmita.addItem(marmita.getDescricao());
        }
    }

    public void carregarBairros() {
        jCBBairro.removeAllItems();
        for (Bairro bairro : BairroDAO.read()) {
            jCBBairro.addItem(bairro.getNome());
        }
    }

    public void ajustarArraysSize() {
        while (principal.size() != 2) {
            principal.add("Não");
        }

        while (misturas.size() != 3) {
            misturas.add("");
        }

        while (guarnicoes.size() != 3) {
            guarnicoes.add("");
        }
    }

    private void settingTableMarmitas() {
        jTMarmitas.setModel(model);
        jTMarmitas.setRowHeight(30);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTMarmitas.getColumnCount(); i++) {
            jTMarmitas.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }

        DefaultTableCellRenderer centralizarCabecalho = (DefaultTableCellRenderer) jTMarmitas.getTableHeader().getDefaultRenderer();
        centralizarCabecalho.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    public int getIdPedido() {
        return PedidoDAO.read().getLast().getId();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JanelaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPedido().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGTipoPedido;
    private javax.swing.JButton jBAdicionar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBFinalizar;
    private javax.swing.JButton jBRemover;
    private javax.swing.JComboBox<String> jCBBairro;
    private javax.swing.JComboBox<String> jCBMarmita;
    private javax.swing.JLabel jLEntrega;
    private javax.swing.JLabel jLTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLable11;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPEndereço;
    private javax.swing.JPanel jPInformacoes;
    private javax.swing.JPanel jPMarmita;
    private javax.swing.JRadioButton jRBBalcao;
    private javax.swing.JRadioButton jRBEntrega;
    private javax.swing.JRadioButton jRBRetirada;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jTBGuarnicao1;
    private javax.swing.JToggleButton jTBGuarnicao2;
    private javax.swing.JToggleButton jTBGuarnicao3;
    private javax.swing.JToggleButton jTBGuarnicao4;
    private javax.swing.JToggleButton jTBMistura1;
    private javax.swing.JToggleButton jTBMistura2;
    private javax.swing.JToggleButton jTBMistura3;
    private javax.swing.JToggleButton jTBMistura4;
    private javax.swing.JToggleButton jTBPrincipal1;
    private javax.swing.JToggleButton jTBPrincipal2;
    private javax.swing.JToggleButton jTBSalada;
    private javax.swing.JTextField jTFAvenida;
    private javax.swing.JTextField jTFCliente;
    private javax.swing.JTextField jTFNumero;
    private javax.swing.JTextField jTFRua;
    private javax.swing.JTable jTMarmitas;
    // End of variables declaration//GEN-END:variables
}
