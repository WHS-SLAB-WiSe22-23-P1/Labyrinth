package de.whs.slab.wise2223.project.labyrinth.level.server;

import com.sun.net.httpserver.HttpHandler;

import java.util.Map;

public abstract class Controller {
    protected final Controller[] children;

    protected Controller(final Controller... children) {
        this.children = children;
    }

    private String addTrailingSlash(final String str) {
        if (str.endsWith("/")) return str;
        else return str + "/";
    }

    public HttpHandler forChildren() {
        return exchange -> {
            String path = addTrailingSlash(exchange.getRequestURI().normalize().getPath());
            for (Controller child : getChildren()) {
                if (path.startsWith(child.getNormalizedPath())) {
                    child.forChildren().handle(exchange);
                    return;
                }
            }

            forMethod(getMethods()).handle(exchange);
        };
    }

    public HttpHandler forMethod(final Map<String, HttpHandler> handlers) {
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

    public final String getNormalizedPath() {
        return addTrailingSlash(getPath());
    }

    public abstract String getPath();

    public String[] getPathSegments() {
        return getNormalizedPath().split("/");
    }

    public abstract Map<String, HttpHandler> getMethods();

    public Controller[] getChildren() {
        return children;
    }
}
