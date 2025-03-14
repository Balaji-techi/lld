package Practice.DungeonGame;

import java.util.*;

class DungeonGame {
    private static final int SIZE = 5;
    private char[][] dungeon;
    private int playerX, playerY, level = 1, health = 100, gold = 0, xp = 0;
    private String weapon = "Fists";
    private boolean gameRunning = true;
    private Map<String, Integer> inventory = new HashMap<>();

    public DungeonGame() {
        dungeon = new char[SIZE][SIZE];
        initializeDungeon();
    }

    private void initializeDungeon() {
        Random rand = new Random();

        for (int i = 0; i < SIZE; i++)
            Arrays.fill(dungeon[i], '.');

        // Place Player
        playerX = rand.nextInt(SIZE);
        playerY = rand.nextInt(SIZE);
        dungeon[playerX][playerY] = 'P';

        // Place Exit
        int exitX, exitY;
        do {
            exitX = rand.nextInt(SIZE);
            exitY = rand.nextInt(SIZE);
        } while (exitX == playerX && exitY == playerY);
        dungeon[exitX][exitY] = 'E';

        // Place Enemies (Goblins, Trolls, Boss)
        for (int i = 0; i < 3; i++) {
            int enemyX, enemyY;
            do {
                enemyX = rand.nextInt(SIZE);
                enemyY = rand.nextInt(SIZE);
            } while (dungeon[enemyX][enemyY] != '.');
            dungeon[enemyX][enemyY] = (i == 2) ? 'B' : 'M'; // 'B' is Boss, 'M' is Monster
        }

        // Place Items (Weapons, Potions, Gold)
        for (int i = 0; i < 3; i++) {
            int itemX, itemY;
            do {
                itemX = rand.nextInt(SIZE);
                itemY = rand.nextInt(SIZE);
            } while (dungeon[itemX][itemY] != '.');
            dungeon[itemX][itemY] = (i == 0) ? 'W' : (i == 1) ? 'I' : 'G'; // Weapon, Potion, Gold
        }
    }

    private void printDungeon() {
        for (char[] row : dungeon) {
            for (char cell : row)
                System.out.print(cell + "  ");
            System.out.println();
        }
        System.out.println("Health: " + health + " | Gold: " + gold + " | XP: " + xp + " | Weapon: " + weapon);
    }

    private void movePlayer(int newX, int newY) {
        if (newX < 0 || newX >= SIZE || newY < 0 || newY >= SIZE) {
            System.out.println("You hit a wall!");
            return;
        }

        char nextCell = dungeon[newX][newY];

        switch (nextCell) {
            case 'M':
                fightMonster();
                break;
            case 'B':
                fightBoss();
                break;
            case 'I':
                inventory.put("Health Potion", inventory.getOrDefault("Health Potion", 0) + 1);
                System.out.println("You found a Health Potion!");
                break;
            case 'W':
                collectWeapon();
                break;
            case 'G':
                gold += 10;
                System.out.println("You found 10 gold!");
                break;
            case 'E':
                System.out.println("You cleared this dungeon level!");
                levelUp();
                return;
        }

        dungeon[playerX][playerY] = '.';
        playerX = newX;
        playerY = newY;
        dungeon[playerX][playerY] = 'P';
    }

    private void fightMonster() {
        Random rand = new Random();
        int damage = (weapon.equals("Sword")) ? rand.nextInt(20) + 10 : rand.nextInt(10) + 5;
        int monsterDamage = rand.nextInt(15) + 5;

        System.out.println("You encountered a monster!");
        System.out.println("You dealt " + damage + " damage!");
        health -= monsterDamage;
        System.out.println("Monster fought back! You lost " + monsterDamage + " HP.");
        xp += 10;
    }

    private void fightBoss() {
        System.out.println("A powerful Boss appears!");
        Random rand = new Random();
        int damage = (weapon.equals("Sword")) ? rand.nextInt(30) + 20 : rand.nextInt(15) + 10;
        int bossDamage = rand.nextInt(25) + 10;

        health -= bossDamage;
        xp += 20;
        gold += 50;

        System.out.println("You dealt " + damage + " damage!");
        System.out.println("Boss attacked! You lost " + bossDamage + " HP.");

        if (health <= 0) {
            System.out.println("You have died. Game Over.");
            gameRunning = false;
        }
    }

    private void collectWeapon() {
        Random rand = new Random();
        String[] weapons = {"Sword", "Bow", "Dagger"};
        weapon = weapons[rand.nextInt(weapons.length)];
        System.out.println("You found a " + weapon + "!");
    }

    private void levelUp() {
        level++;
        health = 100;
        System.out.println("You leveled up! Welcome to Level " + level);
        initializeDungeon();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (gameRunning) {
            printDungeon();
            System.out.println("Move (W/A/S/D) or Shop (X) or Use Potion (P): ");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "W": movePlayer(playerX - 1, playerY); break;
                case "A": movePlayer(playerX, playerY - 1); break;
                case "S": movePlayer(playerX + 1, playerY); break;
                case "D": movePlayer(playerX, playerY + 1); break;
                case "P": usePotion(); break;
                case "X": visitShop(); break;
                case "Q": gameRunning = false; System.out.println("Game Over."); break;
                default: System.out.println("Invalid command!");
            }
        }
        scanner.close();
    }

    private void usePotion() {
        if (inventory.getOrDefault("Health Potion", 0) > 0) {
            health = Math.min(100, health + 30);
            inventory.put("Health Potion", inventory.get("Health Potion") - 1);
            System.out.println("You used a Health Potion (+30 HP)!");
        } else {
            System.out.println("No potions left!");
        }
    }

    private void visitShop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Shop: (1) Buy Potion (10G) | (2) Exit");
        String choice = scanner.nextLine();

        if (choice.equals("1") && gold >= 10) {
            inventory.put("Health Potion", inventory.getOrDefault("Health Potion", 0) + 1);
            gold -= 10;
            System.out.println("You bought a Health Potion!");
        } else {
            System.out.println("Not enough gold or exiting shop.");
        }
    }

    public static void main(String[] args) {
        DungeonGame game = new DungeonGame();
        game.startGame();
    }
}