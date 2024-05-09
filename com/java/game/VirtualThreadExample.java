package com.java.game;

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
}
