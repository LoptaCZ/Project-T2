package raven_cze.projt2.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import raven_cze.projt2.api.ItemBase;
import raven_cze.projt2.common.PT2Content;
import raven_cze.projt2.common.block.TileVoidCube;

public class ItemCrystal extends ItemBase {
    public ItemCrystal(){super("crystal",64,"air","earth","fire","water","taint","vis","empty");}

    @Override
    public @NotNull EnumActionResult onItemUseFirst(@NotNull EntityPlayer player,@NotNull World world,@NotNull BlockPos pos,@NotNull EnumFacing side, float hitX, float hitY, float hitZ,@NotNull EnumHand hand) {
        if(world.getBlockState(pos).getBlock()==PT2Content.blockVoidDevice &&world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))==3){
            TileVoidCube tvc = (TileVoidCube) world.getTileEntity(pos);
            assert tvc != null;
            if (tvc.placed == -1) {
                tvc.placed = (byte) player.getActiveItemStack().getItemDamage();
                //TODO IDK!? world.j(i, j, k, world.a(i, j, k));
                player.getActiveItemStack().setCount(player.getActiveItemStack().getCount() - 1);
                //TODO PLAY SOUND world.a((i + 0.5F), (j + 0.5F), (k + 0.5F), "thaumcraft.place", 0.5F, 1.0F);
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.PASS;
        }return EnumActionResult.FAIL;
    }
}
