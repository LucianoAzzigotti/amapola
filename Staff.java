package src;

import java.util.ArrayList;
import java.util.Iterator;

import codeanticode.glgraphics.GLGraphics;

import processing.core.PApplet;
import toxi.geom.AABB;
import toxi.geom.Vec3D;
import toxi.physics.VerletPhysics;
import toxi.processing.ToxiclibsSupport;

public class Staff implements IRendereable, IVerletObject{
	
	VerletPhysics verlet;
	PApplet parent;

	//  el staff tiene dos puentes de donde se van a colgar las cuerdas (lineas)
	Bridge leftBridge;
	Bridge rightBridge;

	// y tambien tiene un bounding
	AABB  bounding; 
	GLMesh boundingModel;
	
	ArrayList<Line> lines = new ArrayList<Line>(); 
	
	KeyManager km;
	int key = Key.SOL;
	
	private int firstLineToShow = 12;  // el E de la clave de sol
	private int lastLineToShow = 20;

	
	
	public Staff(PApplet p) {
		bounding = new AABB();
		// le paso el renderer
		setRenderer(p);
		
	}
	
	
	
	
	private Vec3D calculateLeftBoundPlaneCenter(){	
		return new Vec3D( 	bounding.x - bounding.getExtent().x, 
							bounding.y ,
							bounding.z  );
	}
	
	private Vec3D calculateRightBoundPlaneCenter(){	
		return new Vec3D( 	bounding.x + bounding.getExtent().x, 
							bounding.y ,
							bounding.z  );
	}
	
	private void addRigthBridge(){
		rightBridge = new Bridge(parent, calculateRightBoundPlaneCenter(), bounding.getExtent().y * 2);
	}
	
	private void addLeftBridge(){
		leftBridge = new Bridge(parent, calculateLeftBoundPlaneCenter(), bounding.getExtent().y * 2);
	}
	
	private void createBoundingModel(){
		boundingModel = new GLMesh(parent, bounding.toMesh(), true);
	}

	
	
	
	
	public void setPosition(Vec3D centerPoint){
		bounding.set(centerPoint);
		
	}
	
	public void setDimensions(int w, int l, int h){
		bounding.setExtent(new Vec3D(w,l,h));
		createBoundingModel();
	}
	


	public void addStaffLines(int qty){
		// antes de crear las lineas tengo que crear 
		// los bridges desde donde se van a conectar		

		addRigthBridge();
		rightBridge.divide(qty+1);
		
		addLeftBridge();
		leftBridge.divide(qty+1);
		
		
		// ahora creo las cuerdas que se conectan a los bridges
		// para pasarleselos luego a las las lineas
		// el cord es la entidad abstracta
		// y la linea la que efectivamente se dibuja
		for(int i = 0 ; i < qty; i++){
			// creo una cuerda y la conecto en el bridge en el respectivo plug
			Cord cord = new Cord( verlet);
			cord.connect(leftBridge.plugs.get(i), rightBridge.plugs.get(i));
			cord.setRigid();
			// a la linea le paso la cuerda
			// la linea tiene que saber donde ubicar las notas y demas
			Line line = new Line(parent,cord,i%2==0);		
			line.setRenderer(parent);			
			line.setColor(1,1,1,1);
			lines.add(line);
		}	
	}
	
	
	
	
	public void render(){
		
		try {
			
			// dibujo el bounding
			boundingModel.getModel().render();
			
			// y dibujo los puentes
			leftBridge.render();
			rightBridge.render();

			// dibujo las lineas
			for(int i = 0 ; i < lines.size() ; i ++){
				if(i >= firstLineToShow && i <= lastLineToShow)
				lines.get(i).render();
			}
			 
	
		} catch (NullPointerException e) {
			System.out.println("NO EXISTE EL PAPPLET");
			e.printStackTrace();
			
		}
		
		
		
	}
	
	// para configurar que lineas quiero ver efectivamente (lineas y espacios adicionales)
	private void setLineShowing(int minLine, int maxLine){
		
	}
	
	private void setRenderer(PApplet p) {
		parent = p;	
	}



	@Override
	public void setVerletPhysics(VerletPhysics vp) {
		this.verlet = vp;	
	}
	
	
	

	
}
