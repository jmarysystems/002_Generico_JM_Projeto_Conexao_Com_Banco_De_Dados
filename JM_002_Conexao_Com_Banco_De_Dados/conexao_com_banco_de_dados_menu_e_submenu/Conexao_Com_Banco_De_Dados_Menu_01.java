/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao_com_banco_de_dados_menu_e_submenu;

import br.com.jmary.home.Home;
import br.com.jmary.utilidades.JOPM;
import criar_banco_de_dados_menu_e_submenu.Criar_Banco_De_Dados_Submenu_01;
import home_controle_menus_norte.imagens.Imagens_Menu_Norte;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author AnaMariana
 */
public class Conexao_Com_Banco_De_Dados_Menu_01 extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;
    
    Home Home;

    /**
     * Creates new form PnHomeDesigner
     * @param Home2
     */
    public Conexao_Com_Banco_De_Dados_Menu_01( Home Home2 ) {
        initComponents();
        ((BasicInternalFrameUI)Fonema_E_Letra_Frame_Interno.getUI()).setNorthPane(null); //retirar o painel superior 
        
        Home = Home2;
        
        //Habilita troca de seta                        
        inicio();
    }
    
    /////////////////////////////////
    URL       imgURL; 
    ImageIcon icon;
            
    URL       imgURL2;
    ImageIcon icon2;
    //////////////////////////////////
    private void inicio(){
        try{                       
            
            imgURL  = Imagens_Menu_Norte.class.getResource("seta_para_baixo.png");
            icon    = new ImageIcon( imgURL );  
            
            imgURL2  = Imagens_Menu_Norte.class.getResource("seta_para_cima.png");
            icon2   = new ImageIcon( imgURL2 ); 
      
            ///SETAR MENU NO HOME//////////////////////////////////////////////
            //Home.Home_Frame_Interno.setJMenuBar( Barra_Menu ); 
            ///////////////////////////////////////////////////////////////////
            
        } catch( Exception e ){ JOPM JOptionPaneMod = new JOPM( 2, "inicio(), \n"
                + e.getMessage() + "\n", this.getClass().getName() ); }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fonema_E_Letra_Frame_Interno = new javax.swing.JInternalFrame();
        Barra_Menu = new javax.swing.JMenuBar();
        menu_seta = new javax.swing.JMenu();
        menu_ajuda = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menu_maimais = new javax.swing.JMenu();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Fonema_E_Letra_Frame_Interno.setBorder(null);
        Fonema_E_Letra_Frame_Interno.setVisible(true);

        menu_seta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home_controle_menus_norte/imagens/seta_para_cima.png"))); // NOI18N
        menu_seta.setToolTipText("Ocultar Submenu");
        menu_seta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menu_setaMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_setaMousePressed(evt);
            }
        });
        Barra_Menu.add(menu_seta);

        menu_ajuda.setText("Ajuda");
        menu_ajuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menu_ajudaMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_ajudaMousePressed(evt);
            }
        });
        menu_ajuda.add(jSeparator3);

        Barra_Menu.add(menu_ajuda);

        menu_maimais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home_controle_menus_norte/imagens/maismais.gif"))); // NOI18N
        menu_maimais.setText("++");
        menu_maimais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menu_maimaisMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_maimaisMousePressed(evt);
            }
        });
        menu_maimais.add(jSeparator6);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home_controle_menus_norte/imagens/conexao.png"))); // NOI18N
        jMenuItem3.setText("Conexão Banco de Dados");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem3MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem3MousePressed(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menu_maimais.add(jMenuItem3);
        menu_maimais.add(jSeparator4);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home_controle_menus_norte/imagens/sql.png"))); // NOI18N
        jMenuItem2.setText("Criar Banco de Dados");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menu_maimais.add(jMenuItem2);

        Barra_Menu.add(menu_maimais);

        Fonema_E_Letra_Frame_Interno.setJMenuBar(Barra_Menu);

        javax.swing.GroupLayout Fonema_E_Letra_Frame_InternoLayout = new javax.swing.GroupLayout(Fonema_E_Letra_Frame_Interno.getContentPane());
        Fonema_E_Letra_Frame_Interno.getContentPane().setLayout(Fonema_E_Letra_Frame_InternoLayout);
        Fonema_E_Letra_Frame_InternoLayout.setHorizontalGroup(
            Fonema_E_Letra_Frame_InternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 563, Short.MAX_VALUE)
        );
        Fonema_E_Letra_Frame_InternoLayout.setVerticalGroup(
            Fonema_E_Letra_Frame_InternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fonema_E_Letra_Frame_Interno)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Fonema_E_Letra_Frame_Interno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 79, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void menu_setaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_setaMousePressed
        Home.tocarSon.tocar( 51 );
        tabJanelaSubmenu( this.menu_seta );
    }//GEN-LAST:event_menu_setaMousePressed

    private boolean cimabaixo = true; 
    private void tabJanelaSubmenu( JMenu seta_do_menu ){
        try{

            if( cimabaixo == false ){
                cimabaixo = true;
                
                seta_do_menu.setToolTipText( "Ocultar Submenu" );
                seta_do_menu.setIcon( icon2 );
                
                Home.adicionar_SubMenu( Home.Sub_Menu_Atual );               
                
            } else if( cimabaixo == true ){
                cimabaixo = false;
                                
                seta_do_menu.setToolTipText( "Mostrar Submenu" );
                seta_do_menu.setIcon( icon );

                Home.retirar_SubMenu();                                                 
            }
            
        } catch( Exception e ){ JOPM JOptionPaneMod = new JOPM( 2, "tabJanelaSubmenu, \n"
                + e.getMessage() + "\n", this.getClass().getName() ); }
    }
    
    private void menu_ajudaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_ajudaMousePressed
        //Sistema_Home.adicionarSubmenu(new Submenu2( Sistema_Home ) );     
    }//GEN-LAST:event_menu_ajudaMousePressed

    private void menu_maimaisMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_maimaisMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_menu_maimaisMousePressed

    private void menu_maimaisMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_maimaisMouseEntered
        Home.tocarSon.tocar( 51 );
    }//GEN-LAST:event_menu_maimaisMouseEntered

    private void menu_ajudaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_ajudaMouseEntered
        Home.tocarSon.tocar( 51 );
    }//GEN-LAST:event_menu_ajudaMouseEntered

    private void menu_setaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_setaMouseEntered
        Home.tocarSon.tocar( 51 );
    }//GEN-LAST:event_menu_setaMouseEntered

    private void jMenuItem3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3MouseEntered

    private void jMenuItem3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MousePressed
        Home.tocarSon.tocar( 51 );
        try{
            
            Home.adicionar_SubMenu(new Conexao_Com_Banco_De_Dados_Submenu_01( Home ) );
            
        } catch( Exception e ){ e.printStackTrace(); JOPM JOptionPaneMod = new JOPM( 2, "adicionarSubmenu( Component c ), \n"
                + e.getMessage() + "\n", "Classe: " + "Controle_de_Alterar_Menu_Norte" ); }
    }//GEN-LAST:event_jMenuItem3MousePressed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2MouseEntered

    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
        Home.tocarSon.tocar( 51 );
        try{

            Home.adicionar_SubMenu(new Criar_Banco_De_Dados_Submenu_01( Home ) );

        } catch( Exception e ){ e.printStackTrace(); JOPM JOptionPaneMod = new JOPM( 2, "adicionarSubmenu( Component c ), \n"
            + e.getMessage() + "\n", "Classe: " + "Controle_de_Alterar_Menu_Norte" ); }
    }//GEN-LAST:event_jMenuItem2MousePressed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuBar Barra_Menu;
    private javax.swing.JInternalFrame Fonema_E_Letra_Frame_Interno;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JMenu menu_ajuda;
    private javax.swing.JMenu menu_maimais;
    private javax.swing.JMenu menu_seta;
    // End of variables declaration//GEN-END:variables
            
}
