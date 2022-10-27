package de.whs.slab.wise2223.project.labyrinth;

import processing.core.PApplet;
import processing.core.PFont;

public class Main extends PApplet {
    public void settings() {
        printArray(PFont.list());
    }

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{"Hello"}, new Main());
    }
}
