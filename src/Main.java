import processing.core.PApplet;

public class Main extends PApplet {

    private int zoom;
    private float weight;
    //setting up PApplet
    public static Main app;
    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public Main() {
        app = this;
    }

    //put size here (not in setup)
    public void settings() {
        size(500, 600);
    }

    //init
    public void setup() {

    }

    //periodic
    public void draw() {
        background(200);

    }

    public void mouseClicked(){

    }

    public void keyPressed(){

    }

    public void fractal(double x, double y, double r){

    }

}