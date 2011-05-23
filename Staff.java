package src;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import toxi.geom.AABB;
import toxi.geom.Vec3D;
import toxi.physics.VerletPhysics;
import toxi.processing.ToxiclibsSupport;

public class Staff extends AABB implements IRendereable, IVerletable{
	
	VerletPhysics verlet;
	PApplet parent;
	ToxiclibsSupport gfx;
	
	Bridge leftBridge;
	Bridge rightBridge;

	ArrayList<Line> lines = new ArrayList<Line>(); 
	
	KeyManager km;
	int key = Key.SOL;
	
	
	public Staff(Vec3D centerPoint, int w, int l, int h ) {
		super();
		setPosition(centerPoint);
		setDimensions(w, l, h);	
	}
	
	
	
	private Vec3D calculateLeftBoundPlaneCenter(){
		
		return new Vec3D( 	x - getExtent().x, 
							y ,
							z  );
	}
	

	private Vec3D calculateRightBoundPlaneCenter(){
		
		return new Vec3D( 	x + getExtent().x, 
							y ,
							z  );
	}
	
	
	// antes de crear las lineas tengo que crear puntos para poder manipularas.
	// eso lo hace el bridge
	
	public void addStaffLines(int qty){
		
		leftBridge.divide(qty+1);
		rightBridge.divide(qty+1);
		
		
		for(int i = 0 ; i < qty; i++){
			
			Cord cord = new Cord(verlet,leftBridge.plugs.get(i), rightBridge.plugs.get(i));
			
			Line line = new Line(cord,false);			
			line.setRenderer(parent);
			
			lines.add(line);
		}
			
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void addRigthBridge(){
		rightBridge = new Bridge(calculateRightBoundPlaneCenter(), getExtent().y * 2);
		rightBridge.setRenderer(parent);
		
	}
	public void addLeftBridge(){
		
		leftBridge = new Bridge(calculateLeftBoundPlaneCenter(), getExtent().y * 2);
		leftBridge.setRenderer(parent);
	
	}
	
	
	
	public void setPosition(Vec3D centerPoint){
		set(centerPoint);
	}
	
	public void setDimensions(int w, int l, int h){
		setExtent(new Vec3D(w,l,h));
	}
	
	
	public void draw(){
		
		try {
			
			parent.pushStyle();
			parent.noFill();
			parent.stroke(255);
			gfx.box(this,true);
			
			leftBridge.draw();
			rightBridge.draw();
			
			parent.popStyle();
			
			// dibujo las lineas
			for(Iterator<Line> i = lines.iterator() ; i.hasNext() ; ){
				i.next().draw();
			}
			
			
	
		} catch (NullPointerException e) {
			System.out.println("NO EXISTE EL PApplet");
			e.printStackTrace();
			
		}
		
		
		
	}
	
	
	@Override
	public void setRenderer(PApplet p) {
		parent = p;
		gfx = new ToxiclibsSupport(parent);
		
	}



	@Override
	public void setVerletPhysics(VerletPhysics vp) {
		this.verlet = vp;	
	}
	
	

	
}
