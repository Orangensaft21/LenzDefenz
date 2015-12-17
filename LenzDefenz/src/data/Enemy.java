package data;

import org.newdawn.slick.opengl.Texture;

import pathfinding.AStarPathFinder;
import pathfinding.Path;
import pathfinding.Path.Step;
import pathfinding.PathFinder;
import pathfinding.TileBasedMap;

import static helpers.Artist.*;
import static helpers.Clock.*;


public class Enemy {
	
	private int width,height,health;
	private float x,y,speed;
	Texture texture;
	private Tile startTile;
	private boolean first = true, alive =true;								//wenn der erste gegner geupdatet wird ist delta riesig
	static private TileBasedMap sd;
	private TileGrid grid;
	
	private int pathStatus;	                                    //wieviele Stufe im Path ist der Mob
	private Step target;
	private double dist;
	private boolean finished = false;
	
	static public AStarPathFinder pf;
	static public Path path;
	
	public Enemy(Texture texture, Tile startTile, int width, int height ,float speed){
		this.texture=texture;
		this.x=startTile.getX();
		this.y=startTile.getY();
		this.width=width;
		this.height=height;
		this.speed=speed;
		this.startTile = startTile;
		this.pathStatus=0;
		
		
		
		if (this.sd == null)
			this.sd= new TileGrid(Boot.map);
		
		this.grid =Boot.grid;
		//test
		if (path==null){
			pf = new AStarPathFinder(sd, 500, false);
			path=pf.findPath(new UnitMover(2), 0, 2, sd.getWidthInTiles()-1, sd.getHeightInTiles()-1);
			
			
		}
		this.target=path.getStep(0);
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
		
		
		
		//check ob finished
		if (target.getX()==19&&target.getY()==14){
			finished = true;

		}else
			target = path.getStep(pathStatus);
		float dx = this.getX()/64-target.getX();
		float dy = (this.getY()/64)-target.getY();
		dist = Math.sqrt(dx*dx+dy*dy);
		if(Math.abs(dist)<0.05 && !finished){
			pathStatus++;
			//this.x=target.getX()*64;
			//this.y=target.getY()*64;
		} 
		//check ob finished und über der letzten kachel, jetz stirbt der Mob
		if(Math.abs(dist)<0.05 && finished){
			speed=0;
			Die();
			return;
		}
		speed=11;
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
	
	private void Die(){
		alive = false;
	}
	
	public void Draw(){
		DrawQuadTex(texture,x,y,width,height);
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
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

}
