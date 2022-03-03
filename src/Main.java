import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        String[][] board = boardInit();
        printBoard(board);

        p1PlaceShips(scanner, board);

        scanner.close();
    }

    private static void printBoard(String[][] board) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(board[i][j]);
                if (j != 10) System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static String[][] boardInit() {
        String[][] board = new String[11][11];
        board[0][0] = " ";
        String[] topRow = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] firstCol = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        System.arraycopy(topRow, 0, board[0], 1, 10);
        for (int i = 1; i < 11; i++) {
            board[i][0] = firstCol[i - 1];
        }
        //
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                board[i][j] = "~";
            }
        }
        return board;
    }

    private static void placeShip(String[][] board, int[] shipCords) {
        for (int i = shipCords[0]; i <= shipCords[2]; i++) {
            for (int j = shipCords[1]; j <= shipCords[3]; j++) {
                board[i][j] = "O";
            }
        }
    }


    private static void p1PlaceShips(Scanner scanner, String[][] board) {
        Pattern pattern = Pattern.compile("[A-J]\\d{1,2}\\s[A-J]\\d{1,2}");
        String[] ships = {"Aircraft Carrier (5 cells)", "Battleship (4 cells)", "Submarine (3 cells)",
                "Cruiser (3 cells)", "Destroyer (2 cells)"};
        int[] shipsLengths = {5, 4, 3, 3, 2};
        int[] shipCords = new int[4];
        for (int i = 0; i < 5; i++) {
            boolean inputPattern = false;
            System.out.printf("Enter the coordinates of the %s: \n", ships[i]);
            String coordinates;
            Matcher m;
            while (!inputPattern) {
                coordinates = scanner.nextLine();
                m = pattern.matcher(coordinates);
                inputPattern = m.find();
                if (inputPattern) {
                    shipCords = convertCords(coordinates);
                    boolean cords = checkIfValid(board, shipCords, shipsLengths[i]);
                        if(!cords) {
                            inputPattern = false;
                        }
                } else System.out.println("Please put coordinates in correct format:");
            }
            placeShip(board, shipCords);
            printBoard(board);
        }
        scanner.close();
    }

    private static int[] convertCords(String coordinates) {
        // cords are strings where _ _ _ _ _ are: start x, start y, space, end x, end y;
        int[] cords = new int[4];
        String[] cordsPrep = coordinates.split(" ");
        cords[0] = (int) cordsPrep[0].charAt(0) - 64;
        cords[1] = Integer.parseInt(cordsPrep[0].replaceAll("[A-Z]", ""));
        cords[2] = (int) cordsPrep[1].charAt(0) - 64;
        cords[3] = Integer.parseInt(cordsPrep[1].replaceAll("[A-Z]", ""));

        if(cords[0] + cords[1] > cords[2] + cords[3]) {
            return new int[] {cords[2], cords[3], cords[0], cords[1]};
        }

        return cords;
    }

    private static boolean checkIfValid(String[][] board, int[] cords, int length) {
        if (cords[1] > 10 || cords[3] > 10) {
            System.out.println("Coordinates out of board area:");
            return false;
        }
//     TODO
//      [ ] - Check if the placement of the ship is valid
//          [*] - check if correct ship is being placed
//          [*] - check if cords are not diagonal
//              if  horizontal then cords[0] == cords[2]
//              if vertical then cords[1] == cords[3]
//          [*] - They cannot be on top of each other
//          [*] - There has to be at least one space in between ships

        if (!(cords[0] == cords[2] && cords[1] != cords[3]
        || cords[0] != cords[2] && cords[1] == cords[3])) {
            System.out.println("You cannot put a ship diagonally, please enter " +
                    "correct coordinates:");
            return false;
        };

        if (cords[0] == cords[2]) if (length != Math.abs(cords[1] - cords[3]) + 1) {
            System.out.println("Wrong length of the ship. Try again:");
            return false;
        };
        if (cords[1] == cords[3]) if (length != Math.abs(cords[0] - cords[2]) + 1) {
            System.out.println("Wrong length of the ship. Try again:");
            return false;
        };

        for (int i = cords[0]; i <= cords[2]; i++) {
            for (int j = cords[1]; j <= cords[3]; j++) {
                if (board[i][j].matches("O")) {
                    System.out.println("You cannot put one ship on top of another. Try again:");
                    return false;
                };
            }
        }
// what is wrong with the code below?
        for (int i = (cords[0] == 1 ? 1 : cords[0] - 1) ; i <= (cords[2] == 10 ? 10 : cords[2] + 1); i++) {
            for (int j = (cords[1] == 1 ? 1 : cords[1] - 1); j <= (cords[3] == 10 ? 10 : cords[3] + 1); j++) {
                if (board[i][j].matches("O")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                };
            }
        }


        return true;
    }
}