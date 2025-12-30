package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;

public class PowerPellet extends Element {
    public PowerPellet(int x, int y) {
        super(x, y);
    }
    public Position getPosition() {
        return position;
    }


}
