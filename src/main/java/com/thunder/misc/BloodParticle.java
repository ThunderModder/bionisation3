package com.thunder.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


public class BloodParticle extends Particle {

    public BloodParticle(ResourceLocation location, World worldIn, double posXIn, double posYIn, double posZIn, double motionX, double motionY, double motionZ) {
        super(worldIn, posXIn, posYIn, posZIn);
        this.particleAlpha = 0.99f;
        particleMaxAge = 600;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionY = motionZ;
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
        setParticleTexture(sprite);
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        motionY += -0.02;
        if(!this.onGround){
            motionX += (rand.nextInt(2) == 0 ? rand.nextFloat() * 0.01 : -rand.nextFloat() * 0.01);
            motionZ += (rand.nextInt(2) == 0 ? rand.nextFloat() * 0.01 : -rand.nextFloat() * 0.01);
        }
    }
}
