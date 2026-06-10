Feature: Game Initialization and First Moves
  In order to play a chess game,
  as a player,
  I want the board to be correctly set up and able to make first moves.

  Scenario Outline: Standard board has correct back-rank pieces
    Given a new standard chess game is started
    Then the <color> <piece> is at <square>

    Examples:
      | color | piece  | square |
      | white | rook   | a1     |
      | white | knight | b1     |
      | white | bishop | c1     |
      | white | queen  | d1     |
      | white | king   | e1     |
      | white | bishop | f1     |
      | white | knight | g1     |
      | white | rook   | h1     |
      | black | rook   | a8     |
      | black | knight | b8     |
      | black | bishop | c8     |
      | black | queen  | d8     |
      | black | king   | e8     |
      | black | bishop | f8     |
      | black | knight | g8     |
      | black | rook   | h8     |

  Scenario Outline: Standard board has correct pawn placement
    Given a new standard chess game is started
    Then the <color> pawn is at <square>

    Examples:
      | color | square |
      | white | a2     |
      | white | b2     |
      | white | c2     |
      | white | d2     |
      | white | e2     |
      | white | f2     |
      | white | g2     |
      | white | h2     |
      | black | a7     |
      | black | b7     |
      | black | c7     |
      | black | d7     |
      | black | e7     |
      | black | f7     |
      | black | g7     |
      | black | h7     |

  Scenario Outline: Both players make their first moves
    Given a new standard chess game is started
    When white moves <white_from> to <white_to>
    And black moves <black_from> to <black_to>
    Then it is white's turn

    Examples:
      | white_from | white_to | black_from | black_to |
      | e2         | e4       | e7         | e5       |
      | d2         | d4       | d7         | d5       |
