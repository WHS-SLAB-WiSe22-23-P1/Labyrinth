package de.whs.slab.wise2223.project.labyrinth.level.server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.whs.slab.wise2223.project.labyrinth.level.generator.RandomisedDepthFirstSearchLevelProvider;
import de.whs.slab.wise2223.project.labyrinth.level.server.Controller;
import de.whs.slab.wise2223.project.labyrinth.model.Dimensions;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerateLevelController extends Controller {
    @Override
    public String getPath() {
        return "/level/generate";
    }

    @Override
    public Map<String, HttpHandler> getMethods() {
        HashMap<String, HttpHandler> methods = new HashMap<>();
        methods.put("POST", this::generateLevel);
        return methods;
    }

    public void generateLevel(final HttpExchange exchange) throws IOException {
        String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody())).lines().collect(Collectors.joining("\n"));
        Dimensions size;
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
    }
}
