package amapola;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import processing.core.*;
import toxi.geom.Vec2D;
import codeanticode.gsvideo.*;

/**
 * Clase principal de la aplicacion visual
 * para Amapola Dry
 * Desde aqui arranca toda la aplicacion
 * @author amapola
 *
 */

public class app extends PApplet{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(new String[] {"amapola.app"});

	}


	public static Logger logger;
	public boolean debug = true;

	/**
	 * Video input se encarga de manejar los frames de video de entrada
	 * puede ser configurado para arrancar con camara web o con un video proxy
	 */
	VideoInput 		vi;
	
	/**
	 * Es el objeto que se encarga de hacer el blob detection
	 * Maneja la CV devolviendo informacion util para ser utilizada por las
	 * demas clases
	 */
	Blobs 			blobs;
	
	/**
	 * esta clase carga fuentes y las mantiene disponibles en todo momento
	 */
	Fonts fonts;

	
	LyricsLoader 	lyric;

	boolean 		nextPhrase = false;			// para probar lanzar las liricas
	
	
	
	public void setup(){
		// Set up a simple configuration that logs on the console.
		//http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html

		logger = Logger.getLogger("amapola.app");
		PropertyConfigurator.configure("./data/log.ini");
		logger.info("LOGGER OK");

		size(800,600,P3D);
		frameRate(60);


		fonts = new Fonts (this);
		fonts.setup();

		vi = new VideoInput(this, false);

		blobs = new Blobs(this);
		
		lyric = new LyricsLoader();
		lyric.readLyricsFile("./data/mind.txt");
		
	}


	////////////////////////////////////////////
	// variables de la escena
	////////////////////////////////////////////
	
	String text = "";
	int 	vel = 0;
	Vec2D	singerCenter = new Vec2D();
	
	
	public void draw(){

		background(127);

		try {		
			// hasta que no tengo el primer cuadro de video
			// no puedo saber cuanto mide por eso
			// tengo que esperar para lanzar el blobdetection
			// TODO: hacer que el blob tenga tamano fijo

			if(! blobs.isReady()){	
				blobs.setup(vi.read());
			}

			blobs.computeBlobs(vi.read());

			singerCenter = blobs.getInterpolatedCenter();				
			
			
			
			if(debug) image(vi.read(), 0, 0);	
			if(debug) blobs.drawBlobsAndEdges(true, true);	
			if(debug){ 			
				fill(255,0,0);
				ellipse(singerCenter.x , singerCenter.y, 10,10 );
				noFill();
				stroke(255, 0 ,0 );
				ellipse(singerCenter.x , singerCenter.y, 200,200);
				
			}
			
			
			
			
		} catch (NullPointerException e) {
			// println("no hay frame");
		}

		

		
		// lanzar textos prueba
		if(nextPhrase == true){		
			// engana pichanga.
			// los textos se llaman desde 1
			int n = (int) random(lyric.phrasesCount()) + 1;
			
			Phrase cual = lyric.getPhrase(n);
			
			logger.info(n);
			logger.info(cual.toString());
			
			text = cual.getText();		
			vel  = cual.getVelocity();

			nextPhrase = false;
		}
		
		fill(0, vel);
		pushMatrix();

		translate(singerCenter.x, singerCenter.y);
		textSize(50);
		textAroundCurve(text);
		
		popMatrix();
		
		fill(0);
		text(frameRate, 20,20);
	}
	
	


	float r	= 100;
	
	void textAroundCurve(String message){
	// We must keep track of our position along the curve
	 float arclength = 0;
	  // For every box
	  for (int i = 0; i < message.length(); i ++ ) {
	    
	    // The character and its width
	    char currentChar = message.charAt(i);
	    // Instead of a constant width, we check the width of each character.
	    float w = textWidth(currentChar); 
	    // Each box is centered so we move half the width
	    arclength += w/2;
	    
	    // Angle in radians is the arclength divided by the radius
	    // Starting on the left side of the circle by adding PI
	    float theta = PI + arclength / r;
	    
	    pushMatrix();
	    
	    // Polar to Cartesian conversion allows us to find the point along the curve. See Chapter 13 for a review of this concept.
	    translate(r * cos(theta), r * sin(theta)); 
	    // Rotate the box (rotation is offset by 90 degrees)
	    rotate(theta + PI/2); 
	    
	    // Display the character
	    fill(0);
	    text(currentChar,0,0);
	    
	    popMatrix();
	    
	    // Move halfway again
	    arclength += w/2;
	  }
}
	
	
	public void keyPressed(){
		
		if(key == 'd') debug = !debug;
		
		if(key == 'l') nextPhrase = true;
	}


}
