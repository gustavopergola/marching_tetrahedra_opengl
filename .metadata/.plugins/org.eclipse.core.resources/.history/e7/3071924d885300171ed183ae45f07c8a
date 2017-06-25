package util.projection;

import util.math.FastMath;
import util.math.Matrix4f;

/**
 *
 * @author mlage
 */
public class Projection {
    
    private float fovY   = 0.0f;
    private float aspect = 0.0f;
    private float zNear  = 0.0f;
    private float zFar   = 0.0f;
    
    public Projection(float fovY, float aspect, float zNear, float zFar){
        this.fovY   = fovY;
        this.aspect = aspect;
        this.zNear  = zNear;
        this.zFar   = zFar;
    }
    
    public Matrix4f perspective() {
        float angle = fovY / 2.0f * FastMath.DEG_TO_RAD;
        float cotangent = FastMath.cos(angle) / FastMath.sin(angle);

        float e = cotangent / aspect; // focal length of the camera

        float fpn = zFar + zNear;  // far plus near
        float fmn = zFar - zNear;  // far minus near

        Matrix4f tempMatrix = new Matrix4f();
        tempMatrix.m11 = e;     tempMatrix.m21 = 0.0f;       tempMatrix.m31 = 0.0f;        tempMatrix.m41 = 0.0f;
        tempMatrix.m12 = 0.0f;  tempMatrix.m22 = cotangent;  tempMatrix.m32 = 0.0f;        tempMatrix.m42 = 0.0f;
        tempMatrix.m13 = 0.0f;  tempMatrix.m23 = 0.0f;       tempMatrix.m33 = -(fpn/fmn);  tempMatrix.m43 = -2.0f * zNear * zFar / fmn;
        tempMatrix.m14 = 0.0f;  tempMatrix.m24 = 0.0f;       tempMatrix.m34 = -1.0f;       tempMatrix.m44 = 0.0f;

        return tempMatrix;
    }

}
