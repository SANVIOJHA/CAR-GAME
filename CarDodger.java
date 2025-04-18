import java.util.Scanner;
import java.util.Random;

public class CarDodger {
    static final int WIDTH = 30;
    static final int HEIGHT = 20;
    static char[][] screen = new char[HEIGHT][WIDTH];
    static int carX = WIDTH / 2;
    static int score = 0;
    static boolean gameRunning = true;
    static int[] enemyX = new int[2];
    static int[] enemyY = new int[2];
    static Random rand = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        initEnemies();
        System.out.println("🎮 Welcome to Java Car Dodger Game!");
        System.out.println("Controls: A = Left, D = Right, P = Pause, Q = Quit");
        System.out.println("Press Enter to start...");
        scanner.nextLine();

        while (gameRunning) {
            drawScreen();
            moveEnemies();
            updateCarPosition();
            checkCollision();
            Thread.sleep(200);
        }

        System.out.println("\nGame Over! Your score: " + score);
    }

    static void initEnemies() {
        for (int i = 0; i < enemyX.length; i++) {
            enemyX[i] = rand.nextInt(WIDTH - 4) + 2;
            enemyY[i] = -i * 10;
        }
    }

    static void drawScreen() {
        clearScreen();
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                screen[i][j] = ' ';

        for (int i = 0; i < HEIGHT; i++) {
            screen[i][0] = '|';
            screen[i][WIDTH - 1] = '|';
        }

        screen[HEIGHT - 2][carX] = '=';
        screen[HEIGHT - 1][carX - 1] = '=';
        screen[HEIGHT - 1][carX] = '=';
        screen[HEIGHT - 1][carX + 1] = '=';

        for (int i = 0; i < enemyX.length; i++) {
            if (enemyY[i] >= 0 && enemyY[i] < HEIGHT - 1) {
                screen[enemyY[i]][enemyX[i]] = '*';
            }
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(screen[i][j]);
            }
            System.out.println();
        }

        System.out.println("Score: " + score);
    }

    static void moveEnemies() {
        for (int i = 0; i < enemyX.length; i++) {
            enemyY[i]++;
            if (enemyY[i] >= HEIGHT) {
                enemyX[i] = rand.nextInt(WIDTH - 4) + 2;
                enemyY[i] = 0;
                score++;
            }
        }
    }

    static void updateCarPosition() {
        try {
            if (System.in.available() > 0) {
                char key = (char) System.in.read();
                if (key == 'a' || key == 'A') {
                    if (carX > 2) carX--;
                } else if (key == 'd' || key == 'D') {
                    if (carX < WIDTH - 3) carX++;
                } else if (key == 'p' || key == 'P') {
                    System.out.println("Game Paused. Press Enter to resume...");
                    scanner.nextLine();
                } else if (key == 'q' || key == 'Q') {
                    gameRunning = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void checkCollision() {
        for (int i = 0; i < enemyX.length; i++) {
            if (enemyY[i] == HEIGHT - 2 && Math.abs(enemyX[i] - carX) <= 1) {
                gameRunning = false;
            }
        }
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
