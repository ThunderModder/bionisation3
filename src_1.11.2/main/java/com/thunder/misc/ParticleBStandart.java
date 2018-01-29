package com.thunder.misc;

import com.thunder.bionisation.Information;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleBStandart extends Particle {

    public ParticleBStandart(ResourceLocation location, World worldIn, double posXIn, double posYIn, double posZIn, double motionX, double motionY, double motionZ) {
        super(worldIn, posXIn, posYIn, posZIn);
        this.particleAlpha = 0.99f;
        this.particleMaxAge = 200;
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
        if(!this.onGround){
            motionX += (rand.nextInt(2) == 0 ? motionX : -motionX);
            motionZ += (rand.nextInt(2) == 0 ? motionZ : -motionZ);
        }else setExpired();
    }
}
