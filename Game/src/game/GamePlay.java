package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class GamePlay extends JPanel {

	int packPositionX = 400;// место нахождение пакмана по умолчанию, координата X
	int packPositionY = 530;// координата Y для пакмана
	int[] fruitPositionX = new int[6]; // позиция фрукта по Х
	int[] fruitPositionY = new int[6]; // позиция фрукта по Y
	int maxLives, maxFruits; // Количество жизней и фруктов (зависит от сложности)
	int fruitNumber; // Номер фрукта в массиве
	int packmanNumber; // Номер пакмана в массиве
	JTextField tfScore; // Табло со счетом
	static int score = 0; // счет игры
	Image background, gameOver, livePackman;
	Image[] fruit; // Массив фруктов
	Image[] packmans; // Массив пакманов
	Timer timer; 

	public GamePlay(int livesNumber, int fruitNumbers) throws IOException {
		packmans = new Image[] { ImageIO.read(new FileInputStream("src/game/images/packman_left.png")),
				ImageIO.read(new FileInputStream("src/game/images/pacman_right.png")) };
		livePackman = ImageIO.read(new FileInputStream("src/game/images/pacman_live.png"));
		background = ImageIO.read(new FileInputStream("src/game/images/fon.jpg"));
		gameOver = ImageIO.read(new FileInputStream("src/game/images/GameOwer.png"));
		fruit = new Image[] { ImageIO.read(new FileInputStream("src/game/images/p0.png")),
				ImageIO.read(new FileInputStream("src/game/images/p1.png")),
				ImageIO.read(new FileInputStream("src/game/images/p3.png")),
				ImageIO.read(new FileInputStream("src/game/images/p4.png")),
				ImageIO.read(new FileInputStream("src/game/images/p5.png")),
				ImageIO.read(new FileInputStream("src/game/images/p6.png")) };
		setLayout(null);
		fruitNumber = (int) (Math.random() * 5); // выбираем случайный фрукт
		maxFruits = fruitNumbers; // Устанавливаем количество фруктов
		maxLives = livesNumber; // Устанавливаем количество жизней

		for (int i = 0; i < maxFruits; i++) { // Устанавливаем первоначальную позицию фрутов по Х и Y
			fruitPositionX[i] = (int) (Math.random() * (800 - 200));
			fruitPositionY[i] = (int) (Math.random() * (- 200));
		}

		tfScore = new JTextField(); // Устанавливаем и оформляем табло с результатом
		tfScore.setBounds(10, 20, 100, 40);
		tfScore.setBackground(new Color(38, 38, 38));
		tfScore.setForeground(new Color(115, 108, 162));
		tfScore.setFont(new Font("serif", Font.BOLD, 30));
		tfScore.setHorizontalAlignment(SwingConstants.CENTER);
		add(tfScore);
		
		timer = new Timer(50, new ActionListener() { // Создаем таймер

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();// вызываем paintComponent

				for (int i = 0; i < maxFruits; i++) {
					fruitPositionY[i] += 5; // Скорость падения фрукта
					if (fruitPositionY[i] > 680) { // Если  фрукт вышел за пределы окна по Х
						maxLives--; // Уменьшаем количество жизней
						fruitPositionY[i] = -100; // Новая позиция фрукта по Х
						fruitPositionX[i] = (int) (Math.random() * (800 - 200)); // Новая позиция фрукта по Y
					}
				}
			}
		});
		timer.start(); //Запускаем таймер
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, null);
		if (packPositionX >= 800) { // Если пакман с краю с права перемещаем его в левый край
			packPositionX = -30;
			g.drawImage(packmans[packmanNumber], packPositionX, packPositionY, null);
		} else if (packPositionX <= -40) { // Если пакман на краю с лева, перемещаем в правый край
			packPositionX = 790;
			g.drawImage(packmans[packmanNumber], packPositionX, packPositionY, null);
		} else {
			g.drawImage(packmans[packmanNumber], packPositionX, packPositionY, null); // Иначе просто рисуем пакмана в зависимости от координат
		}
		
		for (int i = 0; i < maxLives; i++) { // Рисуем жизни пакмана
			g.drawImage(livePackman, i * 40, 70, null);
		}
		
		if (maxLives == 0) { // Если жизней больше нет завершаем игру
			timer.stop(); // Останавливаем таймер
			g.drawImage(gameOver, 180, 250, null); //выводим изображение
		}

		for (int i = 0; i < maxFruits; i++) {
			g.drawImage(fruit[i], fruitPositionX[i], fruitPositionY[i], null); // Рисуем фрукты

			if (packPositionX >= fruitPositionX[i] - 10 && packPositionX <= (fruitPositionX[i] + fruit[i].getWidth(null) + 120)
					&& packPositionY >= fruitPositionY[i] - 10
					&& packPositionY <= (fruitPositionY[i] + fruit[i].getHeight(null) - 10)) { // Если поймали
				fruitPositionX[i] = (int) (Math.random() * (900 - 200)); // Ставим координаты нового фрукта по Х
				fruitPositionY[i] = (int) (Math.random() * (- 200)); // Ставим координаты нового фрукта по Y
				tfScore.setText("" + ++score); // Увеличиваем счет
			}
		}
	}
}