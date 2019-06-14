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
        Generador_de_pruebas generador = new Generador_de_pruebas(30);
        ListaDeCandidatos = generador.getListaDeCandidatos();
        System.out.println("Se genero la prueba");
        // Simulamos una votacion
        Votacion votacion = new Votacion(ListaDeCandidatos,"Votos de prueba.csv");
        System.out.println("Se simulo la votacion");
        // Se guardan los resultados de la votacion en BancoDeVotos
        BancoDeVotos = votacion.getVotos();
        CopiaDeVotos = new ArrayList<Voto>();
        CopiaDeVotos.addAll(BancoDeVotos);

        // Conociendo el numero total de votos y la cantidad de candidatos se calcula la cuota para ser electo.
        Cuota = calculoDeCuota(BancoDeVotos);
        System.out.println("Se calculo la cuota");
        // Ahora inicia el conteo
        System.out.println("Se inicio el ciclo");
        generarPrimerCorteDeVotos();
        fin = verificarFinVotacion();
        while (!fin){
            verificarGanador();
            if (huboGanador){
                transferirVotos(VotosPorTransferir);
                if (verificarFinVotacion()){
                    System.out.println("Han sido electos los "+Asientos+" asientos.");
                    for (Pila_Votos pila : VotosPorCandidato){
                        if (pila.isGanador()){
                            System.out.println(pila.getCandidato().getNombre());
                        }
                    }
                    fin = verificarFinVotacion();
                    break;
                }
            }
            else{
                eliminarCandidato();
                transferirVotos(VotosPorTransferir);
                if (verificarFinVotacion()){
                    System.out.println("Han sido electos los "+Asientos+" asientos.");
                    for (Pila_Votos pila : VotosPorCandidato){
                        if (pila.isGanador()){
                            System.out.println(pila.getCandidato().getNombre());
                        }
                    }
                    fin = verificarFinVotacion();
                    break;
                }
            }
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
        for (Pila_Votos pila : VotosPorCandidato){
            System.out.println("Votos de "+pila.getCandidato().getNombre()+" "+pila.getNumeroVotos());
            // Se verifica solo para las pilas de datos que aun no hayan ganado
            if (!pila.isGanador()){
                if (pila.getNumeroVotos() >= mayor && pila.getNumeroVotos()>=Cuota){
                    mayor = pila.getNumeroVotos();
                }
            }
        }
        // Luego se sacan los candidatos que cumplen esa mayoria
        for (Pila_Votos pila : VotosPorCandidato){
            if (pila.isGanador()){
                if (pila.getNumeroVotos() == mayor){
                    System.out.println(pila.getCandidato().getNombre()+" tiene mayoria de votos.");
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
                return ganadores.get(0);
            }
            catch (IndexOutOfBoundsException e){
                System.out.println("Ningun candidato alcanzo la cuota, se tiene que eliminar a uno");
                return null;
            }
        }
    }

    // Hay que ver si alguien alcanzo la cuota y sacar sus votos a transferir
    public void verificarGanador(){
        huboGanador = false;
        System.out.println("VERIFICANDO GANADOR con un cuota de "+Cuota);
        Candidato ganador = determinarCandidatoConMayoria();
        if (ganador != null){
            System.out.println("Se eligio a "+ganador.getNombre());
            for (Pila_Votos banco_candidato : VotosPorCandidato){
                if (banco_candidato.getCandidato().getCedula().equals(ganador.getCedula())){
                    int VotosDelCandidato = banco_candidato.getNumeroVotos();
                    if (VotosDelCandidato >= Cuota && !banco_candidato.isGanador()){
                        // Cuando un candidato resulta ganador se deben hacer 2 cosas:
                        banco_candidato.setGanador(true);
                        huboGanador = true;
                        System.out.println("El candidato "+banco_candidato.getCandidato().getNombre()+" ha sido electo!");

                        // 1) Eliminarlo de los candidatos aun disponibles (se hace por cedula)
                        for (int i = 0; i < ListaDeCandidatos.size(); i++){
                            if (ListaDeCandidatos.get(i).getCedula().equals(banco_candidato.getCandidato().getCedula())){
                                ListaDeCandidatos.remove(ListaDeCandidatos.get(i));
                            }
                        }
                        System.out.println("Candidatos aun compitiendo: ");
                        for (Candidato candidato : ListaDeCandidatos){
                            System.out.println(candidato.getNombre());
                        }
                        // 2) Si los votos del candidato exceden la cuota se transfiere el excedente
                        if (VotosDelCandidato > Cuota){
                            float VotosExcedentes = VotosDelCandidato-Cuota;
                            VotosPorTransferir = banco_candidato.getVotosExcedentes(VotosExcedentes, ListaDeCandidatos);
                            System.out.println("Los votos a transferir son "+VotosPorTransferir.size());
                        }
                    }
                }
            }
        }

    }

    private String[] eliminarNombreCandidato(String pCandidatoPorEliminar){
        String[] nuevaListaCandidatos = new String[Candidatos.length-1];
        int cont = 0;
        for (int i = 0; i <= Candidatos.length-1; i++){
            if (!Candidatos[i].equals(pCandidatoPorEliminar)){
                nuevaListaCandidatos[cont] = Candidatos[i];
                cont++;
            }
        }
        return nuevaListaCandidatos;
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
                        System.out.println("Se agregó un voto nuevo a "+VotosPorCandidato.get(i).getCandidato().getNombre());
                        System.out.println("Votos de "+VotosPorCandidato.get(i).getCandidato().getNombre()+ " son "+
                        VotosPorCandidato.get(i).getNumeroVotos());
                        break;
                    }
                    i++;
                }
            }
        }
        catch (NullPointerException e){
            System.out.println("No hay votos que transferir");

        }
    }

    public Candidato determinarPerdedor (){
        // Se identifica el candidato con menos votos.
        int menor = BancoDeVotos.size(); // menor inicialmente va a ser el tamaño de todos los votos
        ArrayList<Candidato> perdedores = new ArrayList<Candidato>();
        Candidato perdedor = null;
        System.out.println("Buscando al mas perdedor ...");
        for (int i = 0; i < VotosPorCandidato.size(); i++) {
            System.out.println("Candidato "+(i+1)+" "+VotosPorCandidato.get(i).getCandidato().getNombre()+
            " tiene "+ VotosPorCandidato.get(i).getNumeroVotos()+ " votos.");
            if (VotosPorCandidato.get(i).getNumeroVotos() == menor){
                perdedores.add(VotosPorCandidato.get(i).getCandidato());
                System.out.println("El menor es "+VotosPorCandidato.get(i).getCandidato().getNombre());
            }
            else if (VotosPorCandidato.get(i).getNumeroVotos() < menor) {
                perdedores.add(VotosPorCandidato.get(i).getCandidato());
                menor = VotosPorCandidato.get(i).getNumeroVotos();
                System.out.println("El menor es "+VotosPorCandidato.get(i).getCandidato().getNombre());
            }
        }
        // Se selecciona un perdedor aleatorio si hay empates
        // Si hay un empate en mayoria de votos, selecciona uno por edad.
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
        return perdedor;
    }

    private void eliminarCandidato() {
        Candidato perdedor = determinarPerdedor();
        System.out.println("El perdedor es: "+perdedor.getNombre());
        ArrayList<Pila_Votos> NuevaListaVotosPorCandidatos = new ArrayList<Pila_Votos>();
        for (int i = 0; i < ListaDeCandidatos.size(); i++){
            if (ListaDeCandidatos.get(i).getCedula().equals(perdedor.getCedula())){
                ListaDeCandidatos.remove(ListaDeCandidatos.get(i));
            }
        }
        for (Pila_Votos pila : VotosPorCandidato){
            if (pila.getCandidato().getCedula().equals(perdedor.getCedula())){
                VotosPorTransferir = pila.getVotosExcedentes(pila.getNumeroVotos(),ListaDeCandidatos);
                System.out.println("Se van a tranferir los votos de "+perdedor.getNombre());
            }
        }
        VotosPorCandidato = NuevaListaVotosPorCandidatos;

        System.out.println("El candidato " + perdedor.getNombre() + " tiene la menor cantidad de votos, por lo tanto se elimino");

    }

    public boolean verificarFinVotacion(){
        int cont = 0;
        for (Pila_Votos pila : VotosPorCandidato){
            if (pila.isGanador()){
                cont ++;
            }
        }
        return cont == Asientos;
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
