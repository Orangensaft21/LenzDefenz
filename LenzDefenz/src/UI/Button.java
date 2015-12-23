package UI;

import org.newdawn.slick.opengl.Texture;

public class Button {
	
	private int x,y,width,height;
	private Texture texture;
	private String name;
	
	public Button(String name, Texture texture, int x, int y, int width, int height){
		this.x=x;
		this.y=y;
		this.texture=texture;
		this.name=name;
		this.width=width;
		this.height=height;
	}
	
	public Button(String name, Texture texture, int x, int y){
		this.x=x;
		this.y=y;
		this.texture=texture;
		this.name=name;
		this.width= texture.getImageWidth();
		this.height=texture.getImageHeight();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
	
	
}
