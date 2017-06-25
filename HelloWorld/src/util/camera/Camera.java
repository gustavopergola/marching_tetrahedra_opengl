package util.camera;

import util.math.Matrix4f;
import util.math.Vector3f;

/**
 *
 * @author mlage
 */
public class Camera {

    private Vector3f cameraPt = new Vector3f();
    private Vector3f cameraAt = new Vector3f(); 
    private Vector3f cameraUp = new Vector3f();

    public Camera(Vector3f eye, Vector3f at, Vector3f up) {
        cameraPt = eye;
        cameraAt = at;
        cameraUp = up;
    }

    public Matrix4f viewMatrix() {

        float eyeX = cameraPt.x; float eyeY = cameraPt.y; float eyeZ = cameraPt.z;
        float atX  = cameraAt.x; float atY  = cameraAt.y; float atZ  = cameraAt.z;
        float upX  = cameraUp.x; float upY  = cameraUp.y; float upZ  = cameraUp.z;
        
        //n = at - eye ------------------------------------
        Vector3f tempViewN = new Vector3f();
        tempViewN.setTo(atX, atY, atZ);
        tempViewN.subtract(eyeX, eyeY, eyeZ);
        tempViewN.normalize();
        // ------------------------------------------------
        
        //up ----------------------------------------------
        Vector3f tempViewUP = new Vector3f();
        tempViewUP.setTo(upX, upY, upZ);
        // ------------------------------------------------
        
        //v = up ortogonal a n ----------------------------
        Vector3f tempViewV = new Vector3f();
        tempViewV.setTo(tempViewUP);
        
        Vector3f tempViewV2 = new Vector3f();
        tempViewV2.setTo(tempViewN);
        tempViewV2.multiply(tempViewV.dotProduct(tempViewN));

        tempViewV.subtract(tempViewV2);
        tempViewV.normalize();
        // ------------------------------------------------
        
        //u = vxn -----------------------------------------
        Vector3f tempViewU = new Vector3f();
        tempViewV.crossProduct(tempViewN, tempViewU);
        tempViewU.normalize();
        // ------------------------------------------------

        Matrix4f tempRotation = new Matrix4f();
        tempRotation.m11 = tempViewU.x;  tempRotation.m21 = tempViewU.y;  tempRotation.m31 = tempViewU.z;  tempRotation.m41 = 0.0f;
        tempRotation.m12 = tempViewV.x;  tempRotation.m22 = tempViewV.y;  tempRotation.m32 = tempViewV.z;  tempRotation.m42 = 0.0f;
        tempRotation.m13 =-tempViewN.x;  tempRotation.m23 =-tempViewN.y;  tempRotation.m33 =-tempViewN.z;  tempRotation.m43 = 0.0f;
        tempRotation.m14 = 0.0f;         tempRotation.m24 = 0.0f;         tempRotation.m34 = 0.0f;         tempRotation.m44 = 1.0f;

        Matrix4f tempTranslation = new Matrix4f();
        tempTranslation.m41 = -eyeX;
        tempTranslation.m42 = -eyeY;
        tempTranslation.m43 = -eyeZ;
        
        tempRotation.multiply(tempTranslation);        
        
        return tempRotation;
    }
}
