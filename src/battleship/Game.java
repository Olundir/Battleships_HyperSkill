package battleship;

import java.util.Scanner;

public class Game extends GameRules {
    static Scanner scanner = new Scanner(System.in);

    public static void initialShipPlacement(Battlefield battlefield, int player) {
        System.out.printf("\nPlayer %d, place your ships on the game field\n", player);
        battlefield.createBattlefield();
        battlefield.printBattlefield();
        while (battlefield.getShipNumber() != 0) { // initial placement loop
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n",
                    battlefield.getShip().getShipName(), battlefield.getShip().getShipLength());
            while (true) {
                try {
                    battlefield.getShip().setShipCords(scanner.nextLine());

                    GameRules.checkPlacementRules(battlefield);

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

    public static void playGame(Battlefield battlefieldP1, Battlefield battlefieldP2) {
        while (!GameRules.gameState(battlefieldP1.getAllShips()) && !GameRules.gameState(battlefieldP2.getAllShips())) {
            while (true) {
                try {
                    battlefieldP2.printBattlefieldFOW();
                    System.out.println("---------------------");
                    battlefieldP1.printBattlefield();
                    System.out.println("\nPlayer 1 tak a shot: \n");
                    Shell shell = new Shell();
                    shell.convertShellCords(scanner.nextLine());
                    GameRules.checkFireRules(battlefieldP2, shell.getShellCords());
                    battlefieldP2.placeShell(shell.getShellCords());
                    passTurn();
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage().contains("Error") ? "\n" + e.getMessage() :
                            new Exception(String.format("Error! %s. Try again: " + "\n",
                                    e.getLocalizedMessage())).getMessage());
                }
            }

            if (GameRules.gameState(battlefieldP1.getAllShips()) || GameRules.gameState(battlefieldP2.getAllShips()))
                break;

            while (true) {
                try {
                    battlefieldP1.printBattlefieldFOW();
                    System.out.println("---------------------");
                    battlefieldP2.printBattlefield();
                    System.out.println("\nPlayer 2 tak a shot: \n");
                    Shell shell = new Shell();
                    shell.convertShellCords(scanner.nextLine());
                    GameRules.checkFireRules(battlefieldP1, shell.getShellCords());
                    battlefieldP1.placeShell(shell.getShellCords());
                    passTurn();
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage().contains("Error") ? "\n" + e.getMessage() :
                            new Exception(String.format("Error! %s. Try again: " + "\n",
                                    e.getLocalizedMessage())).getMessage());
                }
            }
        }
    }

    public static void passTurn() {
        System.out.println("\nPress Enter and pass the move to another player\n");


        while (scanner.nextLine().length() > 0) {
            System.out.println("\nPress Enter and pass the move to another player\n");
        }

        System.out.print("\033[H\033[2J");
    }
}
