#version 150

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:globals.glsl>
#moj_import <wylan:goose.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

const float PI = 3.14159265;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;

    float cycleSeconds = 3.0;
    float cycleTicks   = cycleSeconds * (20.0 * 10000.0);
    float wrappedTicks = WorldTimeMS * cycleTicks;

    float t = (wrappedTicks / cycleTicks) * (2.0 * PI);
    //t += sphericalVertexDistance * 0.15;

    vec3 rainbow = vec3(
        abs(sin(t))
    );

    color.rgb = rainbow;

#ifdef ALPHA_CUTOUT
    if (color.a < ALPHA_CUTOUT) {
        discard;
    }
#endif

    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
