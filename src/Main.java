import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

import static java.util.Collections.shuffle;

public class Main extends PApplet {

    private ArrayList<Card> deck, drawnPile;
    private int deckX, deckY, currCardX, currCardY, pilesX, heartsY, diamondsY, clubsY, spadesY, boardX, boardY;
    // int heartsPile, diamondsPile, clubsPile, spadesPile;
    private Card currHearts, currDiamonds, currClubs, currSpades;
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
    }

    //periodic
    public void draw() {
        background(200);
        image(loadImage("url.jpeg"), deckX, deckY, CARD_WIDTH, CARD_HEIGHT);

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

    public void mouseClicked() {
        // if(mouseX>deckX && mouseX<deckX+CARD_WIDTH && mouseY>deckY && mouseY<deckY+CARD_HEIGHT){
        int r;
        for (int c = 0; c < board[0].length; c++) {
            r = getLast(c);
            if (checkMouseBounds(boardX + c * (CARD_WIDTH + CARD_WIDTH / 2), boardY + r * CARD_WIDTH / 2, CARD_WIDTH, CARD_HEIGHT)) {
                addToPile(board[r][c]);
                board[r][c] = null;
                if (r > 0) {
                    board[r - 1][c].flip();
                }
            }
        }
        if (checkMouseBounds(deckX, deckY, CARD_WIDTH, CARD_HEIGHT)) {
            if (deck.size() > 0) {
                drawnPile.add(deck.removeFirst());
            } else {
                deck.addAll(drawnPile);
                drawnPile.clear();
            }

        }else if (!drawnPile.isEmpty() && checkMouseBounds(currCardX, currCardY, CARD_WIDTH, CARD_HEIGHT)) {
            addToPile(drawnPile.removeLast());

        }
    }

    public void keyPressed(){

    }

    private boolean checkMouseBounds (int x, int y, int w, int h){
        return mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h;
    }

    private int getLast(int col) {
        for (int r = 0; r < board.length - 1; r++) {
            if (board[r + 1][col] == null) {
                return r;
            }
        }
        return board.length - 1;
    }

    private void addToPile(Card card) {
        if (card.getSuit() == Suit.HEARTS) {
            if (card.getNum() == 1) {
                currHearts = card;
                deck.remove(currHearts);
            } else if (currHearts != null && card.getNum() == currHearts.getNum() + 1) {
                currHearts = card;
                deck.remove(currHearts);
            }
        } else if (card.getSuit() == Suit.DIAMONDS) {
            if (card.getNum() == 1) {
                currDiamonds = card;
                deck.remove(currDiamonds);
            } else if (currDiamonds != null && card.getNum() == currDiamonds.getNum() + 1) {
                currDiamonds = card;
                deck.remove(currDiamonds);
            }
        } else if (card.getSuit() == Suit.CLUBS) {
            if (card.getNum() == 1) {
                currClubs = card;
                deck.remove(currClubs);
            } else if (currClubs != null && card.getNum() == currClubs.getNum() + 1) {
                currClubs = card;
                deck.remove(currClubs);
            }
        } else if (card.getSuit() == Suit.SPADES) {
            if (card.getNum() == 1) {
                currSpades = card;
                deck.remove(currSpades);
            } else if (currSpades != null && card.getNum() == currSpades.getNum() + 1) {
                currSpades = card;
                deck.remove(currSpades);
            }
        }
    }
}