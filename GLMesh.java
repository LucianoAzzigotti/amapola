package src;
import java.util.logging.Level;

import processing.core.PApplet;
import toxi.geom.Vec3D;
import toxi.geom.mesh.Mesh3D;
import toxi.geom.mesh.SuperEllipsoid;
import toxi.geom.mesh.SurfaceMeshBuilder;
import toxi.geom.mesh.TriangleMesh;
import codeanticode.glgraphics.*;

public class GLMesh{

	TriangleMesh mesh;
	GLModel model;
	boolean isWireframe;
	PApplet parent;
	

	// crea un GLmodel a partir de un mesh
	// es una implementaci—n de :http://codeanticode.wordpress.com/2011/03/28/integrating-toxilibs-and-glgraphics/
	public GLMesh(PApplet parent, Mesh3D mesh, boolean isWireframe) {
	
		this.parent = parent;
		this.mesh = (TriangleMesh) mesh;	
		this.isWireframe = isWireframe;
		initModel();
			
	}
	
	
	public GLModel getModel(){
		return model;
	}
	
	public Mesh3D getMesh(){
		return mesh;
	}
	

	private void initModel(){		
		// TODO Agregar color
		mesh.computeVertexNormals();

		float[] verts = mesh.getMeshAsVertexArray();
		int numV = verts.length / 4; // The vertices array from the mesh object has a spacing of 4.
		float[] norms = mesh.getVertexNormalsAsArray();

		if(isWireframe) {
			
			model = new GLModel(parent, numV, GLModel.LINE_LOOP, GLModel.STATIC);	

			app.logger.log(Level.INFO, ""+numV);
			
			model.beginUpdateVertices();
			for (int i = 0; i < numV ; i++) {
				app.logger.log(Level.INFO, "I:" + i +"\t:" + verts[4 * i] +"\t:"+ verts[4 * i + 1] +"\t:"+ verts[4 * i + 2] +"\t:"+ verts[4 * i + 3]);
				model.updateVertex(i, verts[4 * i], verts[4 * i + 1], verts[4 * i + 2]);
			}			
			model.endUpdateVertices();

		
			model.initNormals();
			model.beginUpdateNormals();
			for (int i = 0; i < numV; i++) model.updateNormal(i, norms[4 * i], norms[4 * i + 1], norms[4 * i + 2]);
			model.endUpdateNormals();

			
		}else{
			
			model = new GLModel(parent, numV, GLModel.TRIANGLES, GLModel.STATIC);

			model.beginUpdateVertices();
			for (int i = 0; i < numV; i++) model.updateVertex(i, verts[4 * i], verts[4 * i + 1], verts[4 * i + 2]);
			model.endUpdateVertices();


			model.initNormals();
			model.beginUpdateNormals();
			for (int i = 0; i < numV; i++) model.updateNormal(i, norms[4 * i], norms[4 * i + 1], norms[4 * i + 2]);
			model.endUpdateNormals();
			
			model.initColors();
			model.setColors(255,255,255,10);
			

		}
		

	}

}
