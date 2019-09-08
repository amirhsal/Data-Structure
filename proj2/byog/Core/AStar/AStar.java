package byog.Core.AStar;

import byog.Core.WorldComponents.Position;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Collections;

public class AStar {
    public static final int VERTICAL_HORIZONTAL_SCORE = 5;
    public static final int DIAGONAL_SCORE = 20;

    public static ArrayList<Node> findHallwayPath(Position entrance,
                                                  Position exit, TETile[][] world) {
        Node[][] worldNodes = new Node[world.length][world[0].length];
        ArrayList<Node> openSet = new ArrayList<Node>();
        ArrayList<Node> closedSet = new ArrayList<Node>();
        createWorldNodes(world, worldNodes, entrance, exit);

        //Get the exit node linked to the start
        Node exitNode = findPath(worldNodes, openSet, closedSet,
                entrance, exit, world);
        if (exitNode == null) {
            //Get the path from exit to start.
            return new ArrayList<Node>();
        }
        return exitNode.getPathToStart();

    }


    private static Node findPath(Node[][] worldNodes, ArrayList<Node> openSet,
                                 ArrayList<Node> closedSet,
                                 Position entrance, Position exit, TETile[][] world) {
        Node current = worldNodes[entrance.getX()][entrance.getY()];

        openSet.add(current);
        while (!openSet.isEmpty()) {
            Collections.sort(openSet, (node1, node2) -> node1.getFScore()
                    - node2.getFScore());
            current = openSet.remove(0);
            ArrayList<Node> neighbors = getNeighborNodes(worldNodes, current);
            for (Node node : neighbors) {
                if (closedSet.contains(node)) {
                    continue;
                }
                if (!openSet.contains(node)) {
                    node.update(current);
                    openSet.add(node);
                } else {
                    Node treeNode = openSet.get(openSet.indexOf(node));
                    if (treeNode.update(current)) {
                        openSet.remove(treeNode);
                        openSet.add(treeNode);
                    }
                }
                if (node.getPosition().equals(exit)) {
                    return node;
                }
            }
            closedSet.add(current);
        }
        return null;
    }

    private static ArrayList<Node> getNeighborNodes(Node[][] worldNodes,
                                                    Node theNode) {
        ArrayList<Node> theNodes = new ArrayList<Node>();

        for (int x = theNode.getPosition().getX() - 1;
             x <= theNode.getPosition().getX() + 1; x++) {
            for (int y = theNode.getPosition().getY() - 1;
                 y <= theNode.getPosition().getY() + 1; y++) {
                if (x >= worldNodes.length || y >= worldNodes[0].length
                        || x < 0 || y < 0) {
                    continue;
                }
                if (theNode.getPreviousNode() != null && !(x == theNode.getPosition().getX()
                        && y == theNode.getPosition().getY())
                        && !(theNode.getPreviousNode().getPosition().getX() == x
                        && theNode.getPreviousNode().getPosition().getY() == y)
                        && (theNode.getPosition().getX() == x
                        || theNode.getPosition().getY() == y)) {
                    if (worldNodes[x][y].isValidPath()) {
                        theNodes.add(worldNodes[x][y]);
                    }
                } else if (!(theNode.getPosition().getX() == x && y == theNode.getPosition().getY())
                        && (theNode.getPosition().getX() == x
                        || theNode.getPosition().getY() == y)) {
                    if (worldNodes[x][y].isValidPath()) {
                        theNodes.add(worldNodes[x][y]);
                    }
                }
            }
        }
        return theNodes;
    }

    private static void createWorldNodes(TETile[][] world, Node[][] worldNodes,
                                         Position entrance, Position exit) {
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[x].length; y++) {
                if (x == world.length - 1 || y == world[x].length - 1 || x == 0 || y == 0) {
                    if (x == entrance.getX() && y == entrance.getY()) {
                        worldNodes[x][y] = new Node(entrance, exit, true, true);
                    } else if (x == exit.getX() && y == exit.getY()) {
                        worldNodes[x][y] = new Node(new Position(x, y), exit, true, false);
                    } else {
                        worldNodes[x][y] = new Node(new Position(x, y), exit, true, false);
                    }
                } else if ((world[x][y].character() == Tileset.WALL.character()
                        || world[x][y].character() == Tileset.FLOOR.character())) {
                    if (x == entrance.getX() && y == entrance.getY()) {
                        worldNodes[x][y] = new Node(entrance, exit, true, true);
                    } else if (x == exit.getX() && y == exit.getY()) {
                        worldNodes[x][y] = new Node(new Position(x, y), exit, true, false);
                    } else {
                        worldNodes[x][y] = new Node(new Position(x, y), exit, false, false);
                    }
                } else {
                    if (x == entrance.getX() && y == entrance.getY()) {
                        worldNodes[x][y] = new Node(entrance, exit, true, true);
                    } else {
                        worldNodes[x][y] = new Node(new Position(x, y), exit, true, false);
                    }
                }
            }
        }

    }

}

