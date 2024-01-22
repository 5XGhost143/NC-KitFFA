package de.ghost143.kitffa;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class info implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("§8-*-*-*-*-*-*-*-*-*");
            player.sendMessage("§a§lKitFFA §7» §aBUILD VERSION§7: §bSNAPSHOT");
            player.sendMessage("§a§lKitFFA §7» §aVersion§7: §b2024.2.2");
            player.sendMessage("§a§lKitFFA §7» §aCreated by §bGhost143");
            player.sendMessage("§8-*-*-*-*-*-*-*-*-*");
        } else {
            sender.sendMessage("§a§lKitFFA §7» §4Fehler!§7, §cBeim Ausführen des Commands§b....");
        }
        return true;
    }
}
