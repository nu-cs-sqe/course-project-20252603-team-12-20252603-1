Feature: Game Initialization and First Moves
  In order to play a chess game,
  as a player,
  I want the board to be correctly set up and able to make first moves.

  Scenario: Standard board is initialized with correct piece placement
    Given a new standard chess game is started
    Then the white king is at e1
    And the black king is at e8
    And it is white's turn
