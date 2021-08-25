package ca.sfu.cmpt213.a2.model;

import java.util.ArrayList;

public class GameModel {
    private final ArrayList<ArrayList<StaticObj>> staticObjMap;
    private ArrayList<ArrayList<Boolean>> exploredMap;
    private ArrayList<Guardian> guardians;
    private Relic relic;
    private Player player;
    private int height;
    private int width;
    private int score;
    private int goal;
    private boolean finished;
    private boolean cheat;

    public GameModel() {
        this.width = 20;
        this.height = 16;
        this.goal = 3;
        this.score = 0;
        this.finished = false;
        this.cheat = false;
        this.staticObjMap = MazeGenerator.MazeGen(height, width);
        ExploredMapInit();
        this.player = new Player(1, 1);
        this.guardians = new ArrayList<>();
        guardians.add(new Guardian(1, width-2));
        guardians.add(new Guardian(14, 1));
        guardians.add(new Guardian(height-2, width-2));
        this.relic = relicSpawn();
    }

    public ArrayList<ArrayList<String>> GameView(){
        ArrayList<ArrayList<String>> returnedView = new ArrayList<>();
        //static objects placed
        for(int i = 0; i < height; i++) {
            ArrayList<String> newRow = new ArrayList<>();
            for(int j = 0; j < width; j++){
                newRow.add(staticObjMap.get(i).get(j).getType());
            }
            returnedView.add(newRow);
        }
        // Unexplored areas hidden
        for(int i = 0; i < height; i++) {
            ArrayList<String> newRow = new ArrayList<>();
            for(int j = 0; j < width; j++){
                if(exploredMap.get(i).get(j)) {
                    newRow.add(returnedView.get(i).get(j));
                }
                else{
                   newRow.add("unexplored");
                }
            }
            returnedView.set(i, newRow);
        }
        // Dynamic Objects added
        if(player.isAlive()){
            returnedView.get(player.getMapPosition().getX()).set(player.getMapPosition().getY(), "player");
        }
        else {
            returnedView.get(player.getMapPosition().getX()).set(player.getMapPosition().getY(), "dead_player");
        }
        returnedView.get(relic.getMapPosition().getX()).set(relic.getMapPosition().getY(), "relic");
        returnedView.get(guardians.get(0).getMapPosition().getX()).set(guardians.get(0).getMapPosition().getY(), "guardian");
        returnedView.get(guardians.get(1).getMapPosition().getX()).set(guardians.get(1).getMapPosition().getY(), "guardian");
        returnedView.get(guardians.get(2).getMapPosition().getX()).set(guardians.get(2).getMapPosition().getY(), "guardian");
        return returnedView;
    }

    public ArrayList<String> StateCheck(){
        ArrayList<String> returnVariableList = new ArrayList<>();
        //Index 0
        if(cheat){
            returnVariableList.add("cheat_on");
        }
        else{
            returnVariableList.add("cheat_off");
        }

        //Index 1
        if(player.isAlive()){
            returnVariableList.add("alive");
        }
        else{
            returnVariableList.add("dead");
        }

        //Index 2
        returnVariableList.add(String.valueOf(score));

        //Index 3
        returnVariableList.add(String.valueOf(goal));

        //Index 4
        if(player.isAlive() && (score >= goal)){
            returnVariableList.add("win");
        }
        else if(player.isAlive() && (score < goal)){
            returnVariableList.add("ongoing");
        }
        else{
            returnVariableList.add("lose");
        }

        return returnVariableList;
    }

    public boolean NewStep(int input){
        boolean returnVal = InputFunction(input);
        if(returnVal){
            PlayerExplore();
            GameMechanics();
            NPCsMove();
            GameMechanics();
        }
        return returnVal;
    }
    public boolean isFinished() {
        return finished;
    }
    private boolean InputFunction(int input){
        boolean commandSuccess = false;
        switch (input){
            case 0:
                commandSuccess = PlayerMove(0);
                break;
            case 1:
                commandSuccess = PlayerMove(1);
                break;
            case 2:
                commandSuccess = PlayerMove(2);
                break;
            case 3:
                commandSuccess = PlayerMove(3);
                break;
            case 4:
                RevealAllMap();
                break;
            case 5:
                CheatMode();
                break;
            default:
                break;
        }
        return commandSuccess;
    }

    private Relic relicSpawn() {
        boolean available = false;
        int mazeX;
        int mazeY;
        do {
            mazeX = (int) (Math.random() * 14) + 1;
            mazeY = (int) (Math.random() * 18) + 1;
            if (staticObjMap.get(mazeX).get(mazeY).getType().equals("empty") && !(player.getMapPosition().getX() == mazeX && player.getMapPosition().getY() == mazeY)) {
                available = true;
            }
        } while (!available);
        return new Relic(mazeX, mazeY);
    }

    private void ExploredMapInit(){
        exploredMap = new ArrayList<>();
        for(int i = 0; i < height; i++) {
            ArrayList<Boolean> newRow = new ArrayList<>();
            for(int j = 0; j < width; j++){
                if(i == 0 || i == height-1){
                    newRow.add(true);
                }
                else if(j == 0 || j == width-1) {
                    newRow.add(true);
                }
                else if( (i == 1 && j == 1) ||
                        (i == 1 && j == 2) ||
                        (i == 2 && j == 1) ||
                        (i == 2 && j == 2) ){
                    newRow.add(true);
                }
                else {
                    newRow.add(false);
                }
            }
            exploredMap.add(newRow);
        }
    }

    private void CheatMode(){
        this.cheat = true;
        this.goal = 1;
    }

    private void RevealAllMap(){
        for(int i = 0; i < height; i++) {
            ArrayList<Boolean> newRow = new ArrayList<>();
            for(int j = 0; j < width; j++) {
                newRow.add(true);
            }
            exploredMap.set(i, newRow);
        }
        this.cheat = true;
    }

    private void PlayerExplore(){
        int[] playerXY = player.getMapPosition().getXY();
        exploredMap.get(playerXY[0]+1).set(playerXY[1]+1,true);
        exploredMap.get(playerXY[0]).set(playerXY[1]+1,true);
        exploredMap.get(playerXY[0]-1).set(playerXY[1]+1,true);
        exploredMap.get(playerXY[0]+1).set(playerXY[1],true);
        exploredMap.get(playerXY[0]).set(playerXY[1],true);
        exploredMap.get(playerXY[0]-1).set(playerXY[1],true);
        exploredMap.get(playerXY[0]+1).set(playerXY[1]-1,true);
        exploredMap.get(playerXY[0]).set(playerXY[1]-1,true);
        exploredMap.get(playerXY[0]-1).set(playerXY[1]-1,true);
    }


    private boolean PlayerMove(int direction){
        if(direction > 3 || direction < 0){
            return false;
        }
        else {
            int[] playerXY = player.getMapPosition().getXY();
            switch (direction){
                case 0:
                    if(staticObjMap.get(playerXY[0]-1).get((playerXY[1])).getType().equals("empty")){
                        player.moveObj(0);
                        return true;
                    }
                    else {
                        return false;
                    }
                case 1:
                    if(staticObjMap.get(playerXY[0]).get((playerXY[1]+1)).getType().equals("empty")){
                        player.moveObj(1);
                        return true;
                    }
                    else {
                        return false;
                    }
                case 2:
                    if(staticObjMap.get(playerXY[0]+1).get((playerXY[1])).getType().equals("empty")){
                        player.moveObj(2);
                        return true;
                    }
                    else {
                        return false;
                    }
                case 3:
                    if(staticObjMap.get(playerXY[0]).get((playerXY[1]-1)).getType().equals("empty")){
                        player.moveObj(3);
                        return true;
                    }
                    else {
                        return false;
                    }
                default:
                    return false;
            }
        }
    }

    private void NPCsMove(){
        for(Guardian guard : guardians) {
            int[] guardXY = guard.getMapPosition().getXY();
            int availableMoves = 0;
            boolean[] available = new boolean[4];
            for(int i = 0; i < 4; i++){
                switch (i){
                    case 0:
                        if(staticObjMap.get(guardXY[0]-1).get((guardXY[1])).getType().equals("empty")){
                            availableMoves++;
                            available[0] = true;
                        }
                        else{
                            available[0] = false;
                        }
                        break;
                    case 1:
                        if(staticObjMap.get(guardXY[0]).get((guardXY[1]+1)).getType().equals("empty")){
                            availableMoves++;
                            available[1] = true;
                        }
                        else{
                            available[1] = false;
                        }
                        break;
                    case 2:
                        if(staticObjMap.get(guardXY[0]+1).get((guardXY[1])).getType().equals("empty")){
                            availableMoves++;
                            available[2] = true;
                        }
                        else{
                            available[2] = false;
                        }
                        break;
                    case 3:
                        if(staticObjMap.get(guardXY[0]).get((guardXY[1]-1)).getType().equals("empty")){
                            availableMoves++;
                            available[3] = true;
                        }
                        else{
                            available[3] = false;
                        }
                        break;
                    default:
                        System.out.println("Loop for available moves for guardian is wrong.");
                        break;
                }
            }
            if((availableMoves == 1) && (guard.getPastMove() != -1)){
                guard.moveObj(guard.getPastMove());
            }
            else{
                boolean moveValid = false;
                int pastMove = guard.getPastMove();
                int move = 0;
                while(!moveValid) {
                     move = (int) (Math.random() * 4);
                    if((pastMove != move) && available[move]) {
                        moveValid = true;
                    }
                }
                guard.moveObj(move);
            }
        }
    }

    private void GameMechanics(){
        for(Guardian guard : guardians){
            if(guard.getMapPosition().equal(player.getMapPosition())){
                player.killPlayer();
                finished = true;
            }
        }
        if(player.isAlive()) {
            if(player.getMapPosition().equal(relic.getMapPosition())) {
                relic = relicSpawn();
                score++;
            }
            if(score >= goal) {
                finished = true;
            }
        }
    }


}