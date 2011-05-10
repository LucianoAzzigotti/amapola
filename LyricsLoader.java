package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

public class LyricsLoader {

	// Lee un archivo de liricas e implementa metodos
	// para poder recuperar las frases en el momento indicado


	private ArrayList 					textLines;			// todas las lineas de lirica
	private Hashtable 					lyricsPhrases;		// las lineas procesadas
	private String 						fileUri;			// la uri del archivo de texto

	public LyricsLoader(){
		textLines = new ArrayList();
	}


	public ArrayList readLyricsFile(String lyricsFile){

		this.fileUri = lyricsFile;

		// si no paso nada salta una exception
		if ((lyricsFile == null) || (lyricsFile == ""))	throw new IllegalArgumentException();

		String line;

		try{    
			BufferedReader in = new BufferedReader(new FileReader(lyricsFile));

			if (!in.ready())
				throw new IOException("No se puede abrir archivo de lirica");

			// mientras haya lineas de texto
			// que no sean un comentario ("#"), las guardo						
			while ((line = in.readLine()) != null) if(!line.startsWith("#")) textLines.add(line);

			// cierro el archivo
			in.close();

		}catch (IOException e){
			e.printStackTrace();
			return null;
		}



		// leo todos los textos y 
		// proceso los parametros
		loadPhrasesArguments();

		return textLines;
	}



	private void loadPhrasesArguments(){

		lyricsPhrases = new Hashtable();

		Iterator it = textLines.iterator();

		while(it.hasNext()){


			// agarro un linea de las que son parametros
			String line = (String) it.next();

			if(line != null){

				amapola.app.logger.info(line);
				// y separo la posicion de la phrase
				// y el par vel-lri

				String textLine[] = line.split(",");

				// me fijo que la linea este mas o menos bien
				if(textLine.length >= 3){
					// lo cargo en la Hashtable
					String note 	= removeSpaces(textLine[0]);
					String velocity = removeSpaces(textLine[1]);
					String text		= textLine[2];
					
					// lo cargo en el hastable
					lyricsPhrases.put(note, new Phrase(velocity, text));	
				
				}	
			}
		}
	}

	
	private Phrase getPhrase(String note) {
		return (Phrase) lyricsPhrases.get(note);
	}

	public int phrasesCount(){
		return lyricsPhrases.size();
	}


	public Phrase getPhrase(int note) {
		return (Phrase) lyricsPhrases.get(String.valueOf(note));
	}
	
	public String removeSpaces(String s) {
		  StringTokenizer st = new StringTokenizer(s," ",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
		}
	

}
