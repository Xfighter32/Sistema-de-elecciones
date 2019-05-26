
package interfazvotante;

/**
 *
 * @author mikom
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;


public class GUIVotacion extends javax.swing.JFrame {
    int minutos,segundos;
    Timer timer;
    boolean tiempoMenor;
    public GUIVotacion() {
        initComponents();
        
        //Iniciamos antes que nada el timer para poder controlar el tiempo del voto.
        labelMinutos.setText("5");
        labelSegundos.setText("00");
        
        minutos = Integer.parseInt(labelMinutos.getText());
        segundos = Integer.parseInt(labelSegundos.getText());
        
        
        //Permite mostrar el tiempo restante
        timer = new Timer(1000, (ActionEvent e) -> {
            if(segundos==0){
                segundos=60;
                minutos--;
            }
            if (minutos==0){
                labelMinutos.setForeground(Color.red);
                labelSegundos.setForeground(Color.red);
                puntitos.setForeground(Color.red);
            }
            if(minutos<0){
                JOptionPane.showMessageDialog(null,"Se ha acabado el tiempo de votación","Tiempo agotado",0);
                botonVotar.setEnabled(false);
                minutos=0;
                segundos=0;
                timer.stop();
            }
            else{
                segundos--;
                if (segundos<10){

                labelSegundos.setText("0"+segundos);
                tiempoMenor=false;
                }
                if(minutos<10){
                    labelMinutos.setText("0"+minutos);
                    if (segundos<10)
                        labelSegundos.setText("0"+segundos);
                    else
                        labelSegundos.setText(""+segundos);
                    tiempoMenor=false;

                }
                if(tiempoMenor){
                labelSegundos.setText(""+segundos);
                labelMinutos.setText(""+minutos);
                }
            }
        });
        timer.start();
        String[] num = {"Candidato PAC","Candidato PLN"};
        int numCandidatosDisponibles= Integer.parseInt(JOptionPane.showInputDialog(null, "Cantidad de Candidatos elegibles: "));
        System.out.println(numCandidatosDisponibles);
        for(int i=0;i<numCandidatosDisponibles;i++){
                        
            //Creación de los ComboBox
            JComboBox comboCandidatos = new JComboBox(num);   
            
            comboCandidatos.setLocation(140,(i+1)*90); //Se agrega a la i un uno para que no inice en 0
            comboCandidatos.setSize(270,30);
            comboCandidatos.setSelectedIndex(0);
            comboCandidatos.setFont(new java.awt.Font("Sarif", 0, 18));
            PanelCandidatos.add(comboCandidatos, i);
            
            //Creación de los Labels
            JLabel labelCandidatos = new JLabel();
            labelCandidatos.setText("Preferencia #"+(i+1));  
            labelCandidatos.setLocation(5,(i+1)*90);  //Se agrega a la i un uno para que no inice en 0
            labelCandidatos.setSize(170, 32);
            
            labelCandidatos.setFont(new java.awt.Font("Sarif", 0, 18));
            PanelCandidatos.add(labelCandidatos,i);
            
            JLabel imagenCandidato = new JLabel();
            imagenCandidato.setIcon(new javax.swing.ImageIcon("C:\\Users\\mikom\\OneDrive\\Documentos\\NetBeansProjects\\InterfazVotante\\src\\interfazvotante\\next (2).png"));
            imagenCandidato.setSize(40,40);
            imagenCandidato.setLocation(450,(i+1)*85);  
            PanelCandidatos.add(imagenCandidato,0);
            System.out.println(i);
        }
        JLabel labelTitulo = new JLabel();
        labelTitulo.setText("Opciones de Votación:");
        labelTitulo.setLocation(12,1);
        labelTitulo.setSize(400,50);
        labelTitulo.setFont(new java.awt.Font("Sarif", 0, 28));
        //Scrollbar(int orientation, int value, int visible, int minimum, int maximum);

        //JScrollBar scrollBar = new JScrollBar();
        //scrollBar.setLocation(100, 300);
        //scrollBar.;
        //PanelCandidatos.add(scrollBar);
        PanelCandidatos.add(labelTitulo, 0);
        PanelCandidatos.setSize(900, 900);
        PanelCandidatos.getVerticalScrollBar().setUnitIncrement(10);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        botonVotar = new javax.swing.JButton();
        botonLimpiar = new javax.swing.JButton();
        labelMinutos = new javax.swing.JLabel();
        puntitos = new javax.swing.JLabel();
        labelSegundos = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelInicial = new javax.swing.JPanel();
        PanelCandidatos = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Serif", 2, 54)); // NOI18N
        jLabel1.setText("Selección de Candidatos");

        botonVotar.setText("Votar");
        botonVotar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVotarActionPerformed(evt);
            }
        });

        botonLimpiar.setText("Limpiar Opciones");

        labelMinutos.setFont(new java.awt.Font("Segoe UI Symbol", 0, 24)); // NOI18N
        labelMinutos.setText("00");

        puntitos.setFont(new java.awt.Font("Segoe UI Symbol", 0, 24)); // NOI18N
        puntitos.setText(":");

        labelSegundos.setFont(new java.awt.Font("Segoe UI Symbol", 0, 24)); // NOI18N
        labelSegundos.setText("00");

        jLabel2.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel2.setText("Tiempo Restante para votar");

        PanelCandidatos.setPreferredSize(new java.awt.Dimension(500, 700));

        javax.swing.GroupLayout PanelInicialLayout = new javax.swing.GroupLayout(PanelInicial);
        PanelInicial.setLayout(PanelInicialLayout);
        PanelInicialLayout.setHorizontalGroup(
            PanelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInicialLayout.createSequentialGroup()
                .addComponent(PanelCandidatos, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );
        PanelInicialLayout.setVerticalGroup(
            PanelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInicialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelCandidatos, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelMinutos)
                .addGap(2, 2, 2)
                .addComponent(puntitos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSegundos)
                .addGap(338, 338, 338))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(botonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(66, 66, 66)
                        .addComponent(botonVotar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(74, 74, 74))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(botonVotar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(botonLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(labelMinutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(puntitos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelSegundos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVotarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVotarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonVotarActionPerformed

    
    
    
    
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
            java.util.logging.Logger.getLogger(GUIVotacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIVotacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIVotacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIVotacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIVotacion().setVisible(true);
                    
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane PanelCandidatos;
    private javax.swing.JPanel PanelInicial;
    private javax.swing.JButton botonLimpiar;
    private javax.swing.JButton botonVotar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelMinutos;
    private javax.swing.JLabel labelSegundos;
    private javax.swing.JLabel puntitos;
    // End of variables declaration//GEN-END:variables
}
