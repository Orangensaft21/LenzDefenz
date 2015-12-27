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
	private float x,y,speed,health,startHealth;
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
	//brauch man nich
	private EnemyType enemyType;
	/*
	 * Liste von allen gegnern
	 */
	public static CopyOnWriteArrayList<Enemy> enemies;
	
	
	static public AStarPathFinder pf;
	static public Path path;
	
	/*public Enemy(Texture texture, Tile startTile, int width, int height ,float speed, float health){
		this.texture=texture;
		this.x=startTile.getX();
		this.y=startTile.getY();
		this.width=width;
		this.height=height;
		this.health=health;
		this.speed=speed;
		this.startTile = startTile;
		this.pathStatus=0;
		
		if (this.sd==null)
			this.sd = (TileBasedMap) Boot.grid;
		
		this.grid =Boot.grid;
		//test
		if (path==null){
			findPath();
		}
		this.target=path.getStep(0);
		
		if (enemies==null){
			enemies = new CopyOnWriteArrayList<Enemy>();
		}
		enemies.add(this);
		if (healthBack==null)
			healthBack=QuickLoad("healthback");
		if (healthVorne ==null)
			healthVorne = QuickLoad("healthtex");
		
	}	*/
	
	public Enemy(EnemyType type, Tile startTile){
		this.texture=type.textures[0]; // noch ändern
		this.x=startTile.getX();
		this.y=startTile.getY();
		this.width=type.textures[0].getImageWidth();
		this.height=type.textures[0].getImageHeight();
		this.health=type.health;
		this.startHealth=type.health;
		this.speed=type.speed;
		this.startTile = startTile;
		this.pathStatus=0;
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
		
		if (enemies==null){
			enemies = new CopyOnWriteArrayList<Enemy>();
		}
		enemies.add(this);
		if (healthBack==null)
			healthBack=QuickLoad("healthback");
		if (healthVorne ==null)
			healthVorne = QuickLoad("healthtex");
		
	}	
	
	/*
	 * enemy laufen
	 */	

	public void update(){
		/*
		 * beim ersten Element ist Delta() extrem gross, deswegen 
		 * er das nicht updaten
		 */
		if (first){
			first=false;
			return;
		}
		
		/*
		 * enemy list aufräumen 
		 */
		for (Enemy e : enemies){
			if (!e.isAlive()){
				enemies.remove(e);
				break;
			}
		}
		
		
		//check ob finished
		if (target.getX()==19&&target.getY()==14){
			finished = true;

		}else
			target = path.getStep(pathStatus);
		float dx = this.getX()/64-target.getX();
		float dy = this.getY()/64-target.getY();
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
			if(dx>0)
				this.x-=speed*Delta();
			else
				this.x+=speed*Delta();
		else
			if(dy>0)
				this.y-=speed*Delta();
			else
				this.y+=speed*Delta();
		
		
			
	}
	
	public void findPath(){
		pf = new AStarPathFinder(sd, 500, false);
		path=pf.findPath(new UnitMover(2), 0, 2, sd.getWidthInTiles()-1, sd.getHeightInTiles()-1);
		for (Step s:path.getSteps())
			System.out.println(s.toString());
		
	}
	
	
	private void Die(){
		alive = false;
	}
	
	public void Damage(int amount){
		health-=amount;
		if (health<=0)
			Die();
	}
	
	public void draw(){
		float healthProzent= health/startHealth;
		DrawQuadTex(texture,x,y,width,height);
		DrawQuadTex(healthBack,x,y-6,width,8);
		DrawQuadTex(healthVorne,x,y-6,TILE_SIZE*healthProzent,8);		
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

	public void setSpeed(float speed) {
		this.speed = speed;
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


	public static synchronized CopyOnWriteArrayList<Enemy> getEnemies() {
		return enemies;
	}


	public static synchronized void removeEnemy(Enemy e) {
		enemies.remove(e);
	}
	
	public float getStartHealth(){
		return startHealth;
	}
	
	
	
}
