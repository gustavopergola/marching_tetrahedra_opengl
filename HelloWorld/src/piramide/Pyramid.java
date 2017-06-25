package piramide;

import java.util.ArrayList;
import util.math.Vector4f;

/**
 *
 * @author gustavopergola
 */
class Pyramid {
    protected ArrayList<Vector4f> colors;
    protected ArrayList<Vector4f> positions;
    
    protected int nverts = 5;
    protected int nfaces = 6;
    
    public Pyramid(){
        positions = new ArrayList<>(5);
        colors    = new ArrayList<>(5);
        
        // Fill the vertices
        positions.add( new Vector4f(-0.5f, 0.0f,-0.5f, 1.0f) ); //p0
        positions.add( new Vector4f( 0.5f, 0.0f,-0.5f, 1.0f) ); //p1
        positions.add( new Vector4f( 0.5f, 0.0f, 0.5f, 1.0f) ); //p2
        positions.add( new Vector4f(-0.5f, 0.0f, 0.5f, 1.0f) ); //p3
        positions.add( new Vector4f(-0.5f, 1f, 0.0f, 1.0f) ); //p4
       

        // Fill the colors
        colors.add( new Vector4f( 0.0f, 0.0f, 1.0f, 1.0f) ); //blue //p0
        colors.add( new Vector4f( 1.0f, 1.0f, 0.0f, 1.0f) ); //yellow //p1
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); //red //p2
        colors.add( new Vector4f( 0.0f, 1.0f, 0.0f, 1.0f) ); //green //p3
        colors.add( new Vector4f( 1.0f, 0.5f, 0.5f, 1.0f) ); //pink //p4

    }
}
