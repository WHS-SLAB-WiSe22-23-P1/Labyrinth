package de.whs.slab.wise2223.project.labyrinth.model;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class LevelImplTest {

    @Test
    public void jsonConstructorTest() throws Exception {
        LevelImpl level = new LevelImpl("{\"width\":5,\"start\":{\"x\":1,\"y\":1},\"end\":{\"x\":2,\"y\":3},\"fields\":[[1,1,0,0,1],[1,0,0,1,1],[0,0,1,1,0],[0,1,1,0,0],[1,1,0,0,0]],\"height\":5}");

        Field fields = LevelImpl.class.getDeclaredField("fields");
        fields.setAccessible(true);

        assertEquals(5, level.getWidth());
        assertEquals(5, level.getHeight());
        assertEquals(new Coordinate(1, 1), level.getStart());
        assertEquals(new Coordinate(2, 3), level.getEnd());
        assertArrayEquals(new byte[]{0b001_10011, 0b0_01100_11, 0b0011_0011, 0b0000000_0}, (byte[]) fields.get(level));
    }

    @Test
    public void GridConstructorTest() throws Exception {
        LevelImpl level = new LevelImpl(5, 5, new Coordinate(1, 1), new Coordinate(2, 3), new boolean[][]{{true, true, false, false, true}, {true, false, false, true, true}, {false, false, true, true, false}, {false, true, true, false, false}, {true, true, false, false, false}});

        Field fields = LevelImpl.class.getDeclaredField("fields");
        fields.setAccessible(true);

        assertEquals(5, level.getWidth());
        assertEquals(5, level.getHeight());
        assertEquals(new Coordinate(1, 1), level.getStart());
        assertEquals(new Coordinate(2, 3), level.getEnd());
        assertArrayEquals(new byte[]{0b001_10011, 0b0_01100_11, 0b0011_0011, 0b0000000_0}, (byte[]) fields.get(level));
    }


    @Test
    public void testGetFieldAt() throws Exception {
        LevelImpl level = new LevelImpl(5, 5, new Coordinate(1, 1), new Coordinate(2, 3), new byte[]{0b001_10011, 0b0_01100_11, 0b0011_0011, 0b0000000_0});

        assertTrue(level.getFieldAt(new Coordinate(0, 0)));
        assertTrue(level.getFieldAt(new Coordinate(0, 1)));
        assertFalse(level.getFieldAt(new Coordinate(0, 2)));
        assertFalse(level.getFieldAt(new Coordinate(0, 3)));
        assertTrue(level.getFieldAt(new Coordinate(0, 4)));
        assertTrue(level.getFieldAt(new Coordinate(1, 0)));
        assertFalse(level.getFieldAt(new Coordinate(1, 1)));
        assertFalse(level.getFieldAt(new Coordinate(1, 2)));
        assertTrue(level.getFieldAt(new Coordinate(1, 3)));
        assertTrue(level.getFieldAt(new Coordinate(1, 4)));
        assertFalse(level.getFieldAt(new Coordinate(2, 0)));
        assertFalse(level.getFieldAt(new Coordinate(2, 1)));
        assertTrue(level.getFieldAt(new Coordinate(2, 2)));
        assertTrue(level.getFieldAt(new Coordinate(2, 3)));
        assertFalse(level.getFieldAt(new Coordinate(2, 4)));
        assertFalse(level.getFieldAt(new Coordinate(3, 0)));
        assertTrue(level.getFieldAt(new Coordinate(3, 1)));
        assertTrue(level.getFieldAt(new Coordinate(3, 2)));
        assertFalse(level.getFieldAt(new Coordinate(3, 3)));
        assertFalse(level.getFieldAt(new Coordinate(3, 4)));
        assertTrue(level.getFieldAt(new Coordinate(4, 0)));
        assertTrue(level.getFieldAt(new Coordinate(4, 1)));
        assertFalse(level.getFieldAt(new Coordinate(4, 2)));
        assertFalse(level.getFieldAt(new Coordinate(4, 3)));
        assertFalse(level.getFieldAt(new Coordinate(4, 4)));
    }

    @Test
    public void testToJSON() throws Exception {
        LevelImpl level = new LevelImpl(5, 5, new Coordinate(1, 1), new Coordinate(2, 3), new byte[]{0b001_10011, 0b0_01100_11, 0b0011_0011, 0b0000000_0});

        assertEquals("{\"width\":5,\"start\":{\"x\":1,\"y\":1},\"end\":{\"x\":2,\"y\":3},\"fields\":[[1,1,0,0,1],[1,0,0,1,1],[0,0,1,1,0],[0,1,1,0,0],[1,1,0,0,0]],\"height\":5}", level.toJSON().toJSONString());
    }
}
