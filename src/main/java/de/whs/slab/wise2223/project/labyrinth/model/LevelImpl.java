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

    private final byte[] fields;

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
        byte[] fields = new byte[(int) Math.ceil(width * height / 8.0)];
        for (int index = 0; index < getWidth() * getHeight(); index++) {
            final int rowIndex = index % width;
            final int columnIndex = index / width;

            final JSONArray row = (JSONArray) json.get(rowIndex);

            final long fieldNum = (long) row.get(columnIndex);
            final boolean field = fieldNum != 0;

            final int fieldsIndex = index / 8;
            final int fieldIndex = index % 8;
            final byte fieldPosition = (byte) (1 << fieldIndex);

            fields[fieldsIndex] = (byte) (fields[fieldsIndex] | (field ? fieldPosition : 0));
        }

        return fields;
    }

    private byte[] parseBytes(final boolean[][] json) {
        byte[] fields = new byte[(int) Math.ceil(width * height / 8.0)];
        for (int index = 0; index < getWidth() * getHeight(); index++) {
            final int rowIndex = index / getWidth();
            final int columnIndex = index % getWidth();

            final boolean[] row = json[rowIndex];

            final boolean field = row[columnIndex];

            final int fieldsIndex = index / 8;
            final int fieldIndex = index % 8;
            final byte fieldPosition = (byte) (1 << fieldIndex);

            fields[fieldsIndex] = (byte) (fields[fieldsIndex] | (field ? fieldPosition : 0));
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
        final byte fieldBit = (byte) (0xFF & (bitPosition % 8));

        return (fieldByte & (1 << fieldBit)) > 0;
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
