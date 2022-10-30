package de.whs.slab.wise2223.project.labyrinth.ui;

import de.whs.slab.wise2223.project.labyrinth.level.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.model.Level;

public abstract class LevelUI {
    final Level level;
    public LevelUI(LevelProvider provider) {
        level = provider.getLevel();
    }

    public abstract void startLevel();
}
