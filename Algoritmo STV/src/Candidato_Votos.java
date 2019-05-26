import java.util.ArrayList;

public class Candidato_Votos {

    // Atributos

    private String Nombre;
    private String Cedula;
    public ArrayList<Voto> Votos;

    // Constructor

    public Candidato_Votos (String pNombre, ArrayList<Voto> pVotos){
        Nombre = pNombre;
        Votos = pVotos;
    }
    // Metodos

    public void agregarVoto(Voto pVoto){
        Votos.add(pVoto);
    }

    public void quitarVoto(String pPreferencia) {
        for (Voto voto : Votos) {
            for (int i = 0; i < Votos.size(); i++) {
                voto.getCandidatoPorPreferencia(pPreferencia);
            }
        }
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}