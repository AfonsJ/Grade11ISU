//importing the java modules
import java.awt.*;

public class ScoreObstacle {
	//x value of the score obstacle
    public int x;
	//y value of the score obstacle
	public int y;
	//the hitbox of the obstacle
	public Rectangle hitbox;
	//boolean if it is visable
    public boolean visable;
    //difficulty int
    public int diff;

    public ScoreObstacle(){
		//set the default values
		this.x = 1310;
        this.y = 0;
        this.diff = 1;
		this.visable = false;
		//set the obstacle's hitbox
		this.hitbox = new Rectangle(this.x, this.y, 1, 772);
    }

	//move method
    public void move(Obstacle ob, BigObstacle bigob){
		//has a difficulty multiplier so it keeps up with the other obstacles
		this.x = this.x -10 * diff;
		if(x < 0){
			//when it reaches 0, starts at the starting position
			this.x = 1280;
		}
		//update the hitbox after moving
		updateHitbox(ob, bigob);
	}
	
	//update hitbox method
	public void updateHitbox(Obstacle obs, BigObstacle bigob){	
		//depending which obstacle is visable will offset the x values
		if(obs.visable == true){
			this.hitbox = new Rectangle((obs.x+20), this.y, 1, 772);
		}else{
			this.hitbox = new Rectangle((bigob.x+20), this.y, 1, 772);
		}

	}
}