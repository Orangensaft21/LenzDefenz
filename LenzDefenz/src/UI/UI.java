package UI;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

public class UI {

	protected ArrayList<Button> buttonList;
	
	public UI(){
		buttonList = new ArrayList<Button>();
	}
	
	
	public void addButton(String name, String textureName, int x, int y){
		buttonList.add(new Button(name,QuickLoad(textureName),x,y));
		
	}
	
	public boolean isButtonClicked(String buttonName){
		Button b = getButton(buttonName);
		//System.out.println(b.getName());
		float mouseY = HEIGHT - Mouse.getY() -1;
		if (Mouse.getX()> b.getX() && Mouse.getX() < b.getX() + b.getWidth()&&
			mouseY>b.getY() && mouseY <b.getY() + b.getHeight())
			return true;
		return false;
	}
	
	public String getButtonClicked(){
		for (Button b: buttonList){
			if (isButtonClicked(b.getName()))
				return b.getName();
		}
		return "nix";
	}
	
	protected Button getButton(String buttonName){
		for (Button b : buttonList){
			if (b.getName().equals(buttonName))
				return b;
		}
		return null;
	}
	
	public void Draw(){
		for (Button b:buttonList){
			DrawQuadTex(b.getTexture(),b.getX(),b.getY(),b.getWidth(),b.getHeight());
		}
	}
	
	
}
