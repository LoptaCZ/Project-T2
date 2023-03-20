package com.raven_cze.projt2.common.network.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandHandler{
    public static void registerServer(CommandDispatcher<CommandSourceStack>dispatcher){
        LiteralArgumentBuilder<CommandSourceStack>main;
        main=Commands.literal("pt2");
        addCMDs(dispatcher,main);
        main=Commands.literal("projt2");
        addCMDs(dispatcher,main);
        main=Commands.literal("projectt2");
        addCMDs(dispatcher,main);
    }

    public static void addCMDs(CommandDispatcher<CommandSourceStack>dispatcher,LiteralArgumentBuilder<CommandSourceStack>main){
        main.then(CommandResearch.create())
                //.then(CommandResearch.create())
        ;
        dispatcher.register(main);
    }
}
