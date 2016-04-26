package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

import pathfinding.AStarPathFinder;
import pathfinding.Path;
import pathfinding.Path.Step;
import pathfinding.TileBasedMap;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

public class Enemy implements Entity{
	
	private int width,height;
	private float x,y,speed,health, timeSlowed;
	Texture texture;
	private Tile startTile;
	private boolean first = true, alive =true;								//wenn der erste gegner geupdatet wird ist delta riesig
	private TileGrid grid;
	private TileBasedMap sd;
	private int pathStatus;	                                    //wieviele Stufe im Path ist der Mob
	private Step target;
	private double dist;
	private boolean finished = false;
	static Texture healthBack;
	static Texture healthVorne;
	Texture[][] textures;
	Wave wave;
	/*
	 * Hilfsvariablen zum zeichnen
	 */
	private int iTexIndex=0,jTexIndex=0;
	private float timeSinceLastDraw;
	//brauch man nich
	private EnemyType enemyType;
	/*
	 * Liste von allen gegnern
	 */
	//public static CopyOnWriteArrayList<Enemy> enemies;
	
	
	static public AStarPathFinder pf;
	static public Path path;
	
	
	
	public Enemy(EnemyType type, Tile startTile, Wave wave){
		this.texture=type.textures[0][0]; // noch ändern
		this.textures=type.textures;
		this.x=startTile.getX();
		this.y=startTile.getY();
		this.width=type.textures[0][0].getImageWidth();
		this.height=type.textures[0][0].getImageHeight();
		this.health=type.health;
		this.speed=type.speed;
		this.startTile = startTile;
		this.pathStatus=0;
		this.timeSinceLastDraw=0;
		this.timeSlowed=0;
		this.wave=wave;
		//
		enemyType=type;
		if (this.sd==null)
			this.sd = (TileBasedMap) Boot.grid;
		
		this.grid =Boot.grid;
		//test
		if (path==null){
			findPath();
		}
		this.target=path.getStep(0);
		
		/*if (enemies==null){
			enemies = new CopyOnWriteArrayList<Enemy>();
		}
		enemies.add(this);*/
		if (healthBack==null)
			healthBack=QuickLoad("healthback");
		if (healthVorne ==null)
			healthVorne = QuickLoad("healthtex");
		//if (textures==null)
			//textures = QuickLoadEnemyTextures();
	}	
	
	/*
	 * enemy laufen
	 */	

	public void update(){
		/*
		 * beim ersten Element ist Delta() extrem gross, deswegen 
		 * er das nicht updaten
		 */
		//System.out.println(enemies.size());
		
		if (first){
			first=false;
			return;
		}
		
		//Slow Timer runterzählen
		timeSlowed-=Delta();
		if (timeSlowed <=0)
			speed=enemyType.speed;
		/*
		 * zeichnen
		 */
		timeSinceLastDraw+=Delta();
		if (timeSinceLastDraw>0.2){
			timeSinceLastDraw=0;
			jTexIndex=(jTexIndex+1)%3;
		}
		
		//check ob finished
		if (target.getX()==19&&target.getY()==14){
			finished = true;

		}else
			target = path.getStep(pathStatus);
		float dx = (this.getX())/64-target.getX();
		float dy = (this.getY()+8)/64-target.getY();
		dist = Math.sqrt(dx*dx+dy*dy);
		if(Math.abs(dist)<0.08 && !finished){
			pathStatus++;
			//this.x=target.getX()*64;
			//this.y=target.getY()*64;
		} 
		//check ob finished und über der letzten kachel, jetz stirbt der Mob
		if(Math.abs(dist)<0.08 && finished){
			speed=0;
			Die();
			return;
		}
		if (Math.abs(dx)>Math.abs(dy))
			if(dx>0){
				this.x-=speed*Delta();
				iTexIndex=3;
			}	
			else{
				this.x+=speed*Delta();
				iTexIndex=1;
			}
		else
			if(dy>0){
				this.y-=speed*Delta();
				iTexIndex=0;
			}
			else{
				this.y+=speed*Delta();
				iTexIndex=2;
			}
				
		
			
	}
	
	public void findPath(){
		pf = new AStarPathFinder(sd, 500, false);
		path=pf.findPath(new UnitMover(2), 0, 2, sd.getWidthInTiles()-1, sd.getHeightInTiles()-1);
		for (Step s:path.getSteps())
			System.out.println(s.toString());
		
	}
	
	
	private void Die(){
		alive = false;
		Player.lives--;
	}
	
	public void Damage(int amount){
		health-=amount;
		if (health<=0)
			Die();
	}
	
	public void draw(){
		DrawQuadTex(textures[iTexIndex][jTexIndex],x,y,width,height);
		
	}
	public void drawHealth(){
		float healthProzent= health/enemyType.health;
		DrawQuadTex(healthBack,x+14,y+3,34,8);
		DrawQuadTex(healthVorne,x+14,y+3,34*healthProzent,8);		
	}
	/*
	 * muss noch weg
	 */
	public EnemyType getEnemyType(){
		return enemyType;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSpeed() {
		return speed;
	}
	
	/*
	 * Funktion nimmt nen Slowfaktor zwischen 0 und 1 +
	 * ne Zeit die heruntergezählt wird
	 */
	
	public void slow(float timeSlowed, float amountSlowed) {
		this.timeSlowed = timeSlowed;
		if (this.speed==enemyType.speed)
			this.speed*=amountSlowed;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public TileGrid getGrid() {
		return grid;
	}

	public void setGrid(TileGrid grid) {
		this.grid = grid;
	}
	
	public boolean isAlive(){
		return alive;
	}

	
	public float getStartHealth(){
		return enemyType.health;
	}
	
	
	
}
