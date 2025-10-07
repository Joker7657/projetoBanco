public class ContaCorrente extends Conta implements ITributavel {
    public ContaCorrente(String cliente) {
        super(cliente);
    }

    @Override
    public boolean sacar(double valor) {
        double valorComTaxa = valor * 1.05;
        if (saldo >= valorComTaxa) {
            saldo -= valorComTaxa;
            return true;
        }
        return false;
    }

    @Override
    public boolean transferir(Conta destino, double valor) {
        if (sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    @Override
    public double calculaTributos() {
        return saldo * 0.01;
    }

    @Override
    public String toString() {
        return String.format("Número: %d | Cliente: %s | Saldo: R$ %.2f | Tipo: Conta Corrente", 
            numero, cliente, saldo);
    }
}