package com.raven_cze.projt2.common.content.items;

import com.raven_cze.projt2.api.IEnchantableItem;
import com.raven_cze.projt2.api.IVisRepairable;
import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.PT2Enchants;
import com.raven_cze.projt2.common.content.tiles.TileHole;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ItemPortableHole extends PT2Item implements IEnchantableItem,IVisRepairable{
    public ItemPortableHole(Properties properties) {
        super(properties);
        properties.durability(500);// Charge
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack){return 120;}


    @Override
    public ArrayList<Enchantment> acceptableEnchants(){
        ArrayList<Enchantment>ench=new ArrayList<>();
        ench.add(PT2Enchants.Repair.get());
        ench.add(Enchantments.UNBREAKING);
        return ench;
    }

    @Override
    public boolean isRepairable(@NotNull ItemStack stack){return false;}

    @Override
    public float visRepairCost(){return 0.75F;}

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext ctx){
        BlockState cBlock=ctx.getLevel().getBlockState(ctx.getClickedPos());
        if(cBlock.hasBlockEntity()){
            return InteractionResult.PASS;
        }else{
            createHole(ctx.getLevel(),ctx.getClickedPos(),ctx.getClickedFace(),(byte)10,10);
        }
        return InteractionResult.SUCCESS;
    }

    public static boolean createHole(Level level,BlockPos pos,Direction side,byte count,int depth){
        BlockState state=level.getBlockState(pos);
        if(!level.isClientSide && state.getBlock()!= net.minecraft.world.level.block.Blocks.AIR && state.getBlock()!= net.minecraft.world.level.block.Blocks.BEDROCK && state.getBlock().defaultDestroyTime()!=-1.0F){
            System.out.println(state);
            if(!level.setBlockAndUpdate(pos,PT2Blocks.HOLE.get().defaultBlockState())){
                TileHole th=new TileHole(pos,state,(short)10,(byte)1,state);
                //TileHole th=(TileHole)level.getBlockEntity(pos);
                if(th.getLevel()!=null){
                    TileHole.oldState=state;
                    TileHole.countdownMax =(short)depth;
                    TileHole.count =count;
                    TileHole.direction =side;

                    th.requestModelDataUpdate();
                    th.setChanged();
                }
            }
            return true;
        }
        return false;
    }
}
