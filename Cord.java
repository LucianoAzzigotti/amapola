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
	// cuantas divisiones le hago al spline entre nodo y nodo
	int splineResolution = 20;

	private ArrayList vertices = new ArrayList();

	// los puntos interpolados de la curva
	private ArrayList points = new ArrayList();

	private int controlPointsQty = 4;

	private Vec3D beggin, end;

	private int lenght;

	ArrayList<VerletParticle> springNodes; 
	// ArrayList<>

	// aca esta el problema
	// el string que creo tiene que tener la capacidad de rigidizarse
	// para rigidizarlo tengo que volver a ubicar las particulas en la posicion en las que
	// fueron creadas y lockearlas
	private ParticleString string;

	private float distanceBetweenNodes;
	float mass = 1;
	float strenght = 1;


	private VerletPhysics physics;

	public Cord(VerletPhysics physics, Vec3D beggin, Vec3D end, int controlPoints) {

		this.physics 			= physics;
		this.beggin 			= beggin;
		this.end				= end;
		this.controlPointsQty 		= controlPoints;

		createSpring();
		createSpline();
	}


	// creo la cadena que conecta las particulas que controlan el spline
	private void createSpring(){

		springNodes = new ArrayList<VerletParticle>();
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



	public void update(){

		// cada uno de los handlers es una particula que flota en el espacio
		// actualizar los handlers con la posicion de esa particula

		ArrayList <Vec3D> particles = new ArrayList<Vec3D>() ;

		for(Iterator it = string.particles.iterator(); it.hasNext(); ){
			particles.add((Vec3D)it.next());	
		}

		// no tengo manera de mover los puntos
		// hay que cargar un nuevo arrayList de puntos
		// y computar el spline ocmo si fuera uno nuevo.
		path.setPointList(particles);


		path.computeVertices(splineResolution);


	}

	// creo un nuevo string pero dejo las particulas lockeadas
	
	public void setRigid(){
		createSpring();
		
		for(Iterator i = string.particles.iterator(); i.hasNext();){
			VerletParticle vp = (VerletParticle) i.next();
			vp.lock();
		}
	}
	
	public void release(){
		// tengo que soltar todas menos las de la punta
		ArrayList<VerletParticle> vp = (ArrayList) string.particles; 	
		for( int i = 1 ; i < vp.size() - 1 ; i++){
			vp.get(i).unlock();
		}
		
	}

	
	public ArrayList getCordPoints(float step){
		points = (ArrayList) path.getDecimatedVertices(step);
		//		app.logger.log(Level.INFO, "Points in line: " + points.size());
		return points;
	}

	public Vec3D getCordPointAt(float position){
		// tengo que calcular los puntos 
		//		points = (ArrayList) path.getDecimatedVertices(res);
		//		System.out.println(points.size());
		//		if(points == null) points = (ArrayList) path.getDecimatedVertices(defaultRes);
		return (Vec3D) points.get( (int) (points.size() * position));

	}

	// este metodo es util para debuggear
	public ArrayList getStringParticles(){
		return (ArrayList) string.particles;
	}

	public Vec3D getHandler(int i) {
		return path.getPointList().get(i);
	}

	// levanto todos los puntos para poder dibujar algo.
	public ArrayList getControlPoints(){
		return (ArrayList) path.getPointList();
	}


}
