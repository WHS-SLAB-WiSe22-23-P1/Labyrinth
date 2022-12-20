package de.whs.slab.wise2223.project.labyrinth.level.server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.whs.slab.wise2223.project.labyrinth.level.server.Controller;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.model.LevelImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LevelController extends Controller {
    private final String url;
    private final String username;
    private final String password;

    public LevelController(final String url, final String username, final String password, final Controller ... children) {
        super(children);
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getPath() {
        return "/level";
    }

    @Override
    public Map<String, HttpHandler> getMethods() {
        HashMap<String, HttpHandler> methods = new HashMap<>();
        methods.put("GET", this::get);
        return methods;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }


    public void get(final HttpExchange exchange) throws IOException {
        final String[] pathSegments = exchange.getRequestURI().getPath().split("/");

        if(pathSegments.length != getPathSegments().length + 1) {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
            return;
        }

        int id;
        try {

        id = Integer.parseUnsignedInt(pathSegments[pathSegments.length - 1]);
        } catch (NumberFormatException e) {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
            return;
        }

        try(Connection connection = getConnection()) {
            final String json;
            final Timestamp createdAt;
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM levels WHERE id = ?;")) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(!resultSet.next()) {
                    exchange.sendResponseHeaders(404, 0);
                    exchange.close();
                    return;
                }

                id = resultSet.getInt(1);
                json = resultSet.getString(2);
                createdAt = resultSet.getTimestamp(3);
            } catch (final SQLException e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500,0);
                exchange.close();
                return;
            }
            Level level = new LevelImpl(json);
            JSONObject result = new JSONObject();
            result.put("level", level.toJSON());
            result.put("id", id);
            result.put("createdAt", createdAt.toString());
            byte[] resultBytes = result.toJSONString().getBytes();

            exchange.sendResponseHeaders(200, resultBytes.length);
            exchange.getResponseBody().write(resultBytes);
            exchange.close();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500,0);
            exchange.close();
            return;
        } catch (ParseException e) {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }
}
