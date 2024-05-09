import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
public class Main extends PApplet {

    private ArrayList<Card> deck;
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
        deck = new ArrayList<Card>();
        Suit currentSuit;

        for(int i=1; i<=4; i++){
            for(int j=1; j<=13; j++){
                if(i == 1){
                    currentSuit = Suit.HEARTS;
                }else if(i == 2){
                    currentSuit = Suit.DIAMONDS;
                }else if(i == 3){
                    currentSuit = Suit.CLUBS;
                }else{
                    currentSuit = Suit.SPADES;
                }
                deck.add(new Card(j, currentSuit));
            }
        }


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