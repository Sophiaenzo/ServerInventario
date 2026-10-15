package formularios;

import controller.TrataClienteController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import util.Funcoes;

public class FormPrincipal extends JFrame {

    private final Funcoes funcoes = new Funcoes();

    public FormPrincipal() {
        initComponents();
        init();
    }

    // --- MANIPULAÇÃO SEGURA DA TABELA SWING ---
    public static void AddLinhaTable(Object[] object) {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) TableConecoes.getModel();
            model.addRow(object);
        });
    }

    public static void DelLinhaTable(String campo) {
        if (campo == null || campo.isEmpty()) return;

        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) TableConecoes.getModel();
            // Loop inverso para evitar erro de deslocamento de índice ao remover linhas
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                if (campo.equals(model.getValueAt(i, 0))) {
                    model.removeRow(i);
                }
            }
        });
    }

    public static void PesLinhaTable(String campo) {
        if (campo == null || campo.isEmpty()) return;

        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) TableConecoes.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                if (campo.equals(model.getValueAt(i, 0))) {
                    model.setValueAt("campo", i, 3);
                }
            }
        });
    }

    // --- INICIALIZAÇÃO DAS CONFIGURAÇÕES E SERVIDORES ---

    private void init() {
        try {
            // Carrega configurações gravadas nas propriedades
            txt_server.setText(funcoes.GetProp("banco.server", "N"));
            txt_porta.setText(funcoes.GetProp("banco.porta", "N"));
            txt_database.setText(funcoes.GetProp("banco.database", "N"));
            txt_usuario.setText(funcoes.GetProp("banco.usuario", "N"));
            txt_senha.setText(funcoes.GetProp("banco.senha", "S"));
            txt_instancia.setText(funcoes.GetProp("banco.instancia", "N"));
            txt_versaoapp.setText(funcoes.GetProp("app.versao", "N"));
            txt_versaopath.setText(funcoes.GetProp("app.versaopath", "N"));
            txt_exportadir.setText(funcoes.GetProp("exporta.path", "N"));

            // 1. SERVIDO LEGADO (Porta 12347)
            new Thread(() -> {
                try {
                    ServerSocket servidorLegado = new ServerSocket(12347);
                    SwingUtilities.invokeLater(() -> lblStatus.setText("Servidor inicializado. Aguardando conexões..."));
                    
                    int idUnico = 1;
                    while (true) {
                        Socket cliente = servidorLegado.accept();
                        ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                        new TrataClienteController(in, out, cliente, idUnico++).start();
                    }
                } catch (Exception e) {
                    System.err.println("Erro no Servidor Legado (Porta 12347): " + e.getMessage());
                }
            }).start();

            // 2. SERVIDOR FLUTTER - DUPLO CHECK (Porta 12348)
            new Thread(() -> {
                try {
                    ServerSocket servidorFlutter = new ServerSocket(12348);
                    System.out.println("-> Servidor Flutter escutando na porta 12348...");

                    while (true) {
                        Socket clienteFlutter = servidorFlutter.accept();
                        System.out.println("-> Nova conexão TCP Flutter recebida de: " + clienteFlutter.getInetAddress().getHostAddress());
                        tratarClienteFlutter(clienteFlutter);
                    }
                } catch (Exception e) {
                    System.err.println("Erro no Servidor Flutter (Porta 12348): " + e.getMessage());
                }
            }).start();

        } catch (Exception e) {
            System.err.println("Erro ao inicializar configurações: " + e.getMessage());
        }
    }

    // --- TRATAMENTO DAS MENSAGENS TCP/JSON DO FLUTTER ---
    private void tratarClienteFlutter(Socket socket) {
        new Thread(() -> {
            String ipCliente = socket.getInetAddress().getHostAddress();
            String usuarioRegistrado = null;

            try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                PrintWriter saida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)
            ) {
                String linha;
                while ((linha = entrada.readLine()) != null) {
                    if (linha.trim().isEmpty()) continue;
                    System.out.println("JSON recebido do Flutter: " + linha);

                    String tipo = extrairValorJson(linha, "tipo");
                    String usuario = extrairValorJson(linha, "usuario");
                    String ipLocal = extrairValorJson(linha, "ip");
                    String hostname = extrairValorJson(linha, "hostname");

                    if (usuario.isEmpty()) usuario = "DESCONHECIDO";
                    if (ipLocal.isEmpty()) ipLocal = ipCliente;
                    if (hostname.isEmpty()) hostname = ipCliente;

                    // Trata atualização de presença do usuário conectado na tabela Swing
                    DelLinhaTable("FLUTTER");
                    if (usuarioRegistrado != null) {
                        DelLinhaTable(usuarioRegistrado);
                    }
                    usuarioRegistrado = usuario;

                    AddLinhaTable(new Object[]{
                        usuario,   // Coluna I.D 
                        ipLocal,   // Coluna IP. Host
                        hostname   // Coluna Host Name
                    });

                    // Caso a mensagem seja a gravação da Dupla Checagem feita pelo App
                    if ("DUPLA_CHECAGEM".equalsIgnoreCase(tipo)) {
                        String loginUsuario = extrairValorJson(linha, "usuario");
                        String primeiroCodigo = extrairValorJson(linha, "primeiro_codigo");
                        String segundoCodigo = extrairValorJson(linha, "segundo_codigo");
                        String codigoBase = extrairValorJson(linha, "codigo_base");

                        // 1. BUSCA O CÓDIGO NUMÉRICO DIRETO NA TABELA E099usu
                        String codigoUsuarioNum = "0";
                        String sqlUsu = "SELECT CODUSU FROM E099usu WHERE UPPER(NOMUSU) = ?";
                        
                        try (java.sql.Connection conn = factory.Conector.getConnection();
                            java.sql.PreparedStatement psUsu = conn.prepareStatement(sqlUsu)) {
                            
                            psUsu.setString(1, loginUsuario.trim().toUpperCase());
                            try (java.sql.ResultSet rsUsu = psUsu.executeQuery()) {
                                if (rsUsu.next()) {
                                    codigoUsuarioNum = rsUsu.getString("CODUSU");
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("-> Erro ao buscar CODUSU na E099usu: " + e.getMessage());
                        }

                        // 2. VERIFICA SE JA FOI CHECADO (usu_dupche = 'S')
                        model.TetrefDaoN dao = new model.TetrefDaoN();
                        boolean jaChecado = dao.isEtiquetaJaChecada(codigoBase);

                        if (jaChecado) {
                            System.out.println("-> [DUPLO CHECK] Atenção: Etiqueta ja checada.");
                            saida.println("ALERTA: Essa etiqueta ja foi registrada anteriormente!");
                        } else {
                            // 3. GRAVA NO BANCO CASO NÃO TENHA SIDO CHECADA
                            boolean gravou = dao.salvarDuplaChecagem(codigoUsuarioNum, primeiroCodigo, segundoCodigo, codigoBase);

                            if (gravou) {
                                System.out.println("-> [DUPLO CHECK] Sucesso!");
                                saida.println("OK: Processado com sucesso!");
                            } else {
                                System.err.println("-> [DUPLO CHECK] Falha ao gravar.");
                                saida.println("ERRO: Falha ao salvar a conferência no banco.");
                            }
                        }
                    } else {
                        // Resposta padrão apenas para mensagens de Keep-Alive / Login / Conexão
                        saida.println("OK: Conectado com sucesso!");
                    }
                }
            } catch (Exception e) {
                System.out.println("Cliente Flutter desconectado (" + ipCliente + "): " + e.getMessage());
            } finally {
                if (usuarioRegistrado != null) {
                    DelLinhaTable(usuarioRegistrado);
                }
                try { socket.close(); } catch (Exception ignored) {}
            }
        }).start();
    }

    // Auxiliar Regex para extrair chaves de texto do JSON enviado pelo Flutter
    private String extrairValorJson(String json, String chave) {
        try {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\"" + chave + "\"\\s*:\\s*\"([^\"]+)\"");
            java.util.regex.Matcher matcher = pattern.matcher(json);
            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void salvarPropriedades() {
        funcoes.SaveProp("app.versao", txt_versaoapp.getText(), "N");
        funcoes.SaveProp("app.versaopath", txt_versaopath.getText(), "N");
        funcoes.SaveProp("exporta.path", txt_exportadir.getText(), "N");
        funcoes.SaveProp("banco.server", txt_server.getText(), "N");
        funcoes.SaveProp("banco.porta", txt_porta.getText(), "N");
        funcoes.SaveProp("banco.usuario", txt_usuario.getText(), "N");
        funcoes.SaveProp("banco.senha", txt_senha.getText(), "S");
        funcoes.SaveProp("banco.database", txt_database.getText(), "N");
        funcoes.SaveProp("banco.instancia", txt_instancia.getText(), "N");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableConecoes = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        txt_porta = new javax.swing.JTextField();
        txt_server = new javax.swing.JTextField();
        txt_senha = new javax.swing.JTextField();
        txt_instancia = new javax.swing.JTextField();
        txt_database = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_versaopath = new javax.swing.JTextField();
        txt_versaoapp = new javax.swing.JTextField();
        txt_exportadir = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jPanel1.setMaximumSize(new java.awt.Dimension(327, 327));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 80));
        lblStatus.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("Clientes Conectados");

        TableConecoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "I.D ", "IP. Host", "Host Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableConecoes);
        if (TableConecoes.getColumnModel().getColumnCount() > 0) {
            TableConecoes.getColumnModel().getColumn(0).setResizable(false);
            TableConecoes.getColumnModel().getColumn(0).setPreferredWidth(50);
            TableConecoes.getColumnModel().getColumn(1).setResizable(false);
            TableConecoes.getColumnModel().getColumn(1).setPreferredWidth(150);
            TableConecoes.getColumnModel().getColumn(2).setResizable(false);
            TableConecoes.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 919, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Conexao", new javax.swing.ImageIcon(getClass().getResource("/imagens/http.png")), jPanel1); // NOI18N
        jPanel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Conexao com banco");
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("Server:");
        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 204));
        jLabel3.setText("Porta:");
        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 204));
        jLabel6.setText("Usuario:");
        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 204));
        jLabel7.setText("Senha:");
        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 204));
        jLabel8.setText("DataBase:");
        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 204));
        jLabel9.setText("Instancia:");

        txt_usuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_porta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_server.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_senha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_instancia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_instancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_instanciaActionPerformed(evt);
            }
        });

        txt_database.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jButton1.setText("CONFIRMAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_server, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_database, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(68, 68, 68)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_instancia, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_porta, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_porta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_server, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_database, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_instancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 61, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Banco de dados", new javax.swing.ImageIcon(getClass().getResource("/imagens/banco.png")), jPanel2); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Caminhos para uso");
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(682, 161));
        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 204));
        jLabel10.setText("Versao Atual do APP");
        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 204));
        jLabel12.setText("Path da versao APP");
        txt_versaopath.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_versaoapp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_exportadir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Exportar dados para ");

        jButton2.setText("CONFIRMAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(21, 21, 21)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_versaoapp, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_versaopath, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                        .addComponent(txt_exportadir)))
                .addContainerGap(74, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_versaoapp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txt_versaopath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_exportadir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 168, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 116, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Diretorio", new javax.swing.ImageIcon(getClass().getResource("/imagens/diretorio.png")), jPanel3); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        salvarPropriedades();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        salvarPropriedades();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_instanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_instanciaActionPerformed
    }//GEN-LAST:event_txt_instanciaActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FormPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private static javax.swing.JLabel lblStatus;
    private static javax.swing.JTable TableConecoes;
    private javax.swing.JTextField txt_database;
    private javax.swing.JTextField txt_exportadir;
    private javax.swing.JTextField txt_instancia;
    private javax.swing.JTextField txt_porta;
    private javax.swing.JTextField txt_senha;
    private javax.swing.JTextField txt_server;
    private javax.swing.JTextField txt_usuario;
    private javax.swing.JTextField txt_versaoapp;
    private javax.swing.JTextField txt_versaopath;
    // End of variables declaration//GEN-END:variables
}