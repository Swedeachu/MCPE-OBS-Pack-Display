import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class PackDisplay {

	public static ArrayList<String> pax;

	public static void renderPacks() {
		try {
			// get all the packs with the uuids of our current packs equipped
			ArrayList<String> activeUUIDS = ActivePacks.getActivePackUUIDS();
			ArrayList<String> activePacks = ActivePacks.getActivePacks(activeUUIDS);
			pax = activePacks;
			System.out.println("\nActive packs: ");
			for (String pack : activePacks) {
				System.out.println(pack);
				File manifest = new File(pack + "\\manifest.json");
				if (manifest.exists()) {
					String name = PackInfoUtil.getPackName(manifest);
					System.out.println(name);
					BufferedImage packIcon = PackInfoUtil.getPackIcon(new File(pack));
					Image dimg = packIcon.getScaledInstance(Window.PackIconFrame.getWidth(), Window.PackIconFrame.getHeight(), Image.SCALE_SMOOTH);
					Window.PackIconFrame.setIcon(new ImageIcon(dimg));
					Window.PackNameFrame.setText(name);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
