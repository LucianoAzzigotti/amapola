package src;

import processing.core.PApplet;
import processing.core.PFont;

public class Fonts {


	PApplet parent;
	PFont arial;
	
	Fonts(PApplet parent){
		this.parent = parent;
	}
	
	public void setup(){

	  arial = parent.loadFont("Meera-48.vlw");
	  parent.textFont(arial);
	  parent.textSize(20);
	}
}
