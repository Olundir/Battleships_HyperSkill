package battleship;

public class Ship {
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