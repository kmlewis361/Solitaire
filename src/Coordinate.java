public class Coordinate {
    private int r, c;

    public Coordinate(int r, int c){
        this.r = r;
        this.c = c;
    }

    public int getR(){
        return r;
    }

    public int getC(){
        return c;
    }

    public String toString(){return r+ ", "+c;}
}
