import java.util.ArrayList;

public class Votos {
    private ArrayList<Voto> listaVotos;

    public Votos (ArrayList<Voto> pListaVotos){
        this.listaVotos = pListaVotos;
    }

    public ArrayList<Voto> getListaVotos() {
        return listaVotos;
    }

    public int size(){
        return this.listaVotos.size();
    }

    public Voto get(int cont){
        return this.listaVotos.get(cont);
    }

    public void remove(Voto pVoto){
        this.listaVotos.remove(pVoto);
    }
}
