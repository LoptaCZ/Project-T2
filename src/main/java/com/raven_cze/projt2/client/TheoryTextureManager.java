package com.raven_cze.projt2.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TheoryTextureManager{
    public static TheoryTextureManager INSTANCE = null;
    //private final AtlasTexture textureAtlas;
    private final TextureManager textureManager;

    private long lastID = 0;

    //will roll over at some point overriding possible textures. Not really a problem since it's ver unlikely somebody will generate 2^64 blackboards in a session
    public long bindNextID() {
        return ++lastID;
    }

    private final Map<String,TextureInstance>textures=new HashMap<>();

    public TheoryTextureManager(TextureManager manager){this.textureManager=manager;}

    public TextureInstance getTheoryInstance(String packed){
        TextureInstance texIns=this.textures.get(packed);
        if(texIns==null){
            texIns=new TextureInstance(null,bindNextID());
            this.textures.put(packed,texIns);
        }
        return texIns;
    }

    public class TextureInstance implements AutoCloseable{
        private static final int WIDTH = 16;
        private final byte[][] pixels;
        private final long id;
        //he be lazy
        @Nullable
        private DynamicTexture texture;
        @Nullable
        private RenderType renderType;
        @Nullable
        private ResourceLocation textureLocation;
        private final Map<Direction,List<BakedQuad>> models = new HashMap<>();

        public static void init(TextureManager textureManager) {
            INSTANCE = new TheoryTextureManager(textureManager);
        }

        private TextureInstance(byte[][] pixels, long id) {
            this.pixels = pixels;
            this.id = id;
        }

        private void initializeTexture() {
            this.texture = new DynamicTexture(WIDTH, WIDTH, false);

            for (int y = 0; y < pixels.length && y < WIDTH; y++) {
                for (int x = 0; x < pixels[y].length && x < WIDTH; x++) { //getColoredPixel(BlackboardBlock.colorFromByte(pixels[x][y]),x,y)
                    this.texture.getPixels().setPixelRGBA(x, y, getColoredPixel(pixels[x][y], x, y));
                }
            }
            this.texture.upload();

            this.textureLocation = TheoryTextureManager.this.textureManager.register("theory/" + Long.toHexString(id), this.texture);
            this.renderType = RenderType.entitySolid(textureLocation);
        }

        @Nonnull
        public List<BakedQuad> getModel(Direction dir, Function<byte[][],List<BakedQuad>> modelFactory){
            if(!models.containsKey(dir)){
                this.models.put(dir, modelFactory.apply(pixels));
            }
            return models.get(dir);
        }

        @Nonnull
        public ResourceLocation getTextureLocation() {
            if(textureLocation == null){
                this.initializeTexture();
            }
            return textureLocation;
        }

        @Nonnull
        public RenderType getRenderType() {
            if(renderType == null){
                this.initializeTexture();
            }
            return renderType;
        }

        //should be called when cache expires
        @Override
        public void close() {
            this.texture.close();
        }
    }
    private static int getColoredPixel(byte i, int x, int y) {
        int offset = i > 0 ? 16 : 0;
        int tint = 1;
        TextureAtlas textureMap = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
        TextureAtlasSprite sprite = textureMap.getSprite(null);//TODO replace null with ResourceLocation
        return getTintedColor(sprite, x, y, offset, tint);
    }


    private static int getTintedColor(TextureAtlasSprite sprite, int x, int y, int offset, int tint) {
        if (sprite == null || sprite.getFrameCount() == 0) return -1;
        int tintR = tint >> 16 & 255;
        int tintG = tint >> 8 & 255;
        int tintB = tint & 255;

        int pixel = sprite.getPixelRGBA(0, Math.min(sprite.getWidth()-1, x + offset), Math.min(sprite.getHeight()-1, y));

        // this is in 0xAABBGGRR format, not the usual 0xAARRGGBB.
        int totalB = pixel >> 16 & 255;
        int totalG = pixel >> 8 & 255;
        int totalR = pixel & 255;
        return NativeImage.combine(255, totalB * tintB / 255, totalG * tintG / 255, totalR * tintR / 255);
    }
}
