package ca.sfu.cmpt213.a2.model;

public class Relic extends DynamicObj{
    public Relic(int x, int y){
        this.objType = "relic";
        this.mapPosition.setX(x);
        this.mapPosition.setY(y);
    }
    @Override
    public void moveObj(int direction){
        System.out.println("Function moveObj is called in Class Relic!");
    }
    @Override
    public void relocateObj(int X, int Y){
        mapPosition = new Location(X, Y);
    }
}
