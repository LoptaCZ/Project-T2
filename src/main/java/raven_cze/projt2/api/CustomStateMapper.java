package raven_cze.projt2.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class CustomStateMapper extends StateMapperBase {
    public static HashMap<String,StateMapperBase> stateMappers=new HashMap();

    public static StateMapperBase getStateMapper(BlockInterfaces.IMetaBlock metaBlock){
        String key = metaBlock.getBlockName();
        StateMapperBase mapper=(StateMapperBase)stateMappers.get(key);
        if(mapper==null){
            mapper=metaBlock.getCustomMapper();
            if(mapper==null)
                mapper=new CustomStateMapper();
            stateMappers.put(key,mapper);
        }
        return mapper;
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state){
        try{
            ResourceLocation rl = (ResourceLocation)Block.REGISTRY.getNameForObject(state.getBlock());
            BlockInterfaces.IMetaBlock metaBlock = (BlockInterfaces.IMetaBlock)state.getBlock();
            String custom = metaBlock.getCustomStateMapping(state.getBlock().getMetaFromState(state),false);
            if(custom!=null) rl=new ResourceLocation(rl.toString()+"_"+custom);
            String prop=metaBlock.appendPropertiesToState()?getPropertyString(state.getProperties()):null;
            return new ModelResourceLocation(rl,prop);
        }catch(Exception e){
            e.printStackTrace();
            ResourceLocation rl=(ResourceLocation) Block.REGISTRY.getNameForObject(state.getBlock());
            return new ModelResourceLocation(rl,"");
        }
    }
}
