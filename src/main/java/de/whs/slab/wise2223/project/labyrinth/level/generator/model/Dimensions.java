package de.whs.slab.wise2223.project.labyrinth.level.generator.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Dimensions {
    public final int width;
    public final int height;

    public Dimensions(String json) throws ParseException {
        this((JSONObject) new JSONParser().parse(json));
    }

    public Dimensions(JSONObject json) {
        width = (int) (long) json.get("width");
        height = (int) (long) json.get("height");
    }

    public Dimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean contains(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < width && coordinate.getY() >= 0 && coordinate.getY() < height;
    }

    public JSONObject toJson() {
        return new JSONObject() {{
            put("width", width);
            put("height", height);
        }};
    }
}
