package cubo;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import util.math.Matrix4f;
import util.math.Vector3f;
import util.shader.ObjectGL;
import util.shader.ShaderProgram;

/**
 *
 * @author marcoslage
 */
public class CubeGL extends Cube implements ObjectGL {

    // Vertex Array Object Id
    private int vaoHandle;
    // Shader Program
    private ShaderProgram shader;
    // Buffer with the Positions
    private FloatBuffer positionBuffer;
    
    // Buffer with the Positions
    private FloatBuffer normalBuffer;
 
    // Buffer with the Colors
    private FloatBuffer colorBuffer;
        
    //Constructor
    public CubeGL() {
        super();
    }

    @Override
    public void fillVAOs() {
        // fills the VBOs
        fillVBOs();

        // create vertex byffer object (VBO) for vertices
        int positionBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);

        // create vertex byffer object (VBO) for normals
        int normalBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);

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
        GL20.glEnableVertexAttribArray(2);

        // assign vertex VBO to slot 0 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);

        // assign vertex VBO to slot 1 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferHandle);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);

        // assign vertex VBO to slot 2 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL20.glVertexAttribPointer(2, 4, GL11.GL_FLOAT, false, 0, 0);

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
        shader.init("shaders/phong.vert", "shaders/phong.frag");
 
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());
    }
    
    public void setFloat(String nameFloat, float dataFloat){        
        // defines the uniform variable
        int fltId = GL20.glGetUniformLocation(shader.getProgramId(), nameFloat);
        GL20.glUniform1f(fltId, dataFloat);   
    }
    

    public void setVector(String nameVector, Vector3f dataVector) {
        // Buffer with the Model Matrix
        FloatBuffer vecBuff = BufferUtils.createFloatBuffer(3);

        // converts from matrix to FloatBuffer
        vecBuff.clear();
        dataVector.store(vecBuff);
        vecBuff.flip();
        
        // defines the uniform variable
        int vecId = GL20.glGetUniformLocation(shader.getProgramId(), nameVector);
        GL20.glUniform3(vecId, vecBuff);
    }
    
    
    public void setMatrix(String nameMatrix, Matrix4f dataMatrix) {
        // Buffer with the Model Matrix
        FloatBuffer matBuff = BufferUtils.createFloatBuffer(16);

        // converts from matrix to FloatBuffer
        matBuff.clear();
        dataMatrix.store(matBuff);
        matBuff.flip();
        
        // defines the uniform variable
        int matrixId = GL20.glGetUniformLocation(shader.getProgramId(), nameMatrix);
        GL20.glUniformMatrix4(matrixId, false, matBuff);
    }

    @Override
    public void render() {
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());

        // bind vertex and color data
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0); // VertexPosition
        GL20.glEnableVertexAttribArray(1); // VertexNormal
        GL20.glEnableVertexAttribArray(2); // VertexColor

        // draw VAO
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3*nfaces);
    }
    
    @Override
    public void fillVBOs() {
        // convert vertex array to buffer
        positionBuffer = BufferUtils.createFloatBuffer(4 * 3 * 12); //4(coordinates)*3(vertices)*12(triangles)
        // convert vertex array to buffer
        normalBuffer = BufferUtils.createFloatBuffer(4 * 3 * 12); //4(coordinates)*3(vertices)*12(triangles)
        // convert color array to buffer
        colorBuffer = BufferUtils.createFloatBuffer(4 * 3 * 12); //4(coordinates)*3(vertices)*12(triangles)

        // bluid the quad faces 
        buildQuad(1, 0, 3, 2);
        buildQuad(2, 3, 7, 6);
        buildQuad(3, 0, 4, 7);
        buildQuad(6, 5, 1, 2);
        buildQuad(4, 5, 6, 7);
        buildQuad(5, 4, 0, 1);

        positionBuffer.flip();
        normalBuffer.flip();
        colorBuffer.flip();
    }

    // private methods

    private void buildQuad(int a, int b, int c, int d) {
        positions.get(a).store(positionBuffer);
        positions.get(c).store(positionBuffer);
        positions.get(b).store(positionBuffer);

        positions.get(a).store(positionBuffer);
        positions.get(d).store(positionBuffer);
        positions.get(c).store(positionBuffer);
        
                
        positions.get(a).store(normalBuffer);
        positions.get(c).store(normalBuffer);
        positions.get(b).store(normalBuffer);

        positions.get(a).store(normalBuffer);
        positions.get(d).store(normalBuffer);
        positions.get(c).store(normalBuffer);
        
        
        colors.get(a).store(colorBuffer);
        colors.get(c).store(colorBuffer);
        colors.get(b).store(colorBuffer);

        colors.get(a).store(colorBuffer);
        colors.get(d).store(colorBuffer);
        colors.get(c).store(colorBuffer);
    }
}
