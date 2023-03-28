package dd.dd;

import dd.dd.files.Commands.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;


import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class pluginstartup extends JavaPlugin implements Listener {

    public static pluginstartup plugin;



    @Override
    public void onEnable() {
        plugin = this; // инициализация переменной plugin
        Bukkit.getPluginManager().registerEvents((this), this);
        getLogger().info("Enabled!");
        Bukkit.getPluginManager().registerEvents(new Prunusevent(), this);
        Bukkit.getPluginManager().registerEvents(new dimasgod(), this);
        Bukkit.getPluginManager().registerEvents(new endernodath(), this);
        Bukkit.getPluginManager().registerEvents(new Permissions(), this);
        Bukkit.getPluginManager().registerEvents(new MilkShake(),this);
        Bukkit.getPluginManager().registerEvents(new changepar(), this);
        Bukkit.getPluginManager().registerEvents(new Dipression(),this);
        getServer().getPluginCommand("malus").setExecutor(new Permissions());
        getServer().getPluginCommand("death").setExecutor(new Permissions());
        getServer().getPluginCommand("setcfg").setExecutor(new changepar());
        getServer().getPluginManager().registerEvents(new CustomSound(), this);


        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
    }
