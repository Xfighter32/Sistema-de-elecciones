import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.*;


public class Votacion {
    private String direccion = "Votos de prueba.csv";
    private ArrayList<Voto> votos;     //Todos los votos
    private ArrayList<Candidato> ListaDeCandidatos;
    private String[] nombres;
    private String[] cedulas;
    private String[] partidos;
    private String[] edades;

    public Votacion(ArrayList<Candidato> pListaDeCandidatos, String pDireccionDeArchivo){
        this.ListaDeCandidatos = pListaDeCandidatos;
        this.direccion = pDireccionDeArchivo;
        this.nombres = new String[ListaDeCandidatos.size()];
        this.cedulas = new String[ListaDeCandidatos.size()];
        this.partidos = new String[ListaDeCandidatos.size()];
        this.edades = new String[ListaDeCandidatos.size()];
        simularVotacion();
    }

    public void simularVotacion (){
        try {
            FileReader lectorDeArchivo = new FileReader(direccion);
            CSVReader lectorCSV = new CSVReader(lectorDeArchivo);

            // Sacar los datos
            nombres = lectorCSV.readNext();
            cedulas = lectorCSV.readNext();
            partidos = lectorCSV.readNext();
            edades = lectorCSV.readNext();

            // Leer el archivo de los candidatos
            String[] eleccion;
            votos = new ArrayList<Voto>();
            while ((eleccion = lectorCSV.readNext()) != null){
                Voto voto = new Voto();
                for (int i=0; i < cedulas.length; i++) {
                   Candidato candidato = new Candidato(nombres[i],cedulas[i], partidos[i],Integer.parseInt(edades[i]));
                   candidato.setPreferencia(eleccion[i]);
                   voto.agregarCandidato(candidato);
                }
                voto.determinarVotoUnico();
                votos.add(voto);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public String[] getCandidatos(){
        return nombres;
    }

    public String[] getCedulasDeCandidatos() {
        return cedulas;
    }

    public ArrayList<Voto> getVotos(){
        return votos;
    }

}

