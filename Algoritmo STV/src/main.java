import java.util.ArrayList;

public class main {

    ArrayList<Candidato> candidatos;
    public static void main (String[] args){
        Generador_de_pruebas generador = new Generador_de_pruebas(4,4,10);
        generador.crearTabla();
        Contador conteo = new Contador();
        conteo.contarVotos();
    }
}
