package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Board;
import domain.FischerRandomBoardInitializer;
import domain.StandardBoardInitializer;
import domain.gamestate.GameState;
import domain.location.Location;
import domain.move.Move;
import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.NonePiece;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Queen;
import domain.piece.Rook;
import java.awt.Window;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class BoardControllerTest {

    private static final String TEST_PLAYER_ONE = "Alice";
    private static final String TEST_PLAYER_TWO = "Bob";

    @Test
    void Constructor_FreshInstance_LastSelectedUnset() {
        Board boardMock = replayNiceBoard();
        BoardController controller = controllerFor(boardMock);

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetSelectedLocation_FreshInstance_ReturnsEmpty() {
        Board boardMock = replayNiceBoard();
        BoardController controller = controllerFor(boardMock);

        Optional<Location> expected = Optional.empty();
        Optional<Location> actual = controller.getSelectedLocation();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetLegalMovesForSelection_WithSelection_WhenBoardReturnsEmpty_ReturnsEmptyList() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Location selected = new Location(0, 6);
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andStubReturn(GameState.WHITE_TURN);
        EasyMock.expect(boardMock.getPieceAt(6, 0)).andReturn(standardGrid[6][0]);
        EasyMock.expect(boardMock.getLegalMoves(selected)).andReturn(List.of());
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        controller.handleSquareClick(selected);

        int expected = 0;
        int actual = controller.getLegalMovesForSelection().size();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetLegalMovesForSelection_WithSelection_ReturnsMovesFromBoard() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Location selected = new Location(0, 6);
        Location destination = new Location(0, 5);
        List<Move> boardMoves = List.of(new Move(selected, destination));
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andStubReturn(GameState.WHITE_TURN);
        EasyMock.expect(boardMock.getPieceAt(6, 0)).andReturn(standardGrid[6][0]);
        EasyMock.expect(boardMock.getLegalMoves(selected)).andReturn(boardMoves);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        controller.handleSquareClick(selected);

        List<Move> expected = boardMoves;
        List<Move> actual = controller.getLegalMovesForSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetLegalMovesForSelection_NoSelection_ReturnsEmptyList() {
        Board boardMock = replayNiceBoard();
        BoardController controller = controllerFor(boardMock);

        int expected = 0;
        int actual = controller.getLegalMovesForSelection().size();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_AfterStandardInit_EightByEightGrid() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(standardGrid);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = isEightByEight(snapshot);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_AfterStandardInit_CornerCellsOccupied() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(standardGrid);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(standardGrid, snapshot);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_MatchesBoardSnapshot_Cellwise() {
        Piece[][] expectedGrid = newStandardStartingGrid();
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(expectedGrid);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(expectedGrid, snapshot);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_StandardStart_ExactlySixteenWhitePieces() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(standardGrid);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        int expected = 16;
        int actual = countPiecesOfColor(snapshot, PieceColor.WHITE);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_StandardStart_ExactlySixteenBlackPieces() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(standardGrid);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        int expected = 16;
        int actual = countPiecesOfColor(snapshot, PieceColor.BLACK);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GameStart_Standard_WhiteTurn() {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);

        GameState expectedState = GameState.WHITE_TURN;
        GameState actualState = controller.getCurrentGameState();
        assertEquals(expectedState, actualState);
        EasyMock.verify(boardMock);
    }

    @Test
    void GameStart_Standard_NoOccupiedPieceHasMoved() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(standardGrid);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);

        boolean expected = true;
        boolean actual = everyOccupiedPieceHasNotMoved(controller.getBoardSnapshot());
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_AfterStandardInit_ReturnsIndependentCopy() {
        Piece[][] snapshot1 = newStandardStartingGrid();
        Piece[][] snapshot2 = newStandardStartingGrid();
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(snapshot1);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(snapshot2);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        Piece[][] actual1 = controller.getBoardSnapshot();
        Piece[][] actual2 = controller.getBoardSnapshot();

        boolean expected = false;
        boolean actual = actual1 == actual2;
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_BishopsOnOppositeColorSquares_WhiteBackRank() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = stubSnapshot(chess960Grid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = bishopsOnOppositeColorSquaresOnRank(snapshot, 7, PieceColor.WHITE);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_BishopsOnOppositeColorSquares_BlackBackRank() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = stubSnapshot(chess960Grid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = bishopsOnOppositeColorSquaresOnRank(snapshot, 0, PieceColor.BLACK);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_KingStrictlyBetweenRooksOnBackRank_WhiteBackRank() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = stubSnapshot(chess960Grid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = kingStrictlyBetweenOwnRooksOnRank(snapshot, 7, PieceColor.WHITE);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_KingStrictlyBetweenRooksOnBackRank_BlackBackRank() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = stubSnapshot(chess960Grid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = kingStrictlyBetweenOwnRooksOnRank(snapshot, 0, PieceColor.BLACK);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_BackRanksMirrorPieceTypes_BackRankTypesMirror() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = stubSnapshot(chess960Grid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRanksMirrorPieceTypes(snapshot);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_BackRanksMirrorPieceTypes_StandardPawnRows() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = stubSnapshot(chess960Grid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960StandardPawnRows(snapshot);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_OneQueenTwoKnightsOnBackRank_WhiteBackRank() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = stubSnapshot(chess960Grid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRankMajorPieceCounts(snapshot, 7, PieceColor.WHITE);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_OneQueenTwoKnightsOnBackRank_BlackBackRank() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = stubSnapshot(chess960Grid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRankMajorPieceCounts(snapshot, 0, PieceColor.BLACK);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_BishopsOppositeColorSquares_WhiteBackRank() {
        Piece[][] seedGrid = newChess960SeedOneGrid();
        Board boardMock = stubSnapshot(seedGrid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = bishopsOnOppositeColorSquaresOnRank(snapshot, 7, PieceColor.WHITE);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_BishopsOppositeColorSquares_BlackBackRank() {
        Piece[][] seedGrid = newChess960SeedOneGrid();
        Board boardMock = stubSnapshot(seedGrid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = bishopsOnOppositeColorSquaresOnRank(snapshot, 0, PieceColor.BLACK);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_KingStrictlyBetweenRooks_WhiteBackRank() {
        Piece[][] seedGrid = newChess960SeedOneGrid();
        Board boardMock = stubSnapshot(seedGrid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = kingStrictlyBetweenOwnRooksOnRank(snapshot, 7, PieceColor.WHITE);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_KingStrictlyBetweenRooks_BlackBackRank() {
        Piece[][] seedGrid = newChess960SeedOneGrid();
        Board boardMock = stubSnapshot(seedGrid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = kingStrictlyBetweenOwnRooksOnRank(snapshot, 0, PieceColor.BLACK);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_BackRanksMirrorPieceTypes() {
        Piece[][] seedGrid = newChess960SeedOneGrid();
        Board boardMock = stubSnapshot(seedGrid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRanksMirrorPieceTypes(snapshot);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_StandardPawnRows() {
        Piece[][] seedGrid = newChess960SeedOneGrid();
        Board boardMock = stubSnapshot(seedGrid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960StandardPawnRows(snapshot);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_OneQueenTwoKnightsOnBackRank_WhiteBackRank() {
        Piece[][] seedGrid = newChess960SeedOneGrid();
        Board boardMock = stubSnapshot(seedGrid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRankMajorPieceCounts(snapshot, 7, PieceColor.WHITE);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_OneQueenTwoKnightsOnBackRank_BlackBackRank() {
        Piece[][] seedGrid = newChess960SeedOneGrid();
        Board boardMock = stubSnapshot(seedGrid);
        BoardController controller = controllerFor(boardMock);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRankMajorPieceCounts(snapshot, 0, PieceColor.BLACK);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnWhitePiece_HasSelection() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForWhitePieceClick(standardGrid, 6, 0);
        BoardController controller = controllerFor(boardMock);
        Location clicked = new Location(0, 6);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnWhitePiece_SelectedLocationMatches() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForWhitePieceClick(standardGrid, 6, 0);
        BoardController controller = controllerFor(boardMock);
        Location clicked = new Location(0, 6);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = selectedLocationMatches(controller.getSelectedLocation(), clicked);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnWhitePiece_BoardUnchanged() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForWhitePieceClickWithSnapshot(standardGrid, 6, 0);
        BoardController controller = controllerFor(boardMock);
        Location clicked = new Location(0, 6);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(standardGrid, controller.getBoardSnapshot());
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnBlackPiece_NoSelectionAfterClick() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClick(standardGrid, 1, 0);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(0, 1));

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnBlackPiece_TurnRemainsWhite() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClick(standardGrid, 1, 0);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(0, 1));

        GameState expected = GameState.WHITE_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnBlackPiece_BoardUnchanged() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClick(standardGrid, 1, 0);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(0, 1));

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(standardGrid, controller.getBoardSnapshot());
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnEmptySquare_NoSelectionAfterClick() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClick(standardGrid, 3, 3);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(3, 3));

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnEmptySquare_TurnRemainsWhite() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClick(standardGrid, 3, 3);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(3, 3));

        GameState expected = GameState.WHITE_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnEmptySquare_BoardUnchanged() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClick(standardGrid, 3, 3);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(3, 3));

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(standardGrid, controller.getBoardSnapshot());
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_InvalidLocation_NoSelectionAfterClick() {
        Board boardMock = replayNiceBoard();
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(-1, 0));

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_InvalidLocation_TurnRemainsWhite() {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        controller.handleSquareClick(new Location(-1, 0));

        GameState expected = GameState.WHITE_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_InvalidLocation_BoardUnchanged() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(standardGrid);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        controller.handleSquareClick(new Location(-1, 0));

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(standardGrid, controller.getBoardSnapshot());
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_OnBlackTurn_OnBlackPiece_HasSelection() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForBlackPieceClick(standardGrid, 1, 0);
        BoardController controller = controllerFor(boardMock);
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_OnBlackTurn_OnBlackPiece_SelectedLocationMatches() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForBlackPieceClick(standardGrid, 1, 0);
        BoardController controller = controllerFor(boardMock);
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = selectedLocationMatches(controller.getSelectedLocation(), clicked);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_OnBlackTurn_OnBlackPiece_BoardUnchanged() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForBlackPieceClickWithSnapshot(standardGrid, 1, 0);
        BoardController controller = controllerFor(boardMock);
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(standardGrid, controller.getBoardSnapshot());
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_OnBlackTurn_OnWhitePiece_NoSelectionAfterClick() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClickOnBlackTurn(standardGrid, 6, 0);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(0, 6));

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_OnBlackTurn_OnWhitePiece_TurnRemainsBlack() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClickOnBlackTurn(standardGrid, 6, 0);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(0, 6));

        GameState expected = GameState.BLACK_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_OnBlackTurn_OnWhitePiece_BoardUnchanged() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClickOnBlackTurn(standardGrid, 6, 0);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(0, 6));

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(standardGrid, controller.getBoardSnapshot());
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_OnBlackTurn_OnEmptySquare_NoSelectionAfterClick() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClickOnBlackTurn(standardGrid, 3, 3);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(3, 3));

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_OnBlackTurn_OnEmptySquare_TurnRemainsBlack() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClickOnBlackTurn(standardGrid, 3, 3);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(3, 3));

        GameState expected = GameState.BLACK_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_OnBlackTurn_OnEmptySquare_BoardUnchanged() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = boardForSquareClickOnBlackTurn(standardGrid, 3, 3);
        BoardController controller = controllerFor(boardMock);

        controller.handleSquareClick(new Location(3, 3));

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(standardGrid, controller.getBoardSnapshot());
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_Chess960Start_FirstWhiteSelectionSamePolicy() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = stubSnapshot(chess960Grid);
        BoardController controller = controllerFor(boardMock);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(chess960Grid, snapshot);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_Chess960Start_FirstWhiteSelection_SelectsAndKeepsTurn() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = boardForWhitePieceClick(chess960Grid, 6, 0);
        BoardController controller = controllerFor(boardMock);
        Location clicked = new Location(0, 6);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_Chess960Start_FirstWhiteSelection_SelectedLocationMatches() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = boardForWhitePieceClick(chess960Grid, 6, 0);
        BoardController controller = controllerFor(boardMock);
        Location clicked = new Location(0, 6);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = selectedLocationMatches(controller.getSelectedLocation(), clicked);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_Chess960Start_FirstWhiteSelection_TurnRemainsWhite() {
        Piece[][] chess960Grid = newChess960FixedStartingGrid();
        Board boardMock = boardForWhitePieceClick(chess960Grid, 6, 0);
        BoardController controller = controllerFor(boardMock);
        Location clicked = new Location(0, 6);

        controller.handleSquareClick(clicked);

        GameState expected = GameState.WHITE_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    private static Board boardForBlackPieceClick(Piece[][] snapshot, int rank, int file) {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andStubReturn(GameState.BLACK_TURN);
        EasyMock.expect(boardMock.getPieceAt(rank, file)).andReturn(snapshot[rank][file]);
        EasyMock.replay(boardMock);
        return boardMock;
    }

    private static Board boardForSquareClickOnBlackTurn(Piece[][] snapshot, int rank, int file) {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andStubReturn(GameState.BLACK_TURN);
        EasyMock.expect(boardMock.getPieceAt(rank, file)).andReturn(snapshot[rank][file]);
        EasyMock.expect(boardMock.getSnapshot()).andStubReturn(snapshot);
        EasyMock.replay(boardMock);
        return boardMock;
    }

    private static Board boardForBlackPieceClickWithSnapshot(
            Piece[][] snapshot, int rank, int file) {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andStubReturn(GameState.BLACK_TURN);
        EasyMock.expect(boardMock.getPieceAt(rank, file)).andReturn(snapshot[rank][file]);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(snapshot);
        EasyMock.replay(boardMock);
        return boardMock;
    }

    private static BoardController controllerFor(Board board) {
        return new BoardController(TEST_PLAYER_ONE, TEST_PLAYER_TWO, board);
    }

    private static Board replayNiceBoard() {
        Board boardMock = EasyMock.createNiceMock(Board.class);
        EasyMock.replay(boardMock);
        return boardMock;
    }

    private static Board stubSnapshot(Piece[][] snapshot) {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(snapshot);
        EasyMock.replay(boardMock);
        return boardMock;
    }

    private static Board boardForWhitePieceClick(Piece[][] snapshot, int rank, int file) {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andStubReturn(GameState.WHITE_TURN);
        EasyMock.expect(boardMock.getPieceAt(rank, file)).andReturn(snapshot[rank][file]);
        EasyMock.replay(boardMock);
        return boardMock;
    }

    private static Board boardForWhitePieceClickWithSnapshot(
            Piece[][] snapshot, int rank, int file) {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andStubReturn(GameState.WHITE_TURN);
        EasyMock.expect(boardMock.getPieceAt(rank, file)).andReturn(snapshot[rank][file]);
        EasyMock.expect(boardMock.getSnapshot()).andReturn(snapshot);
        EasyMock.replay(boardMock);
        return boardMock;
    }

    private static Board boardForSquareClick(Piece[][] snapshot, int rank, int file) {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andStubReturn(GameState.WHITE_TURN);
        EasyMock.expect(boardMock.getPieceAt(rank, file)).andReturn(snapshot[rank][file]);
        EasyMock.expect(boardMock.getSnapshot()).andStubReturn(snapshot);
        EasyMock.replay(boardMock);
        return boardMock;
    }

    private static boolean isEightByEight(Piece[][] snapshot) {
        if (snapshot == null || snapshot.length != 8) {
            return false;
        }
        for (Piece[] row : snapshot) {
            if (row == null || row.length != 8) {
                return false;
            }
        }
        return true;
    }

    private static Piece[][] newStandardStartingGrid() {
        return new Board(new StandardBoardInitializer()).getSnapshot();
    }

    private static Piece[][] newChess960FixedStartingGrid() {
        Piece[][] grid = emptyGrid();
        grid[0][0] = new Rook(PieceColor.BLACK);
        grid[0][1] = new Bishop(PieceColor.BLACK);
        grid[0][2] = new Knight(PieceColor.BLACK);
        grid[0][3] = new Queen(PieceColor.BLACK);
        grid[0][4] = new King(PieceColor.BLACK);
        grid[0][5] = new Knight(PieceColor.BLACK);
        grid[0][6] = new Bishop(PieceColor.BLACK);
        grid[0][7] = new Rook(PieceColor.BLACK);
        for (int file = 0; file < 8; file++) {
            grid[1][file] = new Pawn(PieceColor.BLACK);
        }

        for (int file = 0; file < 8; file++) {
            grid[6][file] = new Pawn(PieceColor.WHITE);
        }

        grid[7][0] = new Rook(PieceColor.WHITE);
        grid[7][1] = new Bishop(PieceColor.WHITE);
        grid[7][2] = new Knight(PieceColor.WHITE);
        grid[7][3] = new Queen(PieceColor.WHITE);
        grid[7][4] = new King(PieceColor.WHITE);
        grid[7][5] = new Knight(PieceColor.WHITE);
        grid[7][6] = new Bishop(PieceColor.WHITE);
        grid[7][7] = new Rook(PieceColor.WHITE);
        return grid;
    }

    private static Piece[][] newChess960SeedOneGrid() {
        return new Board(new FischerRandomBoardInitializer(new Random(1L))).getSnapshot();
    }

    private static boolean selectedLocationMatches(
            Optional<Location> actual, Location expected) {
        return actual.isPresent()
                && actual.get().getX() == expected.getX()
                && actual.get().getY() == expected.getY();
    }

    private static Piece[][] emptyGrid() {
        Piece[][] grid = new Piece[8][8];
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                grid[rank][file] = new NonePiece();
            }
        }
        return grid;
    }

    private static boolean cellWiseSameTypeAndColor(Piece[][] expected, Piece[][] actual) {
        if (!isEightByEight(expected) || !isEightByEight(actual)) {
            return false;
        }
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece e = expected[rank][file];
                Piece a = actual[rank][file];
                if (e.getType() != a.getType() || e.getColor() != a.getColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int countPiecesOfColor(Piece[][] snapshot, PieceColor color) {
        int count = 0;
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece piece = snapshot[rank][file];
                if (piece.getType() != PieceType.NONE && piece.getColor() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean everyOccupiedPieceHasNotMoved(Piece[][] snapshot) {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece piece = snapshot[rank][file];
                if (piece.getType() != PieceType.NONE && piece.hasMoved()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean bishopsOnOppositeColorSquaresOnRank(
            Piece[][] snapshot, int rank, PieceColor color) {
        int firstFile = -1;
        int secondFile = -1;
        for (int file = 0; file < 8; file++) {
            Piece piece = snapshot[rank][file];
            if (piece.getType() == PieceType.BISHOP && piece.getColor() == color) {
                if (firstFile < 0) {
                    firstFile = file;
                } else if (secondFile < 0) {
                    secondFile = file;
                } else {
                    return false;
                }
            }
        }
        if (firstFile < 0 || secondFile < 0) {
            return false;
        }
        int parityFirst = (firstFile + rank) % 2;
        int paritySecond = (secondFile + rank) % 2;
        return parityFirst != paritySecond;
    }

    private static boolean kingStrictlyBetweenOwnRooksOnRank(
            Piece[][] snapshot, int rank, PieceColor color) {
        int rookA = -1;
        int rookB = -1;
        int kingFile = -1;
        for (int file = 0; file < 8; file++) {
            Piece piece = snapshot[rank][file];
            if (piece.getType() == PieceType.NONE || piece.getColor() != color) {
                continue;
            }
            if (piece.getType() == PieceType.ROOK) {
                if (rookA < 0) {
                    rookA = file;
                } else if (rookB < 0) {
                    rookB = file;
                } else {
                    return false;
                }
            } else if (piece.getType() == PieceType.KING) {
                if (kingFile >= 0) {
                    return false;
                }
                kingFile = file;
            }
        }
        if (rookA < 0 || rookB < 0 || kingFile < 0) {
            return false;
        }
        int rookMin = Math.min(rookA, rookB);
        int rookMax = Math.max(rookA, rookB);
        return kingFile > rookMin && kingFile < rookMax;
    }

    private static boolean chess960BackRanksMirrorPieceTypes(Piece[][] snapshot) {
        for (int file = 0; file < 8; file++) {
            Piece blackBack = snapshot[0][file];
            Piece whiteBack = snapshot[7][file];
            if (whiteBack.getType() == PieceType.NONE || blackBack.getType() == PieceType.NONE) {
                return false;
            }
            if (whiteBack.getType() != blackBack.getType()) {
                return false;
            }
            if (!hasCorrectBackRankColors(whiteBack, blackBack)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasCorrectBackRankColors(Piece white, Piece black) {
        return white.getColor() == PieceColor.WHITE && black.getColor() == PieceColor.BLACK;
    }

    private static boolean chess960StandardPawnRows(Piece[][] snapshot) {
        for (int file = 0; file < 8; file++) {
            Piece blackPawn = snapshot[1][file];
            Piece whitePawn = snapshot[6][file];
            if (blackPawn.getType() != PieceType.PAWN || blackPawn.getColor() != PieceColor.BLACK) {
                return false;
            }
            if (whitePawn.getType() != PieceType.PAWN || whitePawn.getColor() != PieceColor.WHITE) {
                return false;
            }
        }
        return true;
    }

    private static boolean chess960BackRankMajorPieceCounts(
            Piece[][] snapshot, int rank, PieceColor color) {
        int queens = 0;
        int knights = 0;
        int bishops = 0;
        int rooks = 0;
        int kings = 0;
        for (int file = 0; file < 8; file++) {
            Piece piece = snapshot[rank][file];
            if (piece.getType() == PieceType.NONE || piece.getColor() != color) {
                return false;
            }
            PieceType type = piece.getType();
            if (type == PieceType.PAWN) {
                return false;
            }
            if (type == PieceType.QUEEN) {
                queens++;
            } else if (type == PieceType.KNIGHT) {
                knights++;
            } else if (type == PieceType.BISHOP) {
                bishops++;
            } else if (type == PieceType.ROOK) {
                rooks++;
            } else if (type == PieceType.KING) {
                kings++;
            }
        }
        return queens == 1
                && knights == 2
                && bishops == 2
                && rooks == 2
                && kings == 1;
    }

    @Test
    void HandleSquareClick_WithSelection_OnLegalDestination_CallsMakeMove() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Location selected = new Location(0, 6);
        Location destination = new Location(0, 5);
        Move move = new Move(selected, destination);
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN).times(3);
        EasyMock.expect(boardMock.getPieceAt(6, 0)).andReturn(standardGrid[6][0]);
        EasyMock.expect(boardMock.getPieceAt(5, 0)).andReturn(standardGrid[5][0]);
        EasyMock.expect(boardMock.getLegalMoves(selected)).andReturn(List.of(move));
        boardMock.makeMove(move);
        EasyMock.expectLastCall().once();
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        controller.handleSquareClick(selected);
        controller.handleSquareClick(destination);

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_WithSelection_OnIllegalDestination_ClearsSelection() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Location selected = new Location(0, 6);
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN).times(2);
        EasyMock.expect(boardMock.getPieceAt(6, 0)).andReturn(standardGrid[6][0]);
        EasyMock.expect(boardMock.getPieceAt(3, 3)).andReturn(standardGrid[3][3]);
        EasyMock.expect(boardMock.getLegalMoves(selected)).andReturn(List.of());
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        controller.handleSquareClick(selected);
        Location illegal = new Location(3, 3);
        controller.handleSquareClick(illegal);

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void HandleSquareClick_WithSelection_OnOwnPiece_ChangesSelection() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN).times(2);
        EasyMock.expect(boardMock.getPieceAt(6, 0)).andReturn(standardGrid[6][0]);
        EasyMock.expect(boardMock.getPieceAt(7, 1)).andReturn(standardGrid[7][1]);
        EasyMock.replay(boardMock);

        Location selected = new Location(0, 6);
        BoardController controller = controllerFor(boardMock);
        controller.handleSquareClick(selected);
        Location otherPiece = new Location(1, 7);
        controller.handleSquareClick(otherPiece);

        Optional<Location> expected = Optional.of(otherPiece);
        Optional<Location> actual = controller.getSelectedLocation();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void ExecuteMove_AfterMoveResultsInGameOver_ShowEndGameCalled() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Location selected = new Location(0, 6);
        Location destination = new Location(0, 5);
        Move move = new Move(selected, destination);
        Board boardMock = EasyMock.createMock(Board.class);
        final MainView mainViewMock = EasyMock.createMock(MainView.class);
        final GameStatsView statsMock = EasyMock.createMock(GameStatsView.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN).times(2);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_WIN).times(3);
        EasyMock.expect(boardMock.getPieceAt(6, 0)).andReturn(standardGrid[6][0]);
        EasyMock.expect(boardMock.getPieceAt(5, 0)).andReturn(standardGrid[5][0]);
        EasyMock.expect(boardMock.getLegalMoves(selected)).andReturn(List.of(move));
        boardMock.makeMove(move);
        EasyMock.expectLastCall().once();
        EasyMock.expect(mainViewMock.getGameStatsView()).andReturn(statsMock);
        statsMock.updateCurrentPlayerLabel(TEST_PLAYER_ONE + " wins!");
        EasyMock.expectLastCall().once();
        mainViewMock.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(boardMock, mainViewMock, statsMock);

        BoardController controller = controllerFor(boardMock);
        controller.setMainView(mainViewMock);
        controller.handleSquareClick(selected);
        controller.handleSquareClick(destination);

        boolean expected = true;
        boolean actual = isAnyWindowOfTypeVisible(EndGameView.class);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock, mainViewMock, statsMock);
    }

    @Test
    void IsGameOver_WhenStateIsBlackWin_ReturnsTrue() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Location selected = new Location(0, 6);
        Location destination = new Location(0, 5);
        Move move = new Move(selected, destination);
        Board boardMock = EasyMock.createMock(Board.class);
        final MainView mainViewMock = EasyMock.createMock(MainView.class);
        final GameStatsView statsMock = EasyMock.createMock(GameStatsView.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN).times(2);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.BLACK_WIN).times(3);
        EasyMock.expect(boardMock.getPieceAt(6, 0)).andReturn(standardGrid[6][0]);
        EasyMock.expect(boardMock.getPieceAt(5, 0)).andReturn(standardGrid[5][0]);
        EasyMock.expect(boardMock.getLegalMoves(selected)).andReturn(List.of(move));
        boardMock.makeMove(move);
        EasyMock.expectLastCall().once();
        EasyMock.expect(mainViewMock.getGameStatsView()).andReturn(statsMock);
        statsMock.updateCurrentPlayerLabel(TEST_PLAYER_TWO + " wins!");
        EasyMock.expectLastCall().once();
        mainViewMock.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(boardMock, mainViewMock, statsMock);

        BoardController controller = controllerFor(boardMock);
        controller.setMainView(mainViewMock);
        controller.handleSquareClick(selected);
        controller.handleSquareClick(destination);

        boolean expected = true;
        boolean actual = isAnyWindowOfTypeVisible(EndGameView.class);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock, mainViewMock, statsMock);
    }

    @Test
    void IsGameOver_WhenStateIsDraw_ReturnsTrue() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Location selected = new Location(0, 6);
        Location destination = new Location(0, 5);
        Move move = new Move(selected, destination);
        Board boardMock = EasyMock.createMock(Board.class);
        final MainView mainViewMock = EasyMock.createMock(MainView.class);
        final GameStatsView statsMock = EasyMock.createMock(GameStatsView.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN).times(2);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.DRAW).times(3);
        EasyMock.expect(boardMock.getPieceAt(6, 0)).andReturn(standardGrid[6][0]);
        EasyMock.expect(boardMock.getPieceAt(5, 0)).andReturn(standardGrid[5][0]);
        EasyMock.expect(boardMock.getLegalMoves(selected)).andReturn(List.of(move));
        boardMock.makeMove(move);
        EasyMock.expectLastCall().once();
        EasyMock.expect(mainViewMock.getGameStatsView()).andReturn(statsMock);
        statsMock.updateCurrentPlayerLabel("Draw!");
        EasyMock.expectLastCall().once();
        mainViewMock.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(boardMock, mainViewMock, statsMock);

        BoardController controller = controllerFor(boardMock);
        controller.setMainView(mainViewMock);
        controller.handleSquareClick(selected);
        controller.handleSquareClick(destination);

        boolean expected = true;
        boolean actual = isAnyWindowOfTypeVisible(EndGameView.class);
        assertEquals(expected, actual);
        EasyMock.verify(boardMock, mainViewMock, statsMock);
    }

    @Test
    void BuildEndGameMessage_WhiteWin_ReturnsPlayer1WinsMessage() {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_WIN);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);

        String expected = TEST_PLAYER_ONE + " wins!";
        String actual = controller.buildEndGameMessage();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void BuildEndGameMessage_BlackWin_ReturnsPlayer2WinsMessage() {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.BLACK_WIN);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);

        String expected = TEST_PLAYER_TWO + " wins!";
        String actual = controller.buildEndGameMessage();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void BuildEndGameMessage_Draw_ReturnsDraw() {
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.DRAW);
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);

        String expected = "Draw!";
        String actual = controller.buildEndGameMessage();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    @Test
    void ExecuteMove_OnNonPromotionMove_AsBlack_MakeMoveCalledDirectly() {
        Piece[][] standardGrid = newStandardStartingGrid();
        Location selected = new Location(0, 1);
        Location destination = new Location(0, 2);
        Move move = new Move(selected, destination);
        Board boardMock = EasyMock.createMock(Board.class);
        EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.BLACK_TURN).times(3);
        EasyMock.expect(boardMock.getPieceAt(1, 0)).andReturn(standardGrid[1][0]);
        EasyMock.expect(boardMock.getPieceAt(2, 0)).andReturn(standardGrid[2][0]);
        EasyMock.expect(boardMock.getLegalMoves(selected)).andReturn(List.of(move));
        boardMock.makeMove(move);
        EasyMock.expectLastCall().once();
        EasyMock.replay(boardMock);

        BoardController controller = controllerFor(boardMock);
        controller.handleSquareClick(selected);
        controller.handleSquareClick(destination);

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
        EasyMock.verify(boardMock);
    }

    private static boolean isAnyWindowOfTypeVisible(Class<?> type) {
        for (Window window : Window.getWindows()) {
            if (type.isInstance(window) && window.isVisible()) {
                return true;
            }
        }
        return false;
    }
}
