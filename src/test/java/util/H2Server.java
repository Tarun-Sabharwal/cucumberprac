package util;

import org.h2.tools.Server;

public class H2Server {
    public static void main(String[] args) throws Exception {

        Server.createTcpServer(
                "-tcpPort", "9092",
                "-tcpAllowOthers",
                "-ifNotExists"   //  IMPORTANT FIX
        ).start();

        Server.createWebServer(
                "-webPort", "8082",
                "-webAllowOthers"
        ).start();

        System.out.println("H2 Server started...");
    }
}