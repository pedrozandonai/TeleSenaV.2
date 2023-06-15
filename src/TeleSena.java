import java.util.Arrays;

public class TeleSena {
    private double valorDeVenda = 10.00;
    private int[] arrayUm = new int[25];
    private int[] arrayDois = new int[25];
    private int[] numerosDaPessoa = new int[25];

    public TeleSena(double valorDeVenda, int[] arrayUm, int[] arrayDois) {
        this.valorDeVenda = valorDeVenda;
        this.arrayUm = arrayUm;
        this.arrayDois = arrayDois;
    }

    public TeleSena(int[] numerosDaPessoa) {
        this.numerosDaPessoa = numerosDaPessoa;
    }

    public TeleSena() {
    }

    public double getValorDeVenda() {
        return valorDeVenda;
    }

    public void setValorDeVenda(double valorDeVenda) {
        this.valorDeVenda = valorDeVenda;
    }

    public int[] getArrayUm() {
        return arrayUm;
    }

    public void setArrayUm(int[] arrayUm) {
        this.arrayUm = arrayUm;
    }

    public int[] getArrayDois() {
        return arrayDois;
    }

    public void setArrayDois(int[] arrayDois) {
        this.arrayDois = arrayDois;
    }

    public int[] getNumerosDaPessoa() {
        return numerosDaPessoa;
    }

    public void setNumerosDaPessoa(int[] numerosDaPessoa) {
        this.numerosDaPessoa = numerosDaPessoa;
    }

    @Override
    public String toString() {
        return "valorDeVenda= " + valorDeVenda +
                "\narrayUm= " + Arrays.toString(arrayUm) +
                "\narrayDois= " + Arrays.toString(arrayDois);
    }
}
