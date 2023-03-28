package dd.dd;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class MyConfig {
    private FileConfiguration config;

    public MyConfig() {
        // Создаем новый файл config.yml в папке плагина
        File configFile = new File("plugins/MyPlugin/config.yml");

        // Если файл не существует, то создаем его
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Создаем новый YamlConfiguration объект на основе файла
        config = YamlConfiguration.loadConfiguration(configFile);

        // Устанавливаем начальные значения
        config.set("myKey", "myValue");
        config.set("anotherKey", 42);

        // Сохраняем конфигурационный файл
        save();
    }

    // Метод для сохранения изменений в конфигурационном файле
    public void save() {
        try {
            config.save(new File("plugins/MyPlugin/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}