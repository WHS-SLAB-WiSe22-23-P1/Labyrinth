package de.whs.slab.wise2223.project.labyrinth.level;

import com.sun.istack.internal.NotNull;
import de.whs.slab.wise2223.project.labyrinth.model.Level;

public abstract class LevelProvider {
    private Level level = null;

    protected abstract Level createNewLevel();

    public @NotNull Level getLevel() {
        if(level != null) {
            return level;
        }

        return level = createNewLevel();
    }

    public void printLevel() {
        System.out.println(level.toJSON().toJSONString());
    }
}
