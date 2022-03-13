package battleship;

public class GameRules {
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
        } else if (length != lengthFromCords) {
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
        for (int i = (cords[0] == 1 ? 1 : cords[0] - 1); i <= (cords[2] == 10 ? 10 : cords[2] + 1); i++) {
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
                battlefield.getBattlefield()[shellCords[0] - 1][shellCords[1] - 1] == 'X') {
            throw new Exception("Error! You already shot here! Try again:\n");
        }
    }

    public static boolean gameState(Ship[] ships) {
        for (Ship ship : ships) {
            if (ship.getShipHP() > 0) return false;
        }
        return true;
    }
}
