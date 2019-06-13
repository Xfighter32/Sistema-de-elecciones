import java.util.ArrayList;

public class Candidato {

    // Atributos

    private String Nombre;
    private String Cedula;
    private String Partido;
    private int Edad;
    private String Preferencia;

    // Constructor

    public Candidato  (String pNombre, String pCedula, String pPartido, int pEdad) {
        Nombre = pNombre;
        Cedula = pCedula;
        Partido = pPartido;
        Edad = pEdad;
    }

    // Metodos

    public String getNombre() {
        return Nombre;
    }

    public String getCedula() {
        return Cedula;
    }

    public String getPreferencia() {
        return Preferencia;
    }

    public void setPreferencia(String preferencia) {
        Preferencia = preferencia;
    }

    public String getPartido() {
        return Partido;
    }

    public int getEdad() {
        return Edad;
    }

    public void imprimirCandidato(){
        System.out.println("Candidato: "+ this.Nombre + " Preferencia: " + this.Preferencia);
    }

}

