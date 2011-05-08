package amapola;

import processing.core.PApplet;
import processing.core.PImage;
import toxi.geom.Rect;
import toxi.geom.Vec2D;
import toxi.math.ExponentialInterpolation;
import toxi.math.Interpolation2D;
import blobDetection.*;

/**
 * Es el objeto que se encarga de hacer el blob detection
 * Maneja la CV devolviendo informacion util para ser utilizada por las
 * demas clases
 * @author amapola
 */

public class Blobs {
	
	Vec2D v;
	
	PApplet parent ;
	BlobDetection bd;
	PImage source;
	public boolean debug = true;
	boolean isReady = false;

	Blobs(PApplet parent){
		this.parent = parent;
	}

	public void setup(PImage source){
	
		this.source = source;

		bd = new BlobDetection((int) (source.width), (int) (source.height));
		bd.setPosDiscrimination(true);
		bd.setThreshold(.4f);
		bd.setBlobMaxNumber(5);

		isReady = true;

	}

	public void setThreshold(float trh){ bd.setThreshold(trh);}


	public void computeBlobs(PImage source){

		bd.computeBlobs(source.pixels);

	}

	public boolean isReady(){return isReady;}

	// ==================================================
	// drawBlobsAndEdges()
	// ==================================================
	public void drawBlobsAndEdges(boolean drawBlobs, boolean drawEdges)
	{
		parent.noFill();
		Blob b;
		EdgeVertex eA,eB;
		
		for (int n=0 ; n<bd.getBlobNb() ; n++)
		{
			b=bd.getBlob(n);
			if (b!=null)
			{
				// Edges
				if (drawEdges)
				{
					parent.strokeWeight(3);
					parent.stroke(0,255,0);
					for (int m=0;m<b.getEdgeNb();m++)
					{
						eA = b.getEdgeVertexA(m);
						eB = b.getEdgeVertexB(m);
						if (eA !=null && eB !=null)
							parent.line(
									eA.x*parent.width, eA.y*parent.height, 
									eB.x*parent.width, eB.y*parent.height
							);
					}
				}

				// Blobs
				if (drawBlobs)
				{
					parent.strokeWeight(1);
					parent.stroke(255,0,0);
					parent.rect(
							b.xMin*parent.width,b.yMin*parent.height,
							b.w*parent.width,b.h*parent.height
					);
				}

			}

		}
	}
	
	
	/**
	 * devuelve el centro de gravedad del blob mas grande que encuentra
	 * en la imagen
	 * @return
	 */
	
	// usado para guardar el ultimo blob mas grande registrado en la imagen
	
	
	
	
	public Vec2D getCenter(){
		
		Rect r = new Rect();
		Blob b;
		Blob bigBlob = null;	
		float currentArea = 0;
		
		for (int n=0 ; n < bd.getBlobNb() ; n++){
			
			b = bd.getBlob(n);			
			r.set(0, 0, b.w, b.h);	
			if(r.getArea() > currentArea) bigBlob = b;
		}
		 		
		return new Vec2D(bigBlob.x * parent.width, bigBlob.y * parent.height);
	}
	
	
	
	Vec2D pc = new Vec2D(0,0);
	
	public Vec2D getInterpolatedCenter(){
		
	    Vec2D c = getCenter();
	    
	    float x = Util.ease(pc.x, c.x, 0.05f);
	    float y = Util.ease(pc.y, c.y, 0.05f);
		
	    Vec2D nc = new Vec2D(x,y);
	    pc = nc;
	    
	    return  nc;
	}
	
}
