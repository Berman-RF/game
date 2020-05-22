package game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class MainWindow extends JFrame {
	private static MainWindow object; 
	GamePlay play; // Окно с игрой
	GameMenu menu; // Окно с меню
	int livesNumbers = 0, fruitNumbers = 0; // Количество жизней и количество фруктов, зависит от выбранной сложности

	private MainWindow() throws IOException {
		setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
		setTitle("Игра. Версия 1.2");
		setBounds(100, 60, 867, 689); // Размеры и расположение окна
		menu = new GameMenu(); // Создаем меню
		addKeyListener(new MyKey()); // К окну добавляем слушателя текущего окна
		MListener listener = new MListener(); // Создаем слушателя для компонентов в JFrame меню
		setContentPane(menu); // Устанавливаем меню в окно
		menu.lbStart.addMouseListener(listener); // Для кликабельных компонентов устанавливаем другого слушателя
		menu.lbExit.addMouseListener(listener);
		menu.lbEasy.addMouseListener(listener);
		menu.lbNormal.addMouseListener(listener);
		menu.lbHard.addMouseListener(listener);
		menu.lbHelp.addMouseListener(listener);
		setVisible(true); // Делаем окно видимым
		setResizable(false); // Делаем окно не изменяемого размера
	}

	public static MainWindow getInstance() throws IOException { // Синглтон
		if (object == null) {
			object = new MainWindow();
		}
		return object;
	}

	class MyKey extends KeyAdapter { // Считываем нажатие клавиш "в право" и "в лево"
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();// получили код клавиши
			if (keyCode == 37) {// клавиша влево
				play.packPositionX -= 30; // Сдвинули позицию пакмана в лево на 30 пикселей
				play.packmanNumber = 0; // Установили картинку пакмана, который смотрит в лево
			}

			if (keyCode == 39) {// клавиша вправо
				play.packPositionX += 30; // Сдвинули позицию пакмана в право на 30 пикселей
				play.packmanNumber = 1; // Установили картинку пакмана, который смотрит в право
			}

		}
	}

	class MListener extends MouseAdapter { // События при клике на разные JLabel

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == menu.lbStart) { // Если нажали на "НАЧАТЬ ИГРУ"
				remove(menu); // Убираем Frame меню
				getContentPane().revalidate(); // Применяем
				try {
					play = new GamePlay(livesNumbers, fruitNumbers); // Создаем JFrame игры, передаем количество жизней и фруктов, зависит от сложности
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (livesNumbers > 0 && fruitNumbers > 0) { // Если сложность выбрана
					setContentPane(play); // Устанавливаем JFrame с игрой
				} else {
					menu.lbHelp.setText("Сначала выберете сложность"); // Иначе пишем предупреждение в окне
				}
			} else if (e.getSource() == menu.lbExit) { // При нажатии "ВЫХОД" завершаем игру
				System.exit(0); 
			}

			/*
			 * Далее идет выбор сложности
			 * При нажатии на определенный Jlabel:
			 * - Устанваливаются цветовые схемы.
			 * - Устанавливается количество жизней и падающих фруктов	
			 */
			if (e.getSource() == menu.lbEasy) {
				menu.lbEasy.setFont(new Font("serif", Font.BOLD, 49));
				menu.lbEasy.setForeground(new Color(73, 70, 113));
				menu.lbNormal.setForeground(new Color(115, 108, 162));
				menu.lbHard.setForeground(new Color(115, 108, 162));
				livesNumbers = 4; fruitNumbers = 3;
			} else if (e.getSource() == menu.lbNormal) {
				menu.lbNormal.setFont(new Font("serif", Font.BOLD, 49));
				menu.lbNormal.setForeground(new Color(73, 70, 113));
				menu.lbEasy.setForeground(new Color(115, 108, 162));
				menu.lbHard.setForeground(new Color(115, 108, 162));
				livesNumbers = 3; fruitNumbers = 4;
			} else if (e.getSource() == menu.lbHard) {
				menu.lbHard.setFont(new Font("serif", Font.BOLD, 49));
				menu.lbHard.setForeground(new Color(73, 70, 113));
				menu.lbNormal.setForeground(new Color(115, 108, 162));
				menu.lbEasy.setForeground(new Color(115, 108, 162));
				livesNumbers = 2; fruitNumbers = 5;
			}
		}
		
		/*
		 * При наведении на JLabel-ы уровней сложности меняем их цветовую схему
		 */
		
		JLabel label;

		@Override
		public void mouseEntered(MouseEvent e) {
			if ((e.getSource() == menu.lbEasy || e.getSource() == menu.lbNormal || e.getSource() == menu.lbHard)) {
				label = (JLabel) e.getSource();
				label.setFont(new Font("serif", Font.BOLD, 49));
				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			if ((e.getSource() == menu.lbEasy || e.getSource() == menu.lbNormal || e.getSource() == menu.lbHard)) {
				label = (JLabel) e.getSource();
				label.setFont(new Font("serif", Font.BOLD, 50));
				label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
}
