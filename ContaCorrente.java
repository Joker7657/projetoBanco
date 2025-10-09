public class ContaCorrente extends Conta implements ITributavel {
    public ContaCorrente(String cliente) {
        super(cliente);
    }

    public boolean sacar(double valor) {
        double valorComTaxa = valor * 1.05;
        if (saldo >= valorComTaxa) {
            saldo -= valorComTaxa;
            return true;
        }
        return false;
    }

    public boolean transferir(Conta destino, double valor) {
        if (sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public double calculaTributos() {
        return saldo * 0.01;
    }

 
    public String toString() {
        return String.format("Número: %d | Cliente: %s | Saldo: R$ %.2f | Tipo: Conta Corrente",
                numero, cliente, saldo);
    }
}