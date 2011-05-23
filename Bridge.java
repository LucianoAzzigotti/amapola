package src;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;

import remixlab.proscene.Frame;
import toxi.geom.Line3D;
import toxi.geom.Vec3D;
import util.Util;

/**
 * bridge se va a encargar de ubicar las lineas y espacios
 * ademas tiene la responsabilidad de ubicar el pentagrama segun la cantidad de lineas que se vayan mostrando
 * esto es: si agrego lineas adicionales es el bridge el que se mueve para dar lugar a las nuevas lineas tomando como referencia
 * el score.
 * EL bridge es un objeto fisico por lo tanto debe ser ubicable en el espacio
 * 
 * @author Diex
 *
 */
public class Bridge implements IRendereable{

	PApplet parent;
	
	Frame	origin;
	
	Line3D	backbone;
	Vec3D top ;
	Vec3D bottom ;
	
	ArrayList<Vec3D> plugs = new ArrayList<Vec3D>();
	int lineStep;
	
	int numLines = 30;	
	ArrayList<Line> lines;
	
	// todo en el sistema se dibuja centrado
	
	public Bridge(Vec3D position, float height){
		
		// el puente tiene un punto central
		// en base a ese punto calculo otros dos puntos para dibujar la columna vertebral
		// esas posiciones se van a estar manipulando todo el tiempo en funcion de como se agrande
		// o se achique el bounding
		
		top = position.add(0, height/2, 0);
		bottom = position.add(0, -height/2, 0);
		
		// creo una linea de arriba a abajo
		// sobre esa linea imaginaria se van a colgar las lineas del pentagrama
		backbone = new Line3D(top,bottom);
		
		this.numLines = numLines;
		lines = new ArrayList<Line>();

		
//		origin = new Frame();
//		origin.setPosition(Util.Vec3DtoPVector(position));
		
	}
	
	
	public Vec3D getTop(){
		return backbone.a;
	}
	
	public Vec3D getBottom(){
		return backbone.b;
	}
	
	
	public void draw(){
		
		parent.pushStyle();
		parent.stroke(255,0,0);
		parent.line(getTop().x,getTop().y,getTop().z, getBottom().x,getBottom().y,getBottom().z);
		
		parent.pushStyle();

		parent.fill(0,0,255);
		
		for(Iterator<Vec3D> i = plugs.iterator(); i.hasNext();){
		
			Vec3D plug = i.next();
			
			parent.pushMatrix();
			parent.translate(plug.x, plug.y, plug.z);
			parent.ellipse(0, 0, 5, 5);
			parent.popMatrix();
		}
		
		parent.popStyle();
	}
	
	public void divide(int qty){
		
		for(int i = 0 ; i < qty; i ++){
			backbone.splitIntoSegments(plugs, backbone.getLength() / qty, false);
		}
		
	}

	@Override
	public void setRenderer(PApplet p) {
		parent = p;
	}
	
	
}
