Feature: Checkmate and Game End
  In order to finish a chess game,
  as a player,
  I want the game to detect checkmate and declare the winner.

  Scenario: Fool's Mate - black wins in four moves
    Given a new standard chess game is started
    When the following moves are made
      | from | to |
      | f2   | f3 |
      | e7   | e5 |
      | g2   | g4 |
      | d8   | h4 |
    Then the game is over
    And black wins
