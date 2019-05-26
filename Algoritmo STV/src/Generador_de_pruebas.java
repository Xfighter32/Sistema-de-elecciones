import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import com.opencsv.*;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;

public class Generador_de_pruebas {
    // Atributos
    String[] listaDeNumeros;
    ArrayList<ArrayList<String>> tabla;
    int CantidadCandidatos;
    int NumeroVotos;

    // Constructor

    public Generador_de_pruebas(int pCantidadPersonas, int pRango, int pNumeroVotos){
        this.listaDeNumeros = new String[pRango];
        this.tabla = new ArrayList<>(pCantidadPersonas);
        this.CantidadCandidatos = pCantidadPersonas;
        this.NumeroVotos = pNumeroVotos;
    }

    // Metodos

    public int tirarAleatorio(){
        Random random = new Random();
        return random.nextInt(4)+1;
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
        File prueba = new File("Votos de prueba.csv");
        try{
            // Objetos para escribir en el CSV
            FileWriter escritorDeArchivos = new FileWriter(prueba);
            CSVWriter escritorCSV = new CSVWriter(escritorDeArchivos);

            // Se hace un encabezado al archivo
            String[] encabezado = new String[CantidadCandidatos];
            while (cont1 < CantidadCandidatos){
                Scanner input = new Scanner(System.in);
                System.out.println("Ingrese el nombre del candidato: ");
                String candidato = input.nextLine();
                encabezado[cont1] = candidato;
                cont1+=1;
            }
            escritorCSV.writeNext(encabezado);

            while (cont2 < NumeroVotos){
                cont1 = 0;
                while (cont1 < CantidadCandidatos){
                    Integer num = tirarAleatorio();
                    String numero = num.toString();
                    if(noEstaEnLaLista(numero,listaDeNumeros)){
                        listaDeNumeros[cont1] = numero;
                        cont1 ++;
                    }
                }
                escritorCSV.writeNext(listaDeNumeros);
                cont2++;
                for (int i=0; i<CantidadCandidatos; i++){
                    listaDeNumeros[i] = "0";
                }
            }
            escritorCSV.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
