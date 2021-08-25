package ca.sfu.cmpt213.a2.model;

public class DynamicObj {
    protected String objType = "undefinedDynamicObj";
    protected Location mapPosition = new Location(-1,-1);

    public String getType(){ return objType; }
    public Location getMapPosition() {return mapPosition; }
    public void moveObj(int direction){
        System.out.println("This is a base class, wrong function call for moveObj!");
    }
    public void relocateObj(int X, int Y){
        System.out.println("This is a base class, wrong function call for relocateObj!");
    }
}
