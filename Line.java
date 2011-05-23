package src;

import java.util.Iterator;

import processing.core.PApplet;
import toxi.geom.Vec3D;


public class Line implements IRendereable{

	PApplet parent;
	// el staff al que pertenezco
	// esto es super importante porque el staff es quien tiene la data de donde se dibuja
	Staff 	staff;


	// la linea es una cuerda
	Cord	cord;
	// una linea en realidad puede ser un espacio
	boolean isLine;



	public Line(Cord cord, boolean isLine) {

		this.cord = cord;
		this.staff = staff;
		this.isLine = isLine;

	}



	@Override
	public void draw() {
		// dibujo la line que une los handlers
		parent.pushMatrix();
		parent.noFill();
		parent.beginShape();
		//
		
		////////// DANGER ACTUALIZO ACA !!!
		cord.update();
		
		for(Iterator i= cord.getCordPoints(.2f).iterator(); i.hasNext(); ) {
			Vec3D v = (Vec3D) i.next();
			parent.vertex(v.x,v.y,v.z);
		}

		parent.endShape();
		parent.popMatrix();


	}



	@Override
	public void setRenderer(PApplet p) {
		this.parent = p ;	
	}

}
