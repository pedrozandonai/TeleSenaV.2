public class Pessoa {
    private String nome;
    private TeleSena[] teleSenas;
    private double valorPremiacao;

    public Pessoa(String nome, int quantidade, double valorPremiacao) {
        this.nome = nome;
        this.teleSenas = new TeleSena[quantidade];
        this.valorPremiacao = valorPremiacao;
    }

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public Pessoa(TeleSena[] teleSenas) {
        this.teleSenas = teleSenas;
    }

    public Pessoa() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TeleSena[] getTeleSenas() {
        return teleSenas;
    }

    public void setTeleSenas(TeleSena[] teleSenas) {
        this.teleSenas = teleSenas;
    }

    public double getValorPremiacao() {
        return valorPremiacao;
    }

    public void setValorPremiacao(double valorPremiacao) {
        this.valorPremiacao = valorPremiacao;
    }
}
