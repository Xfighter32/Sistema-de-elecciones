/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controlador.Controlador;
import Modelo.ModeloVotante;
import Vista.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/*
 * @author mikom
 */
public class Votante {
    
    
    
    
    public static void main(String[] args) throws IOException {
        
        ModeloVotante modelo = new ModeloVotante();
        GUIEntrada guiEntrada = new GUIEntrada();
        GUISalida guiSalida = new GUISalida();
        GUIVotacion guiVotacion = new GUIVotacion();
        Controlador control = new Controlador( modelo, guiEntrada, guiSalida,  guiVotacion);  
        guiEntrada.setVisible(true);
        modelo.conectar();
        
    }
}