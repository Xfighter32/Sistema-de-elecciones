import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import com.opencsv.*;


public class Votacion {
    private static final String direccion = "Votos de prueba.csv";
    private ArrayList<Voto> listaVotos;
    private String[] NombresDeCandidatos;


    public void simularVotacion (String pDireccionDeArchivo){
        try {
            FileReader lectorDeArchivo = new FileReader(direccion);
            CSVReader lectorCSV = new CSVReader(lectorDeArchivo);

            // Sacar los nombres

            NombresDeCandidatos = lectorCSV.readNext(); // Lee una fila

            String[] eleccion;
            listaVotos = new ArrayList<Voto>();
            while ((eleccion = lectorCSV.readNext()) != null){
                int cont = 0;
                Voto voto = new Voto();
                for (int i=0; i < NombresDeCandidatos.length; i++) {
                    Candidato candidato = new Candidato(NombresDeCandidatos[cont],eleccion[i]);
                    voto.agregarCandidato(candidato);
                    cont ++;
                }
                listaVotos.add(voto);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public String[] getCandidatos(){
        return NombresDeCandidatos;
    }

    public ArrayList<Voto> getVotos(){
        return listaVotos;
    }

}

