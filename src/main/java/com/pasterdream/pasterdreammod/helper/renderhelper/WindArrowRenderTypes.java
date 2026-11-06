package com.pasterdream.pasterdreammod.helper.renderhelper;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class WindArrowRenderTypes
{
    /**
     * 萦风箭拖尾：纯色（POSITION_COLOR）三棱柱管，加法混合、禁剔除、不写深度。
     * 混合用加法（LIGHTNING_TRANSPARENCY），输出到主目标（MAIN_TARGET），深度测试 LEQUAL。
     */
    public static final RenderType FLUFFY_WIND_TRAIL = RenderType.create(
            "fluffy_wind_trail",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
                    .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
                    .setOutputState(RenderStateShard.MAIN_TARGET)
                    .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .createCompositeState(false)
    );
}
