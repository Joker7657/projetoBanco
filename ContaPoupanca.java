public class ContaPoupanca extends Conta {
    public ContaPoupanca(String cliente) {
        super(cliente);
    }

    @Override
    public boolean sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
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
    public String toString() {
        return String.format("Número: %d | Cliente: %s | Saldo: R$ %.2f | Tipo: Conta Poupança", 
            numero, cliente, saldo);
    }
}