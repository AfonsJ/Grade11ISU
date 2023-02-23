//importing the java modules
import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class BigObstacle {

	//x value for the sprite
    public int x;
	//y value for the sprite
    public int y;
	
	//x value for the other part of the obstacle
    public int subx;
  	//y value for the other part of the obstacle  
    public int suby;

	//hitbox for the other part of the obstacle
    public Rectangle subhitbox;
	
	//hitbox for sprite
    public Rectangle hitbox;

	//boolean if the spriteis visable
    public boolean visable;

	//difficulty, affects speed
	public int diff;

	//boolean used for switching obstacles using random numbers
	public boolean swtiched;

	//image, stores the sprite
	public Image image;

	//image, store the other image for the other part of the obstacle
	public Image subimage;

	public BigObstacle(){
		//setting our default values for the variables
		this.x = 1280;
		this.y = 708;
		this.diff = 1;
		this.visable = false;
		
		this.subx = 1326;
		this.suby = 754;

		//loading sprites to both portions of the obstacle
		ImageIcon imicon = new ImageIcon("./cactus3.png");
		this.image = imicon.getImage();

		ImageIcon subimicon = new ImageIcon("./cactus2.png");
		this.subimage = subimicon.getImage();

		//setting each sprites hit box
		this.subhitbox = new Rectangle(this.subx, this.suby, 46, 56);
		this.hitbox = new Rectangle(this.x, this.y, 46, 92);
	}
	
	//moves the sprites
	public void move(){
		//move the x value and has a multiplier depending on the difficulty
		this.x = this.x - 10 * diff;
		this.subx = this.subx - 10 * diff;
		//when the sprite reaches 0
		if(x < 0){
			//will generate a number, 1 or 2 and will respectively change the sprite and set its position back to the start
			Random randnum = new Random();
			if(randnum.nextInt(2)+1 == 1){
				this.x = 1280;
				this.subx = 1326;
			}else{
				this.visable = false;
				this.swtiched = true;
			}
		}
		//we update the hitbox since it's position changed
		updateHitbox();
	}
	
	//update hitbox method
	public void updateHitbox(){
		//just redefine the hitbox
		this.hitbox = new Rectangle(this.x, this.y, 46, 92);
		this.subhitbox = new Rectangle(this.subx, this.suby, 46 ,56);
	}

	//check if the obstacle was switched with the smaller one
	public boolean returnSwitched(){
		return this.swtiched;
	}

	//returns if the sprite is visable or not
	public boolean returnVisability(){
		return this.visable;
	}
}