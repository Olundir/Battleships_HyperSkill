package battleship;

public class Battlefield {
    private final char[][] battlefield = new char[10][10];
    private final Ship[] ships;
    private int shipNumber;


    public Battlefield(Ship[] shipsInput) {
        ships = shipsInput;
        this.shipNumber = shipsInput.length;
    }

    public Ship getShip() {
        return this.ships[shipNumber - 1];
    }

    public Ship[] getAllShips() {
        return ships;
    }

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
        if (battlefield[cords[0] - 1][cords[1] - 1] == '~') {
            battlefield[cords[0] - 1][cords[1] - 1] = 'M';
            System.out.println("\nYou missed.\n");

        }
        if (battlefield[cords[0] - 1][cords[1] - 1] == 'O') {
            battlefield[cords[0] - 1][cords[1] - 1] = 'X';
            decreaseHPBasedOnCords(cords);
        }
    }

    public void decreaseHPBasedOnCords(int[] cords) {
        try {
            int[] shipCords;
            for (int s = 0; s < 1; s++) {
                shipCords = ships[s].getShipCords();
                for (int i = shipCords[0]; i <= shipCords[2]; i++) {
                    for (int j = shipCords[1]; j <= shipCords[3]; j++) {
                        if (i == cords[0] && j == cords[1]) {
                            ships[s].decreaseShipHP();
                            if (ships[s].getShipHP() != 0) {
                                System.out.println("You hit a ship!\n");
                                break;
                            } else if (GameRules.gameState(ships)) {
                                System.out.println("You sank the last ship. You won. Congratulations! \n");
                                break;
                            } else {
                                System.out.println("You sank a ship!\n");
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
