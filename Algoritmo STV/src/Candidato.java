import java.util.ArrayList;

public class Candidato {

    // Atributos

    String Nombre;
    String Cedula;
    String Preferencia;

    // Constructor

    public Candidato  (String pNombre, String pPreferencia) {
        Nombre = pNombre;
        Preferencia = pPreferencia;
    }

    // Metodos

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getPreferencia() {
        return Preferencia;
    }

    public void setPreferencia(String preferencia) {
        Preferencia = preferencia;
    }

    public void imprimirCandidato(){
        System.out.println("Candidato: "+ this.Nombre + " Preferencia: " + this.Preferencia);
    }

}

