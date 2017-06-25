package piramide;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import util.math.Matrix4f;
import util.shader.ObjectGL;
import util.shader.ShaderProgram;

/**
 *
 * @author gustavopergola
 */
public class PyramidGL extends Pyramid implements ObjectGL {

    // Vertex Array Object Id
    private int vaoHandle;
    // Rotation Matrix Id
    private int rotationMatrixId = -1;
    // Scale Matrix Id
    private int scaleMatrixId = -1;
    // Shader Program
    private ShaderProgram shader;
    // Buffer with the Positions
    private FloatBuffer positionBuffer;
    // Buffer with the Colors
    private FloatBuffer colorBuffer;
    // Buffer with the Rotation Matrix
    private final FloatBuffer rotationBuffer = BufferUtils.createFloatBuffer(16);
    private final FloatBuffer scaleBuffer = BufferUtils.createFloatBuffer(16);
    
    //Constructor
    public PyramidGL() {
        super();
    }

    @Override
    public void fillVAOs() {
        // fills the VBOs
        fillVBOs();

        // create vertex buffer object (VBO) for vertices
        int positionBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);

        // create VBO for color values
        int colorBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // create vertex array object (VAO)
        vaoHandle = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        // assign vertex VBO to slot 0 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        
        // assign vertex VBO to slot 1 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
 
    @Override
    public void loadShaders() {
        // compile and link vertex and fragment shaders into
        // a "program" that resides in the OpenGL driver
        shader = new ShaderProgram();

        // do the heavy lifting of loading, compiling and linking
        // the two shaders into a usable shader program
        shader.init("shaders/simple.vert", "shaders/simple.frag");
 
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());
}

    public void setMatrix(String nameMatrix, Matrix4f matrix) {
        if (nameMatrix.equals("rotationMatrix")){
        	// converts from matrix to FloatBuffer
            rotationBuffer.clear();
            matrix.store(rotationBuffer);
            rotationBuffer.flip();
            
            // defines the uniform variable
            if(rotationMatrixId == -1) rotationMatrixId = GL20.glGetUniformLocation(shader.getProgramId(), nameMatrix);
            GL20.glUniformMatrix4(rotationMatrixId, false, rotationBuffer);
        }else if (nameMatrix.equals("scaleMatrix")){
        	// converts from matrix to FloatBuffer
            scaleBuffer.clear();
            matrix.store(scaleBuffer);
            scaleBuffer.flip();
          
            // defines the uniform variable
            if(scaleMatrixId == -1) scaleMatrixId = GL20.glGetUniformLocation(shader.getProgramId(), nameMatrix);
            GL20.glUniformMatrix4(scaleMatrixId, false, scaleBuffer);
        }
    }

    @Override
    public void render() {
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());

        // bind vertex and color data
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0); // VertexPosition
        GL20.glEnableVertexAttribArray(1); // VertexColor

        // draw VAO
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3*nfaces -2);
    }
    
    @Override
    public void fillVBOs() {
        // convert vertex array to buffer                         
        positionBuffer = BufferUtils.createFloatBuffer(4 * 3 * 6); //4(coordinates)*3(vertices)*6(triangles)
        // convert color array to buffer
        colorBuffer = BufferUtils.createFloatBuffer(4 * 3 * 6); //4(coordinates)*3(vertices)*6(triangles)

        // build the quad faces 
        buildTriangle(3, 4, 0);
        buildTriangle(0, 4, 1);
        buildTriangle(2, 1, 4);
        buildTriangle(4, 3, 2);
        buildTriangle(3, 0, 1);
        buildTriangle(3, 1, 2);

        positionBuffer.flip();
        colorBuffer.flip();
    }

    // private methods

    private void buildTriangle(int a, int b, int c) {
        positions.get(a).store(positionBuffer);
        positions.get(b).store(positionBuffer);
        positions.get(c).store(positionBuffer);

        colors.get(a).store(colorBuffer);
        colors.get(b).store(colorBuffer);
        colors.get(c).store(colorBuffer);

    }
}
