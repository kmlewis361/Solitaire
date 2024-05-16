import processing.core.PImage;
public class Card {
    private int num;
    private Suit suit;
    private PImage img;
    private int WIDTH, HEIGHT;
    private String imageName;
    private boolean up;

    public Card(int num, Suit suit){
        up = true;
        this.num = num;
        this.suit = suit;
        WIDTH = Main.CARD_WIDTH;
        HEIGHT = Main.CARD_HEIGHT;
        // this.img = img;
        imageName = num+"_of_";
        if(suit == Suit.HEARTS){
           imageName+="hearts.png";
        }else if(suit == Suit.DIAMONDS){
            imageName+="diamonds.png";
        }else if(suit == Suit.CLUBS){
            imageName+="clubs.png";
        }else if(suit == Suit.SPADES){
            imageName+="spades.png";
        }
        img = Main.app.loadImage(imageName);


    }

    public void display(int x, int y){
        if(up) {
            Main.app.image(img, x, y, WIDTH, HEIGHT);
        }else{
            Main.app.image(Main.app.loadImage("url.jpeg"), x, y, WIDTH, HEIGHT);
        }
    }

    public Suit getSuit(){
        return suit;
    }

    public int getNum(){
        return num;
    }

    public void flip(){
        up = !up;
    }

    public void faceUp(){up = true;}

    public String toString() {
        return num + " of " + suit;
    }
}
