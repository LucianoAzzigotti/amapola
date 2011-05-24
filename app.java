package src;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import processing.core.*;
import remixlab.proscene.Scene;
import toxi.geom.AABB;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;
import toxi.geom.mesh.SuperEllipsoid;
import toxi.geom.mesh.SurfaceMeshBuilder;
import toxi.geom.mesh.TriangleMesh;
import toxi.geom.mesh.Vertex;
import toxi.geom.mesh.WETriangleMesh;
import toxi.math.waves.SineWave;
import toxi.physics.VerletPhysics;
import toxi.physics.behaviors.GravityBehavior;
import toxi.processing.ToxiclibsSupport;
import codeanticode.glgraphics.*;


/**
 * Clase principal de la aplicacion visual
 * para Amapola Dry
 * Desde aqui arranca toda la aplicacion
 * @author amapola
 *
 */

public class app extends PApplet{

	public static Logger logger;
	public boolean debug = true;

	public static void main(String[] args) {
		logger = Logger.getLogger("APP");
		PApplet.main(new String[] {"src.app"});

	}

	public Scene scene;
	public ToxiclibsSupport gfx;

	public VerletPhysics verlet;

	// es staff es todo !!!
	Staff staff ;

	SineWave sineWave;

	FontManager fonts;

	public void setup(){

		size(1024,768,GLGraphics.GLGRAPHICS);
		frameRate(60);

		scene = new Scene(this);
		scene.disableKeyboardHandling();
		scene.setRadius(1000);
		scene.camera().setPosition(new PVector(0,0,3000))	;

		gfx = new ToxiclibsSupport(this);

		fonts = new FontManager(this);

		verlet = new VerletPhysics();
		verlet.addBehavior(new GravityBehavior(new Vec3D(0,.01f,0)));

		// como estoy usando proscene el centro es 0,0,0 
		staff = new Staff(this);
		
		staff.setPosition(new Vec3D(0,-400,0));
		staff.setDimensions(width, 128,60);		
		staff.setVerletPhysics(verlet);
		staff.addStaffLines(30);


		sineWave = new SineWave(0	, .01f, .5f, 0.5f	);

	}


	public void draw(){
		// proscene maneja el background sino todo mal
		scene.background(127);

		verlet.update();

		GLGraphics renderer = (GLGraphics)g;
		renderer.beginGL();
		
		// todo lo que dibuje se va a dibujar usando GLModel.
		// o usando funciones directas en opengl
		// el GLModel tiene que ser un objeto parte de la clase
		// por eso hago render() y no draw();
		staff.render();
		

		renderer.endGL();

		


		// dibujo un circulito en algun punto del path
		pushMatrix();
		float val = sineWave.update();
		ellipseMode(CENTER);
		//		Vec3D pointInLine = cord.getCordPointAt(val - 0.000001f);
		//		translate(pointInLine.x , pointInLine.y, pointInLine.z);		
		ellipse(0,0,15,15);
		popMatrix();






		// para dibujar en la pantalla usando proscene
		// tengo que setear de nuevo la perspectiva default de processing
		// existe un metodo scene.beginScreenDrawing() pero no funciona si muevo la camara 
		hint(DISABLE_DEPTH_TEST);
		pushMatrix();
		// Since proscene handles the projection in a slightly different manner
		// we set the camera to Processing default values before calling camera():
		float cameraZ = ((height/2.0f) / tan(PI*60.0f/360.0f));
		perspective(PI/3.0f, scene.camera().aspectRatio(), cameraZ/10.0f, cameraZ*10.0f);
		camera();

		fill(255);
		textSize(14);
		text(frameRate,20 ,20);
		popMatrix();

	}





	void textAroundCurve(String message){
		float r = 100;
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
		if(key == 's'){
			//			cord.setRigid();
		};
		if(key == 'S'){
			//			cord.release();
		};

	}


}
