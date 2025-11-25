package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.model.Position;

public class Ghoststop extends Ghost {
    public Ghoststop(int x, int y) {
        super(x, y);
    }
    @Override
    public Position move() {
        return getPosition();
    }
}