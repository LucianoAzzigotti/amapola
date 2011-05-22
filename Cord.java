package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import javax.xml.stream.events.StartDocument;

import com.sun.media.jai.opimage.NeuQuantOpImage;

import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Spline3D;
import toxi.geom.Vec3D;
import toxi.physics.ParticlePath;
import toxi.physics.ParticleString;
import toxi.physics.VerletParticle;
import toxi.physics.VerletPhysics;
import toxi.physics.VerletSpring;

/**
 * representa una linea de pentagrama en el espacio
 * va a tener un punto de inicio y uno de final
 * y muchos puntos intermedios para comandar la deformacion
 * 
 * @author Diex
 *
 */
public class Cord {
	
	private PApplet parent;
	private Spline3D path;
	private int defaultRes = 8;
	
	// los puntos para crear la curva
	private ArrayList handles = new ArrayList();

	private ArrayList vertices = new ArrayList();
	
	// los puntos interpolados de la curva
	private ArrayList points = new ArrayList();
	
	private int controlPointsQty = 4;
	
	private Vec3D beggin, end;
	
	private int lenght;
	
	ArrayList<VerletParticle> springNodes = new ArrayList<VerletParticle>();
	ArrayList<>
	private ParticleString string;
	private float distanceBetweenNodes;
	float mass = 1;
	float strenght = 3;
	
	
	private VerletPhysics physics;
	
	public Cord(VerletPhysics physics, Vec3D beggin, Vec3D end, int controlPoints) {
		
		this.physics 			= physics;
		this.beggin 			= beggin;
		this.end				= end;
		this.controlPointsQty 		= controlPoints;
		
		createSpring();
		createSpline();
	}
	
	
	
	private void createSpring(){
		
		// calculo la direcci—n en la que se tiene que dibujar la cuerda
		// como el vector que apunta desde el beggin al end
		Vec3D direction = end.sub(beggin);	
		direction.normalize();
		
		
		// calculo la distancia de separaci—n entre las particulas
		distanceBetweenNodes = beggin.distanceTo(end) / controlPointsQty;
		app.logger.log(Level.INFO, "Distance between nodes: " + Float.toString(distanceBetweenNodes));
		
		// calculo el paso entre los nodos de la cuerda
		Vec3D step = direction.scale(distanceBetweenNodes);
		app.logger.log(Level.INFO, "Step " + step.toString());

		// calculo la posicion de cada una de las particulas en funcion del origen
		Vec3D particlePosition = new Vec3D();
		particlePosition.addSelf(beggin);
		
		// la primer particula va fija por eso la creo aparte
		springNodes.add(new VerletParticle(beggin.x, beggin.y, beggin.z, 1).lock());
		
		for(int i = 0 ; i < controlPointsQty ; i++){	
			particlePosition.addSelf(step);
			app.logger.log(Level.INFO, "Particle position: " + particlePosition.toString());			
			springNodes.add(new VerletParticle(particlePosition.x, particlePosition.y, particlePosition.z, 1));
		
		}
		
		//idem particula inicial
		springNodes.add(new VerletParticle(end.x, end.y, end.z, 1).lock());
		
		// creo la cuerda de resortes
		string = new ParticleString(physics, springNodes, strenght);
	
	}
	
	// creo el spline que representa la cuerda
	private void createSpline(){

		path = new Spline3D();		
		
		for(Iterator<VerletParticle> i = string.particles.iterator(); i.hasNext();){
			path.add(i.next());
		}
		
	
				
	}
	
	
	public ArrayList getHandlers(){
		return (ArrayList) path.getPointList();
	}
	
	// siempre que modifico alguno de los handlers tengo que llamar
	// luego a la funcion compute vertices para actualizar
	
	public ArrayList computeVertices(int res){

		// actualizar los handlers con las particulas
		ArrayList <Vec3D> particles = new ArrayList<Vec3D>() ;
		
		for(Iterator it = string.particles.iterator(); it.hasNext(); ){
			particles.add((Vec3D)it.next());	
		}
		
		path.setPointList(particles);
		
		ArrayList<Vec3D> vertices = (ArrayList) path.computeVertices(res);
			
		return vertices;
	}
	
	
	public void setRigid(){
		
		app.logger.log(Level.INFO, "Setting rigid: " + distanceBetweenNodes);
		
		VerletSpring sp ;
		
		for(Iterator i = string.links.iterator(); i.hasNext();){
			sp = ((VerletSpring) i.next());
			app.logger.log(Level.INFO, "Current length: " + sp.getRestLength());
			app.logger.log(Level.INFO, "Current strngh: " + sp.getStrength());
			sp.setRestLength(distanceBetweenNodes);
		}
	}
	
	public ArrayList getDecimatedPoints(float step){
		points = (ArrayList) path.getDecimatedVertices(step);
//		app.logger.log(Level.INFO, "Points in line: " + points.size());
		return points;
	}
	
	public Vec3D getPointAt(float position){
		// tengo que calcular los puntos 
//		points = (ArrayList) path.getDecimatedVertices(res);
//		System.out.println(points.size());
//		if(points == null) points = (ArrayList) path.getDecimatedVertices(defaultRes);
		return (Vec3D) points.get( (int) (points.size() * position));
		
	}

	public ArrayList getStringParticles(){
		return (ArrayList) string.particles;
	}

	public Vec3D getHandler(int i) {
		return path.getPointList().get(i);
	}
	
}
