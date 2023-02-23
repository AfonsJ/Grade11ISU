//			      ICS 3U1
//			   Javier Afonso
//			Wednesday, January 2020
//			   MainGame.java
//
//This is the main file for the game, other files include: BigObstacle.java, Player.java, Obstacle.java, ScoreObstacle.java
//The objective of the game is to avoid the cacti, use space to jump
//
//if sprite don't load, it is an issue with how eclipse ide loads the sprites, grab the sprites
//and put them in a seperate folder and change the image paths in each class 

//importing the classes we will need for the program
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

class GameContent extends JPanel {
	//Timer class for having a game loop
	private Timer timer;
	//Player class for the player object
	private Player player;
	//Obstacle class for the obstacle object
	private Obstacle obstacle;
	//Another obstacle class for a obstacle object
	private BigObstacle big_obstacle;
	//invisable rectangle for score
	private ScoreObstacle scoreobstacle;
	//KeyListner class for the keylistner
	private KeyListener kylistener;
	//score variable
	private int score;

	public GameContent(int width, int height){
		//Setting size and colour of JPanel
		this.setSize(width, height);
		this.setBackground(Color.WHITE);

		//initializing the classes
		this.obstacle = new Obstacle();
		this.big_obstacle = new BigObstacle();
		this.kylistener = new KeyListener();
		this.scoreobstacle = new ScoreObstacle();
		this.player = new Player(obstacle, big_obstacle, scoreobstacle);	


		//Generating a random number which determines which obstacle will spawn
		Random randnum = new Random();
		if(randnum.nextInt(2)+1 == 1){
			this.obstacle.visable = true;
		}else{
			this.big_obstacle.visable = true;
		}

		//adding score
		this.score = 0;

		//adding jlabel for score
		JLabel scoreBoard = new JLabel();
		scoreBoard.setBounds(1270, 40, 20, 20);
		scoreBoard.setText("Score: "+score);
		this.add(scoreBoard);

		//this if for the action listener
		this.setFocusable(true);
		this.addKeyListener(this.kylistener);

		//here we are initializing the timer class, will loop every 15 ms
        this.timer = new Timer(15, new ActionListener(){
            public void actionPerformed(ActionEvent e){
				//update the score, score is divided by five because collisions happen 5 times with the score obstacle and this offsets it
				scoreBoard.setText("Score :"+score/5);
				//will update the players hitbox
				player.updateHitbox();
				//will check if the player is in the air or not, and will either fall or jump
				if(player.returnJump() == true){
					player.move();
				}else{
					player.fall();
				}
				//Check for collisions with other hitboxes and sprites
				checkForCollisions();
				//this is for switching out the obstacles
				checkSwitched();
				//move the obstacles
				obstacle.move();
				big_obstacle.move();
				scoreobstacle.move(obstacle, big_obstacle);
				//repaint now that values have been modified
				repaint();
			}
		});
		//start the timer
		this.timer.start();
	}

	public void paint(Graphics g) {
		//Setting up the paint method
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//setting  the colour
		g2d.setColor(Color.BLACK);

		//drawing the floor
		g2d.draw(new Rectangle(0, 800, 1280, 20));

		//loading the sprite, drawing the clouds
		Image cloudimg = new ImageIcon("./cloud.png").getImage();
		g2d.drawImage(cloudimg, 900, 100, this);
		g2d.drawImage(cloudimg, 200, 80, this);
		g2d.drawImage(cloudimg, 450, 140, this);

		//floor texture (random lines, dots)
		g2d.drawLine(634, 840, 637, 840);
		g2d.drawLine(234, 840, 237, 840);
		g2d.drawLine(834, 840, 835, 840);

		//draw player
		g2d.drawImage(player.image, player.x, player.y, this);		


		//drawing obstacles
		if(big_obstacle.visable == true){
			g2d.drawImage(big_obstacle.image, big_obstacle.x, big_obstacle.y, this);
			g2d.drawImage(big_obstacle.subimage, big_obstacle.subx, big_obstacle.suby, this);
		}

		if(obstacle.visable == true){
			g2d.drawImage(obstacle.image, obstacle.x, obstacle.y, this);
		}

	}

	public void checkSwitched(){
		//used for switching out the obstacles and painting the correct ones
		if(this.obstacle.returnSwitched() == true){
			this.obstacle.swtiched = false;
			this.big_obstacle.visable = true;
		}
		if(this.big_obstacle.returnSwitched() == true){
			this.big_obstacle.swtiched = false;
			this.obstacle.visable = true;
		}
	}
	
	public void checkForCollisions(){
		//check for collisions with the first obstacle
		if(player.hitbox.intersects(obstacle.hitbox) && obstacle.visable == true && player.invincable == false){
			MainGame.deathScreen();
			this.timer.stop();
		} 
		//check for collision with the second obstacle
		if(player.hitbox.intersects(big_obstacle.hitbox) && big_obstacle.visable == true && player.invincable == false){
			MainGame.deathScreen();
			this.timer.stop();
		}
		//check for collision with score obstacle and adds score
		if(player.hitbox.intersects(scoreobstacle.hitbox)){
			score = score + 100;
		}
	}

	//our keylistner
	private class KeyListener extends KeyAdapter{
		//when a key is pressed it passes it to the player class
		public void keyPressed(KeyEvent e){
			player.keyPressed(e);
		}
		//when a key is released it passes it to the player class
		public void keyReleased(KeyEvent e){
			player.keyReleased(e);
		}
	}

	//method to stop the action listener/timer
	public void stopTimer(){
		this.timer.stop();
	}
}


//the class for the death screen
class deathScreen extends JPanel{

	//it uses its own timer for button input
	private Timer timer;

	public deathScreen(int width, int height){
		//Sets JFrame settings, similar to the MainGame class
		this.setSize(width, height);
		this.setBackground(Color.GRAY);
		this.setFocusable(true);
		this.setLayout(null);

		//here we are defining JLabels, their respective text, font size and the size of the label
		JLabel label = new JLabel();
		label.setText("You have died.");
		label.setFont(new Font("Arial", Font.PLAIN, 30));
		label.setBounds(490, 132, 320, 256);
		//same as above, just different text and y position
		JLabel label2 = new JLabel();
		label2.setText("Press the button to restart");
		label2.setFont(new Font("Arial", Font.PLAIN, 30));
		label2.setBounds(420, 220, 420, 256);

		//here we are defining the JButton which restarts the game
		JButton restartButton = new JButton("Press me to restart");
		restartButton.setBounds(510, 462, 160, 128);
		
		//here we are adding it to the frame
		this.add(label);
		this.add(label2);
		this.add(restartButton);
		//here is our timer being set to 15 ms
		this.timer = new Timer(15, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//if the button is pressed, starts a new game, disposes of the death screen and stop this timer
				if(restartButton.getModel().isPressed() == true){
					MainGame.game = new MainGame();
					dScreenLoading.diposeDeathScreen();
					stopTimer();
				}
			}
		});
		//starts the timer
		this.timer.start();
	}

	//a method for stoping the timer
	public void stopTimer(){
		this.timer.stop();
	}

}

//loading screen jframe class 
class dScreenLoading extends JFrame{

	public static dScreenLoading dScreen;

	//heres we are seting up the JFrame
	public dScreenLoading(){
		//here we are putting the settings for our jframe
		
		//here we are passing a new instance of the death screen to the jframe
		this.getContentPane().add(new deathScreen(MainGame.width, MainGame.height));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(MainGame.width, MainGame.height);
		this.setTitle("Dino Game (Press the button to restart the game)");
		this.setResizable(false);
		this.setVisible(true);
	}

	public static void diposeDeathScreen(){
		//this diposes(closes) the death screen
		dScreenLoading.dScreen.dispose();
	}
}

//Main game class
public class MainGame extends JFrame{

	//the width and height of the game window
    public static int width = 1280;

	public static int height = 1024; 

	//main game class
	public static MainGame game;

	public MainGame() {
		//setting the default jframe settings

		//passing a new instance of the gamecontent
		this.getContentPane().add(new GameContent(width, height));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setTitle("Dino Game");
		this.setResizable(false);
		this.setVisible(true);
	}

	//here is what runs when the program is run, the method above
	public static void main(String[] args) {
		MainGame.game = new MainGame();
	}

	//this is what loads the death screen when called
	public static void deathScreen(){
		MainGame.game.dispose();
		dScreenLoading.dScreen = new dScreenLoading();
	}
}