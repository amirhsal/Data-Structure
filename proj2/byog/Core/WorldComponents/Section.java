//package byog.Core.WorldComponents;
//
//import byog.Core.RandomUtils;
//import byog.TileEngine.TETile;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class Section {
//
//    public static final int MAX_SLICE_SIZE = 20;
//    public static final int MIN_SLICE_SIZE = 10;
//
//    private final Position offset;
//    private int width;
//    private int height;
//    private RandomRoom room;
//    private Section previous;
//    private Section next;
//
//    /**
//     * Constructs a Section of the TETile world at the
//     * offset and with width and height
//     *
//     * @param offset The offset position in the TETile world
//     * @param width  The width of the section
//     * @param height The height of the section
//     */
//    public Section(Position offset, int width, int height) {
//        //Ensuring that the Position object won't
//        // change outside of the Section object
//        this.offset = new Position(offset.getX(), offset.getY());
//        this.width = width;
//        this.height = height;
//        this.previous = null;
//        this.next = null;
//
//    }
//
//    public Section(int xOffset, int yOffset, int width, int height) {
//        this.offset = new Position(xOffset, yOffset);
//        this.width = width;
//        this.height = height;
//    }
//
//    /**
//     * Cuts a given 2D TETile array in sections
//     *
//     * @param random The random object to randomize the process
//     * @param world  The 2D TETile array
//     * @return A Collection of Section object's representing
//     * the 2D array cut to slices horizontal and veritcal.
//     */
//    public static ArrayList<Section> slice(Random random, TETile[][] world) {
//        ArrayList<Section> theSections = new ArrayList<Section>();
//        //Ensure that hallways have better chances to find a free path
//        //and some of the background still visible
//        int width = world.length - 10;
//        int height = world[0].length - 10;
//        int xStart = 4;
//        int yStart = 4;
//        //Get the number of max vertical slices
//        int minSlices = width / MAX_SLICE_SIZE;
//        int maxSlices = width / MIN_SLICE_SIZE;
//        int numOfSlices = 4;
//        int sliceWidth = width / numOfSlices;
//        int widthRest = width % numOfSlices;
//        for (int slice = 0; slice < numOfSlices; slice++) {
//            ArrayList<Section> subSlices = new ArrayList<Section>();
//            //With 60% chance slice the vertical slice one time horizontaly
//            if (RandomUtils.bernoulli(random, 0.6)) {
//                boolean sliceOK = false;
//                int lowSliceHeight = 0;
//                while (!sliceOK) {
//                    lowSliceHeight = RandomUtils.uniform(random, 0, height);
//                    if (lowSliceHeight > MIN_SLICE_SIZE
//                            && height - lowSliceHeight > MIN_SLICE_SIZE) {
//                        sliceOK = true;
//                    }
//                }
//                //Add the slices to the subSlices collection
//                subSlices.add(new Section(new Position(xStart, yStart),
//                        sliceWidth, lowSliceHeight));
//                subSlices.add(new Section(new Position(xStart, yStart
//                        + lowSliceHeight), sliceWidth, height - lowSliceHeight));
//            } else {
//                subSlices.add(new Section(new Position(xStart, yStart), sliceWidth, height));
//            }
//
//            //With 40% chance give the slice a little bit of extra width
//            if (widthRest != 0 && RandomUtils.bernoulli(random, 0.4)) {
//                //Determine how much of extra width the slice will get
//                int extraWidth = RandomUtils.uniform(random, 0, widthRest);
//                widthRest -= extraWidth; // Subtract the extra width from remaining width
//                //Give change the width of each slice and add it to the returned slices
//                for (Section sec : subSlices) {
//                    sec.setWidth(sliceWidth + extraWidth);
//                    theSections.add(sec);
//                }
//                xStart += sliceWidth + extraWidth;
//            } else {
//                //Add the slices to the returned collection
//                for (Section sec : subSlices) {
//                    theSections.add(sec);
//                }
//                xStart += sliceWidth;
//            }
//
//        }
//        return theSections;
//    }
//
//    public Position getOffset() {
//        return offset;
//    }
//
//    public int getWidth() {
//        return width;
//    }
//
//    public void setWidth(int newWidth) {
//        this.width = newWidth;
//    }
//
//    public int getHeight() {
//        return height;
//    }
//
//    public void setHeight(int newHeigth) {
//        this.height = newHeigth;
//    }
//
//    public int getXOffset() {
//        return offset.getX();
//    }
//
//    public int getYOffset() {
//        return offset.getY();
//    }
//
//    public Section getPrevious() {
//        return previous;
//    }
//
//    public void setPrevious(Section newPrevious) {
//        previous = newPrevious;
//    }
//
//    public Section getNext() {
//        return next;
//    }
//
//    public void setNext(Section newNext) {
//        next = newNext;
//    }
//
//    public Position getRoomEntrance() {
//        return room.getEntrance();
//    }
//
//    public Position getRoomExit() {
//        return room.getExit();
//    }
//
//    public RandomRoom getRoom() {
//        return room;
//    }
//
//    public void setRoom(RandomRoom newRoom) {
//        this.room = newRoom;
//    }
//
//    public void createRoomEntrance(TETile[][] world) {
//        if (room.getEntrance() != null) {
//            return;
//        }
//        room.createEntrance(world);
//    }
//
//    public void createRoomExit(TETile[][] world) {
//        if (room.getExit() != null) {
//            return;
//        }
//        room.createExit(world);
//    }
//}
//
