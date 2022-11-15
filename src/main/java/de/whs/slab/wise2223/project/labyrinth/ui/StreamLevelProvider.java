package de.whs.slab.wise2223.project.labyrinth.ui;

import de.whs.slab.wise2223.project.labyrinth.level.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.model.LevelImpl;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class StreamLevelProvider extends LevelProvider {
    private final BufferedReader bufferedReader;

    public StreamLevelProvider(InputStream stream) {
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    @Override
    protected Level createNewLevel() {
        try {
            return new LevelImpl(bufferedReader.lines().collect(Collectors.joining()));
        } catch (ParseException e) {
            return null;
        }
    }
}
