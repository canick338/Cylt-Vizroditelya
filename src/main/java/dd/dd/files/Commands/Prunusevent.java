package dd.dd.files.Commands;

import dd.dd.pluginstartup;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;



public class Prunusevent implements Listener {

    HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
    private static Set<String> cooldownPlayers = new HashSet<>();


    private Entity entity;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ItemStack emptyStack = new ItemStack(Material.AIR);
        // Создаем объект FireworkEffect и добавляем ему нужный эффект
        FireworkEffect effect = FireworkEffect.builder()
                .withColor(Color.RED)
                .with(FireworkEffect.Type.BURST)
                .build();

        // Создаем объект FireworkMeta и добавляем туда наш эффект
        FireworkMeta meta = (FireworkMeta) Bukkit.getItemFactory().getItemMeta(Material.FIREWORK_ROCKET);
        meta.addEffect(effect);

        // Создаем объект фейерверка и задаем ему наш мета-объект
        Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
        firework.setFireworkMeta(meta);
    }

    @EventHandler
    public static void onPlayerChat(PlayerChatEvent e) {


        Plugin plugin = pluginstartup.getPlugin(pluginstartup.class);


        double x = plugin.getConfig().getDouble("x");
        double y = plugin.getConfig().getDouble("y");
        double z = plugin.getConfig().getDouble("z");

        double maxDist = plugin.getConfig().getDouble("maxDist");
        double coord = plugin.getConfig().getDouble("CoordDedoTeleport");

        double x2 = plugin.getConfig().getDouble("x2");
        double y2 = plugin.getConfig().getDouble("y2");
        double z2 = plugin.getConfig().getDouble("z2");


        Player p = e.getPlayer();
        String name = plugin.getConfig().getString("Owner");
        String knife = plugin.getConfig().getString("dagger");
        String zam = plugin.getConfig().getString("TeleportIllud");
        plugin.saveConfig();
        Location loc = new Location(Bukkit.getWorld("world"), x, y, z, 0, 0);
        Location location = p.getLocation();
        World world = location.getWorld();


        if (p.hasPermission("molus.active.never")) {
            if (e.getMessage().equalsIgnoreCase(knife)) {
                if (!cooldownPlayers.contains(p.getName())) {
                    ItemStack itemStack = new ItemStack(Material.IRON_SWORD);
                    ItemMeta im = itemStack.getItemMeta();
                    im.setDisplayName("Кинжал Культиста");
                    itemStack.setItemMeta(im);
                    p.getInventory().addItem(itemStack);
                    cooldownPlayers.add(p.getName());
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        public void run() {
                            cooldownPlayers.remove(p.getName());
                        }
                    }, 20 * 60 * 10); // 20  * 60 * 10
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "Вы должны подождать 10 минут перед тем, как использовать эту команду снова.");
                }
            }
        }
        String rain = plugin.getConfig().getString("raycommand");
        Boolean rainoption = plugin.getConfig().getBoolean("rainoption");

        if (p.getName().equals(name)) {
            if (e.getMessage().equalsIgnoreCase("Prunus!")) {
                p.teleport(loc);
            }
        }
        if (p.hasPermission("molus.active.never")) {

            if (e.getMessage().equalsIgnoreCase("Malus!")) {
                try {
                    p.teleport(p.getBedSpawnLocation());
                } catch (Exception exception) {

                }
            }
        } else if (e.getMessage().equalsIgnoreCase("Malus!")) {
            if (!p.hasPermission("molus.active.never")) {
                try {
                    world.strikeLightning(location);
                    p.setHealth(2);
                    p.sendMessage(ChatColor.DARK_RED + " Ты жалок " + p.getName());
                } catch (Exception exception) {

                }
            }
        }

        if (p.getName().equals(name)) {
            if (e.getMessage().equalsIgnoreCase("Tres!")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 1200, 3), true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 1800, 3),true);

            }
        }


        if (p.getName().equals(name)) {
            if (e.getMessage().equalsIgnoreCase("Duos!")) {
                for (Player other : Bukkit.getOnlinePlayers()) {
                    if (p != other && other.getLocation().distance(p.getLocation()) <= maxDist) {
                        other.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 1200, 3), true);
                    }
                }
            }
        }
        Particle.DustTransition dustTransition = new Particle.DustTransition(Color.fromRGB(255, 0, 0), Color.fromRGB(255, 255, 255), 1.0F);

        if (p.getName().equals(name)) {
            if (e.getMessage().equalsIgnoreCase("Unos!")) {
                for (Player other : Bukkit.getOnlinePlayers()) {
                    if (other.getLocation().distance(p.getLocation()) <= maxDist) {
                        BukkitTask particleTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                            Location location1 = other.getLocation();
                            other.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, location1, 50, 1, 2, 1, dustTransition);

                        }, 0L, 20L);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.sendMessage("");
                            }
                            particleTask.cancel();
                        }, 200L);
                    }
                }
            }
        }

        Location location2 = new Location(Bukkit.getWorld("world"), x2, y2, z2, 0, 0);
        if (p.getName().equals(name)) {
            if (e.getMessage().equalsIgnoreCase(zam)) {
                for (Player other : Bukkit.getOnlinePlayers()) {
                    if (other.getLocation().distance(p.getLocation()) <= coord) {
                        Location otherLocation = other.getLocation();
                        other.teleport(location2);
                        for (int i = 0; i < 10; i++) {
                            other.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, otherLocation, 100, 1, 2, 1, dustTransition);
                        }
                    }
                }
                Location pLocation = p.getLocation();
                p.teleport(location2);
                for (int i = 0; i < 10; i++) {
                    p.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, pLocation, 100, 1, 2, 1, dustTransition);
                }
            }
        }
        if (e.getMessage().equalsIgnoreCase("")) {


        }

        if (e.getMessage().equalsIgnoreCase("DimkaShowYT")) {
            p.sendMessage(ChatColor.BLUE + "Подпишись на DimkaShow https://www.youtube.com/@DimkaShowYT" + ChatColor.AQUA + " и поставь лайк");
        }


    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        Plugin plugin = pluginstartup.getPlugin(pluginstartup.class);
        String name = plugin.getConfig().getString("Owner");
        double distance = plugin.getConfig().getDouble("CoordDedoTeleport"); // расстояние, в котором игроки умирают

        // Проверяем, содержит ли сообщение слово "Scansio"
        if (player.getName().equals(name)) {
            if (message.contains("Scansio")) {
                ItemStack emptyStack = new ItemStack(Material.AIR);
                List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                try {
                    // Проходимся по списку игроков и проверяем их расстояние от игрока, отправившего сообщение
                    for (Player p : players) {
                        Particle.DustTransition dustTransition = new Particle.DustTransition(Color.fromRGB(255, 0, 0), Color.fromRGB(255, 255, 255), 1.0F);
                        if (p != player && p.getLocation().distance(player.getLocation()) <= distance) {
                            // Убиваем игрока, если его расстояние меньше или равно указанному
                            Location otherLocation = p.getLocation();
                            for (int j = 0; j < 100; j++) {
                                for (int i = 0; i < 80; i++) {
                                    p.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, otherLocation, 100, 1, 2, 1, dustTransition);
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                p.setHealth(0);
                            }
                        }
                    }
                } catch (Exception exception) {


                }
            }
        }

        if (player.getName().equals(name)) {
            if (message.contains(("Sanguis!"))) {
                List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                try {
                    // Проходимся по списку игроков и проверяем их расстояние от игрока, отправившего сообщение
                    for (Player p : players) {
                        if (p != player && p.getLocation().distance(player.getLocation()) <= 10) {
                            // Убиваем игрока, если его расстояние меньше или равно указанному
                            Location otherLocation = p.getLocation();
                            Particle.DustTransition dustTransition = new Particle.DustTransition(Color.fromRGB(255, 0, 0), Color.fromRGB(255, 255, 255), 1.0F);
                            for (int i = 0; i < 10; i++) {
                                p.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, otherLocation, 100, 1, 2, 1, dustTransition);
                            }
                            p.setHealth(1);
                        }
                    }
                } catch (Exception exception) {

                }
            }
        }
    }
}




