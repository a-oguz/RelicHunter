package ca.sfu.cmpt213.a2.model;

public class Player extends DynamicObj{
    private boolean alive;
    public Player(int x, int y){
        this.alive = true;
        this.objType = "player";
        this.mapPosition.setX(x);
        this.mapPosition.setY(y);
    }
    public boolean isAlive(){
        return this.alive;
    }

    public void killPlayer(){
        this.alive = false;
    }

    @Override
    public void moveObj(int direction){
        switch (direction){
            case 0:
                mapPosition.setX(mapPosition.getX()-1); // North
                break;
            case 1:
                mapPosition.setY(mapPosition.getY()+1); // East
                break;
            case 2:
                mapPosition.setX(mapPosition.getX()+1); // South
                break;
            case 3:
                mapPosition.setY(mapPosition.getY()-1); // West
                break;
            default:
                System.out.println("Function moveObj in Class Player got invalid argument");
                break;
        }
    }

    @Override
    public void relocateObj(int X, int Y){
        System.out.println("Function relocateObj is called in Class Player!");
    }
}
