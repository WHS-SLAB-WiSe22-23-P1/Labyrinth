package de.whs.slab.wise2223.project.labyrinth.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LevelImpl implements Level {
    private final int width;
    private final int height;

    private final Coordinate start;
    private final Coordinate end;

    private final byte[] fields;

    public LevelImpl(JSONObject json) {
        width = (int) json.get("width");
        height = (int) json.get("height");
        start = new Coordinate((JSONObject) json.get("start"));
        end = new Coordinate((JSONObject) json.get("end"));
        fields = parseBytes((JSONArray) json.get("fields"));
    }

    public LevelImpl(int width, int height, Coordinate start, Coordinate end, boolean[][] fields) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.end = end;
        this.fields = parseBytes(fields);
    }

    public LevelImpl(int width, int height, Coordinate start, Coordinate end, byte[] fields) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.end = end;
        this.fields = fields;
    }

    private byte[] parseBytes(final JSONArray json) {
        byte[] fields = new byte[width * height];
        for(int i = 0; i < fields.length; i++) {
            // TODO(nbenson): Parse json to bytes
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

        final int bitPosition = coordinate.getY() * width + coordinate.getX();
        final int bytePosition = bitPosition / 8;
        final byte fieldByte = fields[bytePosition];
        final int fieldBit = bytePosition % 8;


        return (fieldByte & (fieldByte >> fieldBit)) > 0;
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
                for (int x = 0; x < getWidth(); x++) {
                    int finalX = x;
                    add(new JSONArray() {{
                        for (int y = 0; y < getHeight(); y++) {
                            add(getFieldAt(new Coordinate(finalX, y)));
                        }
                    }});
                }
            }});
        }};
    }
}
