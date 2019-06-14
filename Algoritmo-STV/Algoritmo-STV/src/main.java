import java.util.ArrayList;

public class main {

    ArrayList<Candidato> candidatos;
    public static void main (String[] args){
        for (int i =0 ; i < 1000; i++){
            System.out.println("VOTACION = "+(i+1));
            Contador conteo = new Contador(3);
            conteo.ciclo();
        }

    }
}
