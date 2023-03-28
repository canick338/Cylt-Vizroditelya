package dd.dd.files.Commands;

import dd.dd.pluginstartup;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class MilkShake implements Listener {
    
    
@EventHandler
    public void OnPlayerChat(AsyncPlayerChatEvent event){
    Plugin plugin = pluginstartup.getPlugin(pluginstartup.class);
    String name = plugin.getConfig().getString("Owner");
    Player player = event.getPlayer();
    String msg = event.getMessage();
    
    if (player.getWorld().getEnvironment() != World.Environment.THE_END){
        return;
    }
   if (!msg.equalsIgnoreCase("NIHIS") || !player.getName().equals(name)){
       return;
   }
   for (Player endplayer : Bukkit.getOnlinePlayers()) {
       if (endplayer.getWorld().getEnvironment() != World.Environment.THE_END) {
           continue;
       }
       Inventory inventory = endplayer.getInventory();
       ItemStack emptyBucket = ((PlayerInventory) inventory).getItemInMainHand();
       ItemMeta emptymeta = emptyBucket.getItemMeta();

       if (emptyBucket.getType() != Material.BUCKET || emptymeta == null || !emptymeta.hasDisplayName() || !emptymeta.getDisplayName().equals("Фиал Крови Возродителя")){
           continue;
       }
       ItemStack milkbucket = new ItemStack(Material.MILK_BUCKET);
       ItemMeta milkbucketmeta = milkbucket.getItemMeta();
       milkbucketmeta.setDisplayName("Фиал Крови Возродителя");
       milkbucket.setItemMeta(milkbucketmeta);
       ((PlayerInventory) inventory).setItemInMainHand(milkbucket);
       inventory.remove(emptyBucket);
   }
}
  @EventHandler
  public void OnPlayerConsume(PlayerItemConsumeEvent event){
       ItemStack consumeItem = event.getItem();
       ItemMeta consumeItemMeta = consumeItem.getItemMeta();
       if (consumeItem.getType() == Material.MILK_BUCKET && consumeItemMeta == null && consumeItemMeta.hasDisplayName() && consumeItemMeta.getDisplayName().equals("Фиал Крови Возродителя")){
           Player player = event.getPlayer();
           Inventory inventory = player.getInventory();
           ItemStack emptyBucket = new ItemStack(Material.BUCKET);
           ItemMeta emptyBucketMeta = emptyBucket.getItemMeta();
           emptyBucketMeta.setDisplayName("Фиал Крови Возродителя");
           emptyBucket.setItemMeta(emptyBucketMeta);
           inventory.removeItem(consumeItem);
           inventory.addItem(emptyBucket);
           event.setCancelled(true);

           }
       }

}

