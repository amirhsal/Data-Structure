//package byog.Core.WorldComponents;
//
//class Rectangle {
//
//    private int x;
//    private int y;
//    private int width;
//    private int height;
//
//    public Rectangle(int x, int y, int width, int height) {
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//    }
//
//    public Rectangle(int x, int y) {
//        this.x = x;
//        this.y = y;
//        this.width = 0;
//        this.height = 0;
//    }
//
////    public boolean isEdge(int x, int y) {
////        boolean edge = this.x == x;
////        edge = edge || this.y == y;
////        edge = edge || y == this.y + height - 1;
////        edge = edge || x == this.x + width - 1;
////        return edge;
////    }
//
////    public boolean contains(int x, int y) {
////        return x >= this.x && x < this.x + width && y >= this.y
////                && y < this.y + height;
////    }
//
//    //
//    public boolean contains(Position pos) {
//        return this.x <= pos.getX() && this.x + width > pos.getX()
//                && this.y <= pos.getY() && this.y + height > pos.getY();
//    }
//
//    //
////    public boolean containsFullRect(Rectangle rect) {
////        return contains(rect.x, rect.y) && contains(rect.x + rect.width - 1, rect.y)
////                && contains(rect.x, rect.y + rect.height - 1)
////                && contains(rect.x + rect.width - 1, rect.y + rect.height - 1);
////
////    }
//
//    public Position[] getCorners() {
//        return new Position[]{new Position(x, y), new Position(x + width - 1, y),
//                new Position(x + width - 1, y + height - 1),
//                new Position(x, y + height - 1)};
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public void setX(int newX) {
//        this.x = newX;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int newY) {
//        this.y = newY;
//    }
//
//    public int getWidth() {
//        return width;
//    }
//
//    public void setWidth(int newWidth) {
//        width = newWidth;
//    }
//
//    public int getHeight() {
//        return height;
//    }
//
//    public void setHeight(int newHeight) {
//        height = newHeight;
//    }
//}
