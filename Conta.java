public abstract class Conta {
    protected int numero;
    protected String cliente;
    protected double saldo;
    private static int proximoNumero = 101;

    public Conta(String cliente) {
        this.numero = proximoNumero++;
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    // Getters
    public int getNumero() { return numero; }
    public String getCliente() { return cliente; }
    public double getSaldo() { return saldo; }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public abstract boolean sacar(double valor);
    public abstract boolean transferir(Conta destino, double valor);
}