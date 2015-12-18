package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Clock.*;
import static helpers.Artist.*;

public class Projectile {
	private Texture texture;
	private float x,y,speed;
	private int damage;
	
	public Projectile(Texture tex, float x, float y, float speed, int damage){
		this.x=x;
		this.y=y;
		this.texture=tex;
		this.speed=speed;
		this.damage = damage;
	}
	
	public void update(){
		x += Delta()*speed;
		Draw();
	}
	public void Draw(){
		DrawQuadTex(texture,x,y,32,32);
	}
	
}
