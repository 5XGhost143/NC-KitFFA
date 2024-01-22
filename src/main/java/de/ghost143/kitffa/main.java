package de.ghost143.kitffa;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main extends JavaPlugin implements Listener {

    private Location spawnLocation;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        getLogger().info("§a§lKitFFA §7» §aDas Plugin wurde Aktiviert...");
        loadConfig();
        spawnLocation = loadSpawnLocation();

        getCommand("info").setExecutor(new info());
        getCommand("kit").setExecutor(new KitSelect());

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new KitSelect(), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("§a§lKitFFA §7» §cDas Plugin wurde Deaktiviert...");
    }


    private void loadConfig() {
        File configFile = new File(getDataFolder(), "loc.yml");
        if (!configFile.exists()) {
            saveResource("loc.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private Location loadSpawnLocation() {
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        String worldName = config.getString("spawn.world");
        return new Location(getServer().getWorld(worldName), x, y, z);
    }

    private void saveSpawnLocation(Location location) {
        config.set("spawn.x", location.getX());
        config.set("spawn.y", location.getY());
        config.set("spawn.z", location.getZ());
        config.set("spawn.world", location.getWorld().getName());

        try {
            config.save(new File(getDataFolder(), "loc.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);
    ItemStack brustplatte = new ItemStack(Material.DIAMOND_CHESTPLATE);
    ItemStack hose = new ItemStack(Material.DIAMOND_LEGGINGS);
    ItemStack stiefel = new ItemStack(Material.DIAMOND_BOOTS);

    ItemStack schild = new ItemStack(Material.SHIELD);


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(spawnLocation);

        PlayerInventory inventory = player.getInventory();

        player.getInventory().clear();

        inventory.setHelmet(helm);

        inventory.setChestplate(brustplatte);

        inventory.setLeggings(hose);

        inventory.setBoots(stiefel);

        ItemStack diamondAxe = createItem(Material.DIAMOND_AXE, "§aStandart Kit §7■ §a1x §bDiamant Axt");
        enchantItem(diamondAxe, Enchantment.DURABILITY, 10);
        player.getInventory().addItem(diamondAxe);

        ItemStack diamondSword = createItem(Material.DIAMOND_SWORD, "§aStandart Kit §7■ §a1x §bDiamant Schwert");
        enchantItem(diamondSword, Enchantment.DURABILITY, 10);
        player.getInventory().addItem(diamondSword);

        player.sendMessage("§a§a§lKitFFA §7» §aDu hast das Standart Kit erhalten...");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
    }

    private void enchantItem(ItemStack item, Enchantment enchantment, int level) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        event.setDeathMessage(null);

        victim.sendMessage("§a§lKitFFA §7» §4Du bist gestorben....");

        if (killer != null) {
            killer.sendMessage("§a§lKitFFA §7» §aDu hast §b" + victim.getName() + " §agetötet!");
            killer.playSound(killer.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
            killer.setHealth(killer.getMaxHealth());
        }

        getServer().broadcastMessage("§a§lKitFFA §7» §aDer Spieler §b" + victim.getName() + " §aWurde getötet...");


        event.getDrops().clear();

        event.setKeepInventory(true);
        event.setKeepLevel(true);
    }

    @EventHandler
    public void onPlayerDeath2(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            player.spigot().respawn();
            player.teleport(spawnLocation);
        }, 2L);
    }


    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Mob) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        event.getPlayer().sendMessage(""); // Leere Nachricht senden, um die Standardnachricht zu ersetzen
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin2(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    private ItemStack createItem(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setloc") && sender instanceof Player) {
            Player player = (Player) sender;
            spawnLocation = player.getLocation();
            saveSpawnLocation(spawnLocation);
            player.sendMessage("§a§lKitFFA §7» §aLocation gesetzt!....");
            return true;
        }
        return false;
    }
}
