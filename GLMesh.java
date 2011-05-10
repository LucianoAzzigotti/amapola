package src;
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
	PApplet parent;
	
	//	  GLModel glmesh = new GLModel(this, numV, TRIANGLES, GLModel.STATIC);
	
	public GLMesh(PApplet parent, Mesh3D mesh) {
		
		this.parent = parent;
		this.mesh = (TriangleMesh) mesh;
		initModel();
			
	}
	
	
	public GLModel getModel(){
		return model;
	}
	
	public Mesh3D getMesh(){
		return mesh;
	}
	

	private void initModel(){		

		mesh.computeVertexNormals();

		float[] verts = mesh.getMeshAsVertexArray();
		//System.out.println(verts.length);
		int numV = verts.length / 4; // The vertices array from the mesh object has a spacing of 4.
		float[] norms = mesh.getVertexNormalsAsArray();

		model = new GLModel(parent, verts.length, GLModel.TRIANGLES, GLModel.STATIC);
		model.beginUpdateVertices();
		for (int i = 0; i < numV; i++) model.updateVertex(i, verts[4 * i], verts[4 * i + 1], verts[4 * i + 2]);
		model.endUpdateVertices();

		model.initNormals();
		model.beginUpdateNormals();
		for (int i = 0; i < numV; i++) model.updateNormal(i, norms[4 * i], norms[4 * i + 1], norms[4 * i + 2]);
		model.endUpdateNormals();

	}

}
