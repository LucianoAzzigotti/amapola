package src;

import processing.core.PApplet;
import codeanticode.glgraphics.GLModel;

public class GLLine3D implements IGLModel{

	GLModel model;
	PApplet parent;
	
	GLLine3D(){
		
		model = new GLModel(parent, 2, GLModel.LINES, GLModel.STATIC) ;
		
		
		model.beginUpdateVertices();
		model.updateVertex(0, 0	,0 ,0);
		model.updateVertex(1, 1,1,1);
		model.endUpdateVertices();
		
//		model.initNormals();
//		model.beginUpdateNormals();
//		model.updateNormal(1, getBottom().x	,getBottom().y	,getBottom().z);
//		model.updateNormal(0, getTop().x	,getTop().y		,getTop().z);
//		model.endUpdateNormals();
		
		
		model.initColors();
		model.setColors(255,255,255,255);
		
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
}

