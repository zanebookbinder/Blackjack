// Car class with rectangle

//you need this import to use rectangles!
import java.awt.*;


public class Card
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
	public Image pic;

	public Rectangle rec;			//declare a rectangle variable

	public Card(int position, int val, Image i)
	{
		value = val;
		pic=i;
		dx = 5;
		dy = -5;
		width = 30;
		height=80;
		isAlive=false;
		cisAlive=false;
		dposition=position;
		rec= new Rectangle (xpos,ypos,width,height);	//construct a rectangle.  This one uses width and height varibles

	} // constructor
   }









 //end of the generic object class  definition

