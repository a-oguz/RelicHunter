package ca.sfu.cmpt213.a2.model;

import com.sun.istack.internal.NotNull;

public class Location {
    private int x;
    private int y;

    public Location(int X, int Y){
        this.x = X;
        this.y = Y;
    }
    public Location(@NotNull Location newLocation){
        this.x = newLocation.getX();
        this.y = newLocation.getY();
    }

    public void setX(int X) { this.x = X; }
    public void setY(int Y) { this.y = Y; }

    public int getX() { return x; }
    public int getY() { return y; }

    public int[] getXY(){
        int[] XY= {x,y};
        return XY;
    }

    public void setXY(int X, int Y){
        this.x = X;
        this.y = Y;
    }

    public boolean equal(Location comparedLocation){
        if(comparedLocation.getX() == this.x && comparedLocation.getY() == this.y){
            return true;
        }
        else{
            return false;
        }

    }

}
