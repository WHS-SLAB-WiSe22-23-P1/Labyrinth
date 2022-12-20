package de.whs.slab.wise2223.project.labyrinth.level.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.whs.slab.wise2223.project.labyrinth.level.server.controllers.GenerateLevelController;
import de.whs.slab.wise2223.project.labyrinth.level.server.controllers.LevelController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private final HttpServer httpServer;

    public Main(InetSocketAddress address) throws IOException {
        httpServer = HttpServer.create(address, 0);
        initRoutes();
    }


    public static void main(String[] args) throws IOException {
        final Main server = new Main(new InetSocketAddress(InetAddress.getLoopbackAddress(), 8080));

        server.start();
    }

    private void initRoutes() {
        final Controller root = new Controller(new LevelController("jdbc:mysql://localhost:3306/slab", "slab", "bals", new GenerateLevelController())) {
            @Override
            public String getPath() {
                return "/";
            }

            @Override
            public Map<String, HttpHandler> getMethods() {
                return new HashMap<>();
            }
        };

        httpServer.createContext(root.getPath(), exchange -> {
            System.out.printf("%s with %s%n", exchange.getRequestURI().getPath(), exchange.getRequestMethod());
            root.forChildren().handle(exchange);
        });

    }

    public void start() {
        httpServer.start();

        System.out.printf("Server is running on %s%n", httpServer.getAddress());

        System.out.println("Enter exit to stop the Server");
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        while (scanner.hasNext()) {
            if ("exit".equals(scanner.next())) {
                System.out.println("Stopping Server...");
                httpServer.stop(60);
                System.out.println("Server stopped!");
                break;
            }
        }
        scanner.close();
    }
}
