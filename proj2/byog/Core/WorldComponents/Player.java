//package byog.Core.WorldComponents;
//
//import byog.TileEngine.TETile;
//import byog.TileEngine.Tileset;
//
///**
// * Models a player of the game handles the interference  of the player with the
// * generated environment.
// */
//public class Player {
//
//    public static final int MOVE_UP = 0;
//    public static final int MOVE_DOWN = 1;
//    public static final int MOVE_LEFT = 2;
//    public static final int MOVE_RIGHT = 3;
//
//    public static final TETile PLAYERTILE = Tileset.PLAYER;
//    private Position playerPos;
//    private String playerName;
//    private TETile previousTile;
//
//    public Player(Position pos, String name, TETile[][] world) {
//        this.playerName = name;
//        this.playerPos = pos;
//        if (world == null) {
//            throw new IllegalArgumentException("TETile[][] object equals null!");
//        }
//        this.previousTile = world[pos.getX()][pos.getY()];
//        world[pos.getX()][pos.getY()] = Tileset.PLAYER;
//    }
//
//    public Player(Position pos) {
//        this.playerPos = pos;
//    }
//
//    public boolean move(int direction, TETile[][] world) {
//        Position newPosition = null;
//        switch (direction) {
//            case MOVE_UP:
//                newPosition = new Position(this.playerPos.getX(), this.playerPos.getY() + 1);
//                break;
//            case MOVE_DOWN:
//                newPosition = new Position(this.playerPos.getX(), this.playerPos.getY() - 1);
//                break;
//            case MOVE_RIGHT:
//                newPosition = new Position(this.playerPos.getX() + 1, this.playerPos.getY());
//                break;
//            case MOVE_LEFT:
//                newPosition = new Position(this.playerPos.getX() - 1, this.playerPos.getY());
//                break;
//            default:
//                return false;
//        }
//
//        if (canMoveTo(newPosition, world)) {
//            world[playerPos.getX()][playerPos.getY()] = previousTile;
//            world[newPosition.getX()][newPosition.getY()] = Tileset.PLAYER;
//            return true;
//        }
//        return false;
//    }
//
//    private boolean canMoveTo(Position pos, TETile[][] world) {
//        return world[pos.getX()][pos.getY()].character() != Tileset.WALL.character();
//    }
//}
//
