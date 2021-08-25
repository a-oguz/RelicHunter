package ca.sfu.cmpt213.a2;

import ca.sfu.cmpt213.a2.model.GameModel;
import ca.sfu.cmpt213.a2.textui.TextUI;

import static ca.sfu.cmpt213.a2.textui.TextUI.*;
import java.util.Scanner;


public class Main {
    public static GameModel NewGame;
    public static Scanner scanner;
    public static void main (String[] args){
        NewGame = new GameModel();
        scanner = new Scanner(System.in);
        printStart();
        printStatus(NewGame.StateCheck());
        printGame(NewGame.GameView());
        while(!NewGame.isFinished()){
            TextUI.InputHandler();
            printStatus(NewGame.StateCheck());
            printGame(NewGame.GameView());
        }
        printEndResult(NewGame.StateCheck());
        scanner.close();
    }


}
