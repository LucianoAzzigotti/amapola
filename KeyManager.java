package src;

public class KeyManager {
	
	// devuelve el numero de linea en el que tengo que ubicar la nota segun 
	// la clave en la que estoy laburando
	// el manejador de claves le dice al staff (aka Penta) en que linea tiene que ubicar la nota
	// entonces esta clase se conecta con un staff y recibe notas y devuelve la linea
	
	Staff staff;
	
	public KeyManager(Staff staff) {
		this.staff = staff;
	}
	
	
	int ubicarNota(int pitch, int clave, boolean isSostenido){   

	    int numeroDeLinea;
	    
	    int[] sostenidos =  { 0,0,1,1,2,3,3,4,4,5,5,6};      
	    int[] bemoles = {0,1,1,2,2,3,4,4,5,5,6,6};

	      int octava = (pitch / 12) - 2;
	      println("OC: " + octava);
	      int notaNatural = pitch % 12;
	      println("NN: " + notaNatural);
	      int offset = 26;
	       
	                if (isSostenido) {
	                  numeroDeLinea = sostenidos[notaNatural] + clave  + (octava * 12) - offset;

	                } else {
	                  numeroDeLinea = bemoles[notaNatural] + clave  + (octava * 12) -  offset;
	                }
	    //alturas de claves y transformador de alturas
	   println("NL: " + numeroDeLinea);
	   return numeroDeLinea;
	      

	}

}
