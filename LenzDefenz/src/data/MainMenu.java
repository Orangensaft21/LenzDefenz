package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.GameManager;
import helpers.GameManager.GameState;

import static helpers.Artist.*;

public class MainMenu {
	private Texture background;
	private UI menuUI;
	
	public MainMenu(){
		background = QuickLoad("menuhintergrund");
		menuUI = new UI();
		menuUI.addButton("play", "playButton", WIDTH/2 -128, (int) (HEIGHT*0.60f));
		menuUI.addButton("editor", "editorButton", WIDTH/2 -128, (int) (HEIGHT*0.70f));
		menuUI.addButton("quit", "quitButton", WIDTH/2 -128, (int) (HEIGHT*0.80f));
	}
	
	public void update(){
		DrawQuadTex(background,0,0, 2048, 1024);
		menuUI.Draw();
		updateButtons();
	}
	
	private void updateButtons(){
		if (Mouse.isButtonDown(0)){
			if (menuUI.isButtonClicked("play"))
				GameManager.setState(GameState.GAME);
			if (menuUI.isButtonClicked("editor"))
				GameManager.setState(GameState.EDITOR);
			if (menuUI.isButtonClicked("quit"))
				System.exit(0);
		}
	}
}
