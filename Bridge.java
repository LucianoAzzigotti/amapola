package src;

import java.util.ArrayList;
import java.util.Iterator;

import codeanticode.glgraphics.GLGraphics;
import codeanticode.glgraphics.GLModel;

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
	
	GLModel backboneModel;
	GLModel plugModel;
	
	
	Frame	origin;

	Line3D	backbone;
	Vec3D top ;
	Vec3D bottom ;
	
	ArrayList<Vec3D> plugs = new ArrayList<Vec3D>();
	int lineStep;
	
	int numLines = 30;	
	ArrayList<Line> lines;
	
	// todo en el sistema se dibuja centrado
	
	public Bridge(PApplet parent , Vec3D position, float height){
		this.parent = parent;
		
		// el puente tiene un punto central
		// en base a ese punto calculo otros dos puntos para dibujar la columna vertebral
		// esas posiciones se van a estar manipulando todo el tiempo en funcion de como se agrande
		// o se achique el bounding
		
		top = position.add(0, height/2, 0);
		bottom = position.add(0, -height/2, 0);
		
		// creo una linea de arriba a abajo
		// sobre esa linea imaginaria se van a colgar las lineas del pentagrama
		backbone = new Line3D(bottom,top);
		
		this.numLines = numLines;
		lines = new ArrayList<Line>();

		createBackboneModel();

		
//		origin = new Frame();
//		origin.setPosition(Util.Vec3DtoPVector(position));
		
	}
	
	private void createBackboneModel(){
	
		backboneModel = new GLModel(parent, 2, GLModel.LINES, GLModel.STATIC) ;
		
		
		backboneModel.beginUpdateVertices();
		backboneModel.updateVertex(0, getTop().x	,getTop().y		,getTop().z);
		backboneModel.updateVertex(1, getBottom().x	,getBottom().y	,getBottom().z);
		backboneModel.endUpdateVertices();
		
		backboneModel.initNormals();
		backboneModel.beginUpdateNormals();
		backboneModel.updateNormal(0, getTop().x	,getTop().y		,getTop().z);
		backboneModel.updateNormal(1, getBottom().x	,getBottom().y	,getBottom().z);
		backboneModel.endUpdateNormals();
		
		
		backboneModel.initColors();
		backboneModel.setColors(255,0,0,255);
		
		
		
		plugModel = new GLModel(parent, 4, GLModel.QUAD, GLModel.STATIC);
		
		plugModel.beginUpdateVertices();
		plugModel.updateVertex(0, -3, -3, 0);
		plugModel.updateVertex(1,  3, -3, 0);
		plugModel.updateVertex(2,  3, 3, 0);
		plugModel.updateVertex(3,  -3, 3, 0);
		plugModel.endUpdateVertices();
		
		plugModel.initColors();
		plugModel.setColors(255,0,0,255);

	//	renderer.popStyle();

		
	}
	
	
	public Vec3D getTop(){
		return backbone.a;
	}
	
	public Vec3D getBottom(){
		return backbone.b;
	}
	
	
	
	
	
	
	public void divide(int qty){	
		for(int i = 0 ; i < qty; i ++){
			backbone.splitIntoSegments(plugs, backbone.getLength() / qty, false);
		}	
	}
	
	public void setRenderer(PApplet p) {
		parent = p;
	}

	


	public void render() {
		backboneModel.render();

		for(Iterator<Vec3D> i = plugs.iterator(); i.hasNext();){
		
			Vec3D plug = i.next();
			parent.pushMatrix();
			parent.translate(plug.x, plug.y, plug.z);
			plugModel.render();
			parent.popMatrix();
	
		}
		
		
	}
	
	
}
