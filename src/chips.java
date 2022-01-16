import java.awt.*;


public class chips
{
    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public int xpos;				//the x position
    public int ypos;				//the y position
    public int value;
    public int width;
    public int height;
    public boolean isAlive;

    public Rectangle rec;			//declare a rectangle variable

    public chips( int val, boolean isAlive)
    {
        value = val;
        isAlive = isAlive;

        rec= new Rectangle (xpos,ypos,width,height);	//construct a rectangle.  This one uses width and height varibles

    } // constructor
} //end of the generic object class  definition