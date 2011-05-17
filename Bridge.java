package src;

import java.util.ArrayList;

import toxi.geom.Vec3D;

public class Bridge {

	int lineH;
	int numLines = 0;
	ArrayList<Line> lineas;
	
	public Bridge(Vec3D beggin, Vec3D end, int height , int numLines){

		this.numLines = numLines;
		// ubicar las lineas
		
		
		
		// crear las n lineas
		
		
		// 
		
	}
	
	
	private void createLines(){
		lineas = new ArrayList<Line>();
		
		for (int line = 0; line < numLines; line++ ){
			lineas.add(createLine(line));
		}
		
	}
	
	private Line createLine(int cualLinea){
		
		return new Line(beggin, end, numPoints);
	}
	
}
