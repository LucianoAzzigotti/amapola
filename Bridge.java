package src;

import java.util.ArrayList;

import remixlab.proscene.Frame;
import toxi.geom.Vec3D;
import util.Util;

/**
 * bridge se va a encargar de ubicar las lineas y espacios
 * ademas tiene la responsabilidad de ubicar el pentagrama segun la cantidad de lineas que se vayan mostrando
 * esto es: si agrego lineas adicionales es el bridge el que se mueve para dar lugar a las nuevas lineas tomando como referencia
 * el score.
 * EL bridge es un objeto fisico por lo tanto debe ser ubicable en el espacio
 * 
 * @author Diex
 *
 */
public class Bridge {

	Frame	origin;
	int lineStep;
	int numLines = 30;
	
	ArrayList<Line> lines;
	
	// el puente se tiene que encargar de mostrarme las lineas que tengo que ver
	/// o sea tiene que poder ubicar las lineas de manera tal que coincidan
	// las notas con la posicion en el puente.
	// para eso hay que definir una parte visible del puente y una invisible que se va haciendo visible 
	// segun la necesida...
	
	
	
	public Bridge(Vec3D position){

		origin = new Frame();
		origin.setPosition(Util.Vec3DtoPVector(position));
		this.numLines = numLines;
		lines = new ArrayList<Line>();

	}
	
	
	
	public void addLine(Cord cord, int position, boolean isLine){
		lines.add(new Line(cord,isLine));		
	}
	
	
}
