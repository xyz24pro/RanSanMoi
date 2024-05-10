import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Data {
	public static BufferedImage sprite;
	
	public static Image imageHead;
	public static Image imageHead_GoLeft;
	public static Image imageHead_GoRight;
	public static Image imageHead_GoUp;
	public static Image imageHead_GoDown;
	
	public static Image imageBody;
	
	public static Image imageFood;
	public static Image imageFood1;
	public static Image imageFood2;
	
	
	public static Animation HeadGoUp;
	public static Animation HeadGoDown;
	public static Animation HeadGoLeft;
	public static Animation HeadGoRight;
	
	public static Animation Food;
	
	public static void loadImage() {
		try {
			sprite = ImageIO.read(new File("resource/sprite1.png"));
			
			imageHead = sprite.getSubimage(2, 3, 30, 30);
			imageHead_GoLeft = sprite.getSubimage(75, 3, 30, 30);
			imageHead_GoRight = sprite.getSubimage(110, 3, 30, 30);
			imageHead_GoDown = sprite.getSubimage(39, 3, 30, 30);
			imageHead_GoUp = sprite.getSubimage(145, 3, 30, 30);
			
			imageBody = sprite.getSubimage(6, 80, 20, 20);
			
			imageFood = sprite.getSubimage(0, 40, 30, 30);
			imageFood1 = sprite.getSubimage(32, 40, 30, 30);
			imageFood2 = sprite.getSubimage(63, 40, 30, 30);
			
		}catch(Exception e) {}
	}
	public static void loadAllAnimation() {
		HeadGoUp = new Animation();
		HeadGoUp.addImage(imageHead);
		HeadGoUp.addImage(imageHead_GoUp);
		
		HeadGoDown = new Animation();
		HeadGoDown.addImage(imageHead);
		HeadGoDown.addImage(imageHead_GoDown);
		
		HeadGoRight = new Animation();
		HeadGoRight.addImage(imageHead);
		HeadGoRight.addImage(imageHead_GoRight);
		
		HeadGoLeft = new Animation();
		HeadGoLeft.addImage(imageHead);
		HeadGoLeft.addImage(imageHead_GoLeft);
		
		Food = new Animation();
		Food.addImage(imageFood);
		Food.addImage(imageFood1);
		Food.addImage(imageFood2);
		Food.addImage(imageFood1);
	}
}
