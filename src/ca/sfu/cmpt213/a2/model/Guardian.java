package ca.sfu.cmpt213.a2.model;

public class Guardian extends DynamicObj {
    private int pastMove;
    public Guardian(int x, int y){
        this.pastMove = -1;
        this.objType = "guardian";
        this.mapPosition.setX(x);
        this.mapPosition.setY(y);
    }

    public int getPastMove() {
        return pastMove;
    }

    @Override
    public void moveObj(int direction){
        switch (direction){
            case 0:
                mapPosition.setX(mapPosition.getX()-1);
                pastMove = 2;
                break;
            case 1:
                mapPosition.setY(mapPosition.getY()+1);
                pastMove = 3;
                break;
            case 2:
                mapPosition.setX(mapPosition.getX()+1);
                pastMove = 0;
                break;
            case 3:
                mapPosition.setY(mapPosition.getY()-1);
                pastMove = 1;
                break;
            default:
                System.out.println("Function moveObj in Class Guardian got invalid argument");
                System.out.println("Input : "+String.valueOf(direction));
                break;
        }
    }

    @Override
    public void relocateObj(int X, int Y){
        System.out.println("Function relocateObj called in Class Guardian!");
    }
}
