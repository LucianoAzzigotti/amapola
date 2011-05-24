package src;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.opengl.util.BufferUtil;

import codeanticode.glgraphics.GLGraphics;
import codeanticode.glgraphics.GLModel;

import processing.core.PApplet;
import processing.core.PVector;
import toxi.color.TColor;
import toxi.geom.Vec3D;
import util.Util;


public class Line implements IRendereable{

	PApplet parent;

	
	// la linea tiene una cuerda que es lo que
	// le da la vida
	Cord	cord;

	// una linea en realidad puede ser un espacio
	boolean isLine;
	
	GLModel lineSegment;

	TColor lineColor;


	public Line(PApplet parent, Cord cord, boolean isLine) {
		this.parent = parent;
		this.cord = cord;
		this.isLine = isLine;
		lineColor = TColor.newRGB(0, 0, 0);
		createLineSegment();

	}

	
	public void render() {
		
		////////// DANGER ACTUALIZO ACA !!!
		ArrayList<Vec3D> vertices = cord.update(40);
		//////////DANGER ACTUALIZO ACA !!!

		
		//FloatBuffer linePoints = FloatBuffer.allocate(vertices.size()*3);
		FloatBuffer linePoints = BufferUtil.newFloatBuffer(vertices.size()*3);
//		System.out.println(linePoints.capacity());
		
		for(int i = 0 ; i < vertices.size(); i++){ 
			
			linePoints.put(vertices.get(i).x);
//			System.out.print(vertices.get(i).x + ":");
			linePoints.put(vertices.get(i).y);
//			System.out.print(vertices.get(i).y + ":");
			linePoints.put(vertices.get(i).z);
//			System.out.print(vertices.get(i).z + ":");
//			System.out.println();	
		}
		
		
		linePoints.rewind();
		
		GLGraphics renderer = (GLGraphics) parent.g;		

		renderer.gl.glColor3f(lineColor.red() , lineColor.green(), lineColor.blue());
		
		// dibujo solo si es linea
		if(isLine){
			
		renderer.gl.glLineWidth(1);
		// habilito el dibujar por array
		renderer.gl.glEnableClientState(renderer.gl.GL_VERTEX_ARRAY);
		// medida del vertice - tipo de dato - desde cual arranco - el array con los puntos
		renderer.gl.glVertexPointer(3, renderer.gl.GL_FLOAT, 0, linePoints);
		// el modo - desde cual - cuantos dibujo
		renderer.gl.glDrawArrays(renderer.gl.GL_LINE_STRIP, 0, linePoints.limit() / 3 );

		}
		
		//----------------------------------------------------------
//		void ofLine(float x1,float y1,float x2,float y2){
//
//			// use smoothness, if requested:
//			if (bSmoothHinted) startSmoothing();
//
//			linePoints[0] = x1;
//			linePoints[1] = y1;
//			linePoints[2] = x2;
//			linePoints[3] = y2;
//
//			glEnableClientState(GL_VERTEX_ARRAY);
//			glVertexPointer(2, GL_FLOAT, 0, &linePoints[0]);
//			glDrawArrays(GL_LINES, 0, 2);
//
//			// back to normal, if smoothness is on
//			if (bSmoothHinted) endSmoothing();
//
//		}

		
		
		
//		lineSegment = new GLModel(parent, pVertices, GLModel.LINE_STRIP, GLModel.STATIC);
		
//		
//		for(int i = 0 ; i < pVertices.size() ; i++) {
//			
//			
//			
//			
//			lineSegment.beginUpdateVertices();
//			lineSegment.updateVertex(0, vertices.get(i).x,vertices.get(i).y,vertices.get(i).z);
//			lineSegment.updateVertex(1, vertices.get(i+1).x,vertices.get(i+1).y,vertices.get(i+1).z);
//			lineSegment.endUpdateVertices();
//			
//			
//			
//		
//		}
	
//		lineSegment.render();
		//parent.vertex(v.x,v.y,v.z);
	

	}

	
	public void setRenderer(PApplet p) {
		this.parent = p ;	
	}
	
	private void createLineSegment(){
		
		lineSegment = new GLModel(parent, 2, GLModel.LINE_STRIP, GLModel.STREAM);
		
		
		lineSegment.beginUpdateVertices();
		lineSegment.updateVertex(0, 0,0,0);
		lineSegment.updateVertex(1, 1,1,1);
		lineSegment.endUpdateVertices();
		
		lineSegment.initColors();
		lineSegment.setColors(0, 255, 0, 255);
		
		
	
	}
	
	void setColor(float r, float g, float b, float a){
		
	}

	/*
	 * 
	 * 
		// dibujo los handlers
		pushStyle();
		fill(0);
		rectMode(CENTER);

		//		for(Iterator i = cord.getControlPoints().iterator(); i.hasNext(); ) {
		//			Vec3D v=(Vec3D) i.next();
		//			
		//			pushMatrix();
		//			translate(v.x,v.y,v.z);
		//			rect(0,0,5,5);
		//			popMatrix();
		//			
		//		}
		popStyle();

		// dibujo las particulas solo para debugear 
		pushStyle();
		fill(255,0,0);
		ellipseMode(CENTER);
		//		for(Iterator i= cord.getStringParticles().iterator(); i.hasNext(); ) {
		//			Vec3D v=(Vec3D) i.next();
		//			pushMatrix();
		//			translate(v.x,v.y,v.z);
		//			ellipse(0,0,5,5);
		//			popMatrix();
		//
		//		}

		popStyle();

	 */
}
