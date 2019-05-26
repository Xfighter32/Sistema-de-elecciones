import java.util.ArrayList;

public class Voto {

    // Atributos

    private ArrayList<Candidato>   ListaCandidatos;
    private int NumeroDeVoto;

    // Constructor

    public Voto (){
        ListaCandidatos = new ArrayList<Candidato>();
    }

    // Metodos

    public void agregarCandidato(Candidato pCandidato){

        ListaCandidatos.add(pCandidato);
    }

    public void quitarCandidato(Candidato pCandidato){
        for (Candidato candidatoAgregado : ListaCandidatos){
            if (pCandidato == candidatoAgregado){
                ListaCandidatos.remove(candidatoAgregado);
            }
        }
    }

    public int getNumeroDeVoto() {
        return NumeroDeVoto;
    }

    public void setNumeroDeVoto(int numeroDeVoto) {
        NumeroDeVoto = numeroDeVoto;
    }

    public void imprimirCandidatos(){
        System.out.println("Datos del voto: \n");
        for (Candidato candidato : ListaCandidatos){
            candidato.imprimirCandidato();
        }
    }

    public Candidato getCandidatoPorPreferencia(String pPreferencia){
        for (Candidato candidato : ListaCandidatos){
            if (candidato.Preferencia.equals(pPreferencia)){
                return candidato;
            }
        }
        return null;
    }





}
