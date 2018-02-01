package Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class Groups {
	
	private static FileConfiguration config = null;
	private static File configFile = null;

	public static enum PlayerType {
		Player, TNT;
	}


	public static void load() {
		config = getConfig();

		config.options().header("############################################################\n# +------------------------------------------------------+ #\n# |                Locations                | #\n# +------------------------------------------------------+ #\n############################################################");
		getConfig().options().copyDefaults(true);
		save();
	}

	public static void reload() {
		if (configFile == null) {
			configFile = new File("plugins/KingLands/Groups.yml");
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public static FileConfiguration getConfig() {
		if (config == null) {
			reload();
		}
		return config;
	}

	public static void save() {
		if ((config == null) || (configFile == null)) {
			return;
		}
		try {
			config.save(configFile);
		} catch (IOException ex) {
		}
	}


}
