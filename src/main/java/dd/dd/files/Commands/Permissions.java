package dd.dd.files.Commands;

import dd.dd.pluginstartup;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static dd.dd.pluginstartup.plugin;

public class Permissions implements CommandExecutor, Listener {


    private final HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    private final FileConfiguration config;
    private final File file;

    public Permissions() {

        Plugin plugin = pluginstartup.getPlugin(pluginstartup.class);
        file = new File(plugin.getDataFolder(), "Permission.yml");
        config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try {
                file.createNewFile();
                config.options().copyDefaults(true);
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!file.exists()) {
            if (sender.hasPermission("myplugin.createfile")) {
                try {
                    file.createNewFile();
                    config.options().copyDefaults(true);
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have permission to create the file.");
                return false;
            }
        }

        Plugin plugin = pluginstartup.getPlugin(pluginstartup.class);
        String name = plugin.getConfig().getString("Owner");

        if (args.length == 0 || !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Использование: /" + label + " <игрок>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        Player targets = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return false;
        }

        PermissionAttachment attachment = target.addAttachment(plugin);
        PermissionAttachment attachments = target.addAttachment(plugin);

        perms.put(target.getUniqueId(), attachments);
        perms.put(target.getUniqueId(), attachment);
        attachment.setPermission("molus.active.never", true);
        Player player = (Player) sender;

        Location location = target.getLocation();
        World world = location.getWorld();


        if (command.getName().equalsIgnoreCase("malus")) {
            if (sender instanceof Player && ((Player) sender).getName().equals(name) && args.length == 1 && Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).getName().equals(args[0])) {
                target.sendMessage(ChatColor.AQUA + "Новый Культист " + target.getName() + " Древняя латынь " + attachment);
                try {
                    savePermissions(target);
                } catch (IOException e) {
                    // Обработка исключения
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Использование: /" + label + " <игрок>");
                attachment.remove();
                return false;
            }
        }

        if (sender.getName().equals(name)) {
            if (command.getName().equalsIgnoreCase("death")) {
                World worlds = location.getWorld();
                targets.setHealth(1);
                worlds.strikeLightning(location);
                for (Player other : Bukkit.getOnlinePlayers()) {
                    other.sendMessage(ChatColor.DARK_RED + target.getName() + " " + "Умер в Агониях от верховного сатаны");
                    other.playSound(other.getLocation(), Sound.ENTITY_ENDERMAN_DEATH, 100, 1);

                }

                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();
        savePermissions(player);
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        restorePermissions(player);

    }


    @EventHandler
    public void death(PlayerDeathEvent e) {
        Player p = e.getEntity().getKiller();
        try {
            if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                e.setDeathMessage("");
            }
        } catch (NullPointerException exception) {

        }
    }

    private void savePermissions(Player player) throws IOException {
        UUID playerId = player.getUniqueId();
        if (perms.containsKey(playerId)) {

            List<String> permissions = player.getEffectivePermissions().stream()
                    .filter(permission -> permission.getValue())
                    .map(permission -> permission.getPermission())
                    .collect(Collectors.toList());
            try {
            config.set("players." + playerId + ".uuid", player.getUniqueId().toString());
            config.set("players." + playerId + ".permissions", permissions);
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();


            }
        }
    }
        private void restorePermissions (Player player){
            UUID playerId = player.getUniqueId();
            try {
                config.load(file);
            } catch (IOException e) {

            } catch (InvalidConfigurationException e) {

            }
            if (perms.containsKey(playerId)) {
                PermissionAttachment attachment = perms.get(playerId);
                attachment.remove();
                perms.remove(playerId);
            }

            if (!config.contains("players." + playerId.toString() + ".uuid")) {
                return;
            }

            perms.put(playerId, player.addAttachment(plugin));
            PermissionAttachment attachment = perms.get(playerId);
            List<String> permissions = config.getStringList("players." + playerId.toString() + ".permissions");
            for (String permission : permissions) {
                attachment.setPermission(permission, true);
            }
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
       }