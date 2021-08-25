package ca.sfu.cmpt213.a2.model;

import java.util.ArrayList;

public class MazeGenerator {

    public static ArrayList<ArrayList<StaticObj>> MazeGen(int height, int width){
        ArrayList<ArrayList<StaticObj>> newMaze = new ArrayList<>();

        for(int i = 0; i < height; i++){
            ArrayList<StaticObj> newRow = new ArrayList<>();
            for(int j = 0; j< width; j++){
                newRow.add(new Wall());
            }
            newMaze.add(newRow);
        }

        ArrayList<ArrayList<StaticObj>> generatedInterior = MazeInteriorGenerator(height-2,width-2);
        for(int i = 0; i < height-2; i++){
            ArrayList<StaticObj> newRow = newMaze.get(i+1);
            for(int j = 0; j< width-2; j++){
                newRow.set(j+1, generatedInterior.get(i).get(j));
            }
            newMaze.set(i+1,newRow);
        }
        //System.out.println("checking");
        boolean integrity = MazeIntegrityCheck(newMaze);
        while(!integrity){
            generatedInterior = MazeInteriorGenerator(height-2,width-2);
            for(int i = 0; i < height-2; i++){
                ArrayList<StaticObj> newRow = newMaze.get(i+1);
                for(int j = 0; j< width-2; j++){
                    newRow.set(j+1, generatedInterior.get(i).get(j));
                }
                newMaze.set(i+1,newRow);
            }
            integrity = MazeIntegrityCheck(newMaze);
        }
        return newMaze;
    }

    // Uses Randomized Prim's algorithm
    private static ArrayList<ArrayList<StaticObj>>  MazeInteriorGenerator(int height, int width){
        //Initialization
        ArrayList<Integer> XY = new ArrayList<>();
        XY.add(0);
        XY.add(0);
        ArrayList<ArrayList<Boolean>>  visited = new ArrayList<>();
        for(int i = 0; i < height; i++){
            ArrayList<Boolean> newRow = new ArrayList<>();
            for(int j = 0; j< width; j++){
                newRow.add(false);
            }
            visited.add(newRow);
        }
        ArrayList<ArrayList<Integer>> walls= new ArrayList<>();
        //Starting Cell for Prim
        ArrayList<Boolean> tempVisitedRow = visited.get(0);
        tempVisitedRow.set(0, true);
        visited.set(0, tempVisitedRow);

        ArrayList<ArrayList<Integer>> neighbours = Neighbours(XY, visited);
        walls.addAll(neighbours);

        while(!walls.isEmpty()){
            ArrayList<Integer> selectedWall = walls.get((int)(Math.random() * walls.size()));
            neighbours= Neighbours(selectedWall,visited);
            int markCount = 0;
            for (ArrayList<Integer> ints : neighbours) {
                int neighX = ints.get(0);
                int neighY = ints.get(1);
                if (visited.get(neighX).get(neighY)) {
                    markCount++;
                }
            }
            if(markCount == 1){
                int selectX = selectedWall.get(0);
                int selectY = selectedWall.get(1);
                ArrayList<Boolean> tempBool = visited.get(selectX);
                tempBool.set(selectY, true);
                visited.set(selectX,tempBool);
                for (ArrayList<Integer> ints : neighbours) {
                    int neighX = ints.get(0);
                    int neighY = ints.get(1);
                    if (!visited.get(neighX).get(neighY)) {
                        ArrayList<Integer> tempXY = new ArrayList<>();
                        tempXY.add(neighX);
                        tempXY.add(neighY);
                        walls.add(tempXY);
                    }
                }
            }
            walls.remove(selectedWall);
        }

        ArrayList<ArrayList<StaticObj>>  newMap = new ArrayList<>();
        for(int i = 0; i < height; i++){
            ArrayList<StaticObj> newRow = new ArrayList<>();
            for(int j = 0; j< width; j++){
                if(visited.get(i).get(j)){
                    newRow.add(new EmptySpace());
                }
                else{
                    newRow.add(new Wall());
                }
            }
            newMap.add(newRow);
        }


        return newMap; //change it!
    }
    private static ArrayList<ArrayList<Integer>> Neighbours(ArrayList<Integer> current, ArrayList<ArrayList<Boolean>> visitedMap){
        ArrayList<ArrayList<Integer>> neighbours = new ArrayList<>();
        int x = current.get(0);
        int y = current.get(1);
        if( !isOutOfBounds(x,(y+1), visitedMap.size(), visitedMap.get(0).size())){
            ArrayList<Integer> tempIndex= new ArrayList<>();
            tempIndex.add(x);
            tempIndex.add(y+1);
            neighbours.add(tempIndex);
        }
        if( !isOutOfBounds(x,(y-1), visitedMap.size(), visitedMap.get(0).size())){
            ArrayList<Integer> tempIndex= new ArrayList<>();
            tempIndex.add(x);
            tempIndex.add(y-1);
            neighbours.add(tempIndex);
        }
        if( !isOutOfBounds((x+1),y, visitedMap.size(), visitedMap.get(0).size())){
            ArrayList<Integer> tempIndex= new ArrayList<>();
            tempIndex.add(x+1);
            tempIndex.add(y);
            neighbours.add(tempIndex);
        }
        if( !isOutOfBounds((x-1),y, visitedMap.size(), visitedMap.get(0).size())){
            ArrayList<Integer> tempIndex= new ArrayList<>();
            tempIndex.add(x-1);
            tempIndex.add(y);
            neighbours.add(tempIndex);
        }
        return  neighbours;
    }

    private static boolean isOutOfBounds(int x, int y, int maxX, int maxY){
        if( x < 0 || x >= maxX){
            return true;
        }
        if( y < 0 || y >= maxY){
            return true;
        }
        return false;
    }

    private  static boolean MazeIntegrityCheck(ArrayList<ArrayList<StaticObj>> maze){
        String pos1 = maze.get(1).get(1).getType();
        String pos2 = maze.get(1).get(18).getType();
        String pos3 = maze.get(14).get(1).getType();
        String pos4 = maze.get(14).get(18).getType();
        if(pos1.equals("wall") || pos2.equals("wall") || pos3.equals("wall") || pos4.equals("wall")) {
            return false;
        }
        //2x2 check
        for(int i=0; i < (maze.size())-1; i++){
            for(int j=0; j < (maze.get(0).size())-1; j++){
                pos1 = maze.get(i).get(j).getType();
                pos2 = maze.get(i).get(j+1).getType();
                pos3 = maze.get(i+1).get(j).getType();
                pos4 = maze.get(i+1).get(j+1).getType();
                if(pos1.equals(pos2) && pos1.equals(pos3) && pos1.equals(pos4)){
                    return false;
                }
            }
        }


        return true;
    }


}
