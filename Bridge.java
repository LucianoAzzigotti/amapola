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
	
	ArrayList<Cord> lineas;
	
	public Bridge(Vec3D position){

		origin = new Frame();
		origin.setPosition(Util.Vec3DtoPVector(position));
		
		this.numLines = numLines;
		
		// ubicar las lineas
		
		
		
		// crear las n lineas
		
		
		// 
		
	}
	
	
	private void createLines(){
		lineas = new ArrayList<Cord>();
		
		for (int line = 0; line < numLines; line++ ){
			lineas.add(createLine(line));
		}
		
	}
	
	private Cord createLine(int cualLinea){
		
		return new Cord(beggin, end, numPoints);
	}
	
}
