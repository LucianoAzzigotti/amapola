package src;

import processing.core.PApplet;
import processing.core.PFont;
/**
 * esta clase se encarga de manejar las tipograf’as y su aplicacion en el renderer
 * @author Diex
 *
 */
public class FontManager {


	PApplet 	parent;
	PFont 		verdanada;
	
	FontManager(PApplet parent){
		this.parent = parent;
	}
	
	//TODO una estupidez esto.
	// arreglar
	public void setup(){
	  verdanada = parent.loadFont("Verdana72.vlw");
	  parent.textFont(verdanada);
	  parent.textSize(20);
	}
}
