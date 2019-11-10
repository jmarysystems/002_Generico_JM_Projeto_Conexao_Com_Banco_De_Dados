/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao_Com_Banco_De_Dados;

import br.com.jmary.home.Home;
import br.com.jmary.home.jpa.DB;
import br.com.jmary.home.jpa.DB_Bean;
import br.com.jmary.utilidades.Arquivo_Ou_Pasta;
import br.com.jmary.utilidades.JOPM;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public class Conexao_Com_Banco_De_Dados_Generico extends javax.swing.JPanel {
    
    String nomeArquivoPropertiesASerCriado = "db_conf_derby";
    
    Home Home;
    
    JPanel Properties_CriadoX = new JPanel();
    /**
     * Creates new form Visualizador_Interno2
     * @param Home2
     */
    public Conexao_Com_Banco_De_Dados_Generico( Home Home2) {
        initComponents();
        
        Home = Home2;
        
        //Properties_CriadoX = Properties_Criado;
        //Home.ControleTabs.removerTabSemControleSelecionadoPeloNome(jTabbedPane1,Properties_Criado);
        
        inicio();
    }
    
    private void inicio(){
        try{
            String user_dir = System.getProperty("user.dir");
            ta_Endereco_Banco.setText(user_dir);
            
            inicioX();
        }catch(Exception e ){}
    }
    
    String tipo_de_conexao = ""; //"alone"; //network //tcp
    String ip_do_servidor  = ""; //"127.0.0.1";
    String porta_servidor  = ""; //"1527";
    String tipoDeBanco     = ""; //"derby"; "mysql"
    String nomeDoBanco     = ""; //"Banco_De_Dados";
    String usuario         = ""; //"cleilson";
    String senha           = ""; //"23071354";      
    
    String endereco_db     = ""; //System.getProperty("user.dir"); // + System.getProperty("file.separator") + "JMarySystems"       
    
    String driver          = ""; 
    //String driverAlone     = ""; //"org.apache.derby.jdbc.EmbeddedDriver"; 
    //String driverNetwork   = ""; //"org.apache.derby.jdbc.ClientDriver";
    
    String url             = "";     
    //String urlAlone        = "jdbc:"+tipoDeBanco+":" + endereco_db + "/" + nomeDoBanco+";" + "user="+usuario+";" + "password="+senha+"";
    //String urlNetwork      = "jdbc:"+tipoDeBanco+"://" + ip_do_servidor + ":"+porta_servidor+"/" + nomeDoBanco+";" + "user="+usuario+";" + "password="+senha+"";
    private void inicioX() {                                             
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 
        
            tipo_de_conexao = ""; try{ tipo_de_conexao = cbTipoConexao.getSelectedItem().toString().trim(); } catch( Exception e ){}
            tipoDeBanco     = ""; try{ tipoDeBanco = cbTipoBanco.getSelectedItem().toString().trim(); } catch( Exception e ){}
            driver          = ""; try{ driver = cbTipoDriver.getSelectedItem().toString().trim(); } catch( Exception e ){}
            nomeDoBanco     = ""; try{ nomeDoBanco = taNomeBanco.getText().trim(); } catch( Exception e ){}
            ip_do_servidor  = ""; try{ ip_do_servidor = taIpServidor.getText().trim(); } catch( Exception e ){}
            porta_servidor  = ""; try{ porta_servidor = taPortaServidor.getText().trim(); } catch( Exception e ){}                     
            usuario         = ""; try{ usuario = taNomeUsuarioX.getText().trim(); } catch( Exception e ){}                     
            
            ////////////////////////////////////////////////////////////////////////////////////////////////
            char[] senhaX  = null;  try{ senhaX   = taSenhaX.getPassword();           } catch( Exception e ){}
            String senhaP   = "";   try{ senhaP   = Arrays.toString(senhaX);          } catch( Exception e ){}
            String senhaY  = "";    try{ senhaY   = senhaP.replace("[", "");          } catch( Exception e ){}
            String senhaY2 = "";    try{ senhaY2  = senhaY.replace("]", "");          } catch( Exception e ){}
            String senhaY3 = "";    try{ senhaY3  = senhaY2.replace(" ", "");         } catch( Exception e ){}
            String senhaFinal = ""; try{ senhaFinal    = senhaY3.replace(",", "");        } catch( Exception e ){}
            //////////////////////////////////////////////////////////////////////////////////////////////// 
            senha        = ""; try{ senha = senhaFinal; } catch( Exception e ){} 
            
            endereco_db  = ""; try{ endereco_db = ta_Endereco_Banco.getText().trim(); } catch( Exception e ){}
               
            if( tipo_de_conexao.equalsIgnoreCase("alone") ){
                
                url = ""; try{ url = "jdbc:"+tipoDeBanco+":" + endereco_db + "/" + nomeDoBanco+";" + "user="+usuario+";" + "password="+senha+""; } catch( Exception e ){}  
                
                ta_URL.setText(url);
                
                try{ 
                    jpIpServidor.setVisible(false);
                    jpPortaServidor.setVisible(false);
                    
                    jp_Endereco_Banco.setVisible(true);
                } catch( Exception e ){}
            }     
            else if( tipo_de_conexao.equalsIgnoreCase("network") ){
                
                String separador = "&"; //  ";" 
                try{ separador = taSeparador.getText().trim();   } catch( Exception e ){}                
                
                String time_zone_a_usar = "";
                
                String time_zone = null; try{ time_zone = taNomeUsuarioX1.getText().trim();   } catch( Exception e ){}                
                if( time_zone.equalsIgnoreCase("?useTimezone=true&serverTimezone=UTC&") ){
                    
                    time_zone_a_usar = "?useTimezone=true&serverTimezone=UTC&";
                }
                else{
                    time_zone_a_usar = separador;
                }
                
                url = ""; try{ url = "jdbc:"+tipoDeBanco+"://" + ip_do_servidor + ":"+porta_servidor+"/" + nomeDoBanco+ time_zone_a_usar + "user="+usuario + separador + "password="+senha+""; } catch( Exception e ){}  
           
                ta_URL.setText(url);
                
                try{ 
                    jpIpServidor.setVisible(true);
                    jpPortaServidor.setVisible(true);
                    
                    jp_Endereco_Banco.setVisible(false);
                } catch( Exception e ){}
            }
            
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /*
            String arquivoASerCriado = System.getProperty("user.dir") + "\\" + nomeArquivoPropertiesASerCriado + ".properties";                                    
            Arquivo_Ou_Pasta.deletar( new File( arquivoASerCriado ) );            
            Thread.sleep( 100 ); 
            
            Properties properties = new Properties();
                       properties.put("tipo_de_conexao", tipo_de_conexao);
                       properties.put("tipoDeBanco",     tipoDeBanco);
                       properties.put("driver",          driver);
                       properties.put("nomeDoBanco",     nomeDoBanco);
                       properties.put("usuario",         usuario);
                       properties.put("senha",           senha);
                       properties.put("ip_do_servidor",  ip_do_servidor);
                       properties.put("porta_servidor",  porta_servidor);
                       properties.put("endereco_db",     endereco_db);
                       properties.put("url",             url);
                       
            FileOutputStream out = new FileOutputStream( nomeArquivoPropertiesASerCriado + ".properties" );
            properties.storeToXML(out, "updated", "UTF-8"); 
            out.flush();
            out.close();
            
            FileInputStream in = new FileInputStream( nomeArquivoPropertiesASerCriado + ".properties" );
            properties.loadFromXML(in);
            in.close();
                
            
            Home.setar_Banco_de_Dados(properties);
            */
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        
        } catch( Exception e ){  } } }.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rbUser = new javax.swing.JRadioButton();
        rbHome = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();
        JPOpcao_36 = new javax.swing.JPanel();
        taNomeUsuarioX1 = new javax.swing.JTextArea();
        JPOpcao_38 = new javax.swing.JPanel();
        taSeparador = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        JPOpcao_7 = new javax.swing.JPanel();
        jTextArea7 = new javax.swing.JTextArea();
        JPOpcao_25 = new javax.swing.JPanel();
        cbTipoConexao = new javax.swing.JComboBox();
        jPanel17 = new javax.swing.JPanel();
        JPOpcao_9 = new javax.swing.JPanel();
        jTextArea8 = new javax.swing.JTextArea();
        JPOpcao_24 = new javax.swing.JPanel();
        cbTipoBanco = new javax.swing.JComboBox();
        jPanel18 = new javax.swing.JPanel();
        JPOpcao_10 = new javax.swing.JPanel();
        jTextArea9 = new javax.swing.JTextArea();
        JPOpcao_11 = new javax.swing.JPanel();
        cbTipoDriver = new javax.swing.JComboBox();
        jPanel21 = new javax.swing.JPanel();
        JPOpcao_13 = new javax.swing.JPanel();
        jTextArea10 = new javax.swing.JTextArea();
        JPOpcao_12 = new javax.swing.JPanel();
        taNomeBanco = new javax.swing.JTextArea();
        jpIpServidor = new javax.swing.JPanel();
        JPOpcao_18 = new javax.swing.JPanel();
        jTextArea15 = new javax.swing.JTextArea();
        JPOpcao_19 = new javax.swing.JPanel();
        taIpServidor = new javax.swing.JTextArea();
        jpPortaServidor = new javax.swing.JPanel();
        JPOpcao_21 = new javax.swing.JPanel();
        jTextArea18 = new javax.swing.JTextArea();
        JPOpcao_20 = new javax.swing.JPanel();
        taPortaServidor = new javax.swing.JTextArea();
        jp_Endereco_Banco = new javax.swing.JPanel();
        JPOpcao_23 = new javax.swing.JPanel();
        jTextArea20 = new javax.swing.JTextArea();
        JPOpcao_22 = new javax.swing.JPanel();
        ta_Endereco_Banco = new javax.swing.JTextArea();
        jPanel27 = new javax.swing.JPanel();
        JPOpcao_26 = new javax.swing.JPanel();
        jTextArea17 = new javax.swing.JTextArea();
        JPOpcao_27 = new javax.swing.JPanel();
        taSenhaX = new javax.swing.JPasswordField();
        jPanel24 = new javax.swing.JPanel();
        JPOpcao_32 = new javax.swing.JPanel();
        jTextArea19 = new javax.swing.JTextArea();
        JPOpcao_33 = new javax.swing.JPanel();
        taNomeUsuarioX = new javax.swing.JTextArea();
        jPanel32 = new javax.swing.JPanel();
        JPOpcao_34 = new javax.swing.JPanel();
        jTextArea21 = new javax.swing.JTextArea();
        JPOpcao_35 = new javax.swing.JPanel();
        ta_URL = new javax.swing.JTextArea();
        Properties_Criado = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JPOpcao_8 = new javax.swing.JPanel();
        jTextArea11 = new javax.swing.JTextArea();
        Properties_Criado1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jpStatus = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        JPOpcao_28 = new javax.swing.JPanel();
        jTextArea13 = new javax.swing.JTextArea();
        JPOpcao_29 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel20 = new javax.swing.JPanel();
        JPOpcao_30 = new javax.swing.JPanel();
        jTextArea16 = new javax.swing.JTextArea();
        JPOpcao_31 = new javax.swing.JPanel();
        taStatus = new javax.swing.JTextArea();
        jPanel25 = new javax.swing.JPanel();
        JPOpcao_17 = new javax.swing.JPanel();
        jTextArea14 = new javax.swing.JTextArea();
        JPOpcao_16 = new javax.swing.JPanel();
        taNomeUsuario1 = new javax.swing.JTextArea();
        jPanel23 = new javax.swing.JPanel();
        JPOpcao_15 = new javax.swing.JPanel();
        jTextArea12 = new javax.swing.JTextArea();
        JPOpcao_14 = new javax.swing.JPanel();
        taNomeUsuario = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setPreferredSize(new java.awt.Dimension(585, 458));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
        jLabel1.setText("Configuração de conexão com banco de dados");

        rbUser.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rbUser.setSelected(true);
        rbUser.setText("user.dir");
        rbUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                rbUserMouseReleased(evt);
            }
        });

        rbHome.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rbHome.setText("user.home");
        rbHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                rbHomeMouseReleased(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/conexao_com_banco_de_dados_imagens/conectar.gif"))); // NOI18N
        jButton3.setText("Criar .Properties");
        jButton3.setPreferredSize(new java.awt.Dimension(91, 27));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        JPOpcao_36.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_36.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_36MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_36MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_36MousePressed(evt);
            }
        });

        taNomeUsuarioX1.setColumns(20);
        taNomeUsuarioX1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        taNomeUsuarioX1.setRows(5);
        taNomeUsuarioX1.setText("?useTimezone=true&serverTimezone=UTC&");
        taNomeUsuarioX1.setBorder(null);
        taNomeUsuarioX1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                taNomeUsuarioX1FocusLost(evt);
            }
        });
        taNomeUsuarioX1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taNomeUsuarioX1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taNomeUsuarioX1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                taNomeUsuarioX1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_36Layout = new javax.swing.GroupLayout(JPOpcao_36);
        JPOpcao_36.setLayout(JPOpcao_36Layout);
        JPOpcao_36Layout.setHorizontalGroup(
            JPOpcao_36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeUsuarioX1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_36Layout.setVerticalGroup(
            JPOpcao_36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeUsuarioX1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_38.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_38.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_38MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_38MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_38MousePressed(evt);
            }
        });

        taSeparador.setColumns(20);
        taSeparador.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        taSeparador.setRows(5);
        taSeparador.setText("&");
        taSeparador.setBorder(null);
        taSeparador.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                taSeparadorFocusLost(evt);
            }
        });
        taSeparador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taSeparadorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taSeparadorMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                taSeparadorMousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_38Layout = new javax.swing.GroupLayout(JPOpcao_38);
        JPOpcao_38.setLayout(JPOpcao_38Layout);
        JPOpcao_38Layout.setHorizontalGroup(
            JPOpcao_38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taSeparador, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_38Layout.setVerticalGroup(
            JPOpcao_38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taSeparador, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(JPOpcao_38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbUser)
                .addGap(18, 18, 18)
                .addComponent(rbHome)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbUser)
                    .addComponent(rbHome))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JPOpcao_38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JPOpcao_36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JPOpcao_7.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_7MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_7MousePressed(evt);
            }
        });

        jTextArea7.setEditable(false);
        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea7.setRows(5);
        jTextArea7.setText("         Escolha o tipo de conexão.: ");
        jTextArea7.setBorder(null);
        jTextArea7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea7MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea7MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_7Layout = new javax.swing.GroupLayout(JPOpcao_7);
        JPOpcao_7.setLayout(JPOpcao_7Layout);
        JPOpcao_7Layout.setHorizontalGroup(
            JPOpcao_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea7, javax.swing.GroupLayout.PREFERRED_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_7Layout.setVerticalGroup(
            JPOpcao_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_25.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_25.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_25MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_25MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_25MousePressed(evt);
            }
        });

        cbTipoConexao.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        cbTipoConexao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "network", "alone" }));
        cbTipoConexao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(201, 239, 237), 1, true));
        cbTipoConexao.setPreferredSize(new java.awt.Dimension(212, 23));
        cbTipoConexao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoConexaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_25Layout = new javax.swing.GroupLayout(JPOpcao_25);
        JPOpcao_25.setLayout(JPOpcao_25Layout);
        JPOpcao_25Layout.setHorizontalGroup(
            JPOpcao_25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_25Layout.createSequentialGroup()
                .addComponent(cbTipoConexao, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        JPOpcao_25Layout.setVerticalGroup(
            JPOpcao_25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbTipoConexao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(JPOpcao_25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JPOpcao_9.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_9MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_9MousePressed(evt);
            }
        });

        jTextArea8.setEditable(false);
        jTextArea8.setColumns(20);
        jTextArea8.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea8.setRows(5);
        jTextArea8.setText("            Escolha o tipo de banco.: ");
        jTextArea8.setBorder(null);
        jTextArea8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea8MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea8MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_9Layout = new javax.swing.GroupLayout(JPOpcao_9);
        JPOpcao_9.setLayout(JPOpcao_9Layout);
        JPOpcao_9Layout.setHorizontalGroup(
            JPOpcao_9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea8, javax.swing.GroupLayout.PREFERRED_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_9Layout.setVerticalGroup(
            JPOpcao_9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_24.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_24.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_24MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_24MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_24MousePressed(evt);
            }
        });

        cbTipoBanco.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        cbTipoBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "mysql", "derby", "oracle", "sqlserver", "postgresql", "firebirdsql", "odbc" }));
        cbTipoBanco.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(201, 239, 237), 1, true));
        cbTipoBanco.setPreferredSize(new java.awt.Dimension(212, 23));
        cbTipoBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoBancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_24Layout = new javax.swing.GroupLayout(JPOpcao_24);
        JPOpcao_24.setLayout(JPOpcao_24Layout);
        JPOpcao_24Layout.setHorizontalGroup(
            JPOpcao_24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbTipoBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        JPOpcao_24Layout.setVerticalGroup(
            JPOpcao_24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbTipoBanco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(JPOpcao_9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_10.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_10MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_10MousePressed(evt);
            }
        });

        jTextArea9.setEditable(false);
        jTextArea9.setColumns(20);
        jTextArea9.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea9.setRows(5);
        jTextArea9.setText("         Escolha o driver do banco.: ");
        jTextArea9.setBorder(null);
        jTextArea9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea9MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea9MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_10Layout = new javax.swing.GroupLayout(JPOpcao_10);
        JPOpcao_10.setLayout(JPOpcao_10Layout);
        JPOpcao_10Layout.setHorizontalGroup(
            JPOpcao_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea9, javax.swing.GroupLayout.PREFERRED_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_10Layout.setVerticalGroup(
            JPOpcao_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_11.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_11.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_11MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_11MousePressed(evt);
            }
        });

        cbTipoDriver.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        cbTipoDriver.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "com.mysql.cj.jdbc.Driver", "com.mysql.jdbc.Driver", "org.apache.derby.jdbc.EmbeddedDriver", "org.apache.derby.jdbc.ClientDriver", "oracle.jdbc.driver.OracleDriver", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "org.postgresql.Driver", "org.firebirdsql.jdbc.FBDriver", "sun.jdbc.odbc.JdbcOdbcDriver" }));
        cbTipoDriver.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(201, 239, 237), 1, true));
        cbTipoDriver.setPreferredSize(new java.awt.Dimension(212, 23));
        cbTipoDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoDriverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_11Layout = new javax.swing.GroupLayout(JPOpcao_11);
        JPOpcao_11.setLayout(JPOpcao_11Layout);
        JPOpcao_11Layout.setHorizontalGroup(
            JPOpcao_11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbTipoDriver, 0, 540, Short.MAX_VALUE)
        );
        JPOpcao_11Layout.setVerticalGroup(
            JPOpcao_11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbTipoDriver, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(JPOpcao_10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_13.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_13MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_13MousePressed(evt);
            }
        });

        jTextArea10.setEditable(false);
        jTextArea10.setColumns(20);
        jTextArea10.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea10.setRows(5);
        jTextArea10.setText("          Informe o nome do banco.: ");
        jTextArea10.setBorder(null);
        jTextArea10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea10MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea10MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_13Layout = new javax.swing.GroupLayout(JPOpcao_13);
        JPOpcao_13.setLayout(JPOpcao_13Layout);
        JPOpcao_13Layout.setHorizontalGroup(
            JPOpcao_13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea10, javax.swing.GroupLayout.PREFERRED_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_13Layout.setVerticalGroup(
            JPOpcao_13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_12.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_12.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_12MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_12MousePressed(evt);
            }
        });

        taNomeBanco.setColumns(20);
        taNomeBanco.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        taNomeBanco.setRows(5);
        taNomeBanco.setText("Banco_De_Dados");
        taNomeBanco.setBorder(null);
        taNomeBanco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                taNomeBancoFocusLost(evt);
            }
        });
        taNomeBanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taNomeBancoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taNomeBancoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                taNomeBancoMousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_12Layout = new javax.swing.GroupLayout(JPOpcao_12);
        JPOpcao_12.setLayout(JPOpcao_12Layout);
        JPOpcao_12Layout.setHorizontalGroup(
            JPOpcao_12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeBanco, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_12Layout.setVerticalGroup(
            JPOpcao_12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeBanco, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(JPOpcao_13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_18.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_18.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_18MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_18MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_18MousePressed(evt);
            }
        });

        jTextArea15.setEditable(false);
        jTextArea15.setColumns(20);
        jTextArea15.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea15.setRows(5);
        jTextArea15.setText("             Informe o ip do servidor.:");
        jTextArea15.setBorder(null);
        jTextArea15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea15MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea15MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_18Layout = new javax.swing.GroupLayout(JPOpcao_18);
        JPOpcao_18.setLayout(JPOpcao_18Layout);
        JPOpcao_18Layout.setHorizontalGroup(
            JPOpcao_18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea15, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_18Layout.setVerticalGroup(
            JPOpcao_18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_19.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_19.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_19MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_19MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_19MousePressed(evt);
            }
        });

        taIpServidor.setColumns(20);
        taIpServidor.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        taIpServidor.setRows(5);
        taIpServidor.setText("127.0.0.1");
        taIpServidor.setBorder(null);
        taIpServidor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                taIpServidorFocusLost(evt);
            }
        });
        taIpServidor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taIpServidorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taIpServidorMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                taIpServidorMousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_19Layout = new javax.swing.GroupLayout(JPOpcao_19);
        JPOpcao_19.setLayout(JPOpcao_19Layout);
        JPOpcao_19Layout.setHorizontalGroup(
            JPOpcao_19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taIpServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_19Layout.setVerticalGroup(
            JPOpcao_19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taIpServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jpIpServidorLayout = new javax.swing.GroupLayout(jpIpServidor);
        jpIpServidor.setLayout(jpIpServidorLayout);
        jpIpServidorLayout.setHorizontalGroup(
            jpIpServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpIpServidorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpIpServidorLayout.setVerticalGroup(
            jpIpServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(JPOpcao_18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_21.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_21.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_21MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_21MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_21MousePressed(evt);
            }
        });

        jTextArea18.setEditable(false);
        jTextArea18.setColumns(20);
        jTextArea18.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea18.setRows(5);
        jTextArea18.setText("        Informe a porta do servidor.:");
        jTextArea18.setBorder(null);
        jTextArea18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea18MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea18MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea18MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_21Layout = new javax.swing.GroupLayout(JPOpcao_21);
        JPOpcao_21.setLayout(JPOpcao_21Layout);
        JPOpcao_21Layout.setHorizontalGroup(
            JPOpcao_21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea18, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_21Layout.setVerticalGroup(
            JPOpcao_21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea18, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_20.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_20MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_20MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_20MousePressed(evt);
            }
        });

        taPortaServidor.setColumns(20);
        taPortaServidor.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        taPortaServidor.setRows(5);
        taPortaServidor.setText("3306");
        taPortaServidor.setBorder(null);
        taPortaServidor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                taPortaServidorFocusLost(evt);
            }
        });
        taPortaServidor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taPortaServidorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taPortaServidorMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                taPortaServidorMousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_20Layout = new javax.swing.GroupLayout(JPOpcao_20);
        JPOpcao_20.setLayout(JPOpcao_20Layout);
        JPOpcao_20Layout.setHorizontalGroup(
            JPOpcao_20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taPortaServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_20Layout.setVerticalGroup(
            JPOpcao_20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taPortaServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jpPortaServidorLayout = new javax.swing.GroupLayout(jpPortaServidor);
        jpPortaServidor.setLayout(jpPortaServidorLayout);
        jpPortaServidorLayout.setHorizontalGroup(
            jpPortaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPortaServidorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(263, Short.MAX_VALUE))
        );
        jpPortaServidorLayout.setVerticalGroup(
            jpPortaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(JPOpcao_21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_23.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_23.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_23MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_23MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_23MousePressed(evt);
            }
        });

        jTextArea20.setEditable(false);
        jTextArea20.setColumns(20);
        jTextArea20.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea20.setRows(5);
        jTextArea20.setText("    Informe o endereço do banco.:");
        jTextArea20.setBorder(null);
        jTextArea20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea20MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea20MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea20MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_23Layout = new javax.swing.GroupLayout(JPOpcao_23);
        JPOpcao_23.setLayout(JPOpcao_23Layout);
        JPOpcao_23Layout.setHorizontalGroup(
            JPOpcao_23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea20, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_23Layout.setVerticalGroup(
            JPOpcao_23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea20, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_22.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_22.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_22MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_22MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_22MousePressed(evt);
            }
        });

        ta_Endereco_Banco.setColumns(20);
        ta_Endereco_Banco.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        ta_Endereco_Banco.setRows(5);
        ta_Endereco_Banco.setBorder(null);
        ta_Endereco_Banco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ta_Endereco_BancoFocusLost(evt);
            }
        });
        ta_Endereco_Banco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ta_Endereco_BancoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ta_Endereco_BancoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ta_Endereco_BancoMousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_22Layout = new javax.swing.GroupLayout(JPOpcao_22);
        JPOpcao_22.setLayout(JPOpcao_22Layout);
        JPOpcao_22Layout.setHorizontalGroup(
            JPOpcao_22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ta_Endereco_Banco)
                .addContainerGap())
        );
        JPOpcao_22Layout.setVerticalGroup(
            JPOpcao_22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ta_Endereco_Banco, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jp_Endereco_BancoLayout = new javax.swing.GroupLayout(jp_Endereco_Banco);
        jp_Endereco_Banco.setLayout(jp_Endereco_BancoLayout);
        jp_Endereco_BancoLayout.setHorizontalGroup(
            jp_Endereco_BancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_Endereco_BancoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jp_Endereco_BancoLayout.setVerticalGroup(
            jp_Endereco_BancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(JPOpcao_23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_26.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_26.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_26MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_26MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_26MousePressed(evt);
            }
        });

        jTextArea17.setEditable(false);
        jTextArea17.setColumns(20);
        jTextArea17.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea17.setRows(5);
        jTextArea17.setText("                        Informe a senha.:");
        jTextArea17.setBorder(null);
        jTextArea17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea17MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea17MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_26Layout = new javax.swing.GroupLayout(JPOpcao_26);
        JPOpcao_26.setLayout(JPOpcao_26Layout);
        JPOpcao_26Layout.setHorizontalGroup(
            JPOpcao_26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea17, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_26Layout.setVerticalGroup(
            JPOpcao_26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_27.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_27.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_27MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_27MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_27MousePressed(evt);
            }
        });

        taSenhaX.setText("23071354");
        taSenhaX.setBorder(null);
        taSenhaX.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                taSenhaXFocusLost(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_27Layout = new javax.swing.GroupLayout(JPOpcao_27);
        JPOpcao_27.setLayout(JPOpcao_27Layout);
        JPOpcao_27Layout.setHorizontalGroup(
            JPOpcao_27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taSenhaX, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_27Layout.setVerticalGroup(
            JPOpcao_27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taSenhaX, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPOpcao_26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_32.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_32.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_32MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_32MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_32MousePressed(evt);
            }
        });

        jTextArea19.setEditable(false);
        jTextArea19.setColumns(20);
        jTextArea19.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea19.setRows(5);
        jTextArea19.setText("        Informe o nome de usuário.: ");
        jTextArea19.setBorder(null);
        jTextArea19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea19MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea19MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea19MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_32Layout = new javax.swing.GroupLayout(JPOpcao_32);
        JPOpcao_32.setLayout(JPOpcao_32Layout);
        JPOpcao_32Layout.setHorizontalGroup(
            JPOpcao_32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea19, javax.swing.GroupLayout.PREFERRED_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_32Layout.setVerticalGroup(
            JPOpcao_32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea19, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_33.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_33.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_33MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_33MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_33MousePressed(evt);
            }
        });

        taNomeUsuarioX.setColumns(20);
        taNomeUsuarioX.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        taNomeUsuarioX.setRows(5);
        taNomeUsuarioX.setText("cleilson");
        taNomeUsuarioX.setBorder(null);
        taNomeUsuarioX.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                taNomeUsuarioXFocusLost(evt);
            }
        });
        taNomeUsuarioX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taNomeUsuarioXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taNomeUsuarioXMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                taNomeUsuarioXMousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_33Layout = new javax.swing.GroupLayout(JPOpcao_33);
        JPOpcao_33.setLayout(JPOpcao_33Layout);
        JPOpcao_33Layout.setHorizontalGroup(
            JPOpcao_33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeUsuarioX, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_33Layout.setVerticalGroup(
            JPOpcao_33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeUsuarioX, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPOpcao_32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_34.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_34.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_34MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_34MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_34MousePressed(evt);
            }
        });

        jTextArea21.setEditable(false);
        jTextArea21.setColumns(20);
        jTextArea21.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea21.setRows(5);
        jTextArea21.setText("                                          URL.:");
        jTextArea21.setBorder(null);
        jTextArea21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea21MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea21MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea21MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_34Layout = new javax.swing.GroupLayout(JPOpcao_34);
        JPOpcao_34.setLayout(JPOpcao_34Layout);
        JPOpcao_34Layout.setHorizontalGroup(
            JPOpcao_34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea21, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_34Layout.setVerticalGroup(
            JPOpcao_34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_35.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_35.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_35MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_35MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_35MousePressed(evt);
            }
        });

        ta_URL.setColumns(20);
        ta_URL.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        ta_URL.setRows(5);
        ta_URL.setBorder(null);
        ta_URL.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ta_URLFocusLost(evt);
            }
        });
        ta_URL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ta_URLMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ta_URLMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ta_URLMousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_35Layout = new javax.swing.GroupLayout(JPOpcao_35);
        JPOpcao_35.setLayout(JPOpcao_35Layout);
        JPOpcao_35Layout.setHorizontalGroup(
            JPOpcao_35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ta_URL)
                .addContainerGap())
        );
        JPOpcao_35Layout.setVerticalGroup(
            JPOpcao_35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ta_URL, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPOpcao_34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_35, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_Endereco_Banco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jpPortaServidor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jpIpServidor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 983, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpIpServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpPortaServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jp_Endereco_Banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Criar Properties", jPanel1);

        Properties_Criado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Properties_Criado.setPreferredSize(new java.awt.Dimension(585, 458));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
        jLabel2.setText("Arquivo .Properties Criado");

        JPOpcao_8.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_8MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_8MousePressed(evt);
            }
        });

        jTextArea11.setColumns(20);
        jTextArea11.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea11.setRows(5);
        jTextArea11.setBorder(null);
        jTextArea11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea11MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea11MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_8Layout = new javax.swing.GroupLayout(JPOpcao_8);
        JPOpcao_8.setLayout(JPOpcao_8Layout);
        JPOpcao_8Layout.setHorizontalGroup(
            JPOpcao_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea11, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_8Layout.setVerticalGroup(
            JPOpcao_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea11, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        jScrollPane1.setViewportView(JPOpcao_8);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout Properties_CriadoLayout = new javax.swing.GroupLayout(Properties_Criado);
        Properties_Criado.setLayout(Properties_CriadoLayout);
        Properties_CriadoLayout.setHorizontalGroup(
            Properties_CriadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Properties_CriadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Properties_CriadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        Properties_CriadoLayout.setVerticalGroup(
            Properties_CriadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Properties_CriadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Properties Criado", Properties_Criado);

        Properties_Criado1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Properties_Criado1.setPreferredSize(new java.awt.Dimension(585, 458));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
        jLabel3.setText("Testar Conexão");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/conexao_com_banco_de_dados_imagens/img_dbs.jpg"))); // NOI18N

        JPOpcao_28.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_28.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_28MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_28MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_28MousePressed(evt);
            }
        });

        jTextArea13.setEditable(false);
        jTextArea13.setColumns(20);
        jTextArea13.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea13.setRows(5);
        jTextArea13.setText("Tabelas.:");
        jTextArea13.setBorder(null);
        jTextArea13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea13MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea13MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_28Layout = new javax.swing.GroupLayout(JPOpcao_28);
        JPOpcao_28.setLayout(JPOpcao_28Layout);
        JPOpcao_28Layout.setHorizontalGroup(
            JPOpcao_28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextArea13, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        JPOpcao_28Layout.setVerticalGroup(
            JPOpcao_28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_29.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_29.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_29MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_29MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_29MousePressed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "------" }));
        jComboBox1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(201, 239, 237), 1, true));
        jComboBox1.setPreferredSize(new java.awt.Dimension(212, 23));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_29Layout = new javax.swing.GroupLayout(JPOpcao_29);
        JPOpcao_29.setLayout(JPOpcao_29Layout);
        JPOpcao_29Layout.setHorizontalGroup(
            JPOpcao_29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jComboBox1, 0, 493, Short.MAX_VALUE)
        );
        JPOpcao_29Layout.setVerticalGroup(
            JPOpcao_29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPOpcao_28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_30.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_30.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_30MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_30MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_30MousePressed(evt);
            }
        });

        jTextArea16.setEditable(false);
        jTextArea16.setColumns(20);
        jTextArea16.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea16.setRows(5);
        jTextArea16.setText("Status.:");
        jTextArea16.setBorder(null);
        jTextArea16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea16MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea16MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_30Layout = new javax.swing.GroupLayout(JPOpcao_30);
        JPOpcao_30.setLayout(JPOpcao_30Layout);
        JPOpcao_30Layout.setHorizontalGroup(
            JPOpcao_30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextArea16, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        JPOpcao_30Layout.setVerticalGroup(
            JPOpcao_30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_31.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_31.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_31.setPreferredSize(new java.awt.Dimension(289, 46));
        JPOpcao_31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_31MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_31MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_31MousePressed(evt);
            }
        });

        taStatus.setColumns(20);
        taStatus.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        taStatus.setRows(5);
        taStatus.setBorder(null);
        taStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taStatusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taStatusMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                taStatusMousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_31Layout = new javax.swing.GroupLayout(JPOpcao_31);
        JPOpcao_31.setLayout(JPOpcao_31Layout);
        JPOpcao_31Layout.setHorizontalGroup(
            JPOpcao_31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_31Layout.setVerticalGroup(
            JPOpcao_31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_31, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPOpcao_30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jpStatusLayout = new javax.swing.GroupLayout(jpStatus);
        jpStatus.setLayout(jpStatusLayout);
        jpStatusLayout.setHorizontalGroup(
            jpStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpStatusLayout.setVerticalGroup(
            jpStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpStatusLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        JPOpcao_17.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_17.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_17MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_17MousePressed(evt);
            }
        });

        jTextArea14.setEditable(false);
        jTextArea14.setColumns(20);
        jTextArea14.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea14.setRows(5);
        jTextArea14.setText("URL.:");
        jTextArea14.setBorder(null);
        jTextArea14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea14MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea14MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_17Layout = new javax.swing.GroupLayout(JPOpcao_17);
        JPOpcao_17.setLayout(JPOpcao_17Layout);
        JPOpcao_17Layout.setHorizontalGroup(
            JPOpcao_17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea14, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_17Layout.setVerticalGroup(
            JPOpcao_17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_16.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_16.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_16MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_16MousePressed(evt);
            }
        });

        taNomeUsuario1.setColumns(20);
        taNomeUsuario1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        taNomeUsuario1.setRows(5);
        taNomeUsuario1.setBorder(null);
        taNomeUsuario1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taNomeUsuario1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taNomeUsuario1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                taNomeUsuario1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_16Layout = new javax.swing.GroupLayout(JPOpcao_16);
        JPOpcao_16.setLayout(JPOpcao_16Layout);
        JPOpcao_16Layout.setHorizontalGroup(
            JPOpcao_16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeUsuario1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_16Layout.setVerticalGroup(
            JPOpcao_16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeUsuario1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(JPOpcao_17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPOpcao_16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JPOpcao_15.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_15.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_15MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_15MousePressed(evt);
            }
        });

        jTextArea12.setEditable(false);
        jTextArea12.setColumns(20);
        jTextArea12.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextArea12.setRows(5);
        jTextArea12.setText("Driver.: ");
        jTextArea12.setBorder(null);
        jTextArea12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextArea12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextArea12MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextArea12MousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_15Layout = new javax.swing.GroupLayout(JPOpcao_15);
        JPOpcao_15.setLayout(JPOpcao_15Layout);
        JPOpcao_15Layout.setHorizontalGroup(
            JPOpcao_15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea12, javax.swing.GroupLayout.PREFERRED_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_15Layout.setVerticalGroup(
            JPOpcao_15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextArea12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        JPOpcao_14.setBackground(new java.awt.Color(255, 255, 255));
        JPOpcao_14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));
        JPOpcao_14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPOpcao_14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPOpcao_14MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPOpcao_14MousePressed(evt);
            }
        });

        taNomeUsuario.setColumns(20);
        taNomeUsuario.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        taNomeUsuario.setRows(5);
        taNomeUsuario.setBorder(null);
        taNomeUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taNomeUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taNomeUsuarioMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                taNomeUsuarioMousePressed(evt);
            }
        });

        javax.swing.GroupLayout JPOpcao_14Layout = new javax.swing.GroupLayout(JPOpcao_14);
        JPOpcao_14.setLayout(JPOpcao_14Layout);
        JPOpcao_14Layout.setHorizontalGroup(
            JPOpcao_14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPOpcao_14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPOpcao_14Layout.setVerticalGroup(
            JPOpcao_14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPOpcao_14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taNomeUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home_controle_menus_norte/imagens/Save.png"))); // NOI18N
        jButton1.setText("Salvar");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home_controle_menus_norte/imagens/iconeCompleto.png"))); // NOI18N
        jButton2.setText("Ver");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPOpcao_15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPOpcao_14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(JPOpcao_15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JPOpcao_14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2))))
        );

        jButton4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/conexao_com_banco_de_dados_imagens/conectar.gif"))); // NOI18N
        jButton4.setText("Conectar");
        jButton4.setPreferredSize(new java.awt.Dimension(91, 27));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout Properties_Criado1Layout = new javax.swing.GroupLayout(Properties_Criado1);
        Properties_Criado1.setLayout(Properties_Criado1Layout);
        Properties_Criado1Layout.setHorizontalGroup(
            Properties_Criado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Properties_Criado1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Properties_Criado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
                    .addGroup(Properties_Criado1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Properties_Criado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Properties_Criado1Layout.setVerticalGroup(
            Properties_Criado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Properties_Criado1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(Properties_Criado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(Properties_Criado1Layout.createSequentialGroup()
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jpStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Testar_Conexão", Properties_Criado1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1044, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    boolean umavez = false;
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 

            tipo_de_conexao = ""; try{ tipo_de_conexao = cbTipoConexao.getSelectedItem().toString().trim(); } catch( Exception e ){}
            tipoDeBanco     = ""; try{ tipoDeBanco = cbTipoBanco.getSelectedItem().toString().trim(); } catch( Exception e ){}
            driver          = ""; try{ driver = cbTipoDriver.getSelectedItem().toString().trim(); } catch( Exception e ){}
            nomeDoBanco     = ""; try{ nomeDoBanco = taNomeBanco.getText().trim(); } catch( Exception e ){}
            ip_do_servidor  = ""; try{ ip_do_servidor = taIpServidor.getText().trim(); } catch( Exception e ){}
            porta_servidor  = ""; try{ porta_servidor = taPortaServidor.getText().trim(); } catch( Exception e ){}                     
            usuario         = ""; try{ usuario = taNomeUsuarioX.getText().trim(); } catch( Exception e ){}                     
            
            ////////////////////////////////////////////////////////////////////////////////////////////////
            char[] senhaX  = null;  try{ senhaX   = taSenhaX.getPassword();           } catch( Exception e ){}
            String senhaP   = "";   try{ senhaP   = Arrays.toString(senhaX);          } catch( Exception e ){}
            String senhaY  = "";    try{ senhaY   = senhaP.replace("[", "");          } catch( Exception e ){}
            String senhaY2 = "";    try{ senhaY2  = senhaY.replace("]", "");          } catch( Exception e ){}
            String senhaY3 = "";    try{ senhaY3  = senhaY2.replace(" ", "");         } catch( Exception e ){}
            String senhaFinal = ""; try{ senhaFinal    = senhaY3.replace(",", "");        } catch( Exception e ){}
            //////////////////////////////////////////////////////////////////////////////////////////////// 
            senha        = ""; try{ senha = senhaFinal; } catch( Exception e ){} 
            
            endereco_db  = ""; try{ endereco_db = ta_Endereco_Banco.getText().trim();; } catch( Exception e ){}
               
            if( tipo_de_conexao.equalsIgnoreCase("alone") ){
                
                url = ""; try{ url = "jdbc:"+tipoDeBanco+":" + endereco_db + "/" + nomeDoBanco+";" + "user="+usuario+";" + "password="+senha+""; } catch( Exception e ){}  
                
                ta_URL.setText(url);
                
                try{ 
                    jpIpServidor.setVisible(false);
                    jpPortaServidor.setVisible(false);
                    
                    jp_Endereco_Banco.setVisible(true);
                } catch( Exception e ){}
            }     
            else if( tipo_de_conexao.equalsIgnoreCase("network") ){
                
                String separador = "&"; //  ";" 
                try{ separador = taSeparador.getText().trim();   } catch( Exception e ){}                
                
                String time_zone_a_usar = "";
                
                String time_zone = null; try{ time_zone = taNomeUsuarioX1.getText().trim();   } catch( Exception e ){}                
                if( time_zone.equalsIgnoreCase("?useTimezone=true&serverTimezone=UTC&") ){
                    
                    time_zone_a_usar = "?useTimezone=true&serverTimezone=UTC&";
                }
                else{
                    time_zone_a_usar = separador;
                }
                
                url = ""; try{ url = "jdbc:"+tipoDeBanco+"://" + ip_do_servidor + ":"+porta_servidor+"/" + nomeDoBanco+ time_zone_a_usar + "user="+usuario + separador + "password="+senha+""; } catch( Exception e ){}  
           
                ta_URL.setText(url);
                
                try{ 
                    jpIpServidor.setVisible(true);
                    jpPortaServidor.setVisible(true);
                    
                    jp_Endereco_Banco.setVisible(false);
                } catch( Exception e ){}
            }
            
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String arquivoASerCriado = System.getProperty("user.dir") + "\\" + nomeArquivoPropertiesASerCriado + ".properties";                                    
            Arquivo_Ou_Pasta.deletar( new File( arquivoASerCriado ) );            
            Thread.sleep( 100 ); 
            
            Properties properties = new Properties();
                       properties.put("tipo_de_conexao", tipo_de_conexao);
                       properties.put("tipoDeBanco",     tipoDeBanco);
                       properties.put("driver",          driver);
                       properties.put("nomeDoBanco",     nomeDoBanco);
                       properties.put("usuario",         usuario);
                       properties.put("senha",           senha);
                       properties.put("ip_do_servidor",  ip_do_servidor);
                       properties.put("porta_servidor",  porta_servidor);
                       properties.put("endereco_db",     endereco_db);
                       properties.put("url",             url);
                       
            FileOutputStream out = new FileOutputStream( nomeArquivoPropertiesASerCriado + ".properties" );
            properties.storeToXML(out, "updated", "UTF-8"); 
            out.flush();
            out.close();
            
            FileInputStream in = new FileInputStream( nomeArquivoPropertiesASerCriado + ".properties" );
            properties.loadFromXML(in);
            in.close();
                            
            Home.setar_Banco_de_Dados(properties);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            Object[]       itens = { "MOSTRAR ARQUIVO .PROPERTIES", "NÃO MOSTRAR" };
            Object selectedValue = JOptionPane.showInputDialog(null, 
                    "ESCOLHA UM ITEM", 
                    "ARQUIVO .PROPERTIES", 
                    JOptionPane.INFORMATION_MESSAGE, null,
                    itens, itens[0]
            );
            
            if( selectedValue.equals("MOSTRAR ARQUIVO .PROPERTIES") ){
                                
                /*Leitura das variáveis no Properties props*/
                StringBuilder sb = new StringBuilder();
                for(Enumeration elms = properties.propertyNames(); elms.hasMoreElements();){
                    String prop = (String)elms.nextElement();
                    sb.append( prop + " = " + properties.getProperty(prop) + "\n" );
                    //syso(prop + ": " + props.getProperty(prop));
                    //syso(elms.hasMoreElements() ? "\n" : "");
                }
                jTextArea11.setText( sb.toString() );
                   
                if(umavez == false){
                    umavez = true;
                    //Home.ControleTabs.AddTabSemControleNovoSemThread(jTabbedPane1, "Properties Criado", "/imagens_internas/livroTp.gif", Properties_CriadoX);
                    jTabbedPane1.setSelectedComponent(Properties_Criado);
                }
                else{
                    
                    jTabbedPane1.setSelectedComponent(Properties_Criado);
                }
            }        
            
        } catch( Exception e ){  } } }.start();        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void rbUserMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbUserMouseReleased
        rbHome.setSelected(false);
        //rbUser.setSelected(false);
        String user_dir = System.getProperty("user.dir");
        ta_Endereco_Banco.setText(user_dir);
        
        inicioX();
    }//GEN-LAST:event_rbUserMouseReleased

    private void rbHomeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbHomeMouseReleased
        //rbHome.setSelected(false);
        rbUser.setSelected(false);
        String user_dir = System.getProperty("user.home");
        ta_Endereco_Banco.setText(user_dir);
        
        inicioX();
    }//GEN-LAST:event_rbHomeMouseReleased

    private void jTextArea7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea7MouseEntered

    private void jTextArea7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea7MouseExited

    private void jTextArea7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea7MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea7MousePressed

    private void JPOpcao_7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_7MouseEntered

    private void JPOpcao_7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_7MouseExited

    private void JPOpcao_7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_7MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_7MousePressed

    private void jTextArea8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea8MouseEntered

    private void jTextArea8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea8MouseExited

    private void jTextArea8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea8MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea8MousePressed

    private void JPOpcao_9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_9MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_9MouseEntered

    private void JPOpcao_9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_9MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_9MouseExited

    private void JPOpcao_9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_9MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_9MousePressed

    private void jTextArea9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea9MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea9MouseEntered

    private void jTextArea9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea9MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea9MouseExited

    private void jTextArea9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea9MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea9MousePressed

    private void JPOpcao_10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_10MouseEntered

    private void JPOpcao_10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_10MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_10MouseExited

    private void JPOpcao_10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_10MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_10MousePressed

    private void cbTipoDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoDriverActionPerformed
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 
               
            String driver = "";
            try{ driver = cbTipoDriver.getSelectedItem().toString().trim(); } catch( Exception e ){}
            
            //DB_Bean.driver = driver;
            
            inicioX();
                        
        } catch( Exception e ){  } } }.start();
    }//GEN-LAST:event_cbTipoDriverActionPerformed

    private void JPOpcao_11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_11MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_11MouseEntered

    private void JPOpcao_11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_11MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_11MouseExited

    private void JPOpcao_11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_11MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_11MousePressed

    private void JPOpcao_12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_12MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_12MouseEntered

    private void JPOpcao_12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_12MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_12MouseExited

    private void JPOpcao_12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_12MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_12MousePressed

    private void jTextArea10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea10MouseEntered

    private void jTextArea10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea10MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea10MouseExited

    private void jTextArea10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea10MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea10MousePressed

    private void JPOpcao_13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_13MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_13MouseEntered

    private void JPOpcao_13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_13MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_13MouseExited

    private void JPOpcao_13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_13MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_13MousePressed

    private void taNomeBancoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeBancoMouseEntered
        
    }//GEN-LAST:event_taNomeBancoMouseEntered

    private void taNomeBancoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeBancoMouseExited
   
    }//GEN-LAST:event_taNomeBancoMouseExited

    private void taNomeBancoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeBancoMousePressed

    }//GEN-LAST:event_taNomeBancoMousePressed

    private void taNomeUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuarioMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioMouseEntered

    private void taNomeUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuarioMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioMouseExited

    private void taNomeUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuarioMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioMousePressed

    private void JPOpcao_14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_14MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_14MouseEntered

    private void JPOpcao_14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_14MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_14MouseExited

    private void JPOpcao_14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_14MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_14MousePressed

    private void jTextArea12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea12MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea12MouseEntered

    private void jTextArea12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea12MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea12MouseExited

    private void jTextArea12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea12MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea12MousePressed

    private void JPOpcao_15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_15MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_15MouseEntered

    private void JPOpcao_15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_15MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_15MouseExited

    private void JPOpcao_15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_15MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_15MousePressed

    private void JPOpcao_16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_16MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_16MouseEntered

    private void JPOpcao_16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_16MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_16MouseExited

    private void JPOpcao_16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_16MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_16MousePressed

    private void jTextArea14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea14MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea14MouseEntered

    private void jTextArea14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea14MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea14MouseExited

    private void jTextArea14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea14MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea14MousePressed

    private void JPOpcao_17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_17MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_17MouseEntered

    private void JPOpcao_17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_17MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_17MouseExited

    private void JPOpcao_17MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_17MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_17MousePressed

    private void jTextArea15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea15MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea15MouseEntered

    private void jTextArea15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea15MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea15MouseExited

    private void jTextArea15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea15MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea15MousePressed

    private void JPOpcao_18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_18MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_18MouseEntered

    private void JPOpcao_18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_18MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_18MouseExited

    private void JPOpcao_18MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_18MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_18MousePressed

    private void taIpServidorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taIpServidorMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_taIpServidorMouseEntered

    private void taIpServidorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taIpServidorMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_taIpServidorMouseExited

    private void taIpServidorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taIpServidorMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_taIpServidorMousePressed

    private void JPOpcao_19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_19MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_19MouseEntered

    private void JPOpcao_19MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_19MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_19MouseExited

    private void JPOpcao_19MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_19MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_19MousePressed

    private void taPortaServidorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taPortaServidorMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_taPortaServidorMouseEntered

    private void taPortaServidorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taPortaServidorMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_taPortaServidorMouseExited

    private void taPortaServidorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taPortaServidorMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_taPortaServidorMousePressed

    private void JPOpcao_20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_20MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_20MouseEntered

    private void JPOpcao_20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_20MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_20MouseExited

    private void JPOpcao_20MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_20MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_20MousePressed

    private void jTextArea18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea18MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea18MouseEntered

    private void jTextArea18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea18MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea18MouseExited

    private void jTextArea18MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea18MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea18MousePressed

    private void JPOpcao_21MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_21MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_21MouseEntered

    private void JPOpcao_21MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_21MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_21MouseExited

    private void JPOpcao_21MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_21MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_21MousePressed

    private void ta_Endereco_BancoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ta_Endereco_BancoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_Endereco_BancoMouseEntered

    private void ta_Endereco_BancoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ta_Endereco_BancoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_Endereco_BancoMouseExited

    private void ta_Endereco_BancoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ta_Endereco_BancoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_Endereco_BancoMousePressed

    private void JPOpcao_22MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_22MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_22MouseEntered

    private void JPOpcao_22MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_22MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_22MouseExited

    private void JPOpcao_22MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_22MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_22MousePressed

    private void jTextArea20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea20MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea20MouseEntered

    private void jTextArea20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea20MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea20MouseExited

    private void jTextArea20MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea20MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea20MousePressed

    private void JPOpcao_23MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_23MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_23MouseEntered

    private void JPOpcao_23MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_23MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_23MouseExited

    private void JPOpcao_23MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_23MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_23MousePressed

    private void JPOpcao_25MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_25MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_25MousePressed

    private void JPOpcao_25MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_25MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_25MouseExited

    private void JPOpcao_25MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_25MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_25MouseEntered

    private void cbTipoConexaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoConexaoActionPerformed
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 
               
            String tipo_de_conexao = "";
            try{ tipo_de_conexao = cbTipoConexao.getSelectedItem().toString().trim(); } catch( Exception e ){}
            
            //DB_Bean.tipo_de_conexao =  tipo_de_conexao;
            
            inicioX();
                    
        } catch( Exception e ){  } } }.start();
    }//GEN-LAST:event_cbTipoConexaoActionPerformed

    private void JPOpcao_24MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_24MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_24MousePressed

    private void JPOpcao_24MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_24MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_24MouseExited

    private void JPOpcao_24MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_24MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_24MouseEntered

    private void cbTipoBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoBancoActionPerformed
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 
               
            String tipoDeBanco = "";
            try{ tipoDeBanco = cbTipoBanco.getSelectedItem().toString().trim(); } catch( Exception e ){}
            
            //DB_Bean.tipoDeBanco =  tipoDeBanco;
            
            inicioX();
                    
        } catch( Exception e ){  } } }.start();
    }//GEN-LAST:event_cbTipoBancoActionPerformed

    private void JPOpcao_8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_8MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_8MousePressed

    private void JPOpcao_8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_8MouseExited

    private void JPOpcao_8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_8MouseEntered

    private void jTextArea11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea11MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea11MousePressed

    private void jTextArea11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea11MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea11MouseExited

    private void jTextArea11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea11MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea11MouseEntered

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 

            Properties props = new Properties();                        
            FileInputStream in = new FileInputStream( nomeArquivoPropertiesASerCriado + ".properties" );
            props.loadFromXML(in);
            in.close();         
            
            //props.put("usuario", usuario);
            //props.put("senha",   senhaX);
            /*
            String usuario = "";   try{ usuario  = taNomeUsuario.getText().trim();  } catch( Exception e ){}
            char[] senhaX  = null; try{ senhaX   = taSenha.getPassword();           } catch( Exception e ){}
            String senhaP   = "";   try{ senhaP    = Arrays.toString(senhaX);         } catch( Exception e ){}
            String senhaY  = "";   try{ senhaY   = senhaP.replace("[", "");          } catch( Exception e ){}
            String senhaY2 = "";   try{ senhaY2  = senhaY.replace("]", "");         } catch( Exception e ){}
            String senhaY3 = "";   try{ senhaY3  = senhaY2.replace(" ", "");        } catch( Exception e ){}
            String senhaY4 = "";   try{ senhaY4  = senhaY3.replace(",", "");        } catch( Exception e ){}
            */

            Home.setar_Banco_de_Dados(props);
            
            System.out.println("\n**************************************************");
            System.out.println("DB_Bean.url - " + DB_Bean.url);
            System.out.println("DB_Bean.driver - " + DB_Bean.driver);
            System.out.println("**************************************************\n");
                        
            try{
                //   DB_Bean.driver         //   "com.mysql.cj.jdbc.Driver"    
                Class.forName( DB_Bean.driver ); // "com.mysql.jdbc.Driver"  
                    
            } catch (ClassNotFoundException e) { //Driver não encontrado   
                System.err.println("\n**************************************************");
                System.err.println("O driver expecificado nao foi encontrado.");
                System.err.println("\n**************************************************");
                taStatus.setText( "O driver expecificado nao foi encontrado." );
            }
            
            Connection connection = null;
            try{
                    
                //   DB_Bean.url     
                String timezone_utc_mysql = "useTimezone=true&serverTimezone=UTC&";
                String urlNetwork22 = "jdbc:"+tipoDeBanco+"://"+ip_do_servidor+":"+porta_servidor+"/"+nomeDoBanco+"?"+timezone_utc_mysql+"user="+usuario+"&password="+senha+"";
                connection = DriverManager.getConnection ( DB_Bean.url );
                
                if (connection == null) {
                    
                    taStatus.setText( "STATUS--->Não foi possivel realizar conexão" );
                } else {
                    
                    taStatus.setText( "STATUS--->Conectado com sucesso!" );
                    
                    //teste_de_conexao_listarTabelasDoBanco(connection);
                }
                    
            } catch (SQLException e) { //Driver não encontrado   
                System.err.println("\n**************************************************");
                System.err.println("Nao foi possivel conectar ao Banco de Dados.");
                System.err.println("\n**************************************************");
                taStatus.setText( "Nao foi possivel conectar ao Banco de Dados." );
            }
            
        } catch( Exception e ){ e.printStackTrace(); } } }.start();  
    }//GEN-LAST:event_jButton4ActionPerformed

    private void teste_de_conexao_listarTabelasDoBanco(Connection conn){        
        /*new Thread() {   @Override public void run() {*/ try { 
            
            if( jComboBox1.getItemCount() > 0 ) { jComboBox1.removeAllItems(); }
            
            // --- LISTING DATABASE SCHEMA NAMES ---
            ResultSet resultSet = conn.getMetaData().getCatalogs();
 
            while (resultSet.next()) {
                //log.info("Schema Name = " + resultSet.getString("TABLE_CAT"));
                String str = ""; try { str = resultSet.getString("TABLE_CAT"); } catch (Exception e) { } 
                jComboBox1.addItem( str );
            }
            resultSet.close();                                  
            
            taStatus.setText("Conexão Estabelecida Com Sucesso" );
            //taStatus.setText("Conexão Não Estabelecids" );
        } catch( Exception e ){ 
            
            //taStatus.setText("Conexão Estabelecida Com Sucesso" );
            taStatus.setText("Conexão Não Estabelecida" );
            JOPM JOptionPaneMod = new JOPM( 2, "private void teste_de_conexao_listarTabelasDoBanco(){ , \n"
                + e.getMessage() + "\n", "Conexao_Com_Banco_De_Dados_Derby_Alone" ); 
            e.printStackTrace();
        } 
        //} }.start();                 
    }
    
    private void listarTabelasDoBanco2(){        
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 );                     
            DB DB = new DB();
            Connection con = DB.derby();
            String query;
            PreparedStatement ps;
            ResultSet rs;
        
            query = "SELECT TABLENAME FROM SYS.SYSTABLES WHERE TABLETYPE='T'";
            ps = null; try { ps = con.prepareStatement(query); } catch (Exception e) { } 
            rs = null;         try { rs = ps.executeQuery(); } catch (Exception e) { }   
            if(rs!=null){
                try { 
                    
                    ResultSetMetaData md = rs.getMetaData();
                    int count = md.getColumnCount();
                    
                    /*for (int i = 1; i <= count; i++) {
                      
                        if(i == 1){
                            
                            Menu.Tabela_Pesquisa_BD_Estabelecimento.tmPesquisa = new DefaultTableModel( null, new String[]{ 
                                md.getColumnName(i)           
                            } );
        
                            while ( Menu.Tabela_Pesquisa_BD_Estabelecimento.tmPesquisa.getRowCount() > 0){ Menu.Tabela_Pesquisa_BD_Estabelecimento.tmPesquisa.removeRow(0); }
                            
                            Menu.Tabela_Pesquisa_BD_Estabelecimento.tbPesquisa.setModel(Menu.Tabela_Pesquisa_BD_Estabelecimento.tmPesquisa);
                        }
                        else{
                            
                            Menu.Tabela_Pesquisa_BD_Estabelecimento.tmPesquisa.addColumn( md.getColumnName(i) );
                        }
                    }*/
                    
                    if( jComboBox1.getItemCount() > 0 ) { jComboBox1.removeAllItems(); }
                                                            
                    while (rs.next()) {  
                                                                        
                        for (int i = 1; i <= count; i++) {
                            
                            String str = ""; try { str = rs.getString(i); } catch (Exception e) { } 

                            //Menu.Tabela_Pesquisa_BD_Estabelecimento.tmPesquisa.addRow(new Object[]{ str });
                            jComboBox1.addItem( str );
                        }
                    }
                } catch (Exception e) { } 
            }
                        
            taStatus.setText("Conexão Estabelecida Com Sucesso" );
            //taStatus.setText("Conexão Não Estabelecids" );
        } catch( Exception e ){
            //
        } } }.start();                 
    }
    
    private void taStatusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taStatusMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_taStatusMouseEntered

    private void taStatusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taStatusMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_taStatusMouseExited

    private void taStatusMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taStatusMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_taStatusMousePressed

    private void JPOpcao_28MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_28MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_28MouseEntered

    private void JPOpcao_28MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_28MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_28MouseExited

    private void JPOpcao_28MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_28MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_28MousePressed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void JPOpcao_29MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_29MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_29MouseEntered

    private void JPOpcao_29MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_29MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_29MouseExited

    private void JPOpcao_29MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_29MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_29MousePressed

    private void jTextArea13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea13MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea13MousePressed

    private void jTextArea13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea13MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea13MouseExited

    private void jTextArea13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea13MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea13MouseEntered

    private void jTextArea16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea16MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea16MouseEntered

    private void jTextArea16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea16MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea16MouseExited

    private void jTextArea16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea16MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea16MousePressed

    private void JPOpcao_30MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_30MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_30MouseEntered

    private void JPOpcao_30MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_30MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_30MouseExited

    private void JPOpcao_30MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_30MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_30MousePressed

    private void JPOpcao_31MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_31MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_31MouseEntered

    private void JPOpcao_31MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_31MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_31MouseExited

    private void JPOpcao_31MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_31MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_31MousePressed

    private void jTextArea17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea17MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea17MouseEntered

    private void jTextArea17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea17MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea17MouseExited

    private void jTextArea17MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea17MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea17MousePressed

    private void JPOpcao_26MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_26MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_26MouseEntered

    private void JPOpcao_26MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_26MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_26MouseExited

    private void JPOpcao_26MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_26MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_26MousePressed

    private void JPOpcao_27MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_27MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_27MouseEntered

    private void JPOpcao_27MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_27MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_27MouseExited

    private void JPOpcao_27MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_27MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_27MousePressed

    private void jTextArea19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea19MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea19MouseEntered

    private void jTextArea19MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea19MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea19MouseExited

    private void jTextArea19MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea19MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea19MousePressed

    private void JPOpcao_32MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_32MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_32MouseEntered

    private void JPOpcao_32MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_32MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_32MouseExited

    private void JPOpcao_32MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_32MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_32MousePressed

    private void taNomeUsuarioXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuarioXMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioXMouseEntered

    private void taNomeUsuarioXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuarioXMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioXMouseExited

    private void taNomeUsuarioXMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuarioXMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioXMousePressed

    private void JPOpcao_33MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_33MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_33MouseEntered

    private void JPOpcao_33MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_33MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_33MouseExited

    private void JPOpcao_33MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_33MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_33MousePressed

    private void jTextArea21MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea21MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea21MouseEntered

    private void jTextArea21MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea21MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea21MouseExited

    private void jTextArea21MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea21MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea21MousePressed

    private void JPOpcao_34MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_34MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_34MouseEntered

    private void JPOpcao_34MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_34MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_34MouseExited

    private void JPOpcao_34MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_34MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_34MousePressed

    private void ta_URLMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ta_URLMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_URLMouseEntered

    private void ta_URLMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ta_URLMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_URLMouseExited

    private void ta_URLMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ta_URLMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_URLMousePressed

    private void JPOpcao_35MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_35MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_35MouseEntered

    private void JPOpcao_35MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_35MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_35MouseExited

    private void JPOpcao_35MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_35MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_35MousePressed

    private void taNomeBancoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taNomeBancoFocusLost
        inicioX();
    }//GEN-LAST:event_taNomeBancoFocusLost

    private void taIpServidorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taIpServidorFocusLost
        inicioX();
    }//GEN-LAST:event_taIpServidorFocusLost

    private void taPortaServidorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taPortaServidorFocusLost
        inicioX();
    }//GEN-LAST:event_taPortaServidorFocusLost

    private void taNomeUsuarioXFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taNomeUsuarioXFocusLost
        inicioX();
    }//GEN-LAST:event_taNomeUsuarioXFocusLost

    private void taSenhaXFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taSenhaXFocusLost
        inicioX();
    }//GEN-LAST:event_taSenhaXFocusLost

    private void ta_Endereco_BancoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ta_Endereco_BancoFocusLost
        inicioX();
    }//GEN-LAST:event_ta_Endereco_BancoFocusLost

    private void ta_URLFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ta_URLFocusLost
        inicioX();
    }//GEN-LAST:event_ta_URLFocusLost

    private void taNomeUsuarioX1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taNomeUsuarioX1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioX1FocusLost

    private void taNomeUsuarioX1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuarioX1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioX1MouseEntered

    private void taNomeUsuarioX1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuarioX1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioX1MouseExited

    private void taNomeUsuarioX1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuarioX1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuarioX1MousePressed

    private void JPOpcao_36MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_36MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_36MouseEntered

    private void JPOpcao_36MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_36MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_36MouseExited

    private void JPOpcao_36MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_36MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_36MousePressed

    private void taNomeUsuario1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuario1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuario1MouseEntered

    private void taNomeUsuario1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuario1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuario1MouseExited

    private void taNomeUsuario1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taNomeUsuario1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_taNomeUsuario1MousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try{
            Properties properties = new Properties();
            
            String arquivoASerCriado = System.getProperty("user.dir") + "\\" + nomeArquivoPropertiesASerCriado + ".properties";  
            FileInputStream in = new FileInputStream( arquivoASerCriado );
            properties.loadFromXML(in);
            in.close();       
            
            String driver          = properties.getProperty("driver"); 
            String url             = properties.getProperty("url");
            
            taNomeUsuario.setText(driver);
            taNomeUsuario1.setText(url);
                    
        } catch (Exception e) {}
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void taSeparadorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taSeparadorFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_taSeparadorFocusLost

    private void taSeparadorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taSeparadorMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_taSeparadorMouseEntered

    private void taSeparadorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taSeparadorMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_taSeparadorMouseExited

    private void taSeparadorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taSeparadorMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_taSeparadorMousePressed

    private void JPOpcao_38MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_38MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_38MouseEntered

    private void JPOpcao_38MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_38MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_38MouseExited

    private void JPOpcao_38MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPOpcao_38MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPOpcao_38MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPOpcao_10;
    private javax.swing.JPanel JPOpcao_11;
    private javax.swing.JPanel JPOpcao_12;
    private javax.swing.JPanel JPOpcao_13;
    private javax.swing.JPanel JPOpcao_14;
    private javax.swing.JPanel JPOpcao_15;
    private javax.swing.JPanel JPOpcao_16;
    private javax.swing.JPanel JPOpcao_17;
    private javax.swing.JPanel JPOpcao_18;
    private javax.swing.JPanel JPOpcao_19;
    private javax.swing.JPanel JPOpcao_20;
    private javax.swing.JPanel JPOpcao_21;
    private javax.swing.JPanel JPOpcao_22;
    private javax.swing.JPanel JPOpcao_23;
    private javax.swing.JPanel JPOpcao_24;
    private javax.swing.JPanel JPOpcao_25;
    private javax.swing.JPanel JPOpcao_26;
    private javax.swing.JPanel JPOpcao_27;
    private javax.swing.JPanel JPOpcao_28;
    private javax.swing.JPanel JPOpcao_29;
    private javax.swing.JPanel JPOpcao_30;
    private javax.swing.JPanel JPOpcao_31;
    private javax.swing.JPanel JPOpcao_32;
    private javax.swing.JPanel JPOpcao_33;
    private javax.swing.JPanel JPOpcao_34;
    private javax.swing.JPanel JPOpcao_35;
    private javax.swing.JPanel JPOpcao_36;
    private javax.swing.JPanel JPOpcao_38;
    private javax.swing.JPanel JPOpcao_7;
    private javax.swing.JPanel JPOpcao_8;
    private javax.swing.JPanel JPOpcao_9;
    private javax.swing.JPanel Properties_Criado;
    private javax.swing.JPanel Properties_Criado1;
    public javax.swing.JComboBox cbTipoBanco;
    public javax.swing.JComboBox cbTipoConexao;
    public javax.swing.JComboBox cbTipoDriver;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    public javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea10;
    private javax.swing.JTextArea jTextArea11;
    private javax.swing.JTextArea jTextArea12;
    private javax.swing.JTextArea jTextArea13;
    private javax.swing.JTextArea jTextArea14;
    private javax.swing.JTextArea jTextArea15;
    private javax.swing.JTextArea jTextArea16;
    private javax.swing.JTextArea jTextArea17;
    private javax.swing.JTextArea jTextArea18;
    private javax.swing.JTextArea jTextArea19;
    private javax.swing.JTextArea jTextArea20;
    private javax.swing.JTextArea jTextArea21;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JPanel jpIpServidor;
    private javax.swing.JPanel jpPortaServidor;
    private javax.swing.JPanel jpStatus;
    private javax.swing.JPanel jp_Endereco_Banco;
    private javax.swing.JRadioButton rbHome;
    private javax.swing.JRadioButton rbUser;
    private javax.swing.JTextArea taIpServidor;
    private javax.swing.JTextArea taNomeBanco;
    private javax.swing.JTextArea taNomeUsuario;
    private javax.swing.JTextArea taNomeUsuario1;
    private javax.swing.JTextArea taNomeUsuarioX;
    private javax.swing.JTextArea taNomeUsuarioX1;
    private javax.swing.JTextArea taPortaServidor;
    private javax.swing.JPasswordField taSenhaX;
    private javax.swing.JTextArea taSeparador;
    private javax.swing.JTextArea taStatus;
    private javax.swing.JTextArea ta_Endereco_Banco;
    private javax.swing.JTextArea ta_URL;
    // End of variables declaration//GEN-END:variables
        
}
