import java.util.ArrayList;
import java.util.Scanner;

public class BancoDigital {
    private static ArrayList<Conta> contas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> criarConta();
                case 2 -> realizarDeposito();
                case 3 -> realizarSaque();
                case 4 -> realizarTransferencia();
                case 5 -> listarContas();
                case 6 -> calcularTributos();
                case 7 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 7);
    }

    private static void exibirMenu() {
        System.out.println("\n=== Banco BOE ===");
        System.out.println("1. Criar Conta");
        System.out.println("2. Realizar Depósito");
        System.out.println("3. Realizar Saque");
        System.out.println("4. Realizar Transferência");
        System.out.println("5. Listar Contas");
        System.out.println("6. Calcular Total de Tributos");
        System.out.println("7. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void criarConta() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.println("Tipos de conta:");
        System.out.println("1 - Conta Corrente");
        System.out.println("2 - Conta Poupança");
        System.out.print("Escolha o tipo: ");
        int tipo = scanner.nextInt();

        Conta novaConta;
        if (tipo == 1) {
            novaConta = new ContaCorrente(nome);
        } else {
            novaConta = new ContaPoupanca(nome);
        }

        contas.add(novaConta);
        System.out.println("Conta criada com sucesso! Número: " + novaConta.getNumero());
    }

    private static void realizarDeposito() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        
        Conta conta = buscarConta(numero);
        if (conta != null) {
            System.out.print("Valor do depósito: R$ ");
            double valor = scanner.nextDouble();
            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso!");
        } else {
            System.out.println("Conta não encontrada!");
        }
    }

    private static void realizarSaque() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        
        Conta conta = buscarConta(numero);
        if (conta != null) {
            System.out.print("Valor do saque: R$ ");
            double valor = scanner.nextDouble();
            if (conta.sacar(valor)) {
                System.out.println("Saque realizado com sucesso!");
            } else {
                System.out.println("Saldo insuficiente para saque!");
            }
        } else {
            System.out.println("Conta não encontrada!");
        }
    }

    private static void realizarTransferencia() {
        System.out.print("Conta de origem: ");
        int origemNum = scanner.nextInt();
        System.out.print("Conta de destino: ");
        int destinoNum = scanner.nextInt();
        
        Conta origem = buscarConta(origemNum);
        Conta destino = buscarConta(destinoNum);
        
        if (origem != null && destino != null) {
            System.out.print("Valor da transferência: R$ ");
            double valor = scanner.nextDouble();
            if (origem.transferir(destino, valor)) {
                System.out.println("Transferência realizada com sucesso!");
            } else {
                System.out.println("Saldo insuficiente para transferência!");
            }
        } else {
            System.out.println("Conta de origem ou destino não encontrada!");
        }
    }

    private static void listarContas() {
        System.out.println("\n=== Lista de Contas ===");
        for (Conta conta : contas) {
            System.out.println(conta);
        }
    }

    private static void calcularTributos() {
        double totalTributos = 0.0;
        for (Conta conta : contas) {
            if (conta instanceof ITributavel) {
                ITributavel tributavel = (ITributavel) conta;
                totalTributos += tributavel.calculaTributos();
            }
        }
        System.out.println("\n========================================");
        System.out.printf("Total de tributos a recolher: R$ %.2f%n", totalTributos);
        System.out.println("========================================");
    }

    private static Conta buscarConta(int numero) {
        for (Conta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }
}