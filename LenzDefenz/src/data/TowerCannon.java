package data;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

public class TowerCannon {
	private float x,y,timeSinceLastShot,firingSpeed, angle;
	private int width, height, damage;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	private CopyOnWriteArrayList<Projectile> projectiles;
	private Enemy target;
	
	//Sound
	private Audio wavEffect;
	
	//Pojectile Hilfe
	int projOutOfArea = -1;
	
	public TowerCannon(Texture texture, Tile startTile, int damage){
		this.x=startTile.getX();
		this.y=startTile.getY();
		this.startTile = startTile;
		this.baseTexture = texture;
		this.cannonTexture = QuickLoad("cannonGun");
		this.damage = damage;
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.firingSpeed =1.5f;
		this.timeSinceLastShot=0;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
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
		/*for (Enemy e:Enemy.getEnemies()){
			if (e.isAlive()){
				return e;
			}	
		}*/
		
		
		return Enemy.getEnemies().get(1);
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
				//System.out.println(Enemy.enemies.size());
			}
			else
				target = acquireTarget();
		}
		
		for (Projectile p:projectiles){											
			if (!p.isOnMap())												//auch wenn das projektil getroffen hat
				projectiles.remove(p);
			else
				p.update();
		}
		
		angle = calcAngle();
		Draw();
	}
	
	public void Draw(){
		DrawQuadTex(baseTexture,x,y,width,height);
		DrawQuadTexRot(cannonTexture,x,y,width,height,angle);
	}
	
	public void Shoot(){
		//System.out.println(target.getID());
		timeSinceLastShot = 0;
		projectiles.add(new Projectile(QuickLoad("bullet"),x+Game.TILE_SIZE/2-Game.TILE_SIZE/4,
									   y+Game.TILE_SIZE/2-Game.TILE_SIZE/4,911,12,target));
		//wavEffect.playAsSoundEffect(1.0f, 1.0f, false);
	}

}
