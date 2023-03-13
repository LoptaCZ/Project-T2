package com.raven_cze.projt2.common.network.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Predicate;

public class CommandResearch{
    public static LiteralArgumentBuilder<CommandSourceStack>create(){
        LiteralArgumentBuilder<CommandSourceStack>main=Commands.literal("research");
        main.requires(source->source.hasPermission(4));
        //main.then( Commands.argument("player",EntityArgument.player()).executes(context->clear(context,context.getArgument("player",ServerPlayer.class))) );
        main.then(add());
        main.then(remove());
        main.then(gain());
        main.then(clear());
        main.then(check());
        return main;
    }

    //=========================================================
    //TODO pt2 research < add | remove | gain | clear | check >
    //=========================================================
    //TODO      ADD <RESEARCH_NAME> <CATEGORY> <DIFFICULTY> <PREV_RESEARCH> <RESEARCH_THING>
    //TODO      REMOVE <RESEARCH_NAME>
    //TODO      GAIN <PLAYER> <RESEARCH_NAME>
    //TODO      CLEAR <PLAYER> *<RESEARCH_NAME>*
    //TODO      CHECK <PLAYER> <RESEARCH_NAME>

    private static LiteralArgumentBuilder<CommandSourceStack>add(){
        LiteralArgumentBuilder<CommandSourceStack>set=Commands.literal("add");
        set.requires(source->source.hasPermission(source.getServer().getOperatorUserPermissionLevel()))
                .then( Commands.argument("research_name",ComponentArgument.textComponent()) )
                .then( Commands.argument("category",IntegerArgumentType.integer(0,4)) )
                .then( Commands.argument("difficulty",IntegerArgumentType.integer(0,6)) )
                .then( Commands.argument("prev_research",ComponentArgument.textComponent()) )
                .then( Commands.argument("thing",ItemArgument.item()) )
                .executes(ctx->{
                    Object thing=ctx.getArgument("thing",Object.class);
                    if(thing instanceof ItemStack){
                        ctx.getSource().sendSuccess(new TranslatableComponent("enchantment.projt2.ice_aspect"),true);
                        return add(ctx,ctx.getSource().getPlayerOrException());
                    }else if(thing instanceof Enchantment){
                        ctx.getSource().sendSuccess(new TranslatableComponent("enchantment.projt2.venom_aspect"),true);
                        return add(ctx,ctx.getSource().getPlayerOrException());
                    }else{
                        ctx.getSource().sendFailure(new TranslatableComponent("mco.client.outdated.title"));
                    }
                    return 0;
                });
        return set;
    }
    private static LiteralArgumentBuilder<CommandSourceStack>remove(){
        LiteralArgumentBuilder<CommandSourceStack>set=Commands.literal("remove");
        return set;
    }
    private static LiteralArgumentBuilder<CommandSourceStack>gain(){
        LiteralArgumentBuilder<CommandSourceStack>set=Commands.literal("gain");
        return set;
    }
    private static LiteralArgumentBuilder<CommandSourceStack>clear(){
        LiteralArgumentBuilder<CommandSourceStack>set=Commands.literal("clear");
        return set;
    }
    private static LiteralArgumentBuilder<CommandSourceStack>check(){
        LiteralArgumentBuilder<CommandSourceStack>set=Commands.literal("check");
        return set;
    }

    private static int add(CommandContext<CommandSourceStack>context,ServerPlayer player){
        //  Add RESEARCH to Game
        return Command.SINGLE_SUCCESS;
    }
    private static int remove(CommandContext<CommandSourceStack>context,ServerPlayer player){
        //  Remove RESEARCH from Game
        return Command.SINGLE_SUCCESS;
    }
    private static int gain(CommandContext<CommandSourceStack>context,ServerPlayer player){
        //  Add RESEARCH to Player
        context.getSource().sendSuccess(new TranslatableComponent("research.projt2.learn"),true);
        context.getSource().sendFailure(new TranslatableComponent("research.projt2.fail"));
        return Command.SINGLE_SUCCESS;
    }
    private static int clear(CommandContext<CommandSourceStack>context,ServerPlayer player){
        //  Remove whole RESEARCH from PLAYER
        if(player.hasPermissions(4)){
            //  Do Nothing Lol
        }
        context.getSource().sendSuccess(new TranslatableComponent("itemGroup.projt2"),true);
        context.getSource().sendFailure(new TranslatableComponent("research.projt2.fail"));
        return Command.SINGLE_SUCCESS;
    }
    private static int check(CommandContext<CommandSourceStack>context,ServerPlayer player){
        //  Check Validity of RESEARCH
        return Command.SINGLE_SUCCESS;
    }
}
