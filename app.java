package src;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import processing.core.*;
import remixlab.proscene.Scene;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;
import toxi.geom.mesh.SuperEllipsoid;
import toxi.geom.mesh.SurfaceMeshBuilder;
import toxi.geom.mesh.TriangleMesh;
import toxi.math.waves.SineWave;
import toxi.physics.VerletPhysics;
import toxi.physics.behaviors.GravityBehavior;
import codeanticode.glgraphics.*;


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
		PApplet.main(new String[] {"src.app"});

	}


	public Scene scene;
	public boolean debug = true;

	/**
	 * esta clase carga fuentes y las mantiene disponibles en todo momento
	 */
	Fonts 			fonts;

	LyricsLoader 	lyric;



	boolean 		nextPhrase = false;			// para probar lanzar las liricas

	GLMesh glmesh;
	
	
	Cord cord;
	Bridge bridge;
	
	SineWave sineWave;

	VerletPhysics verlet;
	public static Logger logger;

	public void setup(){

		size(800,600,GLConstants.GLGRAPHICS);
		frameRate(60);
		scene = new Scene(this);
		scene.disableKeyboardHandling();

		logger = Logger.getLogger("APP");

		fonts = new Fonts (this);
		fonts.setup();

		lyric = new LyricsLoader("./data/mind.txt");


		TriangleMesh mesh = new TriangleMesh();
		SurfaceMeshBuilder sm = new SurfaceMeshBuilder(new SuperEllipsoid(3f, 3f)); 	
		mesh = (TriangleMesh) sm.createMesh(300);
		//mesh.scale(new Vec3D(100,100,100));

		//	System.out.println(mesh.getNumFaces());

		verlet = new VerletPhysics();
		Vec3D beggin = new Vec3D(-width/2,0,0);
		Vec3D end = new Vec3D(width/2,0,0);

		bridge = new Bridge(beggin);
		
		bridge.addCord(new Cord(verlet, beggin, end, 10	), 0, true);
		cord= new Cord(verlet, beggin, end, 10	);
		
		glmesh = new GLMesh(this,mesh);
		sineWave = new SineWave(0	, .01f, .5f, 0.5f	);
		// probemos un behavior
		verlet.addBehavior(new GravityBehavior(new Vec3D(0,.01f,0)));
		

	}


	////////////////////////////////////////////
	// variables de la escena
	////////////////////////////////////////////

	String text = "sarasa";
	int 	vel = 0;
	Vec2D	pos = new Vec2D(width/2, height/2);


	public void draw(){

		verlet.update();
		
		
		background(127);
		stroke(0);


		// actualizo el spline
		cord.computeVertices(20);

		
		
		
		
		// dibujo los handlers
		pushStyle();
		fill(0);
		rectMode(CENTER);
		for(Iterator i= cord.getHandlers().iterator(); i.hasNext(); ) {
			Vec3D v=(Vec3D) i.next();
			pushMatrix();
			translate(v.x,v.y,v.z);
			rect(0,0,5,5);
			popMatrix();
		}
		popStyle();

		// dibujo las particulas 
		pushStyle();
		fill(255,0,0);
		ellipseMode(CENTER);
		for(Iterator i= cord.getStringParticles().iterator(); i.hasNext(); ) {
			Vec3D v=(Vec3D) i.next();
			pushMatrix();
			translate(v.x,v.y,v.z);
			ellipse(0,0,5,5);
			popMatrix();

		}

		popStyle();


		// dibujo la line que une los handlers
		pushMatrix();
		noFill();
		beginShape();
		
		for(Iterator i= cord.getDecimatedPoints(.2f).iterator(); i.hasNext(); ) {
			Vec3D v=(Vec3D) i.next();
			vertex(v.x,v.y);
		}
		endShape();
		popMatrix();

		
		// dibujo un circulito en algun punto del path
		pushMatrix();
		float val = sineWave.update();
		ellipseMode(CENTER);
		Vec3D pointInLine = cord.getPointAt(val - 0.000001f);
		translate(pointInLine.x , pointInLine.y, pointInLine.z);		
		ellipse(0,0,15,15);
		popMatrix();





		pushMatrix();
		fill(0, vel);
		translate(pos.x, pos.y);
		textSize(50);
		//	textAroundCurve(text);

		popMatrix();



		fill(0);
		textSize(14);
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
		if(key == 'l') {
			// los textos se llaman desde 1
			int n = (int) random(lyric.phrasesCount()) + 1;								
			Phrase cual = lyric.getPhrase(n);
			text = cual.getText();		
			vel  = cual.getVelocity();

		}
		if(key == 's'){
			cord.setRigid();
		};
	}


}
