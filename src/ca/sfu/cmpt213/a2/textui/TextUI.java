package ca.sfu.cmpt213.a2.textui;

import ca.sfu.cmpt213.a2.Main;
import java.util.ArrayList;

public class TextUI {
    public static void printStatus(ArrayList<String> GameStatus){
        String statusToPrint = "|  ";
        if(GameStatus.get(0).equals("cheat_on")){
            statusToPrint = statusToPrint + "Cheats : On  |  ";
        }
        if(GameStatus.get(0).equals("cheat_off")){
            statusToPrint = statusToPrint + "Cheats : Off  |  ";
        }
        if(GameStatus.get(1).equals("alive")){
            statusToPrint = statusToPrint + "Player : Alive  |  ";
        }
        if(GameStatus.get(1).equals("dead")){
            statusToPrint = statusToPrint + "Player : Dead  |  ";
        }

        statusToPrint = statusToPrint + "Score : "+ GameStatus.get(2) +"  |  ";
        statusToPrint = statusToPrint + "Goal : " + GameStatus.get(3) + "  |  ";
        if(GameStatus.get(4).equals("win")){
            statusToPrint = statusToPrint + "Game : Won  |  ";
        }
        if(GameStatus.get(4).equals("ongoing")){
            statusToPrint = statusToPrint + "Game : Ongoing  |  ";
        }
        if(GameStatus.get(4).equals("lose")){
            statusToPrint = statusToPrint + "Game : Lost  |  ";
        }

        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println(statusToPrint);
        System.out.println("-----------------------------------------------------------------------------------");
    }
    public static void printGame(ArrayList<ArrayList<String>> GameView) {
        for (int i = 0; i < 16; i++) {
            String newRow = "";
            for (int j = 0; j < 20; j++) {
                if (GameView.get(i).get(j).equals("empty")) {
                    newRow = newRow + "  " + " ";
                }
                if (GameView.get(i).get(j).equals("wall")) {
                    newRow = newRow + "  " + "#";
                }
                if (GameView.get(i).get(j).equals("unexplored")) {
                    newRow = newRow + "  " + ".";
                }
                if (GameView.get(i).get(j).equals("player")) {
                    newRow = newRow + "  " + "@";
                }
                if (GameView.get(i).get(j).equals("dead_player")) {
                    newRow = newRow + "  " + "X";
                }
                if (GameView.get(i).get(j).equals("guardian")) {
                    newRow = newRow + "  " + "!";
                }
                if (GameView.get(i).get(j).equals("relic")) {
                    newRow = newRow + "  " + "^";
                }

            }
            System.out.println(newRow);
        }
    }

    public static void printStart(){
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("DIRECTIONS");
        System.out.println("\t\tCollect 3 relics!");
        System.out.println("LEGEND");
        System.out.println("\t\t#: Wall");
        System.out.println("\t\t@: You (Treasure Hunter) (Alive)");
        System.out.println("\t\tX: You (Treasure Hunter) (Dead)");
        System.out.println("\t\t!: Guardian");
        System.out.println("\t\t^: Relic");
        System.out.println("\t\t.: Unexplored Space");
        System.out.println("INPUTS (Not case sensitive)");
        System.out.println("\t\t'W'(up), 'A'(left), 'S'(down), 'D'(right) for moving.");
        System.out.println("\t\t'?' for showing these instructions again.");
        System.out.println("\t\t'c' for reducing the win condition to 1 relic. Counts as cheat!");
        System.out.println("\t\t'm' for revealing all map.");
        System.out.println("\t\t'Q' for closing the game.");
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public static void InputHandler(){
        String input = "";
        boolean correctInput = false;
        while(!correctInput) {
            System.out.println("New Input : ");
            input = Main.scanner.nextLine().toLowerCase();
            switch (input) {
                case "w":
                    correctInput = Main.NewGame.NewStep(0);
                    break;
                case "a":
                    correctInput = Main.NewGame.NewStep(3);
                    break;
                case "s":
                    correctInput = Main.NewGame.NewStep(2);
                    break;
                case "d":
                    correctInput = Main.NewGame.NewStep(1);
                    break;
                case "m":
                    correctInput = !Main.NewGame.NewStep(4);
                    break;
                case "c":
                    correctInput = !Main.NewGame.NewStep(5);
                    break;
                case "?":
                    printStart();
                    correctInput = true;
                    break;
                case "q":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input! Valid inputs are [ w, a, s, d, m, c, ?].");
            }
            if(!correctInput){
                System.out.println("The way is blocked");
            }
        }
    }

    public static void printEndResult(ArrayList<String> GameStatus){
        if(GameStatus.get(4).equals("win")){
            if(GameStatus.get(0).equals("cheat_on")){
                System.out.println("--------------------------");
                System.out.println("\t\tCONGRATULATIONS!");
                System.out.println("\tYOU WON... WITH CHEATS...");
                System.out.println("--------------------------");
            }
            if(GameStatus.get(0).equals("cheat_off")){
                System.out.println("--------------------------");
                System.out.println("\tCONGRATULATIONS!");
                System.out.println("\t\tYOU WON!");
                System.out.println("--------------------------");
            }
        }
        if(GameStatus.get(4).equals("lose")){
            if(GameStatus.get(0).equals("cheat_on")){
                System.out.println("--------------------------");
                System.out.println("\t\tYOU LOST...");
                System.out.println("\tEVEN WITH CHEAT...");
                System.out.println("--------------------------");
            }
            if(GameStatus.get(0).equals("cheat_off")){
                System.out.println("--------------------------");
                System.out.println("\t\tYOU LOST!");
                System.out.println("\tPLEASE TRY AGAIN!");
                System.out.println("--------------------------");
            }
        }
    }

}
