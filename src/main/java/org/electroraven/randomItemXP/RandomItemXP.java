package org.electroraven.randomItemXP;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("getRandomizer")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack randomizerItem = createRandomizerItem();
            player.getInventory().addItem(randomizerItem);
            player.sendMessage("You received a Randomizer!");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
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

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.getBlock().getState().setMetadata("placed", new org.bukkit.metadata.FixedMetadataValue(this, true));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getBlock().hasMetadata("placed")) {
            event.setDropItems(false); // Wenn der Block nicht platziert wurde, keine Drops
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        event.getDrops().clear();
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
        return item;
    }

    private Material getRandomMaterial() {
        List<Material> materials = List.of(Material.values());
        return materials.get(random.nextInt(materials.size()));
    }

    private boolean isPlayerPlaced(Material material) {
        return material.isBlock();
    }

    @Override
    public void onDisable() {
    }
}
