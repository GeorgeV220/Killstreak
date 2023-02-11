package com.georgev22.hunter.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.annotation.*;
import com.georgev22.hunter.utilities.MessagesUtil;
import com.georgev22.hunter.utilities.player.UserData;
import com.georgev22.library.maps.HashObjectMap;
import com.georgev22.library.maps.ObjectMap;
import com.georgev22.library.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("killstreak")
public class KillstreakCommand extends BaseCommand {

    @Default
    @Description("{@@commands.descriptions.killstreak}")
    @CommandCompletion("@players")
    @CommandPermission("hunter.bounty")
    public void execute(@NotNull CommandIssuer commandIssuer, String @NotNull [] args) {
        if (args.length == 0) {
            if (!commandIssuer.isPlayer()) {
                MessagesUtil.ONLY_PLAYER_COMMAND.msg(commandIssuer.getIssuer());
                return;
            }
            UserData userData = UserData.getUser((Player) commandIssuer.getIssuer());
            MessagesUtil.KILLSTREAK_COMMAND.msg(
                    commandIssuer.getIssuer(),
                    placeholders(userData),
                    true);
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        UserData userData = UserData.getUser(target);
        MessagesUtil.KILLSTREAK_COMMAND_OTHER.msg(
                commandIssuer.getIssuer(),
                placeholders(userData),
                true);
    }

    private ObjectMap<String, String> placeholders(UserData userData) {
        return new HashObjectMap<String, String>()
                .append("%player%", userData.user().name())
                .append("%kills%", String.valueOf(userData.getKills()))
                .append("%killstreak%", String.valueOf(userData.getKillStreak()))
                .append("%level%", String.valueOf(userData.getLevel()))
                .append("%experience%", String.valueOf(userData.getExperience()))
                .append("%level_roman%", Utils.toRoman(userData.getLevel()));
    }
}
