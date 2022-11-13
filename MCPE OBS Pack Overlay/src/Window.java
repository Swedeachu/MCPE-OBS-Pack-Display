import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window {

	private static JFrame frmPackDisplay;
	public static JLabel PackIconFrame;
	public static JLabel PackNameFrame;

	public static int currentPackIndex = 0;

	public static int mouseX = 0;
	public static int mouseY = 0;

	public static void main(String[] args) {
		frmPackDisplay = new JFrame();
		frmPackDisplay.setAlwaysOnTop(true);
		frmPackDisplay.getContentPane().setBackground(Color.GREEN);
		frmPackDisplay.setForeground(Color.GRAY);
		frmPackDisplay.setTitle("Pack Display");
		
		frmPackDisplay.setUndecorated(true);
		frmPackDisplay.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		
		frmPackDisplay.setVisible(true);
		frmPackDisplay.setBounds(100, 100, 900, 220);
		frmPackDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPackDisplay.getContentPane().setLayout(null);

		frmPackDisplay.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				frmPackDisplay.setLocation(frmPackDisplay.getX() + e.getX() - mouseX, frmPackDisplay.getY() + e.getY() - mouseY);
			}
		});

		frmPackDisplay.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});

		PackIconFrame = new JLabel("");
		PackIconFrame.setBackground(Color.BLACK);
		PackIconFrame.setBounds(10, 33, 128, 128);
		frmPackDisplay.getContentPane().add(PackIconFrame);

		PackNameFrame = new JLabel("Loading Pack");
		PackNameFrame.setForeground(new Color(0, 206, 209));
		PackNameFrame.setFont(new Font("Tahoma", Font.BOLD, 42));
		PackNameFrame.setBounds(148, 66, 742, 62);
		frmPackDisplay.getContentPane().add(PackNameFrame);
		frmPackDisplay.setResizable(false);

		frmPackDisplay.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(e.getButton());
				if (e.getButton() == 1) {
					currentPackIndex++;
					if (currentPackIndex >= PackDisplay.pax.size()) {
						currentPackIndex = 0;
					}
					String pack = PackDisplay.pax.get(currentPackIndex);
					File manifest = new File(pack + "\\manifest.json");
					if (manifest.exists()) {
						String name;
						try {
							name = PackInfoUtil.getPackName(manifest);
							System.out.println(name);
							BufferedImage packIcon = PackInfoUtil.getPackIcon(new File(pack));
							Image dimg = packIcon.getScaledInstance(PackIconFrame.getWidth(), PackIconFrame.getHeight(), Image.SCALE_SMOOTH);
							PackIconFrame.setIcon(new ImageIcon(dimg));
							PackNameFrame.setText(name);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} else if (e.getButton() != 1) {
					PackDisplay.renderPacks();
				}
			}
		});

		PackDisplay.renderPacks();

	}

}
