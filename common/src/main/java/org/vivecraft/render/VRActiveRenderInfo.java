package org.vivecraft.render;

import org.vivecraft.ClientDataHolder;
import org.vivecraft.apiold.VRData;

import com.mojang.math.Vector3f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

public class VRActiveRenderInfo extends Camera
{
    public void setup(BlockGetter p_90576_, Entity pLevel, boolean pRenderViewEntity, boolean pThirdPerson, float pThirdPersonReverse)
    {
        this.initialized = true;
        this.level = p_90576_;
        this.entity = pLevel;
        Minecraft minecraft = Minecraft.getInstance();
        ClientDataHolder dataholder = ClientDataHolder.getInstance();
        RenderPass renderpass = dataholder.currentPass;

//        if (Shaders.isShadowPass && renderpass != RenderPass.THIRD && renderpass != RenderPass.CAMERA)
        if (renderpass != RenderPass.THIRD && renderpass != RenderPass.CAMERA)
        {
            renderpass = RenderPass.CENTER;
        }

        VRData.VRDevicePose eye = dataholder.vrPlayer.vrdata_world_render.getEye(renderpass);
        this.setPosition(eye.getPosition());
        this.xRot = -eye.getPitch();
        this.yRot = eye.getYaw();
        this.forwards.set((float)eye.getDirection().x, (float)eye.getDirection().y, (float)eye.getDirection().z);
        Vec3 vec3 = eye.getCustomVector(new Vec3(0.0D, 1.0D, 0.0D));
        this.up.set((float)vec3.x, (float)vec3.y, (float)vec3.z);
        eye.getCustomVector(new Vec3(1.0D, 0.0D, 0.0D));
        this.left.set((float)vec3.x, (float)vec3.y, (float)vec3.z);
        this.rotation.set(0.0F, 0.0F, 0.0F, 1.0F);
        this.rotation.mul(Vector3f.YP.rotationDegrees(-this.yRot));
        this.rotation.mul(Vector3f.XP.rotationDegrees(this.xRot));
    }

    public void tick()
    {
    }

    public boolean isDetached()
    {
        return false;
    }
}
