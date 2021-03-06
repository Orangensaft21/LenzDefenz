package helpers;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.left;
import static helpers.Artist.top;
import static helpers.Artist.totalZoom;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glVertex3f;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {
	
	public static final int WIDTH = 1280, HEIGHT = 960;
	public static final int TILE_SIZE = 64;
	
	/* zoom Faktor */
	public static float totalZoom;
	/* x Koordinate zum scrollen */
	public static int left;
	/* y Koordinate zum scrollen */
	public static int top;
	
	public static void BeginSession(){
		Display.setTitle("Lenz Defenz");
		try {
			Display.setDisplayMode(new DisplayMode((int) (WIDTH/totalZoom),(int) (HEIGHT/totalZoom)));
			System.out.println(Display.getDisplayMode());
			Display.setVSyncEnabled(true);
			Display.setResizable(false);
			//System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,WIDTH/totalZoom,HEIGHT/totalZoom,0,-1,1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		//glOrtho(0,WIDTH/2f,HEIGHT/2f,0,-1,1);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// test
		//glEnable(org.lwjgl.opengl.GL11.GL_DEPTH_TEST);
		//
		

	}
	/*
	 * funktion die in die Karte zoomt
	 */
	
	public static void zoom(float zoomFactor){
		if (totalZoom+zoomFactor <1)
			return;
		totalZoom += zoomFactor;
		// zurückscrollen
		if (left+WIDTH/totalZoom>=WIDTH)
			//left=0;
			left = WIDTH - (int) Math.floor(WIDTH/totalZoom);
		if (top+HEIGHT/totalZoom>=HEIGHT)
			//top=0;
			top = HEIGHT - (int) Math.floor(HEIGHT/totalZoom);
		
		
		/*glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(left,WIDTH/totalZoom,HEIGHT/totalZoom,top,-1,1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();*/
		
		scrollMap(0,0);
		// falls tiefer reingezoomed
		scrollMap(-32,-32);
	}
	
	/*
	 * funktion zum scrollen
	 */
	public static void scrollMap(int xDirect, int yDirect){
		/*
		 * wenn man verucht links oder oben rauszuscrollen
		 */
		if (left +xDirect<0 || top +yDirect <0 )
			return;
		/*
		 * wenn man versucht rechts oder unten rauszuscrollen
		 */
		if (left+xDirect+WIDTH/totalZoom>WIDTH)
			return;
		/*if (top+yDirect+HEIGHT/totalZoom>=HEIGHT){
			top = (int) (HEIGHT/totalZoom);
		}else*/
		if (top+yDirect+HEIGHT/totalZoom>HEIGHT)
			return;
		top  += yDirect;
		
		left += xDirect;
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(left,(WIDTH/totalZoom)+left,(HEIGHT/totalZoom)+top,top,-1,1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	
	public static boolean checkCollision(float x1, float y1, float width1, float height1,
										 float x2, float y2, float width2, float height2){
		
		if (x1 + width1 > x2 && x1 < x2+width2 && y1 +height1 > y2 && y1 < y2 + height2)
			return true;
		return false;
	}
	
	public static void DrawQuad(float x, float y, float width, float height){
		glBegin(GL_QUADS);
		glVertex2f(x,y);
		glVertex2f(x + width,y);
		glVertex2f(x + width,y + height);
		glVertex2f(x,y+height);
		glEnd();
	}
	
	public static void DrawQuadTex(Texture tex, float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(width,0);
		glTexCoord2f(1,1);
		glVertex2f(width,height);
		glTexCoord2f(0,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	
	public static void DrawQuadTexRot(Texture tex, float x, float y, float width, float height,float angle){
		tex.bind();
		glTranslatef(x+width/2,y+height/2,0);
		glRotatef(angle,0,0,1);
		glTranslatef(-width/2,-height/2,0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(width,0);
		glTexCoord2f(1,1);
		glVertex2f(width,height);
		glTexCoord2f(0,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	
	
	public static Texture LoadTexture(String path, String filetype){
		
		
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(filetype, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	
	private static Texture bullet = null;
	
	public static Texture QuickLoad(String name){
		if (name.equals("bullet"))			
			if (bullet==null)
				bullet = LoadTexture("res/" + name +".png", "PNG");
			else return bullet;
		
		return LoadTexture("res/" + name +".png", "PNG");
	}
	
	
	/*
	 * neu 2016 Enemy Image Hilfsfunktionen
	 */
	
	public static Texture[][] LoadEnemyTextures(String path){
		BufferedImage img = null;

		try {
			InputStream in = Artist.class.getClassLoader().getResourceAsStream("res/"+path);
			//URL f = Artist.class.getClassLoader().getResource("res/"+path);
		    img = ImageIO.read(in);
		    //img = img.getSubimage(48, 16, 48, 48);
		    Texture[][] tex = new Texture[4][3];
		    for(int i=0;i<4;i++)
		    	for(int j=0;j<3;j++){
		    		tex[i][j]=BufferedImageUtil.getTexture("", img.getSubimage(64*j, 64*i, 64, 64));
		    	}
		    return tex;
		    //return BufferedImageUtil.getTexture("", img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
	/*
	 * noch anpassen
	 */
	
	public static Texture[] LoadTowerTextures(String name){
		BufferedImage img = null;
		try{
			//URL f = Artist.class.getClassLoader().getResource("res/"+name);
			//img = ImageIO.read(new File(f.toURI()));
			InputStream in = Artist.class.getClassLoader().getResourceAsStream("res/"+name);
			img = ImageIO.read(in);
			Texture[] tex = new Texture[2];
			for (int i=0;i<2;i++){
				tex[i]=BufferedImageUtil.getTexture("", img.getSubimage(64*i, 0, 64, 64));
			}
			return tex;
		}catch (IOException e) {
			e.printStackTrace();
		
		}
		
		return null;
	}
	
	public static int getMouseXCoord(){
		return (int)(Mouse.getX()/totalZoom+left);
	}
	
	public static int getMouseYCoord(){
		return (int)(HEIGHT/totalZoom -Mouse.getY()/totalZoom-1+top);
	}
	
}
