import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Random;

public class Contador {

    // Atributos

    private ArrayList<Pila_Votos> VotosPorCandidato ;
    private int Asientos;
    private ArrayList<Voto> BancoDeVotos;   // Todos los votos
    private ArrayList<Voto> CopiaDeVotos;   // Copia de todos loas votos
    private ArrayList<Candidato> ListaDeCandidatos;
    private String[] Candidatos;            // Los nombres de los candidatos
    private String[] Cedulas;               // Las cedulas de los candidatos
    private float Cuota;
    private ArrayList<Voto> VotosPorTransferir;
    private boolean huboGanador;
    private boolean ganePorDefault;
    private boolean fin;

    // Constructor

    public Contador (int pAsientos){
        VotosPorCandidato = new ArrayList<Pila_Votos>();
        Asientos = pAsientos;
    }

    // Constructor de copia de votos

    // Metodos

    public void  ciclo() {
        // Generamos la prueba
        Generador_de_pruebas generador = new Generador_de_pruebas(500);
        ListaDeCandidatos = generador.getListaDeCandidatos();
        //System.out.println("Se genero la prueba");
        // Simulamos una votacion
        Votacion votacion = new Votacion(ListaDeCandidatos,"Votos de prueba.csv");
        //System.out.println("Se simulo la votacion");
        // Se guardan los resultados de la votacion en BancoDeVotos
        BancoDeVotos = votacion.getVotos();
        CopiaDeVotos = new ArrayList<Voto>();
        CopiaDeVotos.addAll(BancoDeVotos);

        // Conociendo el numero total de votos y la cantidad de candidatos se calcula la cuota para ser electo.
        Cuota = calculoDeCuota(BancoDeVotos);
        //System.out.println("Se calculo la cuota");
        // Ahora inicia el conteo
        //System.out.println("Se inicio el ciclo");
        generarPrimerCorteDeVotos();
        fin = verificarFinVotacion();
        while (!fin){
            System.out.println("[VERIFICAR GANADOR]");
            verificarGanador();
            if(huboGanador){
                System.out.println("[TRANSFERIR VOTOS]");
                transferirVotos(VotosPorTransferir);
                fin = verificarFinVotacion();
            }
            else {
                if (ganePorDefault) {
                    fin = verificarGanePorDefault();
                }
                else{
                    System.out.println("[ELIMINAR CANDIDATO]");
                    eliminarCandidato();
                    System.out.println("[TRANSFERIR VOTOS]");
                    transferirVotos(VotosPorTransferir);
                }
            }
        }
        // Se muestran los ganadores
        System.out.println("Han sido electos los "+Asientos+" asientos.");
        int cont = 0;
        for (Pila_Votos pila : VotosPorCandidato){
            if (pila.isGanador()){
                cont++;
                System.out.println(pila.getCandidato().getNombre());
            }
        }
        if (cont != Asientos){
            System.out.println("PALMO LA VARA");
        }
    }


    public int calculoDeCuota(ArrayList<Voto> pListaDeVotos){
        // Cuota de Droop
        // Formula = (numVotos / (Asientos+1))+1
        // Conteo de los votos
        int numeroVotos = pListaDeVotos.size();

        return (numeroVotos/(Asientos+1))+1;
    }

    public void generarPrimerCorteDeVotos(){
        int contPreferencia = 1;
        // Por cada candidato
        for (Candidato candidato : ListaDeCandidatos) {
            int cont = 0;
            // Se crea un banco de votos.
            ArrayList<Voto> votos = new ArrayList<Voto>();
            Pila_Votos BancoDeCandidato = new Pila_Votos(candidato, votos);
            // Se buscan los votos que tengan al candidato actual como primera preferencia
            while (cont < BancoDeVotos.size()) {
                Voto voto = BancoDeVotos.get(cont);
                Candidato CandidatoTemporal = voto.getCandidatoPorPreferencia(Integer.toString(contPreferencia));
                if (candidato.getCedula().equals(CandidatoTemporal.getCedula())) {
                    // Se agrega al banco de votos del candidato
                    BancoDeCandidato.agregarVoto(voto);
                    // Se remueve el voto de la pila principal
                    BancoDeVotos.remove(voto);
                    cont--;
                }
                cont++;
            }
            // Se agrega la pila del candidato al array de pilas de los candidatos
            VotosPorCandidato.add(BancoDeCandidato);
            imprimirVotosDeCandidato(BancoDeCandidato);
        }
        BancoDeVotos = CopiaDeVotos;
    }

    public int tirarAleatorio(int pRango){
        Random random = new Random();
        return random.nextInt(pRango);
    }

    public Candidato determinarCandidatoConMayoria(){
        ArrayList<Candidato> ganadores = new ArrayList<Candidato>();
        Candidato ganador = null;
        int mayor = 0;
        // Primero se saca el valor del mayor
        System.out.println("-----------------");
        for (Pila_Votos pila : VotosPorCandidato){
            System.out.println("Votos de "+pila.getCandidato().getNombre()+" "+pila.getNumeroVotos()+" VERIFICAR GANADOR");
            // Se verifica solo para las pilas de datos que aun no hayan ganado
            if (!pila.isGanador()){
                if (pila.getNumeroVotos() >= mayor && pila.getNumeroVotos()>=Cuota){
                    // System.out.println("El candidato "+pila.getCandidato().getNombre()+" tiene mayoria Y SUPERO LA CUOTA");
                    mayor = pila.getNumeroVotos();
                }
            }
        }
        // Luego se sacan los candidatos que cumplen esa mayoria
        for (Pila_Votos pila : VotosPorCandidato){
            if (!pila.isGanador()){
                if (pila.getNumeroVotos() == mayor){
                    // System.out.println(pila.getCandidato().getNombre()+" tiene mayoria de votos.");
                    ganadores.add(pila.getCandidato());
                }
            }
        }
        // Si hay un empate en mayoria de votos, selecciona uno por edad.
        mayor = 0;
        if (ganadores.size() >= 2){
            for (Candidato candidato : ganadores){
                if (candidato.getEdad() > mayor){
                    mayor = candidato.getEdad();
                    ganador = candidato;
                }
            }
            return ganador;
        }
        else{
            try {
                System.out.println("Salio " + ganadores.get(0).getNombre());
                return ganadores.get(0);
            }
            catch (IndexOutOfBoundsException e){
                //System.out.println("Ningun candidato alcanzo la cuota, se tiene que eliminar a uno");
                return null;
            }
        }
    }

    // Hay que ver si alguien alcanzo la cuota y sacar sus votos a transferir
    public void verificarGanador(){
        huboGanador = false;
        if (verificarGanePorDefault()){
            for (Pila_Votos pila : VotosPorCandidato){
                if (ListaDeCandidatos.contains(pila.getCandidato())){
                    pila.setGanador(true);
                }
            }
        }
        else{
            Candidato ganador = determinarCandidatoConMayoria();
            if (ganador != null){
                System.out.println("EL CANDIDATO NO FUE NULL");
                //System.out.println("Se eligio a "+ganador.getNombre());
                for (Pila_Votos banco_candidato : VotosPorCandidato){
                    if (banco_candidato.getCandidato().getCedula().equals(ganador.getCedula())){
                        int VotosDelCandidato = banco_candidato.getNumeroVotos();
                        if (VotosDelCandidato >= Cuota && !banco_candidato.isGanador()){
                            // Cuando un candidato resulta ganador se deben hacer 2 cosas:
                            banco_candidato.setGanador(true);
                            huboGanador = true;
                            //System.out.println("El candidato "+banco_candidato.getCandidato().getNombre()+" ha sido electo!");
                            // 1) Eliminarlo de los candidatos aun disponibles (se hace por cedula)
                            for (int i = 0; i < ListaDeCandidatos.size(); i++){
                                if (ListaDeCandidatos.get(i).getCedula().equals(banco_candidato.getCandidato().getCedula())){
                                    ListaDeCandidatos.remove(ListaDeCandidatos.get(i));
                                }
                            }
                            //System.out.println("Candidatos aun compitiendo: ");
                            for (Candidato candidato : ListaDeCandidatos){
                                //System.out.println(candidato.getNombre());
                            }
                            // 2) Si los votos del candidato exceden la cuota se transfiere el excedente
                            if (VotosDelCandidato > Cuota){
                                float VotosExcedentes = VotosDelCandidato-Cuota;
                                VotosPorTransferir = banco_candidato.getVotosExcedentes(VotosExcedentes, ListaDeCandidatos);
                                //System.out.println("Los votos a transferir son "+VotosPorTransferir.size());
                            }
                        }
                    }
                }
            }
        }
    }

    public void transferirVotos(ArrayList<Voto> pVotosPorTransferir) {
        try {
            for (Voto voto : pVotosPorTransferir){
                int i = 0;
                // Primero se obtiene el candidato del representante del voto
                Candidato candidato = voto.getCandidatoPorPreferencia(Integer.toString(voto.getRepresentante()));
                while (i < VotosPorCandidato.size()){
                    if (candidato.getCedula().equals(VotosPorCandidato.get(i).getCandidato().getCedula())){
                        VotosPorCandidato.get(i).agregarVoto(voto);
                        break;
                    }
                    i++;
                }
            }
        }
        catch (NullPointerException e){
            //System.out.println("No hay votos que transferir");

        }
    }

    public Candidato determinarPerdedor (){
        // Se identifica el candidato con menos votos.
        int menor = BancoDeVotos.size(); // menor inicialmente va a ser el tamaño de todos los votos
        ArrayList<Candidato> perdedores = new ArrayList<Candidato>();
        Candidato perdedor = null;
        System.out.println("Se va a eliminar uno de los siguientes candidatos: ");
        for (Pila_Votos pila : VotosPorCandidato){
            if (!pila.isGanador()){
                System.out.println(pila.getCandidato().getNombre());
            }
        }
        // Primero se saca la menor cantidad de votos
        for (int i = 0; i < VotosPorCandidato.size(); i++) {
            if (VotosPorCandidato.get(i).getNumeroVotos() <= menor){
                menor = VotosPorCandidato.get(i).getNumeroVotos();
            }
        }
        // Luego se sacan todos los que tengan por cantidad de votos ese menor
        for (int i = 0; i < VotosPorCandidato.size(); i++){
            if (VotosPorCandidato.get(i).getNumeroVotos() == menor){
                perdedores.add(VotosPorCandidato.get(i).getCandidato());
            }
        }
        // Si hay un empate en minoria de votos, selecciona uno por edad.
        menor = 150;
        if (perdedores.size() >= 2){
            for (Candidato candidato : perdedores){
                if (candidato.getEdad() < menor){
                    menor = candidato.getEdad();
                    perdedor = candidato;
                }
            }
            return perdedor;
        }
        return perdedores.get(0);
    }

    private void eliminarCandidato() {
        System.out.println("------+---+---+---+--+---+---+------");
        System.out.println("Va a eliminar un candidato");
        Candidato perdedor = determinarPerdedor();
        System.out.println("El perdedor es: "+perdedor.getNombre());
        ArrayList<Pila_Votos> NuevaListaVotosPorCandidatos = new ArrayList<Pila_Votos>();

        // Se elimina el candidato de la lista de candidatos disponibles
        for (int i = 0; i < ListaDeCandidatos.size(); i++){
            if (ListaDeCandidatos.get(i).getCedula().equals(perdedor.getCedula())){
                ListaDeCandidatos.remove(ListaDeCandidatos.get(i));
            }
        }
        // Se copian sus votos a una lista temporal
        for (Pila_Votos pila : VotosPorCandidato){
            if (pila.getCandidato().getCedula().equals(perdedor.getCedula())){
                System.out.println("Se van a transferir los votos de "+perdedor.getNombre());
                VotosPorTransferir = pila.getVotosExcedentes(pila.getNumeroVotos(),ListaDeCandidatos);
            }
        }
        // Una vez que se guardaron los votos por transferir, se elimina la pila
        for (int i = 0; i < VotosPorCandidato.size(); i++){
            if (!VotosPorCandidato.get(i).getCandidato().getCedula().equals(perdedor.getCedula())){
                NuevaListaVotosPorCandidatos.add(VotosPorCandidato.get(i));
            }
        }
        VotosPorCandidato = NuevaListaVotosPorCandidatos;

        System.out.println("El candidato " + perdedor.getNombre() + " tiene la menor cantidad de votos, por lo tanto se elimino");
        System.out.println("------+---+---+---+--+---+---+------");
    }

    public boolean verificarGanePorDefault(){
        int numGanadores = 0;
        // Se saca el numero de personas que ya son ganadoras
        for (Pila_Votos pila : VotosPorCandidato){
            if (pila.isGanador()){
                numGanadores++;
            }
        }
        return numGanadores+ListaDeCandidatos.size() == Asientos;
    }

    public boolean verificarFinVotacion(){
        int cont = 0;
        // Se verifica que el numero de pilas sea mayor al numero de asientos requeridos
        if (VotosPorCandidato.size() > Asientos){
            for (Pila_Votos pila : VotosPorCandidato){
                if (pila.isGanador()){
                    cont ++;
                }
            }
            return cont == Asientos;
        }
        // Si la cantidad de pilas disponible es igual al numero de asientos (es decir ya se descartaron los mas
        // perdedores)
        else if (VotosPorCandidato.size() == Asientos){
            System.out.println("GANO POR DEFAULT");
            return true;
        }
        // Si lo anterior no se cumple no ha terminado la votacion
        else{
            return false;
        }
    }

    public void imprimirVotosDeCandidato (Pila_Votos pBancoDeCandidato){
        int cont = 0;
        for (Voto voto : pBancoDeCandidato.Votos){
            cont ++;
            //voto.imprimirCandidatos();
        }
        System.out.println("Numero de votos para " + pBancoDeCandidato.getCandidato().getNombre() + ": " + cont);
    }

}
