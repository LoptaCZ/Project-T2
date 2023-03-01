#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform float GameTime;
uniform int VoidCubeLayers;

in vec4 texProj0;

const vec3[] COLORS = vec3[](
    vec3(0.23, 0.12, 0.43), //  061 033 110
    vec3(0.0, 0.09, 0.19),  //  006 023 050
    vec3(0.27, 0.08, 0.78),   //  069 020 200
    vec3(0.45, 0.17, 0.59),    //  117 044 150
    vec3(0.67, 0.90, 0.98),    //  170 230 250
    vec3(0.0, 0.0, 0.08),    //  002 002 020
    vec3(0.43, 0.39, 0.23),    //  110 100 060
    vec3(0.1, 0.23, 0.78),    //  025 060 200
    vec3(0.0, 0.27, 0.98),    //  000 070 250
    vec3(0.59, 0.35, 0.0),    //  150 090 130
    vec3(0.08, 0.12, 0.0),    //  020 030 200
    vec3(0.94, 0.35, 0.98),    //  240 090 250
    vec3(0.98, 0.98, 0.98),    //  250 250 250
    vec3(0.39, 0.12, 0.78)     //  100 030 200
);

const mat4 SCALE_TRANSLATE = mat4(
    0.5, 0.0, 0.0, 0.25,
    0.0, 0.5, 0.0, 0.25,
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
);

mat4 void_cube_layer(float layer) {
    mat4 translate = mat4(
        1.0, 0.0, 0.0, 17.0 / layer,
        0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (GameTime * 1.5),
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    );

    mat2 rotate = mat2_rotate_z(radians((layer * layer * 4321.0 + layer * 9.0) * 2.0));

    mat2 scale = mat2((4.5 - layer / 4.0) * 2.0);

    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}

out vec4 fragColor;

void main() {
    vec3 color = textureProj(Sampler0, texProj0).rgb * COLORS[0];
    for (int i = 0; i < VoidCubeLayers; i++) {
        color += textureProj(Sampler1, texProj0 * void_cube_layer(float(i + 1))).rgb * COLORS[i];
    }
    fragColor = vec4(color, 1.0);
}
