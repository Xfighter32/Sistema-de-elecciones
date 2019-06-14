import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.opencsv.*;

import java.util.Scanner;

public class Generador_de_pruebas {
    // Atributos
    String[] listaDeNumeros;
    private int CantidadCandidatos;
    private int NumeroVotos;
    private ArrayList<Candidato> ListaDeCandidatos;

    // Constructor

    public Generador_de_pruebas(int pNumeroVotos){
        this.NumeroVotos = pNumeroVotos;
        crearTabla();
    }

    // Metodos

    public int tirarAleatorio(){
        Random random = new Random();
        return random.nextInt(CantidadCandidatos)+1;
    }

    public boolean noEstaEnLaLista(String pNum, String[] listaDeNumeros){
        for (String elemento : listaDeNumeros){
            if (pNum.equals(elemento)){
                return false;
            }
        }
        return true;
    }

    public void crearTabla(){
        int cont1 = 0;
        int cont2 = 0;
        File candidatosPrueba = new File("Candidatos de prueba.csv");
        File votosPrueba = new File("Votos de prueba.csv");
        ListaDeCandidatos = new ArrayList<Candidato>();
        try{
            // Objetos para leer el archivo de pruebas CSV
            FileReader lectorDeArchivos = new FileReader(candidatosPrueba);
            CSVReader lectorCSV = new CSVReader(lectorDeArchivos);

            // Objetos para escribir en el archivo de votos CSV
            FileWriter escritorDeArchivos = new FileWriter(votosPrueba);
            CSVWriter escritorCSV = new CSVWriter(escritorDeArchivos);

            // Se obtienen los datos de los candidatos
            String[] nombres = lectorCSV.readNext();
            escritorCSV.writeNext(nombres);
            String[] cedulas = lectorCSV.readNext();
            // La cabecera de los votos van a ser las cedulas.
            escritorCSV.writeNext(cedulas);
            String[] partidos = lectorCSV.readNext();
            escritorCSV.writeNext(partidos);
            String[] edades = lectorCSV.readNext();
            escritorCSV.writeNext(edades);

            // Se crean los candidatos
            CantidadCandidatos = nombres.length;
            listaDeNumeros = new String[CantidadCandidatos];

            for (int i = 0; i < CantidadCandidatos; i++){
                Candidato candidato = new Candidato(nombres[i],cedulas[i],partidos[i],Integer.parseInt(edades[i]));
                ListaDeCandidatos.add(candidato);
            }
            while (cont1 < NumeroVotos){
                while (cont2 < CantidadCandidatos){
                    Integer num = tirarAleatorio();
                    String numero = num.toString();
                    if(noEstaEnLaLista(numero,listaDeNumeros)){
                        listaDeNumeros[cont2] = numero;
                        cont2++;
                    }
                }
                escritorCSV.writeNext(listaDeNumeros);
                // Se resetea la lista de numeros
                for (int i=0; i<CantidadCandidatos; i++){
                    listaDeNumeros[i] = "0";
                }
                cont1++;
                cont2 = 0;
            }
            lectorCSV.close();
            escritorCSV.close();
            System.out.println("FIN");

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public ArrayList<Candidato> getListaDeCandidatos(){
        return ListaDeCandidatos;
    }
}
