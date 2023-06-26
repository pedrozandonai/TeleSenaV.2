import java.sql.SQLOutput;
import java.util.*;

public class ControleTeleSena {
    private Pessoa[] pessoas = new Pessoa[20];
    TeleSena teleSena = new TeleSena();
    private int telesVendidas;
    private Pessoa[] ganhadores = new Pessoa[20];
    private Pessoa ganhador;
    private int[] cartelaSorteada = new int[25];
    private int[] cartelaGanhadora = new int[25];

    // Metodo que executa todos comandos para a inicialização do programa
    public void executaTudo(){

        teleSena.setArrayUm(sorteiaNumeros(new int[25]));
        teleSena.setArrayDois(sorteiaNumeros(new int[25]));

        criaPessoas();
        vendeTeles();

        while (!verificaGanhador()){
            criaGanhador();
        }

        distribuiPremio();

        imprimeInformações();
    }

//    // Cria os numeros a serem inseridos dentro das Tele Senas de forma aleatória e sem repetição
    public int[] sorteiaNumeros(int[] cartela){
        boolean numerosRepetidos = true;

        while (numerosRepetidos){
            for (int i = 0; i < cartela.length; i++){
                if (cartela[i] == 0){
                    cartela[i] = (int) (Math.random()*60)+1;
                    Arrays.sort(cartela);
                }
            }

            numerosRepetidos = false;

            Arrays.sort(cartela);

            for (int k = 1; k < cartela.length; k++){
                if (cartela[k] == cartela[k-1] || cartela[k] == 0){
                    cartela[k] = (int) (Math.random()*60)+1;
                    numerosRepetidos = true;
                }
            }
        }

        return cartela;
    }

    // Nova função para verificar um ganhador
    public boolean verificaGanhador(){
        int contadorGanhadores = 0;

        for (Pessoa pessoa : pessoas) {
            for (int j = 0; j < pessoa.getTeleSenas().length; j++) {
                int[] cartelas = pessoa.getTeleSenas()[j].getNumerosDaPessoa();
                if (verificaCartelas(teleSena.getArrayUm(), cartelas) || verificaCartelas(teleSena.getArrayDois(), cartelas)) {
                    for (int k = 0; k < ganhadores.length; k++) {
                        if (ganhadores[k] == null && !jaEhGanhador(pessoa.getNome())) {
                            ganhadores[k] = new Pessoa(pessoa.getNome());
                            setCartelaGanhadora(cartelas);
                            setCartelaSorteada(cartelas);
                            contadorGanhadores++;
                        }
                    }
                }
            }
        }

        return contadorGanhadores > 0;
    }

    private boolean jaEhGanhador(String nome) {
        for (Pessoa p : ganhadores) {
            if (p != null && p.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    // Cria os nomes das pessoas
    public String geraNomes(){
        String[] masculino = {"Lucas", "Gabriel", "Rafael", "Daniel", "David", "Mateus", "Leonardo", "Rodrigo", "Miguel", "Carlos", "Antonio", "Bruno", "Joaquim", "Felipe", "Ricardo"};
        String[] feminino = {"Maria", "Ana", "Julia", "Beatriz", "Carla", "Isabela", "Leticia", "Amanda", "Fernanda", "Alice", "Luiza", "Manuela", "Lara", "Valentina", "Gabriela"};
        String[] sobrenomes = {"Silva", "Santos", "Oliveira", "Pereira", "Lima", "Ferreira", "Costa", "Rodrigues", "Martins", "Gomes", "Melo", "Almeida", "Ribeiro", "Barbosa", "Pinto", "Moura", "Cavalcanti", "Dias", "Castro", "Campos", "Cardoso", "Mendes", "Cruz", "Miranda", "Rocha", "Nogueira", "Araujo", "Souza", "Carvalho", "Monteiro"};
        String nomeCompleto = "";


        Random rand = new Random();

        for (int i = 0; i < 20; i++) {
            String nome;
            // alternar entre nomes masculinos e femininos
            if (i % 2 == 0) {
                nome = masculino[rand.nextInt(masculino.length)];
            } else {
                nome = feminino[rand.nextInt(feminino.length)];
            }
            String sobrenome = sobrenomes[rand.nextInt(sobrenomes.length)];
            nomeCompleto = nome + " " + sobrenome;
        }

        return nomeCompleto;
    }

    // Inicializa todas pessoas dentro do array pessoas
    public void criaPessoas(){
        Pessoa[] ps = new Pessoa[20];
        for (int i = 0; i < ps.length; i++){
            ps[i] = new Pessoa(geraNomes());
        }

        setPessoas(ps);
    }

    // Vende x numeros de Tele Senas para todas pessoas no array e atribui os numeros de cada tele
    public void vendeTeles(){
        int telesVendidas = 0;
        for (int i = 0; i < pessoas.length; i++){
            int qTelePessoa = (int) (Math.random()*15)+1;
            TeleSena[] teleSenas = new TeleSena[qTelePessoa];

            for (int k = 0; k < qTelePessoa;k++){
                TeleSena novaTeleSena = new TeleSena();
                novaTeleSena.setNumerosDaPessoa(sorteiaNumeros(new int[25]));
                teleSenas[k] = novaTeleSena;
            }
            getPessoas()[i].setTeleSenas(teleSenas);
            telesVendidas += qTelePessoa;
        }
        setTelesVendidas(telesVendidas);
    }

    // Verifica a função que procura algum ganhador e acrescenta um numero caso não haja algum
    public void criaGanhador(){
            int comprimentoArrayUm = teleSena.getArrayUm().length + 1;
            int comprimentoArrayDois = teleSena.getArrayDois().length + 1;

            int[] novoArrayUm = new int[comprimentoArrayUm];
            int[] novoArrayDois = new int[comprimentoArrayDois];

            System.arraycopy(teleSena.getArrayUm(), 0, novoArrayUm, 0, teleSena.getArrayUm().length);
            System.arraycopy(teleSena.getArrayDois(), 0, novoArrayDois, 0, teleSena.getArrayDois().length);

            novoArrayUm[comprimentoArrayUm - 1] = 0;
            novoArrayDois[comprimentoArrayDois - 1] = 0;

            novoArrayUm = sorteiaNumeros(novoArrayUm);
            novoArrayDois = sorteiaNumeros(novoArrayDois);

            teleSena.setArrayUm(novoArrayUm);
            teleSena.setArrayDois(novoArrayDois);
    }

    // Método que utiliza o Arrays.binarysearch para buscar em todo array da pessoa numeros que estejam no array tele sena
    public boolean verificaCartelas(int[] cartelaSorteada, int[] cartelaPessoa){

        for (int num : cartelaPessoa){
            if (Arrays.binarySearch(cartelaSorteada, num) < 0){
                return false;
            }else {
                setCartelaSorteada(cartelaPessoa);
                setCartelaGanhadora(cartelaPessoa);
            }
        }
        return true;
    }

    // Método que faz a distribuição do prêmio em dinheiro
    public void distribuiPremio(){
        int count = 0;
        for (int i = 0; i < ganhadores.length; i++){
            if (ganhadores[i] != null){
                count++;
            }
        }

        double premioTotal = ((getTelesVendidas() * 10.00)*0.8)/count;

        for (int i = 0; i < ganhadores.length; i++){
            if (ganhadores[i] != null){
                ganhadores[i].setValorPremiacao(premioTotal);
            }
        }
    }

    // Método que anuncia os ganhadores do sorteio
    public String[] nomeDosGanhadores(){
        String[] nomesGanhadores = new String[ganhadores.length];
        for (int i = 0; i < ganhadores.length; i++){
            if (getGanhadores().length > 1 && getGanhadores()[i] != null){
                System.out.println("GANHADOR " + (i+1) + " : " + getGanhadores()[i].getNome());
                System.out.println("PRÊMIO TOTAL : "+ getGanhadores()[i].getValorPremiacao());
                int x = 0;
                while (x < 1){
                    System.out.println("CARTELA DO GANHADOR: "+ Arrays.toString(getCartelaSorteada()));
                    x++;
                }
            } else if (getGanhadores().length <= 1 && getGanhadores()[i] != null) {
                System.out.println("GANHADOR: "+  getGanhadores()[i].getNome());
                System.out.println("PRÊMIO TOTAL : "+ getGanhadores()[i].getValorPremiacao());
                int x = 0;
                while (x < ganhadores.length){
                    System.out.println("CARTELA DO GANHADOR: "+ Arrays.toString(getCartelaSorteada()));
                    x++;
                }
            }

        }

        for (int i = 0; i < getGanhadores().length; i++){
            if (getGanhadores()[i] != null){
                nomesGanhadores[i] = getGanhadores()[i].getNome();
            }
        }
        System.out.println("CARTELA SORTEADA: "+ Arrays.toString(getCartelaGanhadora()));
        return nomesGanhadores;
    }

    // Método que imprime todas informações conforme ordenado
    public void imprimeInformações(){

        //Números Sorteados na Tele Sena
        System.out.println("Números sorteados na Tele Sena: " + Arrays.toString(getCartelaSorteada()));

        //Quantidade de Tele Senas Vendidas
        System.out.println("\nQuantidade de Teles vendidas: " + getTelesVendidas());

        //Quantidade de ganhadores
        int x = 0;
        for (int i = 0; i < getGanhadores().length; i++){
            if (getGanhadores()[i] != null){
                x++;
            }
        }
        System.out.println("\nQuantidade de Ganhadores: " + x);

        //Nome dos ganhadores
        System.out.println();
        nomeDosGanhadores();

        // Valor total das Tele Senas vendidas
        System.out.println("\nValor total das Tele Senas vendidas: "+ "R$ "+getTelesVendidas()*10);

        // Lucro arrecadado pelo Silvio Santos
        System.out.println("\nLucro obtido pelo Sílvio Santos com a Tele Sena: " +  "R$ "+ ((getTelesVendidas() * 10.00)*0.2));
    }


    public ControleTeleSena(Pessoa[] pessoas) {
        this.pessoas = pessoas;
    }

    public ControleTeleSena() {
    }

    public Pessoa[] getPessoas() {
        return pessoas;
    }

    public void setPessoas(Pessoa[] pessoas) {
        this.pessoas = pessoas;
    }

    public TeleSena getTeleSena() {
        return teleSena;
    }

    public void setTeleSena(TeleSena teleSena) {
        this.teleSena = teleSena;
    }

    public int getTelesVendidas() {
        return telesVendidas;
    }

    public void setTelesVendidas(int telesVendidas) {
        this.telesVendidas = telesVendidas;
    }

    public Pessoa[] getGanhadores() {
        return ganhadores;
    }

    public void setGanhadores(Pessoa[] ganhadores) {
        this.ganhadores = ganhadores;
    }

    public Pessoa getGanhador() {
        return ganhador;
    }

    public void setGanhador(Pessoa ganhador) {
        this.ganhador = ganhador;
    }

    public int[] getCartelaSorteada() {
        return cartelaSorteada;
    }

    public void setCartelaSorteada(int[] cartelaSorteada) {
        this.cartelaSorteada = cartelaSorteada;
    }

    public int[] getCartelaGanhadora() {
        return cartelaGanhadora;
    }

    public void setCartelaGanhadora(int[] cartelaGanhadora) {
        this.cartelaGanhadora = cartelaGanhadora;
    }
}
