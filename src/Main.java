import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

import static java.util.Collections.shuffle;

public class Main extends PApplet {

    private ArrayList<Card> deck;
    private int deckX, deckY;
    public static int  CARD_WIDTH, CARD_HEIGHT;
    private int currCardIndex;
    private Card currCard;
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
        currCardIndex = -1;
        CARD_WIDTH = 60;
        CARD_HEIGHT = 90;
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
        shuffle(deck);

    }

    //periodic
    public void draw() {
        background(200);
        image(loadImage("url.jpeg"), deckX, deckY, CARD_WIDTH, CARD_HEIGHT);
        if(currCardIndex>-1 && currCardIndex<deck.size()) {
            deck.get(currCardIndex).display(100, 20);
        }
    }

    public void mouseClicked(){
        if(mouseX>deckX && mouseX<deckX+CARD_WIDTH && mouseY>deckY && mouseY<deckY+CARD_HEIGHT){
            currCardIndex ++;
            if(currCardIndex>=deck.size()){
                currCardIndex = -1;
            }
        }

    }


    public void keyPressed(){

    }

}