package src;


public class Line {

	// el staff al que pertenezco
	// esto es super importante porque el staff es quien tiene la data de donde se dibuja
	Staff 	staff;
	
	
	// la linea es una cuerda
	Cord	cord;
	// una linea en realidad puede ser un espacio
	boolean isLine;
	
	
	
	public Line(Cord cord, boolean isLine) {
		
		this.staff = staff;
		this.isLine = isLine;
	
	}

}
