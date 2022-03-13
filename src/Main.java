// TODO
//  [ ] - a way to check whether the ship is sunk (individual for each ship)
//  [ ] - extra step -> when the ship is sunken => "You sank a ship!"
//  [ ] - after sinking all the ships => "You sank the last ship. You won. Congratulations!"

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Ship[] ships = {new Ship("Destroyer", 2),
                new Ship("Cruiser", 3),
                new Ship("Submarine", 3),
                new Ship("Battleship", 4),
                new Ship("Aircraft Carrier", 5)};

        Battlefield battlefield = new Battlefield(ships);

        Game.initialShipPlacement(battlefield);
        System.out.println("The game starts!\n");
        Game.playRound(battlefield);
        Game.scanner.close();
    }
}

class Game extends GameRules {
    static Scanner scanner = new Scanner(System.in);

    public static void initialShipPlacement(Battlefield battlefield) {
        battlefield.createBattlefield();
        battlefield.printBattlefield();
        while (battlefield.getShipNumber() != 0) { // initial placement loop
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n",
                    battlefield.getShip().getShipName(), battlefield.getShip().getShipLength());
                while(true) {
                    try {
                        battlefield.getShip().setShipCords(scanner.nextLine());

                        checkPlacementRules(battlefield);

                        battlefield.placeShip();
                        battlefield.printBattlefield();
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage().contains("Error") ? "\n" + e.getMessage() :
                                new Exception(String.format("Error! %s. Try again: " + "\n",
                                        e.getLocalizedMessage())).getMessage());
                    }
                }
        }

    }

    public static void playRound(Battlefield battlefield) {
        int game = 3;
            System.out.println("Take a shot!\n");
            battlefield.printBattlefieldFOW();
        while(game > 0) {
            while (true) {
                try {
                    Shell shell = new Shell();
                    shell.convertShellCords(scanner.nextLine());
                    checkFireRules(battlefield, shell.getShellCords());
                    battlefield.placeShell(shell.getShellCords());
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage().contains("Error") ? "\n" + e.getMessage() :
                            new Exception(String.format("Error! %s. Try again: " + "\n",
                                    e.getLocalizedMessage())).getMessage());
                }
            }
//            if (gameState()) System.out.println("You sank the last ship. You won. Congratulations!");
            game--;
        }
    }

}

class GameRules {
    public static void checkPlacementRules(Battlefield battlefield) throws Exception {
        int[] cords = battlefield.getShip().getShipCords();
        int length = battlefield.getShip().getShipLength();
        int lengthFromCords = 0;
        if (cords[0] == cords[2]) lengthFromCords = Math.abs(cords[1] - cords[3]) + 1;
        if (cords[1] == cords[3]) lengthFromCords = Math.abs(cords[0] - cords[2]) + 1;
        if (cords[1] > 10 || cords[3] > 10) {
            throw new Exception("Error! Wrong ship location! Try again:\n");
        } else if (!(cords[0] == cords[2] && cords[1] != cords[3]
        || cords[0] != cords[2] && cords[1] == cords[3])) {
            throw new Exception("You cannot put a ship diagonally, please enter " +
                    "correct coordinates:\n");
        }
        else if (length != lengthFromCords) {
            throw new Exception("Wrong length of the ship. Try again:\n");
        }

        for (int i = cords[0]; i <= cords[2]; i++) {
            for (int j = cords[1]; j <= cords[3]; j++) {
                if (battlefield.getBattlefield()[i - 1][j - 1] == ('O')) {
                    throw new Exception("You cannot put one ship on top of another. Try " +
                            "again:\n");
                }
            }
        }
        for (int i = (cords[0] == 1 ? 1 : cords[0] - 1) ; i <= (cords[2] == 10 ? 10 : cords[2] + 1); i++) {
            for (int j = (cords[1] == 1 ? 1 : cords[1] - 1); j <= (cords[3] == 10 ? 10 : cords[3] + 1); j++) {
                if (battlefield.getBattlefield()[i - 1][j - 1] == ('O')) {
                    throw new Exception("Error! You placed it too close to another one. Try " +
                            "again:\n");
                }
            }
        }
    }

    public static void checkFireRules(Battlefield battlefield, int[] shellCords) throws Exception {
        if (shellCords[0] > 10 || shellCords[1] > 10) {
            throw new Exception("Error! You entered the wrong coordinates! Try again:\n");
        } else if (battlefield.getBattlefield()[shellCords[0] - 1][shellCords[1] - 1] == 'M' ||
                battlefield.getBattlefield()[shellCords[0] - 1][shellCords[1] - 1] == 'X'){
            throw new Exception("Error! You already shot here! Try again:\n");
        }
    }

    public static boolean gameState() {
        return true;
    }
}

class Battlefield {
    private final char[][] battlefield = new char[10][10];
    private final Ship[] ships;
    private int shipNumber;


    public Battlefield(Ship[] shipsInput) {
        ships = shipsInput;
        this.shipNumber = shipsInput.length;
    }
    public Ship getShip() { return this.ships[shipNumber - 1]; }

    public char[][] getBattlefield() {
        return battlefield;
    }

    public int getShipNumber() {
        return this.shipNumber;
    }

    public void createBattlefield() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                battlefield[i][j] = '~';
            }
        }
    }

    public void printBattlefield() {
        System.out.println(" 1 2 3 4 5 6 7 8 9 10");
        char[] firstCol = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        for (int i = 0; i < 10; i++) {
            System.out.print(firstCol[i] + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(battlefield[i][j]);
                if (j != 9) System.out.print(" ");
            }
            System.out.println();
        }
    }
    public void printBattlefieldFOW() {
        System.out.println(" 1 2 3 4 5 6 7 8 9 10");
        char[] firstCol = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        for (int i = 0; i < 10; i++) {
            System.out.print(firstCol[i] + " ");
            for (int j = 0; j < 10; j++) {
                if (battlefield[i][j] != 'O') {
                    System.out.print(battlefield[i][j]);
                } else {
                    System.out.print("~");
                }
                if (j != 9) System.out.print(" ");
            }
            System.out.println();
        }
    }
    public void placeShip() {
        for (int i = getShip().getShipCords()[0] - 1; i <= getShip().getShipCords()[2] - 1; i++) {
            for (int j = getShip().getShipCords()[1] - 1; j <= getShip().getShipCords()[3] - 1; j++) {
                battlefield[i][j] = 'O';
            }
        }
        this.shipNumber--;
    }
    public void placeShell(int[] cords) {
        if(battlefield[cords[0] - 1][cords[1] - 1] == '~') {
            battlefield[cords[0] - 1][cords[1] - 1] = 'M';
            printBattlefieldFOW();

            System.out.println("\nYou missed \n");

        }
        if(battlefield[cords[0] - 1][cords[1] - 1] == 'O') {
            battlefield[cords[0] - 1][cords[1] - 1] = 'X';
            printBattlefieldFOW();

            decreaseHPBasedOnCords(cords);

        }
    }
    public void decreaseHPBasedOnCords(int[] cords) {
        for (int s = 0; s < 5; s++) {
            int[] shipCords = ships[s].getShipCords();
            for (int i = shipCords[0]; i <= shipCords[2]; i++){
                for (int j = shipCords[1]; j <= shipCords[3]; j++){
//                    System.out.printf("%d %d %d %d \n", i, j, cords[0], cords[1]);
                    if (i == cords[0] && j == cords[1]) {
                        ships[s].decreaseShipHP();
                        System.out.println("You hit a ship! Try again: \n");
                    }
                }
            }
        }
    }
}

class Shell {
    private int[] shellCords;

    public int[] getShellCords() {
        return shellCords;
    }
    public void convertShellCords(String cords) {
        this.shellCords = new int[2];
        this.shellCords[0] = (int) cords.charAt(0) - 64;
        this.shellCords[1] = Integer.parseInt(cords.replaceAll("[A-Z]", ""));
    }
}

class Ship {
    private final String shipName;
    private final int shipLength;
    private int[] shipCords;
    private int shipHP;

    public Ship(String name, int length) {
        shipName = name;
        shipLength = length;
        shipHP = length;
    }

    public String getShipName() { return shipName; }

    public int getShipLength() { return shipLength; }

    public int[] getShipCords() { return this.shipCords; }

    public int getShipHP() { return this.shipHP; }

    public void setShipCords(String cords) {

        String[] cordsPrep = cords.split(" ");
        this.shipCords = new int[4];
        this.shipCords[0] = (int) cordsPrep[0].charAt(0) - 64;
        this.shipCords[1] = Integer.parseInt(cordsPrep[0].replaceAll("[A-Z]", ""));
        this.shipCords[2] = (int) cordsPrep[1].charAt(0) - 64;
        this.shipCords[3] = Integer.parseInt(cordsPrep[1].replaceAll("[A-Z]", ""));

        if(this.shipCords[0] + this.shipCords[1] > this.shipCords[2] + this.shipCords[3]) {
            this.shipCords = new int[] {this.shipCords[2], this.shipCords[3],
                    this.shipCords[0], this.shipCords[1]};
        }
    }
    public void decreaseShipHP() {
        shipHP--;
    }

}