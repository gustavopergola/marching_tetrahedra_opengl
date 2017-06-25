#version 330

layout (location = 0) in vec4 position;
layout (location = 1) in vec4 normal;
layout (location = 2) in vec4 color;

//matrix
uniform mat4 modelmatrix;
uniform mat4 viewmatrix;
uniform mat4 projection;

//light parameters
uniform vec3 lightPos;
uniform vec3 ambientColor; 
uniform vec3 diffuseColor;
uniform vec3 speclarColor;
uniform float kA, kD, kS, sN;

//vertex color
smooth out vec4 theColor;

void main()
{
    mat4 modelView = viewmatrix * modelmatrix;
    mat4 normalMatrix = transpose(inverse(modelView));

    // final vertex position
    gl_Position = projection * modelView * position;

    vec4 positionWorld = modelmatrix * position;
    vec4 newNormal = normalize(normalMatrix * normal);

    //diffuse
    vec3 lightDir = normalize(lightPos - positionWorld.xyz);
    float iD = max(0.0, dot(lightDir, newNormal.xyz));

    //specular
    vec3  v  = -normalize((modelView * position).xyz);
    vec3  h  =  normalize(lightDir + v);
    float iS =  pow(max(0.0, dot(newNormal.xyz, h)), sN);

    vec3 lightFactor = kA * ambientColor + kD * iD * diffuseColor + kS * iS * speclarColor;

    theColor = vec4(color.rgb * lightFactor, color.a);
}