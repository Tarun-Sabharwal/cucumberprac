package org.example.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.TimeZone;

@SpringBootApplication
public class InventoryApplication {
    public static void main(String[] args) throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        startRabbitMQ();
        SpringApplication.run(InventoryApplication.class, args);
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
