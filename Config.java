package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class Config {

	// Lee un archivo de configuracion e implementa metodos
	// para poder recuperar esos parametros

	private ArrayList file;
	private Hashtable<String, String> arguments;
	private String configFile;
	
	public Config(String configFile){
		
		this.configFile = configFile;
		readConfig(configFile);
		
	}

	public ArrayList readConfig(String configFile){

		String line;
		file = new ArrayList();

		// me fijo que el archivo exista
		if ((configFile == null) || (configFile == ""))	throw new IllegalArgumentException();
	
		try{    

			BufferedReader in = new BufferedReader(new FileReader(configFile));
			
			if (!in.ready())
				throw new IOException("No se puede abrir archivo de configuraci�n");

			// mientras haya lineas de texto
			// que no sean un comentario ("#"), las guardo			
			while ((line = in.readLine()) != null) if(!line.startsWith("#")) file.add(line);

			// cierro el archivo
			in.close();

		}catch (IOException e){
			e.printStackTrace();
			return null;
		}
		
		// leo todos los textos y 
		// proceso los parametros
		loadArguments();
		
		return file;
	}
	
	
	private void loadArguments(){
		
		arguments = new Hashtable<String, String>();
		Iterator it = file.iterator();
			
		while(it.hasNext()){
			
			
			// agarro un linea de las que son parametros
			String line = (String) it.next();
		
			if(line != null){
				
				System.out.println(line);
				// y separo el key del value
				// 0 es el key
				// 1 es el valor
				String parameter[] = line.split("=");
				
				// lo cargo en la Hashtable
				String key = parameter[0];
				String value = parameter[1];
			
				// lo cargo en el hastable
				arguments.put(key, value);	
			}
					
		}
	}


	
	public String getStringParameter(String string) {
		return arguments.get(string);
	}
	
	public float getFloatParameter(String string) {
		return Float.valueOf(arguments.get(string));
	}
	
	public boolean getBoolParameter(String string) {
		return Boolean.valueOf(arguments.get(string));
	}	
	
	
	public int getIntParameter(String string) {
		return Integer.valueOf(arguments.get(string));
	}	
	
	public Hashtable<String, String> parameters(){
		return arguments;
	}

}
