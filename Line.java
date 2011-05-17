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

/**
 * representa una linea de pentagrama en el espacio
 * va a tener un punto de inicio y uno de final
 * y muchos puntos intermedios para comandar la deformacion
 * 
 * @author Diex
 *
 */
public class Line {
	
	private PApplet parent;
	
	
	private Spline3D path;
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
	
	public Line(VerletPhysics physics, Vec3D beggin, Vec3D end, int controlPoints) {
		
		this.physics 			= physics;
		this.beggin 			= beggin;
		this.end				= end;
		this.controlPoints 		= controlPoints;
		
		createSpring();
		createSpline();
	}
	
	
	
	private void createSpring(){
		
		Vec3D direction = end.sub(beggin);	
		
		float distanceBetweenPoints = beggin.distanceTo(end) / controlPoints;
		app.logger.log(Level.INFO, "Distance " + Float.toString(distanceBetweenPoints));
		
		direction.normalize();
		
		Vec3D step = direction.scale(distanceBetweenPoints, distanceBetweenPoints, distanceBetweenPoints);
		app.logger.log(Level.INFO, "Step " + step.toString());
		
		Vec3D particlePosition = new Vec3D();
		particlePosition.addSelf(beggin);
		
		float mass = 1;
		
		float strenght = 1;
		
		ArrayList<VerletParticle> particles = new ArrayList<VerletParticle>();
		
		
		particles.add(new VerletParticle(beggin.x, beggin.y, beggin.z, 1).lock());
		
		for(int i = 0 ; i < controlPoints ; i++){
			
			particlePosition.addSelf(step);
			app.logger.log(Level.INFO, particlePosition.toString());
				
			particles.add(new VerletParticle(particlePosition.x, particlePosition.y, particlePosition.z, 1));
		
		}
		
		particles.add(new VerletParticle(end.x, end.y, end.z, 1).lock());
		
		string = new ParticleString(physics, particles, strenght);
	
	}
	
	// para crear un spline necesito lo minimo cuatro puntos
	// los dos puntos iniciales los voy a setear en el constructor
	// tambien voy a necesitar
	// ahora estoy creando solo dos controles al azar
	
	private void createSpline(){

		path = new Spline3D();		
		
		for(Iterator<VerletParticle> i = string.particles.iterator(); i.hasNext();){
			path.add(i.next());
		}
		
		vertices = (ArrayList) path.computeVertices(8);
				
	}
	
	
	public ArrayList getHandlers(){
		return (ArrayList) path.getPointList();
	}
	
	// siempre que modifico alguno de los handlers tengo que llamar
	// luego a la funcion compute vertices para actualizar
	
	public ArrayList computeVertices(int res){
		return vertices = (ArrayList) path.computeVertices(res);
	}
	
	public ArrayList getDecimatedPoints(int res){
		return points = (ArrayList) path.getDecimatedVertices(res);
	}
	
	public Vec3D getPointAt(float position, int res){
		// tengo que calcular los puntos 
		points = (ArrayList) path.getDecimatedVertices(res);
//		System.out.println(points.size());
		return (Vec3D) points.get( (int) (points.size() * position));
		
	}

	public ArrayList getStringParticles(){
		return (ArrayList) string.particles;
	}

	public Vec3D getHandler(int i) {
		return path.getPointList().get(i);
	}
	
}
