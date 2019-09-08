package byog.Core;

<<<<<<< HEAD
<<<<<<< HEAD
/**
 * This is the main entry point for the program. This class simply parses
 * the command line inputs, and lets the byog.Core.Game class take over
 * in either keyboard or input string mode.
=======
=======
>>>>>>> 47829bd0dfbec7017b3565a2b455dd108d4de5dd
import byog.TileEngine.TETile;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
>>>>>>> cdcf17e4a0241573bae8ee2cd0e49ecbbf1f8040
 */
//public class Main {
//    public static void main(String[] args) {
//        if (args.length > 1) {
//            System.out.println("Can only have one argument - the input string");
//            System.exit(0);
//        } else if (args.length == 1) {
//            Game game = new Game();
//?
//            game.gameLoop(args[0]);
////            game.gameLoop();
//            System.out.println(game.toString());
//        } else {
//            Game game = new Game();
//            game.playWithKeyboard();
//            game.gameLoop();
//        }
//        System.out.println("Game has closed");
//        System.exit(0);
//    }
//}
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game game = new Game();
            TETile[][] worldState = game.playWithInputString(args[0]);
            System.out.println(TETile.toString(worldState));
        } else {
            Game game = new Game();
//            game.playWithKeyboard();
        }
    }
}

