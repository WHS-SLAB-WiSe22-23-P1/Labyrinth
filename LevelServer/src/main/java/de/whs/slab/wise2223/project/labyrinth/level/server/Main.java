package de.whs.slab.wise2223.project.labyrinth.level.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.whs.slab.wise2223.project.labyrinth.level.server.controllers.LevelController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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

    public static HttpHandler forMethod(Map<String, HttpHandler> handlers) {
        return exchange -> {
            final String method = exchange.getRequestMethod();
            if (!handlers.containsKey(method)) {
                exchange.sendResponseHeaders(405, 0);
                exchange.close();
                return;
            }

            handlers.get(method).handle(exchange);
        };
    }

    private void initRoutes() {
        final Controller[] controllers = new Controller[]{new LevelController()};

        for (final Controller controller : controllers) {
            httpServer.createContext(controller.getPath(), exchange -> {
                System.out.printf("%s with %s%n", exchange.getRequestURI().getPath(), exchange.getRequestMethod());
                forMethod(controller.getMethods()).handle(exchange);
            });
        }

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
