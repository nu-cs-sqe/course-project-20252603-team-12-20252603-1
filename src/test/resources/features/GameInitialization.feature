Feature: Game Initialization and First Moves
  In order to play a chess game,
  as a player,
  I want the board to be correctly set up and able to make first moves.

  Scenario: Standard board is initialized with correct piece placement
    Given a new standard chess game is started
    Then the white king is at e1
    And the black king is at e8
    And it is white's turn

  Scenario Outline: Both players make their first moves
    Given a new standard chess game is started
    When white moves <white_from> to <white_to>
    And black moves <black_from> to <black_to>
    Then it is white's turn

    Examples:
      | white_from | white_to | black_from | black_to |
      | e2         | e4       | e7         | e5       |
      | d2         | d4       | d7         | d5       |
