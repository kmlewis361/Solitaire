import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

import static java.util.Collections.shuffle;

public class Main extends PApplet {

    private ArrayList<Card> deck, drawnPile;
    private int deckX, deckY, currCardX, currCardY, pilesX, heartsY, diamondsY, clubsY, spadesY, boardX, boardY;
    // int heartsPile, diamondsPile, clubsPile, spadesPile;
    private Card currHearts, currDiamonds, currClubs, currSpades;
    private boolean gameDone;
    public static int CARD_WIDTH, CARD_HEIGHT;
    private Card[][] board;

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
        size(1400, 800);
    }

    //init
    public void setup() {
        CARD_WIDTH = 90;
        CARD_HEIGHT = 130;
        deckX = 20;
        deckY = 20;
        boardX = deckX + CARD_WIDTH + CARD_WIDTH / 2;
        boardY = deckY;
        currCardX = deckX;
        currCardY = deckY + CARD_WIDTH / 2 + CARD_HEIGHT;
        pilesX = width - CARD_WIDTH * 2;
        heartsY = CARD_WIDTH / 2;
        diamondsY = heartsY + CARD_HEIGHT + CARD_WIDTH / 2;
        clubsY = diamondsY + CARD_HEIGHT + CARD_WIDTH / 2;
        spadesY = clubsY + CARD_HEIGHT + CARD_WIDTH / 2;
        deck = new ArrayList<Card>();
        drawnPile = new ArrayList<Card>();
        board = new Card[13][7];
        gameDone = false;
        currHearts = null;
        currDiamonds = null;
        currClubs = null;
        currSpades = null;
        Suit currentSuit;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 13; j++) {
                if (i == 1) {
                    currentSuit = Suit.HEARTS;
                } else if (i == 2) {
                    currentSuit = Suit.DIAMONDS;
                } else if (i == 3) {
                    currentSuit = Suit.CLUBS;
                } else {
                    currentSuit = Suit.SPADES;
                }
                deck.add(new Card(j, currentSuit));
            }
        }
        shuffle(deck);
        for (int c = 0; c < board[0].length; c++) {
            for (int r = 0; r <= c; r++) {
                board[r][c] = deck.removeFirst();
                if (r < c) {
                    board[r][c].flip();
                }
            }
        }
        fill(0);
    }

    //periodic
    public void draw() {
        background(200);
        if (pilesFull()) {
            textAlign(CENTER, CENTER);
            textSize(50);
            text("You win! Press any key to restart", width/2, height/2);
        } else {
            textAlign(LEFT, CENTER);
            textSize(30);
            text("Press any key to restart", 45, height-50);
            if (!deck.isEmpty()) {
                image(loadImage("url.jpeg"), deckX, deckY, CARD_WIDTH, CARD_HEIGHT);
            }
            if (!drawnPile.isEmpty()) {
                drawnPile.getLast().display(currCardX, currCardY);
            }
            if (currHearts != null) {
                currHearts.display(pilesX, heartsY);
            }
            if (currDiamonds != null) {
                currDiamonds.display(pilesX, diamondsY);
            }
            if (currClubs != null) {
                currClubs.display(pilesX, clubsY);
            }
            if (currSpades != null) {
                currSpades.display(pilesX, spadesY);
            }
            for (int c = 0; c < board[0].length; c++) {
                for (int r = 0; r < board.length; r++) {
                    if (board[r][c] != null) {
                        board[r][c].display(boardX + c * (CARD_WIDTH + CARD_WIDTH / 2), boardY + r * CARD_WIDTH / 2);
                    }
                }
            }
        }
    }

    public void mouseClicked() {

            println(getBoardCardClicked(mouseX, mouseY));
            Coordinate clickedCoord = getBoardCardClicked(mouseX, mouseY);
            Card clicked = null;
            if (clickedCoord != null) {
                clicked = board[clickedCoord.getR()][clickedCoord.getC()];
            }
            if (clicked != null) {
                println("clicked != null");
                int r = clickedCoord.getR();
                int c = clickedCoord.getC();
                if ((r == board.length - 1 || (r < board.length && board[r + 1][c] == null) && addToPile(clicked))) {

                    board[r][c] = null;
                    if (r > 0) {
                        board[r - 1][c].faceUp();
                    }
                } else if (addToBoard(clicked)) {
                    board[clickedCoord.getR()][clickedCoord.getC()] = null;
                    if (r > 0 && board[r - 1][c] != null) {
                        board[r - 1][c].faceUp();
                    }
                    r++;
                    while (board[r][c] != null && addToBoard(board[r][c])) {
                        println(r);
                        board[r][c] = null;
                        r++;
                    }
                }
            }

            //checking if deck clicked
            if (checkMouseBounds(deckX, deckY, CARD_WIDTH, CARD_HEIGHT)) {
                if (!deck.isEmpty()) {
                    drawnPile.add(deck.removeFirst());
                } else {
                    deck.addAll(drawnPile);
                    drawnPile.clear();
                }

                //checking if current card is clicked
            } else if (!drawnPile.isEmpty() && checkMouseBounds(currCardX, currCardY, CARD_WIDTH, CARD_HEIGHT)) {
                if (addToPile(drawnPile.getLast())) {
                    drawnPile.removeLast();
                } else if (addToBoard(drawnPile.getLast())) {
                    drawnPile.removeLast();
                }
            }


    }

    public void keyPressed(){
        setup();
        println("restarting");
    }

    private boolean checkMouseBounds (int x, int y, int w, int h){
        return mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h;
    }

    private int getLast(int col) {
        if(col>=board[0].length || col<0){
            println("GETLAST COL INVALID");
            return -1;
        }
        for (int r = 0; r < board.length - 1; r++) {
            if (board[r + 1][col] == null) {
                return r;
            }
        }
        return board.length - 1;
    }

    private boolean addToPile(Card card) {
        if (card.getSuit() == Suit.HEARTS) {
            if (card.getNum() == 1 || (currHearts != null && card.getNum() == currHearts.getNum() + 1)) {
                currHearts = card;
                return true;
            }
        } else if (card.getSuit() == Suit.DIAMONDS) {
            if (card.getNum() == 1 || (currDiamonds != null && card.getNum() == currDiamonds.getNum() + 1)) {
                currDiamonds = card;
                return true;
            }
        } else if (card.getSuit() == Suit.CLUBS) {
            if (card.getNum() == 1 || (currClubs != null && card.getNum() == currClubs.getNum() + 1)) {
                currClubs = card;
                return true;
            }
        } else if (card.getSuit() == Suit.SPADES) {
            if (card.getNum() == 1 || (currSpades != null && card.getNum() == currSpades.getNum() + 1)) {
                currSpades = card;
                return true;
            }
        }
        return false;
    }

    private boolean addToBoard(Card card) {
        for (int c = 0; c < board[0].length; c++) {
            int r = getLast(c);
            if (r == board.length) {
                println("too many cards in column " + c);
            }
            Card last = board[r][c];
            if(board[r+1][c]!=card) {
                if (last == null && card.getNum() == 13 && card.isFaceUp()) {
                    board[0][c] = card;
                    return true;
                } else if (last != null && oppositeColor(card, last) && card.getNum() == last.getNum() - 1) {
                    board[r + 1][c] = card;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean oppositeColor(Card c1, Card c2) {
        if (c1.getSuit() == Suit.HEARTS || c1.getSuit() == Suit.DIAMONDS) {
            if (c2.getSuit() == Suit.CLUBS || c2.getSuit() == Suit.SPADES) {
                return true;
            } else {
                return false;
            }
        } else {
            if (c2.getSuit() == Suit.HEARTS || c2.getSuit() == Suit.DIAMONDS) {
                return true;
            } else {
                return false;
            }
        }
    }

    private Coordinate getBoardCardClicked(int mX, int mY) {
        int c = (mX - boardX) / (CARD_WIDTH + CARD_WIDTH/2);
        if(c<0 || c>=board[0].length){
            return null;
        }
        int last = getLast(c);
       // println("last: " + last);
        int r = (mY - boardY) / (CARD_WIDTH/2);
        if(mY >= boardY + last * CARD_WIDTH / 2 && mY <= boardY + last * CARD_WIDTH / 2 + CARD_HEIGHT){
         //   println("returning last");
            r = last;
        }

        if (r >= 0 && r < board.length && c >=0 && c < board[0].length) {
            return new Coordinate(r, c);
        }
        return null;
    }

    private boolean pilesFull(){
        if(currHearts!=null && currDiamonds!=null && currClubs!=null && currSpades!=null){
            println("no pile is null!");
            boolean r = currHearts.getNum() == 13 && currDiamonds.getNum() == 13 && currClubs.getNum() == 13 &&currSpades.getNum() == 13;
            println(r);
            return r;
        }
        return false;
    }
}