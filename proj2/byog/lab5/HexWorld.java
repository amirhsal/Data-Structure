package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private Random gen;

    public HexWorld(long seed) {
        gen = new Random(seed);
    }

    public static void main(String[] args) {
        HexWorld hex = new HexWorld(5559l);
        TERenderer ter = new TERenderer();
        ter.initialize(20, 20);
        TETile[][] world = createWorld(20, 20);
        hex.add(world, 5, 5, 5);
        ter.renderFrame(world);
    }

    private static TETile[][] createWorld(int width, int height) {
        TETile[][] world = new TETile[width][height];
        for (int index = 0; index < height; index++) {
            for (int column = 0; column < width; column++) {
                world[index][column] = Tileset.NOTHING;
            }
        }
        return world;
    }

    private void initWorld(TETile[][] world, int x, int y, int width, int height) {
        for (int row = x; x < x + height; row++) {
            for (int column = y; y < y + width; column++) {
                world[row][column] = Tileset.NOTHING;
            }
        }
    }

    public void add(TETile[][] world, int x, int y, int size) {
        int height = 2 * size;
        int width = size + 2 * (size - 1);
        TETile tile = null;
        switch (gen.nextInt(3)) {
            case 0:
                tile = Tileset.FLOOR;
                break;
            case 1:
                tile = Tileset.FLOWER;
                break;
            case 2:
                tile = Tileset.GRASS;
                break;
        }
        for (int row = x; row < x + height; row++) {
            for (int column = y; column < y + width; column++) {
                if (isNothing(x, y, row, column, size)) {
                    world[row][column] = Tileset.NOTHING;
                } else {
                    world[row][column] = tile;
                }
            }
        }

    }

    //Defect
    private boolean isNothing(int startX, int startY, int x, int y, int size) {
        boolean nothing = x < startX + size - 1 || x >= startX + 2 * (size - 1);
        nothing = nothing && (y < startY + size - 1 || y >= startY + 2 * (size - 1));
        return nothing;
    }
}
