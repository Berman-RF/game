package game;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;

public class GameMenu extends JPanel {

	Image background, packmMini;
	JLabel lbStart, lbSettings, lbExit, lbHeadline, lbReturnMenu, lbTitle, lbEasy, lbNormal, lbHard, lbHelp;
	JList fruit, live;

	public GameMenu() throws IOException {
		background = ImageIO.read(new FileInputStream("src/game/images/menu_fon.jpg")); // Устанавливаем фон меню
		packmMini = ImageIO.read(new FileInputStream("src/game/images/miniPackman.png")); // Логотип пакмана внизу
		setLayout(null);

		Listener listener = new Listener(); // Создаем слушателя

		Color textColor = new Color(115, 108, 162); // цвет текста
		Color backColor = new Color(38, 38, 38); // цвет фона

		lbHeadline = new JLabel("ИГРА, ВЕРСИЯ 1.2"); // Устанваливаем заголовок
		lbHeadline.setBounds(210, 85, 500, 52);
		lbHeadline.setFont(new Font("serif", Font.BOLD, 50));
		lbHeadline.setForeground(textColor);
		lbHeadline.setBackground(backColor);
		lbHeadline.addMouseListener(listener);
		add(lbHeadline);

		lbStart = new JLabel("НАЧАТЬ ИГРУ");
		lbStart.setBounds(120, 300, 260, 52);
		lbStart.setFont(new Font("serif", Font.BOLD, 30));
		lbStart.setForeground(textColor);
		lbStart.addMouseListener(listener);
		add(lbStart);

		lbExit = new JLabel("ВЫХОД");
		lbExit.setBounds(120, 354, 200, 52);
		lbExit.setFont(new Font("serif", Font.BOLD, 30));
		lbExit.setForeground(textColor);
		lbExit.addMouseListener(listener);
		add(lbExit);
		
		lbHelp = new JLabel(); // JLabel с  предупреждением. Появляется, если не выбрать сложность и нажать на "НАЧАТЬ ИГРУ"
		lbHelp.setBounds(100, 454, 280, 52);
		lbHelp.setFont(new Font("serif", Font.BOLD, 19));
		lbHelp.setForeground(Color.RED);
		add(lbHelp);

		lbTitle = new JLabel("ВЫБЕРЕТЕ СЛОЖНОСТЬ");
		lbTitle.setBounds(400, 180, 600, 52);
		lbTitle.setFont(new Font("serif", Font.BOLD, 28));
		lbTitle.setForeground(textColor);
		add(lbTitle);

		lbEasy = new JLabel("Легко");
		lbEasy.setBounds(472, 240, 600, 52);
		lbEasy.setFont(new Font("serif", Font.BOLD, 50));
		lbEasy.setForeground(textColor);
		add(lbEasy);

		lbNormal = new JLabel("Средне");
		lbNormal.setBounds(472, 300, 600, 52);
		lbNormal.setFont(new Font("serif", Font.BOLD, 50));
		lbNormal.setForeground(textColor);
		add(lbNormal);

		lbHard = new JLabel("Тяжело");
		lbHard.setBounds(472, 360, 600, 52);
		lbHard.setFont(new Font("serif", Font.BOLD, 50));
		lbHard.setForeground(textColor);
		add(lbHard);
	}

	@Override
	protected void paintComponent(Graphics g) { // Рисуем фон и логоти пакмана внизу
		g.drawImage(background, 0, 0, null);
		g.drawImage(packmMini, 690, 500, null);
	}

	class Listener extends MouseAdapter { // Меняем цветовые схемы JLabel-ов "НАЧАТЬ ИГРУ" и "ВЫХОД" при наведении мыши
		JLabel label;

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == lbStart || e.getSource() == lbExit) {
				label = (JLabel) e.getSource();
				label.setFont(new Font("serif", Font.BOLD, 32));
				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == lbStart || e.getSource() == lbExit) {
				label = (JLabel) e.getSource();
				label.setFont(new Font("serif", Font.BOLD, 30));
				label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
}