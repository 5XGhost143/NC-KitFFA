package de.ghost143.kitffa;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class KitSelect implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kit")) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    openGUI(sender, target);
                    return true;
                } else {
                    sender.sendMessage("§cSpieler nicht gefunden!");
                    return true;
                }
            } else {
                sender.sendMessage("§cVerwendung: /kit <Spieler>");
                return true;
            }
        }
        return false;
    }

    private void openGUI(CommandSender opener, Player target) {
        Player player = (opener instanceof Player) ? (Player) opener : target;

        Inventory gui = Bukkit.createInventory(null, 27, "§bKits");

        // SLOTS (EMPTY)
        for (int i = 0; i < 27; i++) {
            gui.setItem(i, createItem(
                    Material.BLACK_STAINED_GLASS_PANE,
                    "§4Leer",
                    Arrays.asList("§cAlles Leer")
            ));
        }

        // KITS
        gui.setItem(11, createItem(
                Material.DIAMOND_SWORD,
                "§aStandart Kit",
                Arrays.asList("§7■ §a1x §bDiamant Schwert", "§7■ §a1x §bDiamant Axt", "§7■ §a1x §bSchild", "§7■ §aGanze Diamant Rüstung")
        ));

        gui.setItem(12, createItem(
                Material.GOLDEN_APPLE,
                "§6Helper Kit",
                Arrays.asList("§7■ §a1x §bDiamant Axt", "§7■ §a1x §bVerzauberter Goldener Apfel","§7■ §aGanze Diamant Rüstung")
        ));

        gui.setItem(13, createItem(
                Material.NETHERITE_SWORD,
                "§4Ritter Kit",
                Arrays.asList("§7■ §a1x §bStein Schwert", "§7■ §a1x §bApfel","§7■ §aHalb Dia, Halb Netherite Rüstung")
        ));

        gui.setItem(14, createItem(
                Material.NETHERITE_SHOVEL,
                "§6Waffenschmiedt Kit",
                Arrays.asList("§7■ §bNetherite Axt", "§7■ §bNetherite Schwert","§7■ §aGanze Diamant Rüstung")
        ));

        gui.setItem(15, createItem(
                Material.APPLE  ,
                "§cHealer Kit",
                Arrays.asList("§7■ §a1x §bVerzauberter Goldener Apfel", "§7■ §a1x §bApfel","§7■ §a1x §bEisen Schwert","§7■ §aGanze Diamant Rüstung")
        ));

        player.openInventory(gui);
    }


    ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);
    ItemStack brustplatte = new ItemStack(Material.DIAMOND_CHESTPLATE);
    ItemStack hose = new ItemStack(Material.DIAMOND_LEGGINGS);
    ItemStack stiefel = new ItemStack(Material.DIAMOND_BOOTS);

    ItemStack schild = new ItemStack(Material.SHIELD);

    ItemStack netherhelm = new ItemStack(Material.NETHERITE_HELMET);
    ItemStack netherbrustplatte = new ItemStack(Material.NETHERITE_CHESTPLATE);
    ItemStack netherhose = new ItemStack(Material.NETHERITE_LEGGINGS);
    ItemStack netherstiefel = new ItemStack(Material.NETHERITE_BOOTS);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§bKits")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                Player player = (Player) event.getWhoClicked();

                if (clickedItem.getType() == Material.DIAMOND_SWORD) {
                    if (player.hasPermission("")) {
                        PlayerInventory inventory = player.getInventory();

                        player.getInventory().clear();

                        inventory.setHelmet(helm);

                        inventory.setChestplate(brustplatte);

                        inventory.setLeggings(hose);

                        inventory.setBoots(stiefel);

                        inventory.setItemInOffHand(schild);

                        ItemStack diamondAxe = createItem2(Material.DIAMOND_AXE, "§aStandart Kit §7■ §a1x §bDiamant Axt");
                        enchantItem(diamondAxe, Enchantment.DURABILITY, 2);
                        player.getInventory().addItem(diamondAxe);

                        ItemStack diamondSword = createItem2(Material.DIAMOND_SWORD, "§aStandart Kit §7■ §a1x §bDiamant Schwert");
                        enchantItem(diamondSword, Enchantment.DURABILITY, 2);
                        player.getInventory().addItem(diamondSword);

                        player.sendMessage("§a§a§lKitFFA §7» §aDu hast dein Neues Kit erhalten....");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                    } else {
                        player.sendMessage("§a§lKitFFA §7» §cDu hast dieses Kit nicht...");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                } else if (clickedItem.getType() == Material.GOLDEN_APPLE) {
                        if (player.hasPermission("kitffa.kit.helperkit")) {
                            PlayerInventory inventory = player.getInventory();

                            player.getInventory().clear();

                            inventory.setHelmet(helm);

                            inventory.setChestplate(brustplatte);

                            inventory.setLeggings(hose);

                            inventory.setBoots(stiefel);

                            ItemStack GOLDENAPPLE = createItem2(Material.ENCHANTED_GOLDEN_APPLE, "§6Helper Kit §7■ §a1x §bVerzauberter Goldener Apfel");
                            enchantItem(GOLDENAPPLE, Enchantment.DURABILITY, 2);
                            player.getInventory().addItem(GOLDENAPPLE);

                            ItemStack DIAXE = createItem2(Material.DIAMOND_AXE, "§6Helper Kit §7■ §a1x §bDiamant Axt");
                            enchantItem(DIAXE, Enchantment.DURABILITY, 1);
                            player.getInventory().addItem(DIAXE);

                            player.sendMessage("§a§a§lKitFFA §7» §aDu hast dein neues Kit erhalten.");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                        } else {
                            player.sendMessage("§a§lKitFFA §7» §cDu hast dieses Kit nicht.");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        }
                } else if (clickedItem.getType() == Material.NETHERITE_SWORD) {
                    if (player.hasPermission("kitffa.kit.ritterkit")) {
                        PlayerInventory inventory = player.getInventory();
                        player.getInventory().clear();


                        inventory.setHelmet(netherhelm);

                        inventory.setChestplate(brustplatte);

                        inventory.setLeggings(netherhose);

                        inventory.setBoots(stiefel);

                        ItemStack STONESWORD = createItem2(Material.STONE_SWORD, "§4Ritter Kit §7■ §a1x §bStein Schwert");
                        enchantItem(STONESWORD, Enchantment.DURABILITY, 2);
                        player.getInventory().addItem(STONESWORD);

                        ItemStack APPLE = createItem2(Material.APPLE, "§4Ritter Kit §7■ §a1x §bApfel");
                        enchantItem(APPLE, Enchantment.DURABILITY, 2);
                        player.getInventory().addItem(APPLE);

                        player.sendMessage("§a§a§lKitFFA §7» §aDu hast dein Neues Kit erhalten....");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                    } else {
                        player.sendMessage("§a§lKitFFA §7» §cDu hast dieses Kit nicht...");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                } else if (clickedItem.getType() == Material.NETHERITE_SHOVEL) {
                    if (player.hasPermission("kitffa.kit.waffenschmiedt")) {
                        PlayerInventory inventory = player.getInventory();
                        player.getInventory().clear();

                        inventory.setHelmet(helm);

                        inventory.setChestplate(brustplatte);

                        inventory.setLeggings(hose);

                        inventory.setBoots(stiefel);


                        ItemStack NETHERAXE = createItem2(Material.NETHERITE_AXE, "§6Waffenschmiedt Kit §7■ Netherite Axt");
                        enchantItem(NETHERAXE, Enchantment.DURABILITY, 1);
                        player.getInventory().addItem(NETHERAXE);

                        ItemStack NETHERSWORD = createItem2(Material.NETHERITE_SWORD, "§6Waffenschmiedt Kit §7■ Netherite Schwert");
                        enchantItem(NETHERSWORD, Enchantment.DURABILITY, 1);
                        player.getInventory().addItem(NETHERSWORD);

                        player.sendMessage("§a§a§lKitFFA §7» §aDu hast dein Neues Kit erhalten....");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                    } else {
                        player.sendMessage("§a§lKitFFA §7» §cDu hast dieses Kit nicht.");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                } else if (clickedItem.getType() == Material.APPLE) {
                    if (player.hasPermission("kitffa.kit.healer")) {
                        PlayerInventory inventory = player.getInventory();
                        player.getInventory().clear();

                        inventory.setHelmet(helm);

                        inventory.setChestplate(brustplatte);

                        inventory.setLeggings(hose);

                        inventory.setBoots(stiefel);

                        ItemStack GOLDAPPLE = createItem2(Material.ENCHANTED_GOLDEN_APPLE, "§cHealer Kit §7■ §a1x §bVerzauberter Goldener Apfel");
                        enchantItem(GOLDAPPLE, Enchantment.DURABILITY, 1);
                        player.getInventory().addItem(GOLDAPPLE);

                        ItemStack APPLE = createItem2(Material.APPLE, "§cHealer Kit §7■ §a1x §bApfel");
                        enchantItem(APPLE, Enchantment.DURABILITY, 1);
                        player.getInventory().addItem(APPLE);

                        ItemStack IRONSWORD = createItem2(Material.IRON_SWORD, "§cHealer Kit §7■ §a1x §bEisen Schwert");
                        enchantItem(IRONSWORD, Enchantment.DURABILITY, 1);
                        player.getInventory().addItem(IRONSWORD);


                        player.sendMessage("§a§a§lKitFFA §7» §aDu hast dein Neues Kit erhalten....");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                    } else {
                        player.sendMessage("§a§lKitFFA §7» §cDu hast dieses Kit nicht.");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
            }
        }
    }
    private ItemStack createItem(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createItem2(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    private void enchantItem(ItemStack item, Enchantment enchantment, int level) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
    }
}
