package src;

import processing.core.PApplet;
import toxi.geom.AABB;
import toxi.geom.Vec3D;
import toxi.processing.ToxiclibsSupport;

public class Staff extends AABB implements IRendereable{
	
	PApplet parent;
	ToxiclibsSupport gfx;
	
	Bridge leftBridge;
	Bridge rightBridge;

	Line[] lines = new Line[30]; 
	
	KeyManager km;
	int key = Key.SOL;
	
	
	public Staff(Vec3D centerPoint, int w, int l, int h ) {
		super();
		setPosition(centerPoint);
		setDimensions(w, l, h);	
	}
	
	
	public void addRigthBridge(){
		rightBridge = new Bridge(calculateRightBoundPlaneCenter(), getExtent().y * 2);
		rightBridge.setRenderer(parent);
		
	}
	public void addLeftBridge(){
		
		leftBridge = new Bridge(calculateLeftBoundPlaneCenter(), getExtent().y * 2);
		leftBridge.setRenderer(parent);
	
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
	
		} catch (NullPointerException e) {
			System.out.println("NO EXISTE EL PApplet");
			e.printStackTrace();
			
		}
		
		
		
	}
	
	
	Vec3D beggin(){
		return null;
	}

	@Override
	public void setRenderer(PApplet p) {
		parent = p;
		gfx = new ToxiclibsSupport(parent);
		
	}
	
	

	
}
