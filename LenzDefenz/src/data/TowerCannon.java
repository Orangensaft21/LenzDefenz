package data;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

public class TowerCannon {
	private float x,y,timeSinceLastShot,firingSpeed, angle;
	private int width, height, damage;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private Enemy target;
	
	//Sound
	private Audio wavEffect;
	
	//Pojectile Hilfe
	int projOutOfArea = -1;
	
	public TowerCannon(Texture texture, Tile startTile, int damage,ArrayList<Enemy> enemies){
		this.x=startTile.getX();
		this.y=startTile.getY();
		this.startTile = startTile;
		this.baseTexture = texture;
		this.cannonTexture = QuickLoad("cannonGun");
		this.damage = damage;
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.firingSpeed =1f;
		this.timeSinceLastShot=0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemies=enemies;
		this.target=acquireTarget();
		this.angle = calcAngle();
		
		/*sound
		 * 
		 */
		try {
			wavEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/awp1.wav"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Enemy acquireTarget(){
		return enemies.get(0);
	}
	
	private float calcAngle(){
		double angleTemp = Math.atan2(target.getY()-y, target.getX()-x);
		return (float) Math.toDegrees(angleTemp) -90;
	}
	
	/*
	 * Projectiles deleten nich elegant aber funktioniert.
	 */
	public void update(){
		
		timeSinceLastShot += Delta();
		if (timeSinceLastShot > firingSpeed){
			
			if (target.isAlive()){
				Shoot();
				System.out.println(Enemy.enemies.size());
			}
			else
				Enemy.enemies.remove(target);						// Mob löschen  besser woanders hinmachen
				target = Enemy.enemies.get(Enemy.enemies.size()-1);
		}
		
		for (Projectile p:projectiles){
			if (p.isOnMap())
				p.update();
				else{
					projOutOfArea=projectiles.indexOf(p);
			}
		}
		if (projOutOfArea!=-1){
			projectiles.remove(projOutOfArea);
			projOutOfArea=-1;
		}
		angle = calcAngle();
		Draw();
	}
	
	public void Draw(){
		DrawQuadTex(baseTexture,x,y,width,height);
		DrawQuadTexRot(cannonTexture,x,y,width,height,angle);
	}
	
	public void Shoot(){
		timeSinceLastShot = 0;
		projectiles.add(new Projectile(QuickLoad("bullet"),x+Game.TILE_SIZE/2-Game.TILE_SIZE/4,
									   y+Game.TILE_SIZE/2-Game.TILE_SIZE/4,911,12,target));
		//wavEffect.playAsSoundEffect(1.0f, 1.0f, false);
	}

}
