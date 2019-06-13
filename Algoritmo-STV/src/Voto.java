import java.util.ArrayList;

public class Voto {

    // Atributos

    private ArrayList<Candidato> ListaCandidatos;
    private boolean VotoUnico = false;
    private int representante = 1;

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

    public void determinarVotoUnico() {
        if (ListaCandidatos.size() == 1){
            VotoUnico = true;
        }
    }

    public boolean isVotoUnico() {
        return VotoUnico;
    }

    public int getRepresentante() {
        return representante;
    }

    public void setRepresentante(int representante) {
        this.representante = representante;
    }

    public void imprimirCandidatos(){
        System.out.println("Datos del voto: \n");
        for (Candidato candidato : ListaCandidatos){
            candidato.imprimirCandidato();
        }
    }

    public Candidato getCandidatoPorPreferencia(String pPreferencia){
        for (Candidato candidato : ListaCandidatos){
            if (candidato.getPreferencia().equals(pPreferencia)){
                return candidato;
            }
        }
        return null;
    }





}
