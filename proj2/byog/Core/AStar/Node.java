package byog.Core.AStar;

import byog.Core.WorldComponents.Position;

import java.util.ArrayList;

/**
 * Implements the A* pathfinding algorithm for graphs
 *
 * @source <a href="https://en.wikipedia.org/wiki/A*
 * _search_algorithm#Description">A* Algorithm Wikipedia</a>
 */
public class Node {

    private Position nodePos;
    private Position goal;
    private Node previousNode;
    private boolean start;
    private boolean validPathNode;
    private int gScore;
    private int hScore;

    ////////////  Constructors
    public Node(Position pos, Position goal, boolean validPath) {
        this.nodePos = pos;
        this.goal = goal;
        gScore = Integer.MAX_VALUE;
        this.start = false;
        this.validPathNode = validPath;

    }

    public Node(Position pos, Position goal, boolean validPath, boolean start) {
        this.nodePos = pos;
        this.goal = goal;
        gScore = 0;
        hScore = 0;
        this.start = start;
        this.validPathNode = validPath;
    }

    private static int recurvCalcGScore(Node theNode) {
        if (theNode.previousNode == null) {
            return 0;
        }
        return theNode.nextNodeCost(theNode.previousNode.nodePos)
                + recurvCalcGScore(theNode.previousNode);
    }

    private static ArrayList<Node> getPathToStartRecurv(Node node) {
        ArrayList<Node> theList = new ArrayList<Node>();
        theList.add(node);
        if (node.start) {
            return theList;
        }
        ArrayList<Node> previousItems = getPathToStartRecurv(node.previousNode);
        theList.addAll(previousItems);
        return theList;
    }

    ///// GETTERS ///////////
    public Node getPreviousNode() {
        return previousNode;
    }

    public Position getPosition() {
        return new Position(this.nodePos.getX(), this.nodePos.getY());
    }

    public void setValidPathNode(boolean validPath) {
        this.validPathNode = validPath;
    }

    public int getFScore() {
        return gScore + hScore;
    }

//    public int getPathLengthToStart() {
//        return findPathLengthRecurs(this);
//    }

    public ArrayList<Node> getPathToStart() {
        return getPathToStartRecurv(this);
    }

    /////// Mutator Methods //////////////

    public boolean isValidPath() {
        return validPathNode;
    }

    //////// Private Helper Methods //////////////

    public int nextNodeCost(Position nextPos) {
        if (this.nodePos.getX() == nextPos.getX() || this.nodePos.getY() == nextPos.getY()) {
            return AStar.VERTICAL_HORIZONTAL_SCORE;
        } else {
            return AStar.DIAGONAL_SCORE;
        }
    }

    public boolean update(Node previous) {
        if (calculateGScore(previous)) {
            calculateHScore();
            return true;
        }
        return false;
    }

    private boolean calculateGScore(Node previous) {
        //If node passed is null set gScore to Max Integer value.
        if (previous == null) {
            gScore = Integer.MAX_VALUE;
            return true;
        }
        //If this node has no previous node then set the new previous node and calculate gScore.
        if (this.previousNode == null && !this.start) {
            this.previousNode = previous;
            gScore = recurvCalcGScore(this);
            return true;
        }
        //Else calculate the new gScore and set as gScore the min between the old and the new one.
        //Change this node's previous node accordingly.
        int newGScore = nextNodeCost(previous.nodePos) + recurvCalcGScore(previous);
        if (newGScore <= gScore) {
            this.previousNode = previous;
            gScore = newGScore;
            return true;
        }
        return false;

    }

    /**
     * @source <a href="https://youtu.be/mZfyt03LDH4">
     * Youtube link</a> used for the heuristics method
     */
    private void calculateHScore() {
        int dX = Math.abs(this.nodePos.getX() - this.goal.getX());
        int dY = Math.abs(this.nodePos.getY() - this.goal.getY());
        hScore = AStar.DIAGONAL_SCORE * Math.min(dX, dY)
                + AStar.VERTICAL_HORIZONTAL_SCORE * (Math.max(dX, dY)
                - Math.min(dX, dY));
    }

//    private int findPathLengthRecurs(Node node) {
//        if (start) {
//            return 0;
//        }
//        return findPathLengthRecurs(node.previousNode) + 1;
//    }

    //////// Overriden Methods //////////////////

//    @Override
//    public boolean equals(Object other) {
//        if (other == null) {
//            return false;
//        }
//        if (other.getClass() == this.getClass()) {
//            Node otherNode = (Node) other;
//            if (this.nodePos.equals(otherNode.nodePos)) {
//                return true;
//            }
//        }
//        return false;
//    }
}

