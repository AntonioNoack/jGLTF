attribute vec4 a_position;
attribute vec4 a_normal;
attribute vec4 a_tangent;

attribute ivec4 a_joint;
attribute vec4 a_weight;

attribute vec2 a_baseColorTexCoord;
attribute vec2 a_metallicRoughnessTexCoord;
attribute vec2 a_normalTexCoord;
attribute vec2 a_occlusionTexCoord;
attribute vec2 a_emissiveTexCoord;

uniform mat4 u_modelViewMatrix;
uniform mat4 u_projectionMatrix;
uniform mat3 u_normalMatrix;

#ifdef NUM_JOINTS 
uniform mat4 u_jointMat[NUM_JOINTS];
#endif 

uniform vec3 u_lightPosition = vec3(-800,500,500);

varying vec3 v_position;
varying vec3 v_normal;
varying vec4 v_tangent;

varying vec3 v_lightPosition;

varying vec2 v_baseColorTexCoord;
varying vec2 v_metallicRoughnessTexCoord;
varying vec2 v_normalTexCoord;
varying vec2 v_occlusionTexCoord;
varying vec2 v_emissiveTexCoord;

void main(){

    
#ifdef NUM_JOINTS
    mat4 skinningMatrix;
    skinningMatrix  = a_weight.x * u_jointMat[a_joint.x];
    skinningMatrix += a_weight.y * u_jointMat[a_joint.y];
    skinningMatrix += a_weight.z * u_jointMat[a_joint.z];
    skinningMatrix += a_weight.w * u_jointMat[a_joint.w];
    vec4 pos = u_modelViewMatrix * skinningMatrix * a_position;
#else
    vec4 pos = u_modelViewMatrix * a_position;
#endif

    v_position = vec3(pos.xyz) / pos.w;
    v_normal = u_normalMatrix * vec3(a_normal);
    v_tangent = vec4(u_normalMatrix * vec3(a_tangent), a_tangent.w);

    v_lightPosition = vec3(u_modelViewMatrix * vec4(u_lightPosition, 1.0));

    v_baseColorTexCoord = a_baseColorTexCoord;
    v_metallicRoughnessTexCoord = a_metallicRoughnessTexCoord;
    v_normalTexCoord = a_normalTexCoord;
    v_occlusionTexCoord = a_occlusionTexCoord;
    v_emissiveTexCoord = a_emissiveTexCoord;

    gl_Position = u_projectionMatrix * vec4(v_position, 1.0);

}


