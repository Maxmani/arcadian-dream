/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.util.IEntityDataSaver;

public class HouraiElixirCommand {

    @SuppressWarnings("unused")
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal(ArcadianDream.MOD_ID)
                .then(CommandManager.literal("elixir").requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("target", EntityArgumentType.entity())
                                .then(CommandManager.literal("clear").executes(HouraiElixirCommand::clear))
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.argument("level", IntegerArgumentType.integer(0, 3)).executes(HouraiElixirCommand::set))))));
    }

    private static int clear(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Entity entity = EntityArgumentType.getEntity(context, "target");
        IEntityDataSaver target = (IEntityDataSaver) entity;
        target.arcadiandream$getPersistentData().remove("elixir");

        context.getSource().sendFeedback(() -> Text.translatable(ArcadianDream.MOD_ID + ".commands.elixir.clear",
                entity.getName().getString()), true);

        return 1;
    }

    private static int set(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Entity entity = EntityArgumentType.getEntity(context, "target");
        int elixirLevel = IntegerArgumentType.getInteger(context, "level");
        IEntityDataSaver target = (IEntityDataSaver) entity;
        target.arcadiandream$getPersistentData().putByte("elixir", (byte) elixirLevel);

        context.getSource().sendFeedback(() -> Text.translatable(ArcadianDream.MOD_ID + ".commands.elixir.set",
                entity.getName().getString(), elixirLevel), true);

        return 1;
    }
}
