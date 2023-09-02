package com.plusls.llsmanager.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.plusls.llsmanager.LlsManager;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class LlsAlertRawCommand {
    public static void register(LlsManager llsManager) {
        llsManager.commandManager.register(createBrigadierCommand(llsManager));
    }

    private static BrigadierCommand createBrigadierCommand(LlsManager llsManager) {
        var command = LiteralArgumentBuilder.<CommandSource>literal("lls-alertraw").
                then(RequiredArgumentBuilder.<CommandSource, String>argument("raw", StringArgumentType.greedyString()).
                        executes(commandContext -> {
                            String componentJson = StringArgumentType.getString(commandContext, "raw");
                            Component component = GsonComponentSerializer.gson().deserialize(componentJson);
                            llsManager.server.getAllPlayers().forEach(it -> it.sendMessage(component));
                            return 0;
                        })
                );
        return new BrigadierCommand(command);
    }
}
