package byog.Core.WorldComponents;

import byog.TileEngine.TETile;

/**
 * Models a Position in the TETile grid
 */
public final class Position {

    private int x;
    private int y;

    /**
     * Constructs a Position object which is the position of a TETile in the world.
     * It May be Out Of Bounds checks only if the Position's coordinates are positive.
     *
     * @param x The x coordinate of the tile.
     * @param y The y coordinate of the tile.
     * @throws IllegalArgumentException If either of the arguments
     *                                  are negative: {@code x < 0 || y < 0}
     */
    public Position(int x, int y) throws IllegalArgumentException {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be positive: "
                    + "x = " + x + ", y = " + y);
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate of the Position object
     *
     * @return The x coordinate of the Position object
     */
    public int getX() {
        return x;
    }

    /**
     * Sets a new value to the x coordinate of the Position
     *
     * @param newX The new x coordinate of the object
     * @throws IllegalArgumentException If the argument is negative: {@code newX < 0}
     */
    public void setX(int newX) throws IllegalArgumentException {
        if (newX < 0) {
            throw new IllegalArgumentException("Negative argument newX = " + newX);
        }
        this.x = newX;
    }

    /**
     * Gets the y coordinate of the Position object.
     *
     * @return The y coordinate of the object.
     */
    public int getY() {
        return y;
    }

    /**
     * Validates that the position is not out of bounds of the TETile grid.
     *
     * @param world The TETile grid
     * @return True if the position is inside of the grid's bounds otherwise false;
     */
    public boolean validate(TETile[][] world) {
        if (x < world.length && y < world[0].length) {
            return true;
        }
        return false;
    }

//    public boolean equals(Object other) {
//        if (other == null) {
//            return false;
//        }
//        if (other.getClass() == this.getClass()) {
//            Position otherPos = (Position) other;
//            if (otherPos.x == this.x && otherPos.y == this.y) {
//                return true;
//            }
//        }
//        return false;
//    }


}

