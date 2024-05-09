package com.java.game;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class VirtualThreadExample {
    public static void main(String[] args) {
        // Criar um ExecutorService para virtual threads
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        
        // Criar um número de virtual threads para execução de tarefas concorrentes
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("Executando tarefa: " + taskId + " por " + Thread.currentThread().getName());
                try {
                    // Simula trabalho por 1 segundo
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Tarefa " + taskId + " concluída");
            });
        }
        System.out.println("Uso de memória após aguardar as threads:");
        printMemoryUsage();

        System.out.println("Informações sobre Garbage Collector após aguardar as threads:");
        printGarbageCollectorUsage();

        // Fechar o executor após a conclusão de todas as tarefas
        executor.shutdown();

        try {
            // Aguarda todas as tarefas serem concluídas ou um tempo limite de 5 segundos
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Algumas tarefas não foram concluídas a tempo.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Aguardando interrupção");
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

    public static void printGarbageCollectorUsage() {
        List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gc : gcs) {
            System.out.println("Garbage Collector: " + gc.getName());
            System.out.println(" - Total de coletas: " + gc.getCollectionCount());
            System.out.println(" - Tempo total de coleta: " + gc.getCollectionTime() + " ms");
        }
    }
}

