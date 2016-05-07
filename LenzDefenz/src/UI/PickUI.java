package UI;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import org.newdawn.slick.opengl.Texture;

import data.TileType;
import data.TowerType;
import static helpers.Artist.WIDTH;
public class PickUI extends UI{
	
	private String itemPicked ="none";
	private Texture greenFrame = QuickLoad("rahmen");
	
	public String getItemPicked() {
		return itemPicked;
	}
	
	public void setItemPicked(String towerPicked) {
		this.itemPicked = towerPicked;
	}
	
	@Override
	public void Draw(){
		for (Button b:buttonList){
			DrawQuadTex(b.getTexture(),b.getX(),b.getY(),b.getWidth(),b.getHeight());
		}
		Button b = getButton(itemPicked);
		if (b!=null){
			DrawQuadTex(greenFrame,b.getX()-3,b.getY()-3,70,70);
		}
	}
	
	//für Towers
	public void addButton(String name, TowerType type, int x, int y){
		
		buttonList.add(new Button(name,type.textures[0],x,y));
		
	}
	//für Editor (Tiles), könnte man noch anders anordnen wenn zuviele
	public void addButton(String name, TileType type, int x, int y){
		
		buttonList.add(new Button(name,QuickLoad(type.textureName),x,y));
		
	}
	
	public void setButtonMousePos(int x, int y){
		float verschieb = 0.00f;
		for (Button b: buttonList){
			b.setX((int)(x+WIDTH*verschieb));
			b.setY(y);
			verschieb += 0.05f;
		}
	}
	
}
