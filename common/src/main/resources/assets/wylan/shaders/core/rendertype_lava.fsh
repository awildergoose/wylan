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

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    vec3 targetColor = vec3(111.0/255.0, 101.0/255.0, 61.0/255.0);
    const vec3 LUMA = vec3(0.2126, 0.7152, 0.0722);

    float lum = dot(color.rgb, LUMA);
    float targetLum = dot(targetColor, LUMA);
    float safeTargetLum = max(targetLum, 1e-4);
    vec3 targetScaled = targetColor * (lum / safeTargetLum);

    color.rgb = mix(color.rgb, targetScaled, LavaTransitionProgress);

#ifdef ALPHA_CUTOUT
    if (color.a < ALPHA_CUTOUT) {
        discard;
    }
#endif

    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
