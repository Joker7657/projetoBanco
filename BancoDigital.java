import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BancoDigital {
    private static ArrayList<Conta> contas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = 0;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    realizarDeposito();
                    break;
                case 3:
                    realizarSaque();
                    break;
                case 4:
                    realizarTransferencia();
                    break;
                case 5:
                    listarContas();
                    break;
                case 6:
                    calcularTributos();
                    break;
                case 7:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Digite um número entre 1 e 7.");
                    break;
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
    }

    private static void criarConta() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine().trim();

        int tipo;
        do {
            System.out.println("Tipos de conta:");
            System.out.println("1 - Conta Corrente");
            System.out.println("2 - Conta Poupança");
            tipo = lerInteiro("Escolha o tipo de conta: ");
            if (tipo != 1 && tipo != 2) {
                System.out.println("Tipo de conta inválido! Digite 1 ou 2.");
            }
        } while (tipo != 1 && tipo != 2);

        Conta novaConta = (tipo == 1) ? new ContaCorrente(nome) : new ContaPoupanca(nome);
        contas.add(novaConta);
        System.out.printf("Conta criada com sucesso! Número: %d | Cliente: %s%n", novaConta.getNumero(), nome);
    }

    private static void realizarDeposito() {
        Conta conta = buscarContaPorInput();
        if (conta == null) return;

        double valor;
        do {
            valor = lerDouble("Valor do depósito (positivo): R$ ");
            if (valor <= 0) System.out.println("O valor deve ser maior que zero!");
        } while (valor <= 0);

        conta.depositar(valor);
        System.out.printf("Depósito realizado! Saldo atual: R$ %.2f%n", conta.getSaldo());
    }

    private static void realizarSaque() {
        Conta conta = buscarContaPorInput();
        if (conta == null) return;

        double valor;
        do {
            valor = lerDouble("Valor do saque (positivo): R$ ");
            if (valor <= 0) System.out.println("O valor deve ser maior que zero!");
        } while (valor <= 0);

        if (conta.sacar(valor)) {
            System.out.printf("Saque realizado! Saldo atual: R$ %.2f%n", conta.getSaldo());
        } else {
            System.out.println("Saldo insuficiente para saque!");
        }
    }

    private static void realizarTransferencia() {
        System.out.println("Conta de origem:");
        Conta origem = buscarContaPorInput();
        if (origem == null) return;

        System.out.println("Conta de destino:");
        Conta destino = buscarContaPorInput();
        if (destino == null) return;

        if (origem.getNumero() == destino.getNumero()) {
            System.out.println("Não é possível transferir para a mesma conta!");
            return;
        }

        double valor;
        do {
            valor = lerDouble("Valor da transferência (positivo): R$ ");
            if (valor <= 0) System.out.println("O valor deve ser maior que zero!");
        } while (valor <= 0);

        if (origem.transferir(destino, valor)) {
            System.out.printf("Transferência realizada! Saldo origem: R$ %.2f | Saldo destino: R$ %.2f%n",
                    origem.getSaldo(), destino.getSaldo());
        } else {
            System.out.println("Saldo insuficiente para transferência!");
        }
    }

    private static void listarContas() {
        System.out.println("\n=== Lista de Contas ===");
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            for (Conta conta : contas) {
                System.out.println(conta);
            }
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

    private static Conta buscarContaPorInput() {
        int numero = lerInteiro("Digite o número da conta: ");
        Conta conta = buscarConta(numero);
        if (conta == null) System.out.println("Conta não encontrada!");
        return conta;
    }

    private static int lerInteiro(String mensagem) {
        int valor;
        while (true) {
            try {
                System.out.print(mensagem);
                valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
                scanner.nextLine();
            }
        }
    }

    private static double lerDouble(String mensagem) {
        double valor;
        while (true) {
            try {
                System.out.print(mensagem);
                valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número válido.");
                scanner.nextLine();
            }
        }
    }
}
