import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Processo {
    int id;
    int tempoChegada;
    int tempoExecucao;

    public Processo(int id, int tempoChegada, int tempoExecucao) {
        this.id = id;
        this.tempoChegada = tempoChegada;
        this.tempoExecucao = tempoExecucao;
    }
}

public class Escalonador {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            List<Processo> processos = lerProcessos();
            if (processos.isEmpty()) break;

            System.out.println("\n------------------- FIFO -------------------");
            List<Processo> resultadoFIFO = executarFIFO(new ArrayList<>(processos));
            exibirResultados(resultadoFIFO, "FIFO");

            System.out.println("\n------------------- SJF --------------------");
            List<Processo> resultadoSJF = executarSJF(new ArrayList<>(processos));
            exibirResultados(resultadoSJF, "SJF");

            if (!continuarExecucao()) break;
        }
        scanner.close();
    }

    private static List<Processo> lerProcessos() {
        System.out.println("--------------------Escalonador FIFO/SJF-------------------");
        System.out.print("Informe a quantidade de processos: ");
        int numProcessos = lerInteiro(1, Integer.MAX_VALUE);
        System.out.println("-----------------------------------------------------------");

        List<Processo> processos = new ArrayList<>();
        for (int i = 1; i <= numProcessos; i++) {
            System.out.println("Processo " + i + ":");
            int chegada = lerInteiro("Chegada (>= 0): ", 0, Integer.MAX_VALUE);
            int execucao = lerInteiro("Execução (> 0): ", 1, Integer.MAX_VALUE);
            processos.add(new Processo(i, chegada, execucao));
            System.out.println("-----------------------------------------------------------");
        }
        return processos;
    }

    private static int lerInteiro(String mensagem, int min, int max) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor >= min && valor <= max) return valor;
                System.out.println("Valor inválido. Digite um número entre " + min + " e " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static int lerInteiro(int min, int max) {
        return lerInteiro("", min, max);
    }

    private static List<Processo> executarFIFO(List<Processo> processos) {
        processos.sort(Comparator.comparingInt(p -> p.tempoChegada)); // Ordena por tempo de chegada
        int tempoAtual = 0;
        for (Processo p : processos) {
            if (tempoAtual < p.tempoChegada) {
                tempoAtual = p.tempoChegada; // Aguarda até o processo chegar
            }
            tempoAtual += p.tempoExecucao;
        }
        return processos;
    }

    private static List<Processo> executarSJF(List<Processo> processos) {
        List<Processo> resultado = new ArrayList<>();
        int tempoAtual = 0;
        while (!processos.isEmpty()) {
            List<Processo> disponiveis = new ArrayList<>();
            for (Processo p : processos) {
                if (p.tempoChegada <= tempoAtual) {
                    disponiveis.add(p);
                }
            }

            if (disponiveis.isEmpty()) {
                tempoAtual = processos.stream().mapToInt(p -> p.tempoChegada).min().orElse(tempoAtual + 1);
                continue;
            }

            Processo proximo = disponiveis.stream()
                    .min(Comparator.comparingInt(p -> p.tempoExecucao))
                    .orElseThrow();

            resultado.add(proximo);
            tempoAtual = Math.max(tempoAtual, proximo.tempoChegada) + proximo.tempoExecucao;
            processos.remove(proximo);
        }
        return resultado;
    }

    private static void exibirResultados(List<Processo> processos, String algoritmo) {
        printGrafico(processos);
        double tme = calcularTempoMedioEspera(processos);
        double tmp = calcularTempoMedioProcessamento(processos);
        System.out.printf("TMP (%s): %.2f\tTME (%s): %.2f%n", algoritmo, tmp, algoritmo, tme);
    }

    private static void printGrafico(List<Processo> processos) {
        int tempoTotal = 0;
        for (Processo p : processos) {
            System.out.printf("P%d ", p.id);
            for (int j = 0; j < p.tempoChegada; j++) System.out.print(" "); // Ajuste na exibição
            for (int j = 0; j < p.tempoExecucao; j++) System.out.print("■");
            tempoTotal += p.tempoExecucao;
            System.out.println(" " + tempoTotal);
        }
    }

    private static double calcularTempoMedioEspera(List<Processo> processos) {
        int tempoTotal = 0;
        int somaEspera = 0;
        for (Processo p : processos) {
            somaEspera += Math.max(0, tempoTotal - p.tempoChegada);
            tempoTotal = Math.max(tempoTotal, p.tempoChegada) + p.tempoExecucao;
        }
        return (double) somaEspera / processos.size();
    }

    private static double calcularTempoMedioProcessamento(List<Processo> processos) {
        int tempoTotal = 0;
        int somaProcessamento = 0;
        for (Processo p : processos) {
            tempoTotal = Math.max(tempoTotal, p.tempoChegada) + p.tempoExecucao;
            somaProcessamento += tempoTotal - p.tempoChegada;
        }
        return (double) somaProcessamento / processos.size();
    }

    private static boolean continuarExecucao() {
        System.out.println("\nDeseja calcular novamente? (S/N)");
        return scanner.nextLine().trim().equalsIgnoreCase("S");
    }
}
