import java.awt.*;


public class Card2
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
    public int bposition;
    public int value;

    public Rectangle rec;			//declare a rectangle variable

    public Card2(int position, int val)
    {
        value = val;
        dx = 5;
        dy = -5;
        width = 30;
        height=80;
        isAlive=true;
        cisAlive=true;
        bposition=position;
        rec= new Rectangle (xpos,ypos,width,height);	//construct a rectangle.  This one uses width and height varibles

    } // constructor
}









//end of the generic object class  definition

