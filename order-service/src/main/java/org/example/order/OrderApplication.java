package org.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) throws Exception {
        startRabbitMQ();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try { stopRabbitMQ(); } catch (Exception ignored) {}
        }));
        SpringApplication.run(OrderApplication.class, args);
    }

    private static void startRabbitMQ() throws Exception {
        File dir = findComposeDir();
        new ProcessBuilder("docker-compose", "-f", "docker-compose.yml", "up", "-d")
                .directory(dir).inheritIO().start().waitFor();
        System.out.println("RabbitMQ starting... waiting 8s");
        Thread.sleep(8000);
    }

    private static void stopRabbitMQ() throws Exception {
        File dir = findComposeDir();
        new ProcessBuilder("docker-compose", "-f", "docker-compose.yml", "down")
                .directory(dir).inheritIO().start().waitFor();
    }

    private static File findComposeDir() {
        File dir = new File(System.getProperty("user.dir"));
        while (dir != null) {
            if (new File(dir, "docker-compose.yml").exists()) return dir;
            dir = dir.getParentFile();
        }
        throw new RuntimeException("docker-compose.yml not found");
    }
}
