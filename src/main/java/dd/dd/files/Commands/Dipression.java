package dd.dd.files.Commands;

import dd.dd.pluginstartup;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static dd.dd.pluginstartup.plugin;

public class Dipression implements Listener {


    private boolean isRaining = false; // переменная для отслеживания состояния "дождя"

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        World world = player.getWorld();

        if (message.equalsIgnoreCase("NIHIS")) {
            if (world.getEnvironment() == World.Environment.THE_END && !isRaining) { // проверяем, что дождь еще не идет
                isRaining = true; // устанавливаем флаг, что дождь начался
                int duration = 30; // длительность дождя в секундах
                int taskID = startRaindrops(player, 20, duration); // запускаем задачу на спавн частиц
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    isRaining = false; // устанавливаем флаг, что дождь закончился
                    Bukkit.getScheduler().cancelTask(taskID); // останавливаем задачу
                }, duration * 20); // задержка в тиках, 20 тиков в секунду
            }
        }
    }

    public int startRaindrops(Player player, int radius, int duration) {
        Location center = player.getLocation();
        World world = center.getWorld();
        int taskID = new BukkitRunnable() { // создаем новую задачу
            int timeLeft = duration; // оставшееся время дождя в секундах

            @Override
            public void run() {
                if (timeLeft <= 0) { // если время вышло, останавливаем задачу
                    cancel();
                    return;
                }
                for (double x = center.getX() - radius; x <= center.getX() + radius; x++) {
                    for (double z = center.getZ() - radius; z <= center.getZ() + radius; z++) {
                        for (double y = center.getY() - radius; y <= center.getY() + radius; y++) {
                            Location loc = new Location(world, x, y, z);
                            if (loc.distance(center) <= radius) {
                                world.spawnParticle(Particle.WATER_DROP, loc, 1, 0, 0, 0, 0);
                            }
                        }
                    }
                }
                timeLeft--; // уменьшаем оставшееся время на 1 секунду
            }
        }.runTaskTimer(plugin, 0, 20).getTaskId(); // запускаем задачу и получаем ее ID
        return taskID;
    }
}