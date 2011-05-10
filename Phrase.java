package src;

public class Phrase {

		
		String  velocity;
		String  text;
		
		public Phrase(String velocity, String text){
			this.velocity   = velocity;
			this.text 		= text;		
		}

		
		
		public int getVelocity(){
			return Integer.parseInt(velocity);
		}
		
		public String getText(){
			return text;
		}
		
		public String toString(){
			
			return ("vel: " + velocity + " text: " +  text);
			
		}
		

}
