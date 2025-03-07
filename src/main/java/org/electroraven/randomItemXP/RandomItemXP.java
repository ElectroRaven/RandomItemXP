package org.electroraven.randomItemXP;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class RandomItemXP extends JavaPlugin implements Listener {
    private final Random random = new Random();
    private final NamespacedKey placedBlockKey = new NamespacedKey(this, "placed");
    private boolean isLevelRandomizerActive = false;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("getRandomizer")).setExecutor(this);
        Objects.requireNonNull(getCommand("startlevelrandomizer")).setExecutor(this);
        Objects.requireNonNull(getCommand("stoplevelrandomizer")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("getRandomizer")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ItemStack randomizerItem = createRandomizerItem();
                player.getInventory().addItem(randomizerItem);
                player.sendMessage("You received a Randomizer!");
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("startlevelrandomizer")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                isLevelRandomizerActive = true;
                player.sendMessage("Level Randomizer is now active!");
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("stoplevelrandomizer")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                isLevelRandomizerActive = false;
                player.sendMessage("Level Randomizer has been deactivated.");
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        if (isLevelRandomizerActive) {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand().getType() == Material.STICK) {
                int levels = player.getLevel();
                if (levels > 0) {
                    player.setLevel(0);
                    ItemStack randomItem = createRandomItem(levels);
                    player.getInventory().addItem(randomItem);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                    player.spawnParticle(Particle.ENCHANT, player.getLocation(), 10);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (isLevelRandomizerActive) {
            // Markiere den Block als vom Spieler platziert
            event.getBlock().getState().setMetadata("placed", new org.bukkit.metadata.FixedMetadataValue(this, true));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isLevelRandomizerActive) {
            if (!event.getBlock().hasMetadata("placed")) {
                event.setDropItems(false); // Wenn der Block nicht platziert wurde, keine Drops
            }
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        if (isLevelRandomizerActive) {
            event.getDrops().clear(); // Entfernen aller Mob-Drops
        }
    }

    private ItemStack createRandomizerItem() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Randomizer");
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createRandomItem(int amount) {
        Material material = getRandomMaterial();
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            item.setItemMeta(meta);
        }
        return item;
    }

    private Material getRandomMaterial() {
        List<Material> materials = List.of(Material.values());
        return materials.get(random.nextInt(materials.size()));
    }

    @Override
    public void onDisable() {
    }
}
