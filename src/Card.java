import processing.core.PImage;
public class Card {
    private int num;
    private Suit suit;
    private PImage img;
    private int WIDTH, HEIGHT;
    private String imageName;

    public Card(int num, Suit suit){
        this.num = num;
        this.suit = suit;
        WIDTH = 60;
        HEIGHT = 90;
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
        Main.app.image(img, x, y, WIDTH, HEIGHT);
    }
}
