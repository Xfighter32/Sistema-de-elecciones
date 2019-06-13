package Modelo;
//@author mikom

import Controlador.Controlador;
import Vista.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

        
public class ModeloVotante {
    Controlador controlador;
    GUIEntrada guiEntrada;
    GUISalida guiSalida;
    GUIVotacion guiVotacion;
    Timer tiempo;
    
    public void iniciarVotacion() throws IOException{
        //Iniciamos antes que nada el timer para poder controlar el tiempo del voto.
        guiVotacion.labelMinutos.setText("5");
        guiVotacion.labelSegundos.setText("00");
        
        //Permite mostrar el tiempo restante
        tiempo = new Timer (1000, new ActionListener () 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
                boolean tiempoMenor = false;
                int minutos, segundos;
                minutos = Integer.parseInt(guiVotacion.labelMinutos.getText());
                segundos = Integer.parseInt(guiVotacion.labelSegundos.getText());
                if(segundos==0){
                    segundos=60;
                    minutos--;
                }
                if (minutos==0){
                    guiVotacion.labelMinutos.setForeground(Color.red);
                    guiVotacion.labelSegundos.setForeground(Color.red);
                    guiVotacion.puntitos.setForeground(Color.red);
                }
                if(minutos<0){
                    JOptionPane.showMessageDialog(null,"Se ha acabado el tiempo de votación","Tiempo agotado",0);
                    guiVotacion.botonVotar.setEnabled(false);
                    minutos=0;
                    segundos=0;
                    tiempo.stop();
                }
                else{
                    segundos--;
                    if (segundos<10){
                        guiVotacion.labelSegundos.setText("0"+segundos);
                        tiempoMenor=false;
                    }
                    if(minutos<10){
                        guiVotacion. labelMinutos.setText("0"+minutos);
                        if (segundos<10)
                            guiVotacion.labelSegundos.setText("0"+segundos);
                        else
                            guiVotacion.labelSegundos.setText(""+segundos);
                        tiempoMenor=false;

                    }
                    if(tiempoMenor){
                        guiVotacion.labelSegundos.setText(""+segundos);
                        guiVotacion.labelMinutos.setText(""+minutos);
                    }
                }
            } 
        });
        tiempo.start();
          
        String[] num = {"Candidatos de cada partido","Candidato PAC","Candidato PLN"};  //Esto debe recibirse en una variable y no raw text
        int numCandidatosDisponibles= Integer.parseInt(JOptionPane.showInputDialog(null, "Cantidad de Candidatos elegibles: "));
        int jPanel1Size;
        for(int i=0;i<numCandidatosDisponibles;i++){
            if(i==0){
                JComboBox comboCandidatos = new JComboBox(num);   
                //Aumente
                comboCandidatos.setLocation(140,(i+1)*25); //Se agrega a la i un uno para que no inice en 0
                comboCandidatos.setSize(270,30);
                comboCandidatos.setSelectedIndex(0);
                comboCandidatos.setFont(new java.awt.Font("Segoe UI Light", 0, 18));
                guiVotacion.jPanel1.add(comboCandidatos, i);

                //Creación de los Labels
                JLabel labelCandidatos = new JLabel();
                labelCandidatos.setText("Preferencia #"+(i+1));  
                labelCandidatos.setLocation(5,(i+1)*25);  //Se agrega a la i un uno para que no inice en 0
                labelCandidatos.setSize(170, 32);

                labelCandidatos.setFont(new java.awt.Font("Segoe UI Light", 0, 18));
                guiVotacion.jPanel1.add(labelCandidatos,i);

                JLabel imagenCandidato = new JLabel();
                imagenCandidato.setIcon(new javax.swing.ImageIcon("C:\\Users\\mikom\\OneDrive\\Documentos\\NetBeansProjects\\InterfazVotante\\src\\interfazvotante\\next (2).png"));
                imagenCandidato.setSize(40,40);
                imagenCandidato.setLocation(450,(i+1)*22);  
               guiVotacion.jPanel1.add(imagenCandidato,0);
                System.out.println(i);
                jPanel1Size = comboCandidatos.getY();
            }
            else{
            
                //Creación de los ComboBox
                JComboBox comboCandidatos = new JComboBox(num);   

                comboCandidatos.setLocation(140,(i+1)*44); //Se agrega a la i un uno para que no inice en 0
                comboCandidatos.setSize(270,30);
                comboCandidatos.setSelectedIndex(0);
                comboCandidatos.setFont(new java.awt.Font("Segoe UI Light", 0, 18));
                guiVotacion.jPanel1.add(comboCandidatos, i);

                //Creación de los Labels
                JLabel labelCandidatos = new JLabel();
                labelCandidatos.setText("Preferencia #"+(i+1));  
                labelCandidatos.setLocation(5,(i+1)*44);  //Se agrega a la i un uno para que no inice en 0
                labelCandidatos.setSize(170, 32);

                labelCandidatos.setFont(new java.awt.Font("Segoe UI Light", 0, 18));
                guiVotacion.jPanel1.add(labelCandidatos,i);

                JLabel imagenCandidato = new JLabel();
                imagenCandidato.setIcon(new javax.swing.ImageIcon("C:\\Users\\mikom\\OneDrive\\Documentos\\NetBeansProjects\\InterfazVotante\\src\\interfazvotante\\next (2).png"));
                imagenCandidato.setSize(40,40);
                imagenCandidato.setLocation(450,(i+1)*42);  
                guiVotacion.jPanel1.add(imagenCandidato,0);
                System.out.println(i);
                jPanel1Size = comboCandidatos.getY();
            }
            System.out.println("jPanel1Size: " + jPanel1Size);
            Dimension listSize = new Dimension(guiVotacion.PanelCandidatos.getWidth(), jPanel1Size + 50);
            guiVotacion.PanelCandidatos.setSize(listSize);
            guiVotacion.PanelCandidatos.setMaximumSize(listSize);
            guiVotacion.PanelCandidatos.setPreferredSize(listSize);

            guiVotacion.jPanel1.setSize(listSize);
            guiVotacion.jPanel1.setMaximumSize(listSize);
            guiVotacion.jPanel1.setPreferredSize(listSize);

            guiVotacion.PanelCandidatos.setViewportView(guiVotacion.jPanel1);

            guiVotacion.PanelCandidatos.setSize(900, 900);
            guiVotacion.PanelCandidatos.getVerticalScrollBar().setUnitIncrement(10);
        }
    }  
    
        public static void conectar() throws IOException {
        
        BufferedReader sin;
        PrintStream sout;
        BufferedReader stdin;
        try ( Socket sk = new Socket("127.0.0.1", 5000)) {
            sin = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            sout = new PrintStream(sk.getOutputStream());
            stdin = new BufferedReader(new InputStreamReader(System.in));
            String s;
            while (true) {
                System.out.print("Client : ");
                s = stdin.readLine();
                sout.println(s);
                if (s.equalsIgnoreCase("BYE")) {
                    System.out.println("Connection ended by client");
                    break;
                }
                s = sin.readLine();
                System.out.print("Server : " + s + "\n");
            }
        }
        sin.close();
        sout.close();
        stdin.close();
    }
}

