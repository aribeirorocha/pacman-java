Sugestões de trivial tests:

@Test
void testPacmanMovesRight() {
    Game game = new Game();
    game.movePacman(Direction.RIGHT);
    assertEquals(new Position(1,0), game.getPacman().getPosition());
}
@Test
void testPacmanBlockedByWall() {
    Game game = new Game();
    game.placeWall(new Position(1,0));
    game.movePacman(Direction.RIGHT);
    assertEquals(new Position(0,0), game.getPacman().getPosition()); // não se move
}
@Test
void testPacmanCollectsSinglePoint() {
    Game game = new Game();
    game.placePoint(new Position(1,0));
    game.movePacman(Direction.RIGHT);
    assertEquals(1, game.getScore());
    assertFalse(game.hasPoint(new Position(1,0)));
}
