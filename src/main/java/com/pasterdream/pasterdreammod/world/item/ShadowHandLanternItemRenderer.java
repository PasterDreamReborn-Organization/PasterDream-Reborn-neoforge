package com.pasterdream.pasterdreammod.world.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.util.AnimUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.RenderUtil;

import java.util.HashSet;
import java.util.Set;

public class ShadowHandLanternItemRenderer extends GeoItemRenderer<ShadowHandLanternItem> {
    public ShadowHandLanternItemRenderer() {
        super(new ShadowHandLanternItemModel());
    }

    @Override
    public RenderType getRenderType(ShadowHandLanternItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    private static final float SCALE_RECIPROCAL = 1.0f / 16.0f;
    protected boolean renderArms = false;
    protected MultiBufferSource currentBuffer;
    protected RenderType renderType;
    public ItemDisplayContext transformType;
    protected ShadowHandLanternItem animatable;
    private final Set<String> hiddenBones = new HashSet<>();

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        this.transformType = transformType;
        if (this.animatable != null)
            this.animatable.getTransformType(transformType);
        super.renderByItem(stack, transformType, matrixStack, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    @Override
    public void actuallyRender(PoseStack matrixStackIn, ShadowHandLanternItem animatable, BakedGeoModel model, RenderType type, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, boolean isRenderer, float partialTicks,
            int packedLightIn, int packedOverlayIn, int renderColor) {
        this.currentBuffer = renderTypeBuffer;
        this.renderType = type;
        this.animatable = animatable;
        super.actuallyRender(matrixStackIn, animatable, model, type, renderTypeBuffer, vertexBuilder, isRenderer, partialTicks, packedLightIn, packedOverlayIn, renderColor);
        if (this.renderArms) {
            this.renderArms = false;
        }
    }

    @Override
    public void renderRecursively(PoseStack stack, ShadowHandLanternItem animatable, GeoBone bone, RenderType type, MultiBufferSource buffer, VertexConsumer bufferIn, boolean isReRender, float partialTick, int packedLightIn, int packedOverlayIn,
            int renderColor) {
        Minecraft mc = Minecraft.getInstance();
        String name = bone.getName();
        boolean renderingArms = false;
        if (name.equals("") || name.equals("right")) {
            bone.setHidden(true);
            renderingArms = true;
        } else {
            bone.setHidden(this.hiddenBones.contains(name));
        }
        if (this.transformType != null && this.transformType.firstPerson() && renderingArms) {
            AbstractClientPlayer player = mc.player;
            if (player != null) {
                float armsAlpha = player.isInvisible() ? 0.15f : 1.0f;
                PlayerRenderer playerRenderer = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(player);
                PlayerModel<AbstractClientPlayer> model = playerRenderer.getModel();
                stack.pushPose();
                RenderUtil.translateMatrixToBone(stack, bone);
                RenderUtil.translateToPivotPoint(stack, bone);
                RenderUtil.rotateMatrixAroundBone(stack, bone);
                RenderUtil.scaleMatrixForBone(stack, bone);
                RenderUtil.translateAwayFromPivotPoint(stack, bone);
                ResourceLocation loc = player.getSkin().texture();
                if (name.equals("")) {
                    stack.translate(-1.0f * SCALE_RECIPROCAL, 2.0f * SCALE_RECIPROCAL, 0.0f);
                    AnimUtils.renderPartOverBone(model.leftArm, bone, stack,
                            this.currentBuffer.getBuffer(RenderType.entitySolid(loc)),
                            packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                    AnimUtils.renderPartOverBone(model.leftSleeve, bone, stack,
                            this.currentBuffer.getBuffer(RenderType.entityTranslucent(loc)),
                            packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                } else if (name.equals("right")) {
                    stack.translate(1.0f * SCALE_RECIPROCAL, 2.0f * SCALE_RECIPROCAL, 0.0f);
                    AnimUtils.renderPartOverBone(model.rightArm, bone, stack,
                            this.currentBuffer.getBuffer(RenderType.entitySolid(loc)),
                            packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                    AnimUtils.renderPartOverBone(model.rightSleeve, bone, stack,
                            this.currentBuffer.getBuffer(RenderType.entityTranslucent(loc)),
                            packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                }
                VertexConsumer itemBuffer = this.currentBuffer.getBuffer(type);
                stack.popPose();
                super.renderRecursively(stack, animatable, bone, type, buffer, itemBuffer, isReRender, partialTick, packedLightIn, packedOverlayIn, renderColor);
                return;
            }
        }
        super.renderRecursively(stack, animatable, bone, type, buffer, bufferIn, isReRender, partialTick, packedLightIn, packedOverlayIn, renderColor);
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowHandLanternItem instance) {
        return super.getTextureLocation(instance);
    }
}
