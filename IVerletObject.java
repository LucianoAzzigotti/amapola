package src;

import toxi.physics.VerletPhysics;
/**
 * las clases que implementana esta interfase son las que pueden interactuar con 
 * el sistema de particulas
 * 
 * @author Diex
 *
 */
public interface IVerletObject {
	public void setVerletPhysics(VerletPhysics vp);
}
