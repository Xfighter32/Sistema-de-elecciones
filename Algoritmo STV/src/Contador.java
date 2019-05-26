import java.util.ArrayList;

public class Contador {

    // Atributos

    private ArrayList<Candidato_Votos> VotosPorCandidato ;
    private ArrayList<Voto> BancoDeVotos;   // Todos los votos
    private String[] Candidatos;            // Los nombres de los candidatos
    private Candidato CandidatoTemporal;    // Para crear temporalmente al candidato

    // Constructor

    public Contador (){
        VotosPorCandidato = new ArrayList<Candidato_Votos>();
    }

    // Metodos

    public float calculoDeCuota(ArrayList<Voto> pListaDeVotos, String[] pCandidatos){

        // Formula = numVotos / (numCandidatos+1)


        // Conteo de los votos
        int numeroVotos = 0;
        for (Voto voto : pListaDeVotos){
            numeroVotos ++;
        }

        // Conteo de los candidatos
        int numCandidatos = 0;
        for (String candidato : pCandidatos){
            numCandidatos ++;
        }
        numCandidatos ++;

        return numeroVotos/numCandidatos;
    }

    public void contarVotos(){
        boolean finVotacion = false;

        // Se corre la simulacion
        Votacion votacion = new Votacion();
        votacion.simularVotacion("Votos de prueba.csv");
        BancoDeVotos = votacion.getVotos();
        Candidatos = votacion.getCandidatos();
        int numVoto = 1;
        Integer contPreferencia = 1;
        ArrayList<Voto> CopiaDeVotos = BancoDeVotos;

        // Se inicia el conteo
        while (!finVotacion){
            for (String candidato : Candidatos){
                int cont = 0;

                // Crea un banco de votos por cada candidato.
                ArrayList<Voto> votos = new ArrayList<Voto>();
                Candidato_Votos BancoDeCandidato = new Candidato_Votos(candidato,votos);

                // Se buscan los votos que tengan al candidato actual por preferencia
                while (cont < BancoDeVotos.size()){
                    Voto voto = BancoDeVotos.get(cont);

                    CandidatoTemporal = voto.getCandidatoPorPreferencia(contPreferencia.toString());
                    System.out.println("Haciendo el proceso para el candidato: " + CandidatoTemporal);
                    // Si tienen el mismo nombre y es la preferencia requerida.
                    if (candidato.equals(CandidatoTemporal.getNombre())){
                        System.out.println(numVoto);
                        voto.setNumeroDeVoto(numVoto);
                        BancoDeCandidato.agregarVoto(voto);
                        BancoDeVotos.remove(voto);
                        System.out.println("Bien");
                        cont --;
                    }
                    cont ++;
                }
                VotosPorCandidato.add(BancoDeCandidato);    // Agrega los votos por candidato
                imprimirVotosDeCandidato(BancoDeCandidato);
            }

            finVotacion = true;
            contPreferencia ++;

        }
    }

    public void imprimirVotosDeCandidato (Candidato_Votos pBancoDeCandidato){
        int cont = 0;
        for (Voto voto : pBancoDeCandidato.Votos){
            cont ++;
            voto.imprimirCandidatos();
        }
        System.out.println("Numero de votos para " + pBancoDeCandidato.getNombre() + ": " + cont);
    }

}
