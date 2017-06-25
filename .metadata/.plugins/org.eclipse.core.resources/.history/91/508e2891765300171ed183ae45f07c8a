#version 330

smooth in vec4 theColor;
in vec4 positionWorld;
in vec4 newNormal;
in mat4 modelView;
in vec4 thePosition;

out vec4 outputColor;

//light parameters
uniform vec3 lightPos;
uniform vec3 ambientColor; 
uniform vec3 diffuseColor;
uniform vec3 speclarColor;
uniform float kA, kD, kS, sN;


void main()
{
	//diffuse
    vec3 lightDir = normalize(lightPos - positionWorld.xyz);
    float iD = max(0.0, dot(lightDir, newNormal.xyz));

    //specular
    vec3  v  = -normalize((modelView * thePosition).xyz);
    vec3  h  =  normalize(lightDir + v);
    float iS =  pow(max(0.0, dot(newNormal.xyz, h)), sN);

    vec3 lightFactor = kA * ambientColor + kD * iD * diffuseColor + kS * iS * speclarColor;

    vec4 newTheColor = vec4(theColor.rgb * lightFactor, theColor.a);
    outputColor = newTheColor;
}