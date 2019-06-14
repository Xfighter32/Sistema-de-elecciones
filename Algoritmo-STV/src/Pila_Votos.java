import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pila_Votos {

    // Atributos

    private Candidato Candidato;
    private boolean Ganador = false;
    public ArrayList<Voto> Votos;

    // Constructor

    public Pila_Votos (Candidato pCandidato, ArrayList<Voto> pVotos){
        Candidato = pCandidato;
        Votos = pVotos;
    }
    // Metodos

    public void agregarVoto(Voto pVoto){
        Votos.add(pVoto);
    }

    public int numeroVotos(){
        return Votos.size();
    }

    public int getNumeroVotos(){
        return Votos.size();
    }

    public Candidato getCandidato() {
        return Candidato;
    }

    public boolean verificarCandidatosDisponibles(ArrayList<Candidato> lista, String elemento){
        boolean bandera = false;
        for (int i = 0; i < lista.size(); i++){
            if (lista.get(i).getCedula().equals(elemento)){
                bandera = true;
            }
        }
        return bandera;
    }
        // 2,
    public ArrayList<Voto> getVotosExcedentes(float pNumVotosExcedentes, ArrayList<Candidato> pCandidatosDisponibles){
        System.out.println("SACANDO "+pNumVotosExcedentes+" votos excedentes de "+Candidato.getNombre()+"!");
        ArrayList<Voto> VotosExcedentes = new ArrayList<Voto>();
        System.out.println("Se creo la lista para votos excedentes");
        int cont = 0;
        int siguiente = 1;
        for (int i = 0; i < Votos.size(); i++){
            if (VotosExcedentes.size() == pNumVotosExcedentes){
                break;
            }
            else if (!Votos.get(i).isVotoUnico()){
                    // Se verifica que el voto no sea unico
                    System.out.println("El voto no es unico");
                    // Obtiene la siguiente opcion
                    Candidato SiguienteOpcion = Votos.get(i).getCandidatoPorPreferencia(Integer.toString(Votos.get(i).getRepresentante()+siguiente));
                    while (SiguienteOpcion != null){
                        // Se verifica que la no se haya completado la lista de votos excedentes y que la segunda opcion no haya sido eliminada
                        if (cont < pNumVotosExcedentes){
                            if (verificarCandidatosDisponibles(pCandidatosDisponibles, SiguienteOpcion.getCedula())){
                                Votos.get(i).setRepresentante(Votos.get(i).getRepresentante()+siguiente);
                                VotosExcedentes.add(Votos.get(i));
                                // Se saca el voto de la lista local de votos
                                Votos.remove(Votos.get(i));
                                System.out.println("El candidato "+SiguienteOpcion.getNombre()+" sigue compitiendo y se agregó a la lista de votos excedentes");
                                cont ++;
                                System.out.println("La lista de votos excedentes tiene "+VotosExcedentes.size()+" votos.");
                                break;
                            }
                            else{
                                siguiente++;
                                SiguienteOpcion = Votos.get(i).getCandidatoPorPreferencia(Integer.toString(Votos.get(i).getRepresentante()+siguiente));
                                System.out.println("El candidato ya no esta compitiendo, pasando a la siguiente opcion ...");
                            }

                        }
                        else{
                            break;
                        }

                    }
                }

            }

        System.out.println("Se completó la lista de votos excedentes, tiene "+VotosExcedentes.size()+" votos.");
        return VotosExcedentes;
    }

    public boolean isGanador() {
        return Ganador;
    }

    public void setGanador(boolean ganador) {
        Ganador = ganador;
    }

    public void quitarVoto(String pPreferencia) {
        for (Voto voto : Votos) {
            for (int i = 0; i < Votos.size(); i++) {
                voto.getCandidatoPorPreferencia(pPreferencia);
            }
        }
    }
}