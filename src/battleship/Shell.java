package battleship;

public class Shell {
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
