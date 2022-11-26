package de.whs.slab.wise2223.project.labyrinth.level.server;

import com.sun.net.httpserver.HttpHandler;

import java.util.Map;

public interface Controller {
    String getPath();

    Map<String, HttpHandler> getMethods();
}
