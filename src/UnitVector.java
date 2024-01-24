import java.lang.Math;
public class UnitVector {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        unitize();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        unitize();
    }

    public void setByAngle(int angle){
        double radians = (Math.PI / 180) * angle;
        setX(Math.cos(radians));
        setY(Math.sin(radians)); // automatically unitizes
        reformat();
    }

    public double getNorm(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public UnitVector(double x, double y) {
        this.x = x;
        this.y = y;
        unitize();
    }

    public UnitVector(int angle) {
        setByAngle(angle);
    }

    private void unitize() {
        double length = getNorm();
        x = (double) (x / length);
        y = (double) (y / length);
    }

    /* Reformats the vector to have a positive x direction and... */
    private void reformat(){
        x = Math.abs(x);
    }
}
