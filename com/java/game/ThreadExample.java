package com.java.game;

public class ThreadExample {
    static class WorkerTask implements Runnable {
        private final int taskId;

        public WorkerTask(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            System.out.println("Iniciando tarefa: " + taskId + " na thread: " + Thread.currentThread().getName());
            try {
                // Simula um trabalho por 2 segundos
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Reconfigura a flag de interrupção
                System.out.println("Tarefa " + taskId + " foi interrompida.");
            }
            System.out.println("Tarefa " + taskId + " concluída na thread: " + Thread.currentThread().getName());
        }
    }
    public static void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory(); // Memória total alocada para a JVM
        long freeMemory = runtime.freeMemory();  // Memória livre disponível
        long maxMemory = runtime.maxMemory();   // Memória máxima que a JVM pode usar

        System.out.println("Memória total: " + totalMemory / (1024 * 1024) + " MB");
        System.out.println("Memória livre: " + freeMemory / (1024 * 1024) + " MB");
        System.out.println("Memória máxima: " + maxMemory / (1024 * 1024) + " MB");
    }

    public static void main(String[] args) {
        // Criar e iniciar várias threads para executar tarefas concorrentes
        int numThreads = 5; // Número de threads que queremos iniciar
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new WorkerTask(i));
            threads[i].start(); // Iniciar a thread
        }

        System.out.println("Uso de memória antes de aguardar as threads:");
        printMemoryUsage();

        // Aguarda todas as threads terminarem
        for (Thread thread : threads) {
            try {
                thread.join(); // Aguarda a conclusão da thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Reconfigura a flag de interrupção
                System.out.println("Aguardando interrupção.");
            }
        }

        System.out.println("Todas as tarefas foram concluídas.");
    }
}
