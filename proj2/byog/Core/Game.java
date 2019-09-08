package byog.Core;

//import byog.Core.WorldComponents.Player;
//import byog.Core.WorldComponents.Section;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.File;
import java.io.Serializable;
import java.util.Random;

//import java.util.ArrayList;

//import java.awt.*;

public class Game implements Serializable {

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    public static final int TITLE_FONT_SIZE = 32;
    public static final int MENU_FONT_SIZE = 23;
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final File LOAD_FILE = new File("save.txt");
    //    private ArrayList<Section> sections;
//    private Player player;
    private String loadString;
    private String history;


//    public Game() {
//        this.sections = new ArrayList<Section>();
//    }


    ////////////// 1. Game Controling Methods ////////////////////
    //In this section are methods that control the game. As control is meant invoke
    //save operations, Main menu, no behavioral control in this section.

    /**
     * Picks a RANDOM tile with a 33% change of being
     * a wall, 33% chance of being a flower, and 33%
     * chance of being empty space.
     */
    private static TETile randomTile(Random random) {
        int tileNum = random.nextInt(3);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.NOTHING;
            default:
                return Tileset.NOTHING;
        }
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
//    public void playWithKeyboard() {
//        initialize();
//        runMainMenu();
//        gameLoop();
//
//    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = null;

        switch (input.charAt(0)) {
            case 'N':
            case 'n':
                finalWorldFrame = newGame(input.substring(1, input.length()));
                break;
            case 'L':
            case 'l':
                if (input.length() > 1) {
                    finalWorldFrame = newGame(input.substring(1));
                } else if (LOAD_FILE.exists()) {
                    finalWorldFrame = new TETile[WIDTH][HEIGHT];
//                    loadGame(null, finalWorldFrame);
                } else {
                    System.exit(0);
                }

                break;
            case 'Q':
            case 'q':
                System.exit(0);
                break;
            default:
                break;
        }

        return finalWorldFrame;
    }

//    private void initialize() {
//        StdDraw.setCanvasSize();
//        StdDraw.setFont(new Font("Monaco", Font.BOLD, TITLE_FONT_SIZE));
//        StdDraw.setXscale(0, 512);
//        StdDraw.setYscale(0, 512);
//    }

//    private void runMainMenu() {
////        drawMainMenu();
//        boolean exit = false;
//        do {
////            if (StdDraw.hasNextKeyTyped()) {
////                switch (StdDraw.nextKeyTyped()) {
////                    case 'Q':
////                    case 'q':
////                        System.exit(0);
////                        break;
////                    case 'N':
////                    case 'n':
////                        showSeedInputScreen();
////                        exit = true;
////                        break;
////                    case 'L':
////                    case 'l':
////                        if (!LOAD_FILE.exists()) {
////                            System.exit(0);
////
////                        }
//
//
//                        TETile[][] world = new TETile[WIDTH][HEIGHT];
//                        TERenderer ter = new TERenderer();
//                        ter.initialize(WIDTH, HEIGHT);
//                        loadGame(null, world);
//                        gameLoop(this.loadString);
//                        ter.renderFrame(world);
//                        StdDraw.show();
//                        exit = true;
//                        break;
//
//                }
//
//            }
//        } while (!exit);
//
//    }


    //////////////// 2. World Generation ///////////////////////
    //////////////// 2.1. Initialization ///////////////

    private void showSeedInputScreen() {
        boolean stop = false;
        boolean firstRun = true;
        String seedInput = "";
        StdDraw.text(512 / 2, 512 / 2 - 150, "Please input a seed:");
        StdDraw.show();
        while (!stop) {
            if (StdDraw.hasNextKeyTyped()) {
                char nextKey = StdDraw.nextKeyTyped();
                if (nextKey == 'S' || nextKey == 's') {
                    stop = true;
                }
                if (Character.isAlphabetic(nextKey)) {
                    nextKey = Character.toUpperCase(nextKey);
                }
                if (Character.isDigit(nextKey) || nextKey == 'S' || nextKey == 's') {
                    seedInput += Character.toString(nextKey);
                }
                if (firstRun) {
                    firstRun = false;
                } else {
//                    drawMainMenu();
                    StdDraw.text(512 / 2, 512 / 2 - 150, "Please input a seed:");
                }
                StdDraw.text(512 / 2, 512 / 2 - 175, seedInput);
                StdDraw.show();
                if (!Character.isDigit(nextKey) && !(nextKey == 'S' || nextKey == 's')) {
                    StdDraw.text(512 / 2, 512 / 2 - 75,
                            "Only Digits! And S or s to start!");
                }
            }
        }
        this.history = "L" + seedInput;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(newGame(seedInput));
        StdDraw.show();

    }

    private TETile[][] newGame(String inputString) {
        int end = Math.min(inputString.indexOf("s"), inputString.indexOf('S'));
        if (end == -1) {
            end = Math.max(inputString.indexOf("s"), inputString.indexOf('S'));
            if (end == -1) {
                System.out.println("Game String must be of type:"
                        + " \"^[NnLl][\\d]+[Ss].*\"");
                System.out.println("Explanation of regular expression: "
                        + "The string mus begin with N,n,L or l then one");
                System.out.println("or more digits and then s or S after "
                        + "that any character none or more times!");
                System.exit(0);
            }
        }
        long seed = 0;
        try {
            seed = Long.parseLong(inputString.substring(0, end));
        } catch (NumberFormatException e) {
            System.out.println("Seed must be of type long or int and must end with 'S': "
                    + inputString);
            System.exit(0);
        }
        this.history = "L" + inputString.substring(0, end + 1);
        if (end == inputString.length() - 1) {
            return generateWorld(new Random(seed));
        } else {
            inputString = inputString.substring(end + 1);
            return generateWorld(new Random(seed));
        }

    }

    ///////////// 2.2 Generation //////////////////////

    public void newGame(String inputString, TETile[][] world) {
        int end = Math.min(inputString.indexOf("s"), inputString.indexOf('S'));
        if (end == -1) {
            end = Math.max(inputString.indexOf("s"), inputString.indexOf('S'));
            if (end == -1) {
                System.out.println("Game String must be of type:"
                        + " \"^[NnLl][\\d]+[Ss].*\"");
                System.out.println("Explanation of regular expression: "
                        + "The string mus begin with N,n,L or l then one");
                System.out.println("or more digits and then s or S after "
                        + "that any character none or more times!");
                System.exit(0);
            }
        }
        long seed = 0;
        try {
            seed = Long.parseLong(inputString.substring(0, end));
        } catch (NumberFormatException e) {
            System.out.println("Seed must be of type long or int and must end with 'S': "
                    + inputString);
            System.exit(0);
        }
        this.history = "L" + inputString.substring(0, end + 1);
        if (end == inputString.length() - 1) {
            world = generateWorld(new Random(seed), world);
        } else {
            //Do extra moves - moves not yet implemented
            world = generateWorld(new Random(seed), world);
        }
    }

    private TETile[][] generateWorld(Random random) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];


        /** Picks a RANDOM tile with a 33% change of being
         *  a wall, 33% chance of being a flower, and 33%
         *  chance of being empty space.
         */

        int height = world[0].length;
        int width = world.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = randomTile(random);
            }
        }
        return world;
    }


//    	for(int x = 0; x < WIDTH; x++) {
//    		for(int y = 0; y < HEIGHT; y++) {
//    			world[x][y] = Tileset.WATER;
//    		}

//
//    	sections = Section.slice(random, world);
//    	Section previousSection = null;
//    	for(Section currentSection: sections) {
//    		currentSection.setRoom(new RandomRoom(currentSection, random));
//    		currentSection.getRoom().drawRoom(world);
//    	}
//    	for(Section currentSection: sections) {
//    		if(previousSection == null) {
//    			previousSection = currentSection;
//    			continue;
//    		}
//    		Hallway hall = new Hallway(previousSection.getRoom(),
//          currentSection.getRoom());
//    		currentSection.getRoom().setEntranceHallway(hall);
//    		while(!hall.drawHallway(world)) {
//    			hall.createHallway(world);
//    		}
//    		previousSection = currentSection;
//    	}
//    	Position playerPos = sections.get(0).getRoom().getPlayerStartPosition(world);
//    	//createBeacj(world);
//    	this.player = new Player(playerPos, "player1", world);

    private TETile[][] generateWorld(Random random, TETile[][] world) {

		/* for(int x = 0; x < WIDTH; x++) {
    		for(int y = 0; y < HEIGHT; y++) {
    			world[x][y] = Tileset.WATER;
    		}
    	}*/
        int height = world[0].length;
        int width = world.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = randomTile(random);
            }
        }
        return world;
    }

    /**
     * Picks a RANDOM tile with a 33% change of being
     * a wall, 33% chance of being a flower, and 33%
     * chance of being empty space.
     */
		/*private static TETile randomTile(Random random) {
			int tileNum = random.nextInt(3);
			switch (tileNum) {
				case 0: return Tileset.WALL;
				case 1: return Tileset.FLOWER;
				case 2: return Tileset.NOTHING;
				default: return Tileset.NOTHING;
			}
		}

    	sections = Section.slice(random, world);
    	Section previousSection = null;
    	for(Section currentSection: sections) {
    		currentSection.setRoom(new RandomRoom(currentSection, random));
    		currentSection.getRoom().drawRoom(world);
    	}
    	for(Section currentSection: sections) {
    		if(previousSection == null) {
    			previousSection = currentSection;
    			continue;
    		}
    		Hallway hall = new Hallway(previousSection.getRoom(), currentSection.getRoom());
    		currentSection.getRoom().setEntranceHallway(hall);
    		while(!hall.drawHallway(world)) {
    			hall.createHallway(world);
    		}
    		previousSection = currentSection;
    	}
    	Position playerPos = sections.get(0).getRoom().getPlayerStartPosition(world);
    	this.player = new Player(playerPos, "Player1", world);
    createBeach(world);


    private void createBeach(TETile[][] world) {
        for(int x = 0; x < world.length; x++) {
            for(int y = 0; y < world[x].length; y++) {
                char tile = world[x][y].character();
                char water = Tileset.WATER.character();
                if(tile == water) {
                    if(hasWallAsNeighbor(x,y,world)) {
                        world[x][y] = Tileset.SAND;
                    }
                }
            }
        }
    }
    */
    //////////// 2.2.1 Generation Helpers ///////////////////
    private boolean hasWallAsNeighbor(int x, int y, TETile[][] world) {
        for (int xWorld = x - 1; xWorld <= x + 1; xWorld++) {
            for (int yWorld = y - 1; yWorld <= y + 1; yWorld++) {
                if (!(xWorld == x && yWorld == y) && !(xWorld >= world.length
                        || xWorld < 0 || yWorld >= world[0].length || yWorld < 0)) {
                    char tile = world[xWorld][yWorld].character();
                    char wall = Tileset.WALL.character();
                    if (tile == wall) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

   /* //////////// 3. Drawing Methods //////////////////////////

    private void drawMainMenu() {
        StdDraw.clear(Color.BLACK);
        int textY = 512 / 2 + 100;
        int textX = 512 / 2;
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(textX, textY, "CS61B: \tThe Game");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, MENU_FONT_SIZE));
        textY -= 100;
        StdDraw.text(textX, textY, "(N)New Game");
        textY -= 50;
        StdDraw.text(textX, textY, "(L)Load Game");
        textY -= 50;
        StdDraw.text(textX, textY, "(Q)Quit Game");
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    ////////////// 4. Game Behavior Methods ////////////////////

    public void gameLoop(String input) {
        boolean exit = false;
        if (input == null || input.equals("")){
            exit = true;}
        for (int charIndex = input.toUpperCase().indexOf('S') + 1; charIndex < input.length()
                && !exit; charIndex++) {
            char nextKey = input.charAt(charIndex);
            if (nextKey == 'A' || nextKey == 'a') {
                //Move player left
                this.history += Character.toString(nextKey);
                System.out.println("Move player left");
            } else if (nextKey == 'S' || nextKey == 's') {
                //Move player down
                this.history += Character.toString(nextKey);
                System.out.println("Move player down");
            } else if (nextKey == 'D' || nextKey == 'd') {
                //Move player right
                this.history += Character.toString(nextKey);
                System.out.println("Move player right");
            } else if (nextKey == 'W' || nextKey == 'w') {
                //Move player up
                this.history += Character.toString(nextKey);
                System.out.println("Move player up");
            } else if (nextKey == ':') {
                boolean stop = false;
                while (!stop) {
                    if (input.charAt(charIndex + 1) == 'Q'
                            || input.charAt(charIndex + 1) == 'q') {
                        writeObject(null);
                        stop = true;
                        System.exit(0);
                    }
                }
            } else if (nextKey == 'q' || nextKey == 'Q') {
                System.exit(0);
            }
        }

    }

    public void gameLoop() {
        boolean exit = false;
        while (!exit) {
            if (StdDraw.hasNextKeyTyped()) {
                char nextKey = StdDraw.nextKeyTyped();
                if (nextKey == 'A' || nextKey == 'a') {
                    //Move player left
                    this.history += Character.toString(nextKey);
                    System.out.println("Move player left");
                } else if (nextKey == 'S' || nextKey == 's') {
                    //Move player down
                    this.history += Character.toString(nextKey);
                    System.out.println("Move player down");
                } else if (nextKey == 'D' || nextKey == 'd') {
                    //Move player right
                    this.history += Character.toString(nextKey);
                    System.out.println("Move player right");
                } else if (nextKey == 'W' || nextKey == 'w') {
                    //Move player up
                    this.history += Character.toString(nextKey);
                    System.out.println("Move player up");
                } else if (nextKey == ':') {
                    boolean stop = false;
                    while (!stop) {
                        if (StdDraw.hasNextKeyTyped()) {
                            nextKey = StdDraw.nextKeyTyped();
                            if (nextKey == 'Q' || nextKey == 'q') {
                                writeObject(null);
                                stop = true;
                                System.exit(0);
                            }
                        }
                    }
                } else if (nextKey == 'q' || nextKey == 'Q') {
                    System.exit(0);
                }
            }
        }

    }


    //////////////// 5. Load Methods ////////////////////


    private void loadGame(String loadString, TETile[][] world) {
        if (loadString == null) {
            readObject(null);
            newGame(this.loadString.substring(1, this.loadString.length()), world);
        } else {
            newGame(loadString, world);
        }

    }

    public void readObject(ObjectInputStream in) {
        Scanner reader = null;
        if (in == null) {
            try {
                reader = new Scanner(LOAD_FILE);
            } catch (FileNotFoundException ex) {
                System.exit(0);
            }
        } else {
            reader = new Scanner(in);
        }
        if (reader.hasNextLine()) {
            this.loadString = reader.nextLine();
        } else {
            this.playWithKeyboard();
        }
        reader.close();
    }

    /////////////////// 6. Save Methods ///////////////////////

    public void writeObject(ObjectOutputStream fileStream) {
        PrintWriter out = null;
        if (fileStream == null) {

            try {
                out = new PrintWriter(new FileOutputStream(LOAD_FILE.getName()));
            } catch (FileNotFoundException ex) {
                System.out.println("File does not exists: ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out = new PrintWriter(fileStream);
        }
        out.println(this.history);
        out.close();
    }*/


/////////////////////// 7. Rest Or Under Construction /////////////////////////

    public void readObjectNoData() {

    }

}

