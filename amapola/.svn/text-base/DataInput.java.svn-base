package amapola;
import oscP5.*;
import netP5.*;

/**
 * en esta clase voy a centralizar toda la mensajeria OSC
 * entre el cliente live y la aplicacion
 * cada clase que se registra como listener recibe los mensajes que le corresponde
 * todo el mapeo se hace aca
 * 
 * @author amapola
 *
 */

public class DataInput {

	OscP5 oscP5;
	NetAddress myRemoteLocation;


	float[] bands; 
	float[] controller; 
	float[] ipod;

	int patch = 0;



	public void setup(){

		/* start oscP5, listening for incoming messages at port 12000 */
		oscP5 = new OscP5(this,12000);

		/* myRemoteLocation is a NetAddress. a NetAddress takes 2 parameters,
		 * an ip address and a port number. myRemoteLocation is used as parameter in
		 * oscP5.send() when sending osc packets to another computer, device, 
		 * application. usage see below. for testing purposes the listening port
		 * and the port of the remote location address are the same, hence you will
		 * send messages back to this sketch.
		 */
		myRemoteLocation = new NetAddress("127.0.0.1",12000);

		bands 		= new float[3];
		controller 	= new float[3];
		ipod 		= new float[3];
	}


	public void oscEvent(OscMessage theOscMessage) {


		if(theOscMessage.checkAddrPattern("/audioHI") == true) {
			if(theOscMessage.checkTypetag("f")) {
				float secondValue = theOscMessage.get(0).floatValue();
				bands[2] = secondValue;
				return;
			}  
		} 

		if(theOscMessage.checkAddrPattern("/audioMID") == true) {
			if(theOscMessage.checkTypetag("f")) {
				float secondValue = theOscMessage.get(0).floatValue();
				bands[1] = secondValue;
				return;
			}  
		} 

		if(theOscMessage.checkAddrPattern("/audioLOW")==true) {
			if(theOscMessage.checkTypetag("f")) {
				float secondValue = theOscMessage.get(0).floatValue();
				bands[0] = secondValue;
				return;
			}  
		} 


		if(theOscMessage.checkAddrPattern("/patch")==true) {
			if(theOscMessage.checkTypetag("i")) {
				int value = theOscMessage.get(0).intValue();
				patch = value;
				System.out.print("### received an osc message /patch with typetag f.");
				System.out.println(" patch: "+ patch);
				return;
			}  
		}

		if(theOscMessage.checkAddrPattern("/ctrl")==true) {
			if(theOscMessage.checkTypetag("if")) {
				int firstValue = theOscMessage.get(0).intValue();
				float secondValue = theOscMessage.get(0).floatValue();

				switch (firstValue) {
				case 0 :
					controller[0] = secondValue;
					break;

				case 1 :
					controller[1] = secondValue;
					break;

				case 2 :
					controller[2] = secondValue;
					break;

				}
				return;
			}  
		}




		if(theOscMessage.checkAddrPattern("/ipod")==true) {
			if(theOscMessage.checkTypetag("fff")) {
				ipod [0] = theOscMessage.get(0).floatValue();
				ipod [1] = theOscMessage.get(1).floatValue();
				ipod [2] = theOscMessage.get(2).floatValue();
				return;
			}  
		}
	}
}
