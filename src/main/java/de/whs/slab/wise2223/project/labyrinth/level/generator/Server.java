package de.whs.slab.wise2223.project.labyrinth.level.generator;

import com.sun.net.httpserver.HttpServer;
import de.whs.slab.wise2223.project.labyrinth.level.generator.model.Dimensions;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 8080), 0);

        server.createContext("/level/", exchange -> {
            System.out.printf("/level/ with %s%n", exchange.getRequestMethod());

            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                exchange.sendResponseHeaders(405, 0);
                exchange.close();
                return;
            }

            String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining("\n"));
            Dimensions size = null;
            try {
                size = new Dimensions(body);
            } catch (ParseException e) {
                exchange.sendResponseHeaders(400, 0);
                exchange.close();
                return;
            }
            Level level = new RandomisedDepthFirstSearchLevelProvider(size).getLevel();
            byte[] response = level.toJSON().toJSONString().getBytes();
            exchange.sendResponseHeaders(201, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });

        server.start();

        System.out.printf("Server is running on %s%n", server.getAddress());

        System.out.println("Enter exit to stop the Server");
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        while (scanner.hasNext()) {
            if("exit".equals(scanner.next())) {
                System.out.println("Stopping Server...");
                server.stop(60);
                System.out.println("Server stopped!");
                break;
            }
        }
        scanner.close();
    }
}
