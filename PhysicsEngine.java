package src;

import toxi.physics.*;
import toxi.physics.constraints.*;
import toxi.physics.behaviors.GravityBehavior;
import toxi.geom.*;

public class PhysicsEngine {

	private VerletPhysics physics;

	private static final PhysicsEngine engine = new PhysicsEngine();
	 

	public PhysicsEngine getInstance(){
		return engine;
	}
	
	public PhysicsEngine(){
		// setup physics engine
		physics = new VerletPhysics();
		physics.addBehavior(new GravityBehavior(Vec3D.Y_AXIS.scale(0.0f))  );

	}

	public  VerletPhysics physics(){return physics;}
	
	
	public  ParticleString createString(int numParticles, int restLenght){
		// string start position & direction
		Vec3D startPos=new Vec3D( - numParticles/2 * restLenght  , 0, 0);
		Vec3D dir=new Vec3D(restLenght,0,0);

		// create the string as a line along the direction vector
		ParticleString string=new ParticleString(physics, startPos, dir, numParticles, 1, 0.5f);

		// anchor the 1st particle in space 
		string.getHead().lock();
		string.getTail().lock();

		return string;

	}
}
