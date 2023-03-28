package dd.dd.files.Commands;

import dd.dd.pluginstartup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.BatToggleSleepEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class endernodath implements Listener {

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        Plugin plugin = pluginstartup.getPlugin(pluginstartup.class);

        Boolean start = plugin.getConfig().getBoolean("bed_end");


        if (start == true) {


            if (event.getPlayer().getWorld().getEnvironment().equals((Object) World.Environment.THE_END)) {

                event.setCancelled(true);
                event.setUseBed(Event.Result.ALLOW);
                event.getPlayer().setBedSpawnLocation(event.getBed().getLocation(), false);

            } else {
                event.setCancelled(true);
                event.setUseBed(Event.Result.ALLOW);
                event.getPlayer().setBedSpawnLocation(event.getBed().getLocation(), false);
            }

            if (event.getPlayer().getWorld().getEnvironment().equals((Object) World.Environment.NETHER)) {
                event.setCancelled(true);
                event.setUseBed(Event.Result.ALLOW);
                event.getPlayer().setBedSpawnLocation(event.getBed().getLocation(), false);
            } else {
                event.setCancelled(true);
                event.setUseBed(Event.Result.ALLOW);
                event.getPlayer().setBedSpawnLocation(event.getBed().getLocation(), false);
            }
        }

        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            World world = event.getPlayer().getWorld();
            Location bedLocation = event.getBed().getLocation();

            if (player.hasPermission("molus.active.never")) {
                for (Player other : Bukkit.getOnlinePlayers()) {
                    if (other.getLocation().distance(bedLocation) <= 200 && !other.isSleeping()) {
                        if (world.getEnvironment() == World.Environment.NETHER) {
                            BukkitTask ghastTask = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    world.playSound(bedLocation, Sound.AMBIENT_CAVE, 100, 1);
                                }
                            }.runTaskTimer(plugin, 0L, 20L); // 20L = 1 second


                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (!player.isSleeping()) {
                                        ghastTask.cancel();
                                    }
                                }
                            }.runTaskTimer(plugin, 0L, 1L);
                        } else if (world.getEnvironment() == World.Environment.THE_END) {
                            BukkitTask endermanTask = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    world.playSound(bedLocation, Sound.AMBIENT_CAVE, 100, 1);
                                }
                            }.runTaskTimer(plugin, 0L, 20L); // 20L = 1 second


                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (!player.isSleeping()) {
                                        endermanTask.cancel();
                                    }
                                }
                            }.runTaskTimer(plugin, 0L, 1L);
                        } else if (world.getEnvironment() == World.Environment.NORMAL) {
                            BukkitTask ghastTask = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    world.playSound(bedLocation, Sound.AMBIENT_CAVE, 100, 1);
                                }
                            }.runTaskTimer(plugin, 0L, 20L); // 20L = 1 second


                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (!player.isSleeping()) {
                                        ghastTask.cancel();
                                    }
                                }
                            }.runTaskTimer(plugin, 0L, 1L);
                        }
                    }
                }
            }
        }
    }
}

