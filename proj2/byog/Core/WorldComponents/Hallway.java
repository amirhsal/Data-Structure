//package byog.Core.WorldComponents;
//
//import byog.Core.AStar.Node;
//import byog.TileEngine.TETile;
//import byog.TileEngine.Tileset;
//
//import java.util.ArrayList;
//
//
//public class Hallway {
//
//    public static final int MIN_SIZE = 3;
//    public static final int SOUTH_EAST = 0;
//    public static final int NORTH_EAST = 1;
//    public static final int NORTH_WEST = 2;
//    public static final int SOUTH_WEST = 3;
//
//    private ArrayList<Rectangle> hallwayRects;
//    private RandomRoom previousRoom;
//    private RandomRoom nextRoom;
//    private ArrayList<Node> path;
//    private Rectangle hallwayContainer;
//    private boolean created;
//    private ArrayList<Position> wallTiles;
//
//    public Hallway(RandomRoom previousRoom, RandomRoom nextRoom) {
//        this.previousRoom = previousRoom;
//        this.nextRoom = nextRoom;
//        this.wallTiles = new ArrayList<Position>();
//        this.hallwayRects = new ArrayList<Rectangle>();
//        created = false;
//    }
//
////    public void createHallway(TETile[][] world) {
////        previousRoom.createExit(world);
////        nextRoom.createEntrance(world);
////        ArrayList<Node> path = AStar.findHallwayPath(previousRoom.getExit(),
////                nextRoom.getEntrance(), world);
////        this.path = path;
////        this.created = !this.path.isEmpty();
////    }
//
//    public void drawPath(TETile[][] world) {
//        for (Node node : path) {
//            world[node.getPosition().getX()][node.getPosition().getY()] = Tileset.FLOOR;
//        }
//    }
//
//    private void createHallwayRects(TETile[][] world) {
//        Node previousNode = null;
//        Rectangle rect = null;
//        boolean horizontalPart = isHorizontalPassage(path.get(0).getPosition(), world);
//        int rectLength = 0;
//
//        for (Node currentNode : path) {
//            //Check if the current node is the startNode
//            if (currentNode.getPosition().equals(this.nextRoom.getEntrance())) {
//                previousNode = currentNode;
//                rectLength = 1;
//                if (horizontalPart) {
//                    rect = new Rectangle(currentNode.getPosition().getX(),
//                            currentNode.getPosition().getY() - 1);
//                    rect.setHeight(MIN_SIZE);
//                } else {
//                    rect = new Rectangle(currentNode.getPosition().getX() - 1,
//                            currentNode.getPosition().getY());
//                    rect.setWidth(MIN_SIZE);
//                }
//                continue;
//            }
//            int previousX = previousNode.getPosition().getX();
//            int previousY = previousNode.getPosition().getY();
//            int currentX = currentNode.getPosition().getX();
//            int currentY = currentNode.getPosition().getY();
//            if (horizontalPart) {
//                if (isHorizontalMove(previousNode.getPosition(), currentNode.getPosition())) {
//                    if (previousX == currentX - 1) {
//                        rectLength++;
//                    } else {
//                        rect.setX(currentX);
//                        rectLength++;
//                    }
//                } else {
//                    //Turning Node.
//                    rect.setWidth(rectLength);
//                    hallwayRects.add(rect);
//                    rectLength = 3;
//                    if (previousY == currentY - 1) {
//                        rect = new Rectangle(previousX - 1, previousY - 1);
//                    } else {
//                        rect = new Rectangle(currentX - 1, currentY);
//                    }
//                    rect.setWidth(MIN_SIZE);
//                    horizontalPart = false;
//                }
//            } else {
//                if (!isHorizontalMove(previousNode.getPosition(), currentNode.getPosition())) {
//                    if (previousY - 1 == currentY) {
//                        rect.setY(currentY);
//                        rectLength++;
//                    } else {
//                        rectLength++;
//                    }
//                } else {
//                    //Turning Node
//                    rect.setHeight(rectLength);
//                    hallwayRects.add(rect);
//                    rectLength = 3;
//                    if (previousX == currentX - 1) {
//                        rect = new Rectangle(previousX - 1, previousY - 1);
//                    } else {
//                        rect = new Rectangle(currentX, currentY - 1);
//                    }
//                    rect.setHeight(MIN_SIZE);
//                    horizontalPart = true;
//                }
//            }
//            previousNode = currentNode;
//        }
//        if (horizontalPart) {
//            rect.setWidth(rectLength);
//        } else {
//            rect.setHeight(rectLength);
//        }
//        hallwayRects.add(rect);
//
//    }
//
//    public ArrayList<Node> getPath() {
//        return this.path;
//    }
//
////    public boolean drawHallway(TETile[][] world) {
////        if (!created) {
////            return false;
////        }
////        createHallwayRects(world);
////        setDrawingProperties();
////        for (int x = hallwayContainer.getX(); x < hallwayContainer.getX()
////                + hallwayContainer.getWidth(); x++) {
////            for (int y = hallwayContainer.getY(); y < hallwayContainer.getY()
////                    + hallwayContainer.getHeight(); y++) {
////                if (isHallwayTile(x, y)) {
////                    if (isHallwayWallTile(x, y, world)) {
////                        world[x][y] = Tileset.WALL;
////                        wallTiles.add(new Position(x, y));
////                    } else {
////                        world[x][y] = Tileset.FLOOR;
////                    }
////                }
////            }
////        }
////        Position entr = previousRoom.getExit();
////        world[entr.getX()][entr.getY()] = Tileset.FLOOR;
////        entr = nextRoom.getEntrance();
////        world[entr.getX()][entr.getY()] = Tileset.FLOOR;
////        return true;
////    }
//
////    public boolean isHallwayTile(int x, int y) {
////        for (Rectangle rect : hallwayRects) {
////            if (rect.contains(x, y)) {
////                return true;
////            }
////        }
////        return false;
////    }
//
//    private boolean isHallwayTile(Position tile) {
//        for (Rectangle rect : hallwayRects) {
//            if (rect.contains(tile)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean isHallwayWallTile(int x, int y, TETile[][] world) {
//        for (int neighborX = x - 1; neighborX <= x + 1; neighborX++) {
//            for (int neighborY = y - 1; neighborY <= y + 1; neighborY++) {
//                if (neighborX < 0 || neighborY < 0
//                        || neighborX >= world.length || neighborY >= world[0].length) {
//                    return true;
//                }
////                if (!isHallwayTile(neighborX, neighborY)) {
////                    return true;
////                }
//            }
//        }
//        return false;
//    }
//
//    private void setDrawingProperties() {
//        int minX = Integer.MAX_VALUE;
//        int maxX = -1;
//        int minY = Integer.MAX_VALUE;
//        int maxY = -1;
//        for (Rectangle rect : hallwayRects) {
//            Position[] corners = rect.getCorners();
//            for (Position corner : corners) {
//                if (minX > corner.getX()) {
//                    minX = corner.getX();
//                }
//
//                if (minY > corner.getY()) {
//                    minY = corner.getY();
//                }
//
//                if (maxX < corner.getX()) {
//                    maxX = corner.getX();
//                }
//
//                if (maxY < corner.getY()) {
//                    maxY = corner.getY();
//                }
//            }
//        }
//        hallwayContainer = new Rectangle(minX, minY, maxX - minX + 1,
//                maxY - minY + 1);
//    }
//
//    private boolean isHorizontalMove(Position start, Position next) {
//        if (start.getY() == next.getY()) {
//            return true;
//        }
//        return false;
//    }
//
//    public ArrayList<Position> getHallwayWallTiles(TETile[][] world) {
//        return wallTiles;
//    }
//
//    public boolean isHorizontalPassage(Position pos, TETile[][] world) {
//        if (world[pos.getX() - 1][pos.getY()].character()
//                == Tileset.WALL.character()
//                && world[pos.getX() + 1][pos.getY()].character()
//                == Tileset.WALL.character()) {
//            return false;
//        }
//        return true;
//    }
//}
//
