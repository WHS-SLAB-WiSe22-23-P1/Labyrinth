package de.whs.slab.wise2223.project.labyrinth.ui;

import de.whs.slab.wise2223.project.labyrinth.model.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import processing.core.PApplet;

public abstract class LevelUI extends PApplet {
    protected final Level level;
    public LevelUI(LevelProvider provider) {
        level = provider.getLevel();
    }

    public abstract void startLevel();
}
