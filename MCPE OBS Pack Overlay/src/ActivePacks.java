import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class ActivePacks {
	
	private static ArrayList<String> packPaths;
	
	public static ArrayList<String> getActivePackUUIDS() {
		File json = new File(PackInfoUtil.getMinecraftPE() + "\\global_resource_packs.json");
		ArrayList<String> uuids = new ArrayList<String>();
		if (json.exists()) {
			String jsonString = "pack_id";
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(json.getAbsolutePath()));
				String line = reader.readLine();
				while (line != null) {
					if (line.contains(jsonString)) {
						String UUID = StringUtils.substringBetween(line, ": \"", "\",");
						System.out.println(UUID);
						uuids.add(UUID);
					}
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return uuids;
	}
	
	public static ArrayList<String> getActivePacks(ArrayList<String> uuids) throws IOException {
		ArrayList<String> activePacks = new ArrayList<String>();
		File packDir = new File(PackInfoUtil.getResourcePacksDir());
		if (packDir.exists()) {
			System.out.println(packDir.getAbsolutePath());
			packPaths = new ArrayList<String>();
			readFiles(packDir.listFiles());
			System.out.println(packPaths.size());
			for (String pack : packPaths) {
				if (new File(pack + "\\manifest.json").exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(pack + "\\manifest.json"));
					String line = reader.readLine();
					while (line != null) {
						for (String uuid : uuids) {
							if (line.contains(uuid)) {
								activePacks.add(pack);
							}
						}
						line = reader.readLine();
					}
					reader.close();
				}
			}
		}
		return activePacks;
	}

	public static void readFiles(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				if (new File(file.getAbsolutePath() + "\\manifest.json").exists()) {
					packPaths.add(file.getAbsolutePath());
					System.out.println(file.getAbsolutePath());
				}
				readFiles(file.listFiles());
			}
		}
	}
}
