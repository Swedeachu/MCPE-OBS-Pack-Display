import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public class PackInfoUtil {

	public static String getPackName(File manifest) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(manifest.getAbsolutePath()));
		String line = reader.readLine();
		while (line != null) {
			if (line.contains("\"name\":")) {
				String name = StringUtils.substringBetween(line, "\"name\": \"", "\",");
				reader.close();
				return name;
			} else if (line.contains("\"name\" : \"")) {
				String name = StringUtils.substringBetween(line, "\"name\" : \"", "\",");
				reader.close();
				return name;
			}
			line = reader.readLine();
		}
		reader.close();
		return null;
	}

	public static BufferedImage getPackIcon(File pack) {
		try {
			return ImageIO.read(new File(pack + "\\pack_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getResourcePacksDir() {
		return new File(System.getenv("APPDATA")).getParentFile().getAbsolutePath() + "\\Local\\Packages\\Microsoft.MinecraftUWP_8wekyb3d8bbwe\\LocalState\\games\\com.mojang\\resource_packs";
	}

	public static String getMinecraftPE() {
		return new File(System.getenv("APPDATA")).getParentFile().getAbsolutePath() + "\\Local\\Packages\\Microsoft.MinecraftUWP_8wekyb3d8bbwe\\LocalState\\games\\com.mojang\\minecraftpe";
	}

}
