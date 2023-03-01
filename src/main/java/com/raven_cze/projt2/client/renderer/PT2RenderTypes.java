package com.raven_cze.projt2.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.raven_cze.projt2.client.ClientHandler;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;

@SuppressWarnings("unused")
public class PT2RenderTypes extends RenderStateShard{
    private static final int bufferSize=262144;
    public PT2RenderTypes(String pName,Runnable pSetupState,Runnable pClearState){super(pName,pSetupState,pClearState);}
    //  Shader State Shards
    private static final ShaderStateShard RENDERTYPE_VOID_CUBE_SHADER=new ShaderStateShard(ClientHandler::getRenderTypeVoidCubeShader);

    public static final VertexFormat BLOCK_WITH_OVERLAY=new VertexFormat(ImmutableMap.<String,VertexFormatElement>builder()
            .put("Position",DefaultVertexFormat.ELEMENT_POSITION)
            .put("Color",DefaultVertexFormat.ELEMENT_COLOR)
            .put("UV0",DefaultVertexFormat.ELEMENT_UV0)
            .put("UV1",DefaultVertexFormat.ELEMENT_UV1)
            .put("UV2",DefaultVertexFormat.ELEMENT_UV2)
            .put("Normal",DefaultVertexFormat.ELEMENT_NORMAL)
            .put("Padding",DefaultVertexFormat.ELEMENT_PADDING)
            .build());

    //  Render Types
    public static final RenderType TRANSLUCENT_FULLBRIGHT;
    public static final RenderType SOLID_FULLBRIGHT;
    public static final RenderType LINES;
    public static final RenderType POINTS;
    public static final RenderType TRANSLUCENT_TRIANGLES;
    public static final RenderType TRANSLUCENT_POSITION_COLOR;
    public static final RenderType TRANSLUCENT_NO_DEPTH;
    public static final RenderType CHUNK_MARKER;
    public static final RenderType POSITION_COLOR_LIGHTMAP;
    public static final RenderType ITEM_DAMAGE_BAR;
    public static final RenderType PARTICLES;
    private static final Function<ResourceLocation, RenderType> GUI_CUTOUT;
    private static final Function<ResourceLocation, RenderType> GUI_TRANSLUCENT;
    private static final Function<ResourceLocation, RenderType> FULLBRIGHT_TRANSLUCENT;
    private static final ShaderStateShard RENDERTYPE_POSITION_COLOR = RENDERTYPE_LIGHTNING_SHADER;
    protected static final RenderStateShard.TextureStateShard BLOCK_SHEET_MIPPED = new RenderStateShard.TextureStateShard(InventoryMenu.BLOCK_ATLAS, false, true);
    protected static final RenderStateShard.LightmapStateShard LIGHTMAP_DISABLED = new RenderStateShard.LightmapStateShard(false);
    protected static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }, RenderSystem::disableBlend);
    protected static final RenderStateShard.TransparencyStateShard NO_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
            "no_transparency",
            RenderSystem::disableBlend, () -> {
    });
    protected static final RenderStateShard.DepthTestStateShard DEPTH_ALWAYS = new RenderStateShard.DepthTestStateShard("always",GL11.GL_ALWAYS);

    public static final RenderType VOID_CUBE;
    public static final RenderType VOID_CUBE_32;

    //  Creating Render Type Definitions
    static{
        TRANSLUCENT_FULLBRIGHT=null;
        SOLID_FULLBRIGHT=null;
        LINES=null;
        POINTS=null;
        TRANSLUCENT_TRIANGLES=null;
        TRANSLUCENT_POSITION_COLOR=null;
        TRANSLUCENT_NO_DEPTH=null;
        CHUNK_MARKER=null;
        POSITION_COLOR_LIGHTMAP=null;
        ITEM_DAMAGE_BAR=null;
        PARTICLES=null;

        GUI_CUTOUT=Util.memoize(texture->RenderType.create("gui_cutout_"+texture,DefaultVertexFormat.POSITION_COLOR_TEX,VertexFormat.Mode.QUADS,256,false,false,makeGUIState(texture).createCompositeState(false)));
        GUI_TRANSLUCENT=Util.memoize(texture->RenderType.create("gui_translucent_"+texture,DefaultVertexFormat.POSITION_COLOR_TEX,VertexFormat.Mode.QUADS,256,false,false,makeGUIState(texture).setTransparencyState(TRANSLUCENT_TRANSPARENCY).createCompositeState(false)));
        FULLBRIGHT_TRANSLUCENT=Util.memoize(texture->RenderType.create(
                "projt2:fullbright_translucent_"+texture,DefaultVertexFormat.NEW_ENTITY,VertexFormat.Mode.QUADS,256,
                false,false,RenderType.CompositeState.builder()
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setTextureState(new TextureStateShard(texture,false,false))
                        .setLightmapState(LIGHTMAP_DISABLED)
                        .setShaderState(RenderStateShard.NO_SHADER)
                        .setCullState(NO_CULL)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true)
                        )
        );

        //  My Own
        VOID_CUBE_32=RenderType.create("projt2:void_cube_32",DefaultVertexFormat.POSITION,VertexFormat.Mode.QUADS,bufferSize,false,false,RenderType.CompositeState.builder().setShaderState(RENDERTYPE_VOID_CUBE_SHADER).setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(VoidCubeRenderer.tunnel,true,false).add(new ResourceLocation("projt2","textures/particlefield32.png"),true,false).build()).createCompositeState(false));
        VOID_CUBE=RenderType.create("projt2:void_cube",DefaultVertexFormat.POSITION,VertexFormat.Mode.QUADS,bufferSize,false,false,RenderType.CompositeState.builder().setShaderState(RENDERTYPE_VOID_CUBE_SHADER).setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(VoidCubeRenderer.tunnel,true,false).add(VoidCubeRenderer.field,true,false).build()).createCompositeState(false));
    }

    public static RenderType getGUI(ResourceLocation texture){return GUI_CUTOUT.apply(texture);}
    public static RenderType getGUI_Trans(ResourceLocation texture){return GUI_TRANSLUCENT.apply(texture);}

    private static RenderType.CompositeState.CompositeStateBuilder makeGUIState(ResourceLocation texture){
        return RenderType.CompositeState.builder()
                .setTextureState( new RenderStateShard.TextureStateShard(texture,false,false) )
                .setShaderState(RenderStateShard.POSITION_COLOR_TEX_SHADER);
    }
}
