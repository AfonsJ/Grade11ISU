//importing the java modules
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Player {
	//x value for the player sprite
	public int x;
	//y value for the player sprite
	public int y;
	//hitbox for the player sprite
	public Rectangle hitbox;
	//"new y", used for moving the sprite (jumping)
	public int ny;
	//image, holds the sprite
	public Image image;
	//checks if the player is jumping
	public boolean jumping;
	//checks if the player has hit the maximum jump height
	private boolean maxReached;
	
	//used for storing the obstacle classes (used for changing difficulty)
	private Obstacle ob;

	private BigObstacle bigob;

	private ScoreObstacle scorob;
	
	//boolean to check if the player is invincable
	public boolean invincable;

	public Player(Obstacle ob, BigObstacle bigob, ScoreObstacle scorob){
		//setting our sprite
		ImageIcon imicon = new ImageIcon("./trex.png");
		this.image = imicon.getImage();

		//setting the default values for the player class
		this.x = 260;
		this.y = 747;
		this.ny = 0;
		this.jumping = false;
		this.maxReached = false; 
		this.invincable = false;
		this.ob = ob;
		this.bigob = bigob;
		this.scorob = scorob;
	}

	//method for updating the hitbox
	public void updateHitbox(){
		this.hitbox = new Rectangle(this.x, this.y, 50, 52);
	}


	//keypressed, checks which key was pressed
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		//if space is pressed jump
		if(key == KeyEvent.VK_SPACE){
			//if the max height hasnt reached jump otherwise fall
			if(maxReached == false && this.y >= 747){
				this.ny = 3;
				this.jumping = true;
			}else{
				this.ny = 3;
				this.jumping = false;
			}
		}
	}
	
	//move method, used for moving(jumping)
	public void move(){
		this.y = this.y - (this.ny * 3);
		//caps the players jump height to keep game fair
		if(this.y <= 595){
			this.maxReached = true;
			this.jumping = false;
		}
	}

	//fall method, used for moving(falling)
	public void fall(){
		//makes sure the player isnt going through the floor
		if(this.y >= 747){
			this.ny = 0;
		}
		this.y = this.y + (this.ny * 3); 
	}

	//increases the difficulty/speed
	public void changeDiffUp(){
		ob.diff++;
		bigob.diff++;
		scorob.diff++;
	}

	//decreases the difficulty/speed
	public void changeDiffDown(){
		if(ob.diff > 0){
			ob.diff--;
			bigob.diff--;
			scorob.diff--;
		}
	}

	//keyreleased, is called when the user releases a key
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		//if the key is the space then we make the player fall
		if(key == KeyEvent.VK_SPACE){
			this.maxReached = false;
			this.ny = 3;
			this.jumping = false;
		}

		//if the key is released then change difficulty
		if(key == KeyEvent.VK_P){
			changeDiffUp();
		}

		if(key == KeyEvent.VK_O){
			changeDiffDown();
		}

		//if the key is released change the invincability of the player
		if(key == KeyEvent.VK_I){
			if(invincable == true){
				invincable = false;
			}else{
				invincable = true;
			}
		}
	}

	//returns if the player is in a state of jumping
	public boolean returnJump(){
		return this.jumping;
	}
}
