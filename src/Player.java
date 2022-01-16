import java.awt.*;


public class Player
{

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public int xpos;				//the x position
    public int ypos;				//the y position
    public boolean isAlive;
    public boolean cisAlive;//a boolean to denote if the hero is alive or dead.
    public int dx;					//the speed of the hero in the x direction
    public int dy;					//the speed of the hero in the y direction
    public int width;
    public int height;
    public int dposition;
    public int value;
    public int numbercount;
    public Image pic;
    public Card hand[];
    public Rectangle rec;			//declare a rectangle variable

    public Player(int nc, int val)
    {
        value = val;
        numbercount = nc;
        hand = new Card[15];
        rec= new Rectangle (xpos,ypos,width,height);	//construct a rectangle.  This one uses width and height varibles

    } // constructor
}












