package pt.up.fe.ldts.game.state;

import com.googlecode.lanterna.input.KeyStroke;
import pt.up.fe.ldts.game.graphics.GraphicInterface;

import java.io.IOException;

public interface State {
    void draw(GraphicInterface graphics) throws IOException, InterruptedException;
    State processKey(KeyStroke key) throws IOException;
}
