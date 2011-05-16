package src;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.events.StartDocument;

import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Spline3D;
import toxi.geom.Vec3D;
import toxi.physics.ParticlePath;

/**
 * representa una linea de pentagrama en el espacio
 * va a tener un punto de inicio y uno de final
 * y muchos puntos intermedios para comandar la deformacion
 * 
 * @author Diex
 *
 */
public class Line {
	
	private Spline3D path;
	private PApplet parent;
	// los puntos para crear la curva
	private ArrayList handles = new ArrayList();
	// los handlers
	private ArrayList vertices = new ArrayList();
	// los puntos interpolados de la curva
	private ArrayList points = new ArrayList();
	
	private Vec3D beggin, end;
	
	public Line(PApplet parent, Vec3D beggin, Vec3D end) {
		
		this.beggin = beggin;
		this.end	= end;
		
		path = new Spline3D();
	
		this.parent = parent;	
		
		createSpline();
	}
	
	
	// para crear un spline necesito lo minimo cuatro puntos
	// los dos puntos iniciales los voy a setear en el constructor
	// tambien voy a necesitar
	// ahora estoy creando solo dos controles al azar
	
	private void createSpline(){
		
		
		
		
		
		
		path.add(beggin);
		
		
		for(int i = 0 ; i < 2 ; i++){
			
			float x = (end.x - beggin.x) * .33f * (i) + (end.x - beggin.x) * .33f ;
			
			float y = 0;
			float z = 0; 
			
			parent.print(":" + x);
			parent.print(":" + y);
			parent.print(":" + z);
			parent.println();
			
			
			path.add(x , y , z );
		}
		
		path.add(end);
		
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
		System.out.println(points.size());
		return (Vec3D) points.get( (int) (points.size() * position));
		
	}
	
}
