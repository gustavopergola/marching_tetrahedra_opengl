package piramide;

/**
 *
 * @author gustavopergola
 */
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

import util.math.FastMath;
import util.math.Matrix4f;

public class Main2 {

    // Creates a new pyramid
    private final PyramidGL pyramid = new PyramidGL();

    // Animation:
    private float  currentAngle = 0.0f;
    // Rotation Matrix:
    private final Matrix4f rotationMatrix = new Matrix4f();
    private final Matrix4f scaleMatrix = new Matrix4f();
    
    /**
     * General initialization stuff for OpenGL
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {
        
        // width and height of window and view port
        int width = 800;
        int height = 600;

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
        
        // Standard OpenGL Version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: "   + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
         
        // initialize basic OpenGL stuff
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void run() {
        // Creates the vertex array object. 
        // Must be performed before shaders compilation.
        pyramid.fillVAOs();
        pyramid.loadShaders();

        while (Display.isCloseRequested() == false) {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            
            currentAngle += 0.01f;
            float c = FastMath.cos(currentAngle);
            float s = FastMath.sin(currentAngle);

            rotationMatrix.m11 = c; rotationMatrix.m12 = -s;
            rotationMatrix.m21 = s; rotationMatrix.m22 = c;
            
            
            //scaleMatrix.m11 = c; scaleMatrix.m22 = c; scaleMatrix.m33 = c;
              
            pyramid.setMatrix("rotationMatrix", rotationMatrix);
            pyramid.setMatrix("scaleMatrix", scaleMatrix);
            pyramid.render();

            // check for errors
            if (GL11.GL_NO_ERROR != GL11.glGetError()) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }

            // swap buffers and sync frame rate to 60 fps
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    /**
     * main method to run the example
     * @param args
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException {
        Main2 example = new Main2();
        example.initGl();
        example.run();
    }
}
