package pt.up.fe.ldts.game;

class Ghoststop extends Ghost {
    public Ghoststop(int x, int y) {
        super(x, y);
    }
    @Override
    public Position move() {
        return getPosition();
    }
}