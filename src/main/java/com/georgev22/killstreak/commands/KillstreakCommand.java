package com.georgev22.killstreak.commands;

import com.georgev22.api.maps.ObjectMap;
import com.georgev22.api.utilities.MinecraftUtils;
import com.georgev22.api.utilities.Utils;
import com.georgev22.killstreak.utilities.MessagesUtil;
import com.georgev22.killstreak.utilities.player.UserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KillstreakCommand extends BukkitCommand {

    public KillstreakCommand() {
        super("killstreak");
        this.description = "Killstreak command";
        this.usageMessage = "/killstreak";
        this.setPermission("killstreak.use");
        this.setPermissionMessage(MinecraftUtils.colorize(MessagesUtil.NO_PERMISSION.getMessages()[0]));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!testPermission(sender)) return true;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessagesUtil.ONLY_PLAYER_COMMAND.msg(sender);
                return true;
            }
            UserData userData = UserData.getUser((Player) sender);
            MessagesUtil.KILLSTREAK_MAIN_COMMAND.msg(
                    sender,
                    ObjectMap.newHashObjectMap()
                            .append("%player%", sender.getName())
                            .append("%kills%", String.valueOf(userData.getKills()))
                            .append("%killstreak%", String.valueOf(userData.getKillStreak()))
                            .append("%level%", String.valueOf(userData.getLevel()))
                            .append("%experience%", String.valueOf(userData.getExperience()))
                            .append("%level_roman%", Utils.toRoman(userData.getLevel())),
                    true);
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        UserData userData = UserData.getUser(target);
        MessagesUtil.KILLSTREAK_MAIN_COMMAND_OTHER.msg(
                sender,
                ObjectMap.newHashObjectMap()
                        .append("%player%", target.getName())
                        .append("%kills%", String.valueOf(userData.getKills()))
                        .append("%killstreak%", String.valueOf(userData.getKillStreak()))
                        .append("%level%", String.valueOf(userData.getLevel()))
                        .append("%experience%", String.valueOf(userData.getExperience()))
                        .append("%level_roman%", Utils.toRoman(userData.getLevel())),
                true);
        return true;
    }
}
