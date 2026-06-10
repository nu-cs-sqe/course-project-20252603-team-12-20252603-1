Feature: Checkmate and Game End
  In order to finish a chess game,
  as a player,
  I want the game to detect checkmate and declare the winner.

  Scenario: Scholar's Mate - white wins in seven moves
    Given a new standard chess game is started
    When the following moves are made
      | from | to |
      | e2   | e4 |
      | e7   | e5 |
      | f1   | c4 |
      | b8   | c6 |
      | d1   | h5 |
      | g8   | f6 |
      | h5   | f7 |
    Then the game is over
    And white wins

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
