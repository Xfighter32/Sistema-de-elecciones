package Controlador;

import Modelo.ModeloVotante;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controlador {
    ModeloVotante modelo;
    GUIEntrada guiEntrada;
    GUISalida guiSalida;
    GUIVotacion guiVotacion;


    public Controlador(ModeloVotante modelo, GUIEntrada guiEntrada, GUISalida guiSalida, GUIVotacion guiVotacion) {
        
        this.guiEntrada = guiEntrada;
        this.guiEntrada.botonIngresar.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                botonIngresarPresionado(evt);
                guiEntrada.setVisible(false);
                GUIVotacion votacion = new GUIVotacion();
                votacion.setVisible(true);
                guiEntrada.dispose();
            } catch (IOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.guiSalida = guiSalida;
        this.guiVotacion = guiVotacion;
        this.guiVotacion.botonVotar.addActionListener((ActionEvent e) -> {
            votar();
        });
        
        this.modelo = modelo;
    }

    public Controlador(GUIEntrada aThis) {
        guiEntrada = aThis;
    }
    
    //Este Metodo elimina la pantalla de 
    public void votar(){
        
    }
    public void botonIngresarPresionado(java.awt.event.ActionEvent evt) throws IOException {
        guiEntrada.setVisible(false);        
        GUIVotacion votacion = new GUIVotacion();
        votacion.setVisible(true);
        guiEntrada.dispose();
    }
    
}
        

