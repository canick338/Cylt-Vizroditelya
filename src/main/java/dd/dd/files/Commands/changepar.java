package dd.dd.files.Commands;

import dd.dd.pluginstartup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class changepar implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Plugin plugin = pluginstartup.getPlugin(pluginstartup.class);
        Player player = (Player) sender;
        String name = plugin.getConfig().getString("Owner");
        if (sender.getName().equals(name)) {
            if (cmd.getName().equalsIgnoreCase("setcfg")) {
                if (args.length == 0) {
                    // Показать список параметров из конфига, которые можно изменить
                    FileConfiguration config = plugin.getConfig();
                    Set<String> keys = config.getKeys(true); // Получаем все ключи из конфига
                    List<String> changeableKeys = new ArrayList<>();
                    for (String key : keys) {
                        // Проверяем, что параметр можно изменить
                        if (!config.isConfigurationSection(key) && config.get(key) != null) {
                            changeableKeys.add(key);

                        }
                    }


                    sender.sendMessage("Доступные параметры для изменения:");
                    sender.sendMessage(String.join(", ", changeableKeys));
                    return true;
                } else if (args.length < 2) {
                    // Если не указано новое значение, вывести ошибку
                    sender.sendMessage("Недостаточно аргументов! Используйте /setcfg <параметр> <значение>");
                    return false;
                }


                String param = args[0]; // Параметр для изменения
                String value = args[1]; // Новое значение параметра


                Object newValue;
                try {
                    newValue = Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    newValue = value;
                }


                FileConfiguration config = plugin.getConfig(); // Получаем конфигурацию плагина
                config.set(param, value); // Изменяем значение параметра
                config.set(param, newValue);


                plugin.saveConfig(); // Сохраняем изменения в конфигурационном файле
                sender.sendMessage("Значение параметра " + param + " изменено на " + value); // Сообщение об успешном изменении параметра

                return true;
            }
        }
        return false;
    }
}

