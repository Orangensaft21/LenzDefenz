package UI;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import org.newdawn.slick.opengl.Texture;

import data.TowerType;

public class TowerPickUI extends UI{
	
	private String towerPicked ="none";
	private Texture greenFrame = QuickLoad("rahmen");
	
	public String getTowerPicked() {
		return towerPicked;
	}
	
	public void setTowerPicked(String towerPicked) {
		this.towerPicked = towerPicked;
	}
	
	@Override
	public void Draw(){
		for (Button b:buttonList){
			DrawQuadTex(b.getTexture(),b.getX(),b.getY(),b.getWidth(),b.getHeight());
		}
		Button b = getButton(towerPicked);
		if (b!=null){
			DrawQuadTex(greenFrame,b.getX()-3,b.getY()-3,70,70);
		}
	}
	
	
	public void addButton(String name, TowerType type, int x, int y){
		
		buttonList.add(new Button(name,type.textures[0],x,y));
		
	}
	
}
