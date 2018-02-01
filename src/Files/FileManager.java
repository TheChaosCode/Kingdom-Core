package Files;

import me.TheChaosCode.Main;

public class FileManager {
	

	public void setup(Main p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		Groups.reload();
		Groups.load();
		Groups.save();

		
		Locations.reload();
		Locations.load();
		Locations.save();
	}


	public static void saveConfig() {
		Groups.save();
		Locations.save();
	}

	public static void reloadConfig() {
		Groups.reload();
	}
}

