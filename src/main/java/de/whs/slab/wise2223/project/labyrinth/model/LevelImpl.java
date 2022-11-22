package de.whs.slab.wise2223.project.labyrinth.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LevelImpl implements Level {
    private final int width;
    private final int height;

    private final Coordinate start;
    private final Coordinate end;

    private final boolean[][] fields;

    public LevelImpl(String json) throws ParseException {
        this((JSONObject) new JSONParser().parse(json));
    }

    public LevelImpl(JSONObject json) {
        width = (int) (long) json.get("width");
        height = (int) (long) json.get("height");
        start = new Coordinate((JSONObject) json.get("start"));
        end = new Coordinate((JSONObject) json.get("end"));
        fields = parseBytes((JSONArray) json.get("fields"));
    }

    public LevelImpl(int width, int height, Coordinate start, Coordinate end, boolean[][] fields) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.end = end;
        this.fields = fields;
    }

    private boolean[][] parseBytes(final JSONArray json) {
        final boolean[][] fields = new boolean[getHeight()][getWidth()];

        for (int y = 0; y < getHeight(); y++) {
            final JSONArray column = (JSONArray) json.get(y);
            for (int x = 0; x < getWidth(); x++) {
                fields[y][x] = (long) column.get(x) != 0;
            }
        }

        return fields;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Coordinate getStart() {
        return start;
    }

    @Override
    public Coordinate getEnd() {
        return end;
    }

    @Override
    public boolean getFieldAt(Coordinate coordinate) throws IllegalArgumentException {
        if (coordinate.getX() < 0 || coordinate.getX() >= getWidth() || coordinate.getY() < 0 || coordinate.getY() >= getHeight())
            throw new IllegalArgumentException(String.format("Coordinate %s does not exist in this Level.", coordinate.toJSON().toJSONString()));

        return fields[coordinate.getY()][coordinate.getX()];
    }

    @SuppressWarnings("unchecked")
    @Override
    public JSONObject toJSON() {
        return new JSONObject() {{
            put("width", getWidth());
            put("height", getHeight());
            put("start", getStart().toJSON());
            put("end", getEnd().toJSON());
            put("fields", new JSONArray() {{
                for (int y = 0; y < getHeight(); y++) {
                    final int finalY = y;
                    add(new JSONArray() {{
                        for (int x = 0; x < getWidth(); x++) {
                            add(getFieldAt(new Coordinate(x, finalY)) ? 1 : 0);
                        }
                    }});
                }
            }});
        }};
    }
}
