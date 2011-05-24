package src;

import codeanticode.glgraphics.GLModel;
import processing.core.PApplet;
import toxi.geom.Line3D;
import toxi.geom.Spline3D;
import toxi.geom.mesh.Mesh3D;
import toxi.geom.mesh.TriangleMesh;

public class GLSpline {

	
	Spline3D spline;
	GLModel model;
	
	PApplet parent;
	
	
	public GLSpline(PApplet parent, Spline3D spline) {
		this.spline = spline;
		this.parent = parent;	
		initModel();
			
	}
	
	void initModel(){
				
			// TODO Agregar color
			mesh.computeVertexNormals();

			float[] verts = spline.ge;
			int numV = verts.length / 4; // The vertices array from the mesh object has a spacing of 4.
			float[] norms = mesh.getVertexNormalsAsArray();

			model = new GLModel(parent, verts.length, GLModel.LINE, GLModel.STATIC);
			
			model.beginUpdateVertices();
			for (int i = 0; i < numV; i++) model.updateVertex(i, verts[4 * i], verts[4 * i + 1], verts[4 * i + 2]);
			model.endUpdateVertices();

			model.initNormals();
			model.beginUpdateNormals();
			for (int i = 0; i < numV; i++) model.updateNormal(i, norms[4 * i], norms[4 * i + 1], norms[4 * i + 2]);
			model.endUpdateNormals();

		

	}

}
