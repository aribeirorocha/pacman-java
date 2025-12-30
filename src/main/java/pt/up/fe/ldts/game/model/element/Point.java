package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.graphics.GraphicInterface;

public class Point extends Element {

    // 1. Construtor Simples para Pontos Normais
    // Assume-se que a classe pai (Element) tem um construtor que recebe x e y.
    public Point(int x, int y) {
        // CORREÇÃO CRÍTICA: Chama o construtor da Superclasse (Element)
        super(x, y);
        // Se a Superclasse (Element) não tiver um construtor (x, y), este código falhará.
        // O construtor do Element deve ser algo como:
        // public Element(int x, int y) { this.position = new Position(x, y); }
    }


}