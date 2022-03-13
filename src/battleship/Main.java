package battleship;

public class Main {

    public static void main(String[] args) {
        Ship[] shipsP1 = {new Ship("Destroyer", 2),
                new Ship("Cruiser", 3),
                new Ship("Submarine", 3),
                new Ship("Battleship", 4),
                new Ship("Aircraft Carrier", 5)
        };
        Ship[] shipsP2 = {new Ship("Destroyer", 2),
                new Ship("Cruiser", 3),
                new Ship("Submarine", 3),
                new Ship("Battleship", 4),
                new Ship("Aircraft Carrier", 5)
        };

        Battlefield battlefieldP1 = new Battlefield(shipsP1);
        Battlefield battlefieldP2 = new Battlefield(shipsP2);

        Game.initialShipPlacement(battlefieldP1, 1);
        Game.passTurn();
        Game.initialShipPlacement(battlefieldP2, 2);

        System.out.println("The game starts!\n");
        Game.playGame(battlefieldP1, battlefieldP2);
        Game.scanner.close();
    }
}