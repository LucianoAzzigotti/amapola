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
	
	private int controlPoints = 4;
	
	private Vec3D beggin, end;
	
	private int lenght;
	
	private ParticleString string;
	private VerletPhysics physics;
	
	public Cord(VerletPhysics physics, Vec3D beggin, Vec3D end, int controlPoints) {
		
		this.physics 			= physics;
		this.beggin 			= beggin;
		this.end				= end;
		this.controlPoints 		= controlPoints;
		
		createSpring();
		createSpline();
	}
	
	
	
	private void createSpring(){
		
		// calculo la direcci—n en la que se tiene que dibujar la cuerda
		Vec3D direction = end.sub(beggin);	
		
		// calculo la distancia de separaci—n entre las particulas
		float distanceBetweenNodes = beggin.distanceTo(end) / controlPoints;
		app.logger.log(Level.INFO, "Distance between nodes: " + Float.toString(distanceBetweenNodes));
		direction.normalize();
		
		// calculo el paso entre los nodos de la cuerda
		Vec3D step = direction.scale(distanceBetweenNodes);
		app.logger.log(Level.INFO, "Step " + step.toString());
		
		Vec3D particlePosition = new Vec3D();
		// calculo la posicion de cada una de las particulas en funcion del origen
		particlePosition.addSelf(beggin);
		
		float mass = 1;
		float strenght = 1;
		
		ArrayList<VerletParticle> particles = new ArrayList<VerletParticle>();
		
		// la primer particula va fija por eso la creo aparte
		particles.add(new VerletParticle(beggin.x, beggin.y, beggin.z, 1).lock());
		
		for(int i = 0 ; i < controlPoints ; i++){
			
			particlePosition.addSelf(step);
			app.logger.log(Level.INFO, "Particle position: " + particlePosition.toString());
				
			particles.add(new VerletParticle(particlePosition.x, particlePosition.y, particlePosition.z, 1));
		
		}
		
		//idem particula inicial
		particles.add(new VerletParticle(end.x, end.y, end.z, 1).lock());
		
		// creo la cuerda de resortes
		string = new ParticleString(physics, particles, strenght);
	
	}
	
	// creo el spline que representa la cuerda
	private void createSpline(){

		path = new Spline3D();		
		
		for(Iterator<VerletParticle> i = string.particles.iterator(); i.hasNext();){
			path.add(i.next());
		}
		
		// vertices = (ArrayList) path.computeVertices(8);
				
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
		
		for(Iterator i = string.links.iterator(); i.hasNext();){
			((VerletSpring) i.next()).setStrength(10);
		}
	}
	
	public ArrayList getDecimatedPoints(float step){
		points = (ArrayList) path.getDecimatedVertices(step);
		app.logger.log(Level.INFO, "Points in line: " + points.size());
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
