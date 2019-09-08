//package byog.Core.WorldComponents;
//
//import byog.Core.RandomUtils;
//import byog.TileEngine.TETile;
//import byog.TileEngine.Tileset;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class RandomRoom {
//
//    /**
//     * Room type Middle room: this room is in the middle of two rooms
//     * and has two hallway openings
//     */
//    //public static final int MIDDLE_ROOM = 0;
//    /**
//     * Room type First room, has one hallway opening
//     * and only next section
//     */
//    //public static final int FIRST_ROOM = 1;
//    /**
//     * Room type Last room, has one hallway opening and only a previous section
//     */
//    //public static final int LAST_ROOM = 2;
//
//    public static final int ROOM_MIN_WIDTH_HEIGHT = 5;
//    private Section roomSection;
//    private Rectangle roomContainer;
//    private Rectangle[] roomRects;
//    private Hallway entranceHall;
//    private Position entrance;
//    private Position exit;
//    private int triesToCreateExit;
//    private Random random;
//    private ArrayList<Position> wallTiles;
//    private ArrayList<Position> floorTiles;
//
//    ////////////////// Constructors ////////////////////////////
//
//    /**
//     * Constructs the random room object. Of type MIDDLE_ROOM,
//     * middle rooms have two hallway openings.
//     *
//     * @param xOff   The x coordinate offset of the
//     *               section where it should be drawn.
//     * @param yOff   The y coordinate offset of the section
//     *               where it should be drawn.
//     * @param width  The width of the section.
//     * @param height the height of the section.
//     * @param random The Random object of the world.
//     */
//    public RandomRoom(int xOff, int yOff, int width, int height, Random random) {
//        this.random = random;
//        this.roomSection = new Section(new Position(xOff, yOff), width, height);
//        this.triesToCreateExit = 0;
//        wallTiles = new ArrayList<Position>();
//        floorTiles = new ArrayList<Position>();
//        initializeRoom();
//    }
//
//    public RandomRoom(Section section, Random random) {
//        this.random = random;
//        this.roomSection = section;
//        this.triesToCreateExit = 0;
//        wallTiles = new ArrayList<Position>();
//        floorTiles = new ArrayList<Position>();
//        initializeRoom();
//    }
//
//    ////////////////// Getters ////////////////////////
//
//
//    /**
//     * Initializes The randomRoom
//     */
//    private void initializeRoom() {
//        int numberOfRects = RandomUtils.uniform(random, 1, 5);
//        createRects(numberOfRects);
//        setRoomDrawingProperties();
//    }
//
//    /**
//     * Initializes the variables of the containing rectangle
//     */
//    private void setRoomDrawingProperties() {
//        //Initialize min and max variables
//        int xMin = roomSection.getXOffset() + roomSection.getWidth();
//        int xMax = roomSection.getXOffset();
//        int yMin = roomSection.getYOffset() + roomSection.getHeight();
//        int yMax = roomSection.getYOffset();
//
//        for (int rect = 0; rect < roomRects.length; rect++) {
//            Position[] corners = roomRects[rect].getCorners();
//            for (int corner = 0; corner < corners.length; corner++) {
//                if (xMin > corners[corner].getX()) {
//                    xMin = corners[corner].getX();
//                }
//                if (xMax < corners[corner].getX()) {
//                    xMax = corners[corner].getX();
//                }
//                if (yMin > corners[corner].getY()) {
//                    yMin = corners[corner].getY();
//                }
//                if (yMax < corners[corner].getY()) {
//                    yMax = corners[corner].getY();
//                }
//            }
//        }
//        //Create room containing rectangle
//        roomContainer = new Rectangle(xMin, yMin, xMax - xMin + 1, yMax - yMin + 1);
//    }
//
//    private void createRects(int numberOfRects) {
//        roomRects = new Rectangle[numberOfRects];
//        for (int rect = 0; rect < roomRects.length; rect++) {
//            boolean doneRight = false;
//            do {
//                int xRoom = 0;
//                int yRoom = 0;
//
//                //Get random x coordinate for Rectangle
//                do {
//                    xRoom = RandomUtils.uniform(random, roomSection.getXOffset()
//                            + 2, roomSection.getXOffset() + roomSection.getWidth());
//                } while (ROOM_MIN_WIDTH_HEIGHT >= roomSection.getWidth()
//                        - (xRoom - roomSection.getXOffset()) - 1);
//                //Get random y coordinate for Rectangle
//                do {
//                    yRoom = RandomUtils.uniform(random, roomSection.getYOffset()
//                            + 2, roomSection.getYOffset() + roomSection.getHeight());
//                } while (ROOM_MIN_WIDTH_HEIGHT >= roomSection.getHeight()
//                        - (yRoom - roomSection.getYOffset()) - 1);
//                int width = 0;
//                int height = 0;
//                //Get random width for Rectangle
//                if (ROOM_MIN_WIDTH_HEIGHT == roomSection.getHeight()
//                        - (yRoom - roomSection.getYOffset()) - 1) {
//                    height = 3;
//                } else {
//                    height = RandomUtils.uniform(random, ROOM_MIN_WIDTH_HEIGHT,
//                            roomSection.getHeight()
//                                    - (yRoom - roomSection.getYOffset()) - 1);
//                }
//                //Get random Height for rectangle
//                if (ROOM_MIN_WIDTH_HEIGHT == roomSection.getWidth()
//                        - (xRoom - roomSection.getXOffset()) - 1) {
//                    width = 3;
//                } else {
//                    width = RandomUtils.uniform(random, ROOM_MIN_WIDTH_HEIGHT,
//                            roomSection.getWidth() - (xRoom - roomSection.getXOffset()) - 1);
//                }
//
//                //See if the generated rectangle makes any difference to the rooms shape
//                Rectangle roomRect = new Rectangle(xRoom, yRoom, width, height);
//                if (rect == 0) {
//                    roomRects[rect] = roomRect;
//                    doneRight = true;
//                } else {
//                    if (!containedByOther(roomRect, rect)
//                            && atLeastOneCornerContained(roomRect, rect)) {
//                        roomRects[rect] = roomRect;
//                        doneRight = true;
//                    }
//                }
//            } while (!doneRight);
//        }
//    }
//
//    private boolean containedByOther(Rectangle roomRect, int numberOfRectsDone) {
//        for (int rect = 0; rect < numberOfRectsDone; rect++) {
////            if (roomRects[rect].containsFullRect(roomRect)) {
////                return true;
////            }
//        }
//        return false;
//    }
//
//    private boolean atLeastOneCornerContained(Rectangle roomRect,
//                                              int numberOfRectsDone) {
//        Position[] corners = roomRect.getCorners();
//        for (int rect = 0; rect < numberOfRectsDone; rect++) {
//            for (int corner = 0; corner < corners.length; corner++) {
//                if (roomRects[rect].contains(corners[corner])) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public void drawRoom(TETile[][] world) {
//        for (int x = roomContainer.getX(); x < roomContainer.getX()
//                + roomContainer.getWidth(); x++) {
//            for (int y = roomContainer.getY(); y < roomContainer.getY()
//                    + roomContainer.getHeight(); y++) {
//                if (isRoomTile(x, y)) {
//                    if (isWallTile(x, y, world)) {
//                        world[x][y] = Tileset.WALL;
//                        wallTiles.add(new Position(x, y));
//                    } else {
//                        world[x][y] = Tileset.FLOOR;
//                        floorTiles.add(new Position(x, y));
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Determines if it is a room tile either wall or floor tile
//     *
//     * @param x The x coordinate of the tile
//     * @param y The y coordinate of the tile
//     * @return True if the tile is a room tile otherwise false.
//     */
////    public boolean isRoomTile(int x, int y) {
////        for (int rect = 0; rect < roomRects.length; rect++) {
////            if (roomRects[rect].contains(x, y)) {
////                return true;
////            }
////        }
////        return false;
////    }
//
//    public boolean isRoomTile(Position tile) {
//        for (int rect = 0; rect < roomRects.length; rect++) {
//            if (roomRects[rect].contains(tile)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Determines if a room tile is wall or floor tile. Must call first isRoomTile()
//     * to determine if it is a room tile
//     *
//     * @param x The x coordinate of the tile
//     * @param y The y coordinate of the tile
//     * @return True if the tile is Wall type, or false if it is Floor type
//     */
//    public boolean isWallTile(int x, int y, TETile[][] world) {
//        for (int neighborX = x - 1; neighborX <= x + 1; neighborX++) {
//            for (int neighborY = y - 1; neighborY <= y + 1; neighborY++) {
//                if (neighborX < 0 || neighborY < 0
//                        || neighborX >= world.length || neighborY >= world[0].length) {
//                    return true;
//                }
//                if (!isRoomTile(neighborX, neighborY)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public void createEntrance(TETile[][] world) {
//        boolean created = false;
//        int tile = 0;
//        while (!created) {
//            //Choose random wall tile for the Entrance
//            tile = RandomUtils.uniform(random, wallTiles.size());
//            //If the random entrance is valid stop the loop
//            if (isValidPassage(wallTiles.get(tile).getX(),
//                    wallTiles.get(tile).getY(), world)) {
//                entrance = new Position(wallTiles.get(tile).getX(),
//                        wallTiles.get(tile).getY());
//                created = true;
//            }
//        }
//    }
//
//    public void setEntranceHallway(Hallway newHallaway) {
//        this.entranceHall = newHallaway;
//    }
//
//    public void createExit(TETile[][] world) {
//        boolean created = false;
//        int tile = 0;
//        while (!created && !(this.triesToCreateExit >= 2)) {
//            //Create random coordinates for the exit
//            tile = RandomUtils.uniform(random, wallTiles.size());
//            //If the random exit is valid stop the loop
//            if (isValidPassage(wallTiles.get(tile).getX(),
//                    wallTiles.get(tile).getY(), world)) {
//                exit = new Position(wallTiles.get(tile).getX(),
//                        wallTiles.get(tile).getY());
//                created = true;
//            }
//        }
////        if (triesToCreateExit >= 2) {
////            this.triesToCreateExit++;
////            findAlternativeExit(world);
////        }
//        this.triesToCreateExit++;
//    }
//
//    public Position getPlayerStartPosition(TETile[][] world) {
//        return floorTiles.get(RandomUtils.uniform(random, floorTiles.size()));
//    }
//
//    private ArrayList<Position> getFloorTiles(TETile[][] world) {
//        return floorTiles;
//    }
//
////    private void findAlternativeExit(TETile[][] world) {
////        ArrayList<Position> wallTiles = entranceHall.getHallwayWallTiles(world);
////        boolean created = false;
////        while (!created) {
////            Position tile = wallTiles.get(RandomUtils.uniform(random, 0,
////                    wallTiles.size()));
////            if (isValidAlternativePassage(tile.getX(), tile.getY(), world)) {
////                exit = tile;
////                created = true;
////            }
////        }
////    }
//
//    private boolean isValidAlternativePassage(int passageX, int passageY, TETile[][] world) {
//        if (passageX > 1 && passageY > 1 && passageX < world.length - 2
//                && passageY < world[0].length - 2) {
//            if (entranceHall.isHallwayTile(passageX, passageY)) {
//                if (entranceHall.isHallwayWallTile
// (passageX, passageY, world)) {
//                    if ((world[passageX + 1][passageY]
// .character()
//                            != Tileset.WALL.character()
// && world[passageX - 1][passageY].character()
//                            != Tileset.WALL.character())
//                            || world[passageX][passageY + 1]
// .character()
//                            != Tileset.WALL.character() &&
// world[passageX][passageY - 1].character()
//                            != Tileset.WALL.character()) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean isValidPassage(int passageX, int passageY, TETile[][] world) {
//        //This ensures that no entrance or exit is created too close to the worlds edge
//        if (passageX > 1 && passageY > 1 && passageX < world.length - 2
//                && passageY < world[0].length - 2) {
//            if (isRoomTile(passageX, passageY)) {
//                if (isWallTile(passageX, passageY, world)) {
//                    //Check if the passage is walkable the player can't reach diagonal roots.
//                    if ((world[passageX - 1][passageY].character() != Tileset.WALL.character()
//                            && world[passageX + 1][passageY].character()
//                            != Tileset.WALL.character())
//                            || (world[passageX][passageY - 1].character()
//                            != Tileset.WALL.character()
//                            && world[passageX][passageY + 1].character()
//                            != Tileset.WALL.character())) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private ArrayList<Position> getWallTiles() {
//        return wallTiles;
//    }
//
//    public Position getEntrance() {
//        return entrance;
//    }
//
//    public Position getExit() {
//        return exit;
//    }
//    /*public static void main(String... args) {
//        TETile[][] world = new TETile[40][40];
//        for(int x = 0; x < world.length; x++) {
//            for(int y = 0; y < world[x].length; y++) {
//                world[x][y] = Tileset.NOTHING;
//            }
//        }
//        TERenderer ter = new TERenderer();
//        ter.initialize(40, 40);
//        Random gen = new Random(31235465456l);
//        RandomRoom room = new RandomRoom(2, 2, 15, 15, gen);
//        RandomRoom room2 = new RandomRoom(16, 16, 20, 20, gen);
//        room.drawRoom(world);
//        room2.drawRoom(world);
//        ter.renderFrame(world);
//        Hallway hall = new Hallway(room, room2);
//        hall.drawHallway(world);
//        ter.renderFrame(world);
//    }
//    */
//
//}
//
//
