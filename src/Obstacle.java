//importing the java modules
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Obstacle {
	//x value of obstacle
	public int x;
	//y value of obstacle
	public int y;
	//hitbox of sprite
	public Rectangle hitbox;
	//boolean if the sprite is visable
	public boolean visable;
	//the difficulty of the game
	public int diff = 1;
	//boolean if the obstacle is switched
	public boolean swtiched;
	//image, holds the sprite
	public Image image;
	
	public Obstacle(){
		//setting our default values for obstacle
		this.x = 1280;
		this.y = 730;
		this.diff = 1;
		this.visable = false;
		this.swtiched = false;
		//loading the sprite
		ImageIcon imicon = new ImageIcon("./cactus1.png");
		this.image = imicon.getImage();
		//setting the hitbox
		this.hitbox = new Rectangle(this.x, this.y, 46, 72);
	}
	
	//move method
	public void move(){
		//has a difficulty multiplier
		this.x = this.x -10 * diff;
		//if it reaches 0
		if(x < 0){
			//will generate a number, 1 or 2 and will respectively change the sprite and set its position back to the start
			Random randnum = new Random();
			if(randnum.nextInt(2)+1 == 1){
				this.x = 1280;
			}else{
				this.visable = false;
				this.swtiched = true;
			}
		}
		//updates the hitbox with the new values
		updateHitbox();
	}
	
	//updates the obstacle's hitbox
	public void updateHitbox(){
		this.hitbox = new Rectangle(this.x, this.y, 46, 72);
	}

	//returns a boolean if the obstacle has been switched or not
	public boolean returnSwitched(){
		return this.swtiched;
	}

	//returns whether or not the obstacle is visable
	public boolean returnVisability(){
		return this.visable;
	}
}
