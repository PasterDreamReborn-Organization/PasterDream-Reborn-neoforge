package com.pasterdream.pasterdreammod.world.block.claypan;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class ClaypanBlockEntityRenderer implements BlockEntityRenderer<ClaypanBlockEntity>
{
    public ClaypanBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(ClaypanBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        FluidStack fluidStack = blockEntity.getFluidTanks()[0].getFluid();
        if (fluidStack.isEmpty())
        {
            return;
        }

        //获取流体纹理
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = extensions.getStillTexture(fluidStack);

        if (stillTexture == null)
        {
            return;
        }

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        if (sprite == null)
        {
            return;
        }

        //获取流体颜色
        int color = extensions.getTintColor(fluidStack);
        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        float minX = 0.0625f;
        float maxX = 0.9375f;
        float minZ = 0.0625f;
        float maxZ = 0.9375f;
        float yLevel = 0.1875f;

        //渲染
        poseStack.pushPose();
        VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        float nx = 0, ny = 1, nz = 0;

        consumer.addVertex(matrix, minX, yLevel, minZ)
                .setColor(r, g, b, a)
                .setUv(sprite.getU0(), sprite.getV0())
                .setOverlay(packedOverlay)
                .setLight(packedLight)
                .setNormal(nx, ny, nz);

        consumer.addVertex(matrix, minX, yLevel, maxZ)
                .setColor(r, g, b, a)
                .setUv(sprite.getU0(), sprite.getV1())
                .setOverlay(packedOverlay)
                .setLight(packedLight)
                .setNormal(nx, ny, nz);

        consumer.addVertex(matrix, maxX, yLevel, maxZ)
                .setColor(r, g, b, a)
                .setUv(sprite.getU1(), sprite.getV1())
                .setOverlay(packedOverlay)
                .setLight(packedLight)
                .setNormal(nx, ny, nz);

        consumer.addVertex(matrix, maxX, yLevel, minZ)
                .setColor(r, g, b, a)
                .setUv(sprite.getU1(), sprite.getV0())
                .setOverlay(packedOverlay)
                .setLight(packedLight)
                .setNormal(nx, ny, nz);

        poseStack.popPose();
    }
}
