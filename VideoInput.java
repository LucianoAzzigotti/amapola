package src;
import netP5.Logger;
import codeanticode.gsvideo.*;
import processing.core.*;
/**
 * Video input es el encargado de manejar la entrada de video y tener un stream
 * de imagen disponible para pasar al analizador de blobs
 * La imagen puede venir de una camara o de un video playback
 * 
 * @author amapola
 *
 */

public class VideoInput {
	
	private boolean 	useWebcam = false;
	
	private GSCapture 	capture;
	private GSMovie   	movie;
	private PImage 		frame;

	
	public VideoInput(PApplet parent, boolean useWebcam) {

		this.useWebcam = useWebcam;

		if(this.useWebcam){
	
			capture = new GSCapture(parent, 320, 240);
			frame = new PImage(320, 240);

			amapola.app.logger.info("CAMERA STARTED");
			
		}else{

			movie = new GSMovie(parent, "dance2.mp4");
			movie.loop();

			amapola.app.logger.info("MOVIE LOADED");
		}

	}


	/**
	 * read() me asegura que siempre voy a tener un PIMage disponible para
	 * no tener problemas del otro lado.
	 * Si no cargo una imagen nueva, entonces voy a usar la ultima.
	 * Esto puede traer aparejados problemas de playback pero me asegura que
	 * el draw de processing va a correr sin cuelgues
	 * TODO: implementar su propio thread
	 * @return
	 */
	
	public PImage read(){

		if(useWebcam){
			if (capture.available()) {
				capture.read();
				frame = capture;
			}	
		}else{
			
			if (movie.available()) {
				movie.read();				
				frame = movie;
			}
		}
		return frame;
	}

}
