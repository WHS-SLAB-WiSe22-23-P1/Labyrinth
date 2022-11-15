package de.whs.slab.wise2223.project.labyrinth.ui.topdown;

import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.ui.StreamLevelProvider;
import processing.core.PApplet;
import processing.core.PFont;

public class Main extends PApplet {
    public void settings() {
        printArray(PFont.list());
    }

    public static void main(String[] args) {
        Level level = new StreamLevelProvider(System.in).getLevel();

        System.out.println(level.toJSON().toJSONString());

        PApplet.runSketch(new String[]{"Top down UI"}, new Main());
    }
}
