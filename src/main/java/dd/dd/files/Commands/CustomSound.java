package dd.dd.files.Commands;

import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomSound implements Listener {

    public static void playCustomSound(Player player, JavaPlugin plugin) {
        if (player.getInventory().contains(Material.MILK_BUCKET) &&
                player.getInventory().getItem(player.getInventory().first(Material.MILK_BUCKET)).getItemMeta().getDisplayName().equals("Фиал Крови Возродителя")) {

            org.bukkit.Sound sound = org.bukkit.Sound.AMBIENT_CAVE;
            player.playSound(player.getLocation(), sound, SoundCategory.MASTER, 100, 1);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.getInventory().contains(Material.MILK_BUCKET) &&
                            player.getInventory().getItem(player.getInventory().first(Material.MILK_BUCKET)).getItemMeta().getDisplayName().equals("Фиал Крови Возродителя")) {

                        player.playSound(player.getLocation(), sound, SoundCategory.MASTER, 100, 1);

                    } else {

                        cancel();

                    }
                }
            }.runTaskTimer(plugin, 0, 20); // Запускаем таймер с интервалом 20 тиков (1 секунда = 20 тиков)
        }
    }
}