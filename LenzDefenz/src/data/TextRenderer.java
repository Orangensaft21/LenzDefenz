package data;

import static helpers.Artist.WIDTH;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;;


public class TextRenderer {
   
   public static final int CENTER = 0; //Alignment (Optional but it overides the x value if either of these three a passed)
   public static final int LEFT = 1;
   public static final int RIGHT = 2;
   
   private static int charWidth;
   @SuppressWarnings("unused")
private static int charHeight;
   private static float charWidthInTex;
   private static float charHeightInTex;
   
   private static int charsAcross;
   private static int charsDown;
   private static int charSpacing;
   
   private static Texture texture;
   
   public TextRenderer(String texFileName, int charWidth, int charHeight) {
      TextRenderer.charWidth = charWidth;
      TextRenderer.charHeight = charHeight;
      
      try {
         texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(texFileName));
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      charsAcross = texture.getImageWidth() / charWidth;
      charsDown = texture.getImageHeight() / charHeight;
      System.out.println(texture.getImageWidth() + " " + texture.getWidth());
      
      charWidthInTex = texture.getWidth() / charsAcross;
      charHeightInTex = texture.getHeight() / charsDown;
      charSpacing = charWidth - 5;
      
   }
   
   public static void drawString(String text ,double x, double y, double size, float red, float green, float blue, double orientation) {
      double charSize;
      double charSpace;
      //If no size value passed, use default size
      if (size == 0) {
         charSize = charWidth;
         charSpace = charSpacing;
      }
      else {
         charSize = charSpacing * size;
         charSpace = charSize - 4;
      }
      
      if (orientation == CENTER) {
         x = (WIDTH - (text.length() * charSpace))/2;
      }
      if (orientation == LEFT) {
         x = 0;
      }
      if (orientation == RIGHT) {
         x = (WIDTH - (text.length() * charSpace + 10));
      }
      
      
      
      
      glPushMatrix();
      texture.bind();
      
      glBegin(GL_QUADS);
      
      for (int i = 0; i < text.length(); i++) {
         int c = (text.charAt(i)) - 32;
         
         float u = (c % charsAcross) * charWidthInTex;
         float v = (c / charsAcross) * charHeightInTex;

         glColor3f(red, green, blue); //Color can be (must be) set when function is called.
         glTexCoord2f(u, v);
         glVertex2d(x + (i * charSpace), y);
         glTexCoord2f(u + charWidthInTex, v);
         glVertex2d(x + (i * charSpace) + charSize, y);
         glTexCoord2f(u + charWidthInTex, v + charHeightInTex);
         glVertex2d(x + (i * charSpace) + charSize, y + charSize);
         glTexCoord2f(u, v + charHeightInTex);
         glVertex2d(x + (i * charSpace), y + charSize);
      }
      glEnd();
      glPopMatrix();
      
      
      
      
   }
}