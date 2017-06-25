/*
 * Undergraduate course of Computer Graphics.
 * Copyright (C) 2014 - Prof. Marcos Lage
 * 
 * Universidade Federal Fluminense
 * Instituto de Computação
 * 
 * Version 1.0
 */

package hello;

//java imports
import java.nio.FloatBuffer;

//lwjgl imports
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.opengl.*;

//my imports
import util.shader.ShaderProgram;

/**
 * Hello world OpenGL application, using shaders.
 */
public class ShaderCode {

    /**
     * General initialization stuff for OpenGL.
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {
        
        // width and height of window and view port
        int width  = 640;
        int height = 480;

        // set up window and display
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setVSyncEnabled(true);
        Display.setTitle("Shader OpenGL Hello");

        // set up OpenGL to run in forward-compatible mode
        // so that using deprecated functionality will
        // throw an error.
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        Display.create(pixelFormat, contextAtrributes);
        
        // check of the OpenGL version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: "   + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
         
        // initialize OpenGL viewport
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void run() {
        // creates the vertex array object. Must be performed before the
        // shaders compilation.
        int vaoHandle = constructVertexArrayObject();

        // compile and link vertex and fragment shaders into
        // a "program" that resides in the OpenGL driver
        ShaderProgram shader = new ShaderProgram();

        // do the heavy lifting of loading, compiling and linking
        // the two shaders into a usable shader program
        shader.init("shaders/simple.vert", "shaders/simple.frag");

        while (Display.isCloseRequested() == false) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // tell OpenGL to use the shader
            GL20.glUseProgram(shader.getProgramId());

            // bind vertex and color data
            GL30.glBindVertexArray(vaoHandle);
            GL20.glEnableVertexAttribArray(0); // VertexPosition
            GL20.glEnableVertexAttribArray(1); // VertexColor

            // draw VAO
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);

            // check for errors
            if (GL11.glGetError() != GL11.GL_NO_ERROR) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }

            // swap buffers and sync frame rate to 60 fps
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    /**
     * Create Vertex Array Object necessary to pass data to the shader
     */
    private int constructVertexArrayObject() {
        // creation of coordinates 
        float[] positionData = new float[]{
            0.0f, 0.0f, 0.0f, 1.0f,
           -1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f
        };

        // creation of colors
        float[] colorData = new float[]{
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f
        };

        // copy the positions array to the buffer
        FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(positionData.length);
        positionBuffer.put(positionData);
        positionBuffer.flip();

        // copy the colors array to buffer
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colorData.length);
        colorBuffer.put(colorData);
        colorBuffer.flip();

        // creation of the vertex byffer object (VBO) for vertices
        int positionBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);

        // creation of the VBO for color values
        int colorBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);

        // creation of the vertex array object (VAO)
        int vaoHandle = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        
        // assignment of the position VBO to slot 0 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);

        // assignment of the color VBO to slot 1 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);

        return vaoHandle;
    }

    /**
     * main method to run the example
     * @param args
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException {
        ShaderCode example = new ShaderCode();
        example.initGl();
        example.run();
    }
}
