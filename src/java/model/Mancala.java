/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author ben
 */
public class Mancala implements Serializable {

    private final int[] board;
    private int gameBeanPool;
    private final int[] playerNum;

    private int turn;
    private int player1Score;
    private int player2Score;
    private int winner;
    private String winMsg;

    /**
     * Constructor for Mancala Bean. Set the contents of the Pits with 4 beans
     * in each Pit and adds those beans to the main game Bean Pool
     */
    public Mancala() {
        board = new int[14];

        this.turn = 0;

        this.playerNum = new int[]{1, 2};

        this.player2Score = 0;
        this.player1Score = 0;

        for (int i = 0; i < board.length; i++) {
            if (i == 0 || i == 7) {
                board[i] = 0;
            } else {
                board[i] = 4;
            }
        }

        gameBeanPool = 48;

    }

    /**
     * sow simulates moving beans around the pits on the board
     *
     * @param pointer
     */
    public void sow(int pointer) {

        int i;

        //place beans in pool for distribution
        int beanPool = board[pointer];

        //remove beans from origin pit
        board[pointer] = 0;

        //place beans in pits until no more beans left in pool
        for (i = pointer + 1; beanPool != 0 && !isOver(); i++) {
            
            if (i > 13) {
                i = 0;
            }

            //skip player2 homePit if not player2
            if (i == 0 && getPlayerNum() != playerNum[1]) {
                i++;
            }

            //skip player1 homePit if not player1
            if (i == 7 && getPlayerNum() != playerNum[0]) {
                i++;
            }

            //add bean to current pit at pointer
            board[i] += 1;

            //remove bean from beanPool
            beanPool--;

            /* remove beans from main beanpool and award points when bean
                is placed in a home pit */
            if (i == 0 || i == 7) {
                assignPoints(1);
            }

            //if is last placed bean
            if (beanPool == 0) {
                if (board[i] == 1) {
                    steal(i);
                }

                if (i != 0 && i != 7 && !isOver()) {
                    
                    turn += 1;
                }

            }
        }
    }

    /**
     * removes beans from the opponents side of the board if bean is placed into
     * pit containing no beans
     *
     * @param pointer the current pit
     */
    public void steal(int pointer) {
        int opposite = (board.length) - pointer;

        if (0 < pointer && pointer < 7 && pointer != 7
                && getPlayerNum() == playerNum[0] && board[opposite] != 0) {
            board[7] += board[pointer] + board[opposite];
            assignPoints(board[pointer] + board[opposite]);
            board[pointer] = 0;
            board[opposite] = 0;
        } else if (7 < pointer
                && getPlayerNum() == playerNum[1] && board[opposite] != 0) {
            board[0] += board[pointer] + board[opposite];
            assignPoints(board[pointer] + board[opposite]);
            board[pointer] = 0;
            board[opposite] = 0;
        }

    }

    /**
     * awards to players based on the passed value removes equal number from the
     * game's beanPool
     *
     * @param points the number of points to be awarded
     */
    private void assignPoints(int points) {
        gameBeanPool -= points;

        setPlayer1Score();
        setPlayer2Score();
    }

    /**
     * isOver determines if the game has been finished
     *
     * @return boolean value based on remaining beans if no more left in
     * gameBeanPool then game is over
     */
    public boolean isOver() {
        boolean player1OutofPits = (board[1] == 0
                && board[2] == 0
                && board[3] == 0
                && board[4] == 0
                && board[5] == 0
                && board[6] == 0);

        boolean player2OutofPits = (board[8] == 0
                && board[9] == 0
                && board[10] == 0
                && board[11] == 0
                && board[12] == 0
                && board[13] == 0);

        if (player1OutofPits || player2OutofPits) {
            int p1Points = 0;
            int p2Points = 0;

            for (int i = 1; i < 7; i++) {
                p1Points += board[i];
            }
            board[7] += p1Points;
            assignPoints(p1Points);

            for (int i = 8; i < 14; i++) {
                p2Points += board[i];
            }
            board[0] += p2Points;
            assignPoints(p2Points);
        }

        boolean winByPoints = gameBeanPool == 0;

        if (winByPoints || player1OutofPits || player2OutofPits) {
            setWinner();
        }

        return winByPoints || player1OutofPits || player2OutofPits;
    }

    public void reset() {
        this.turn = 0;

        this.player2Score = 0;
        this.player1Score = 0;

        for (int pointer = 0; pointer < board.length; pointer++) {
            if (pointer == 0 || pointer == 7) {
                board[pointer] = 0;
            } else {
                board[pointer] = 4;
            }
        }

        winner = 0;
        gameBeanPool = 0;

        for (int pointer : board) {
            gameBeanPool += pointer;
        }

    }

    /*
     *
     *
     *
     *
     *
     *
     *   SETTERS AND GETTERS LAND
     *
     *
     *
     *
     *
     */
    
    /**
     * getBoard() returns the representation of the board as an array of ints
     *
     * @return board which is an array of ints
     */
    public int[] getBoard() {
        /*
        int[] revBoard = new int[board.length];
        int pointer = board.length - 1;
        int counter;

        for (counter = 0; pointer > 0 && counter < board.length; counter++) {
            revBoard[counter] = board[pointer];
            pointer--;

        }
         */
        return board;
    }

    /**
     * returns gameBeanPool which is the total beans left in play
     *
     * @return the total beans left in play
     */
    public int getGameBeanPool() {
        return gameBeanPool;
    }

    /**
     * gets the current turn number
     *
     * @return the current turn number
     */
    public int getTurn() {
        return turn;
    }

    /**
     * sets the score for Player 1
     */
    public void setPlayer1Score() {
        this.player1Score = board[7];
    }

    /**
     * sets the score for Player 2
     */
    public void setPlayer2Score() {
        this.player2Score = board[0];
    }

    /**
     * returns the score for Player 1
     *
     * @return the score for Player 1
     */
    public int getPlayer1Score() {
        return player1Score;
    }

    /**
     * returns the score for Player 2
     *
     * @return the score for Player 2
     */
    public int getPlayer2Score() {
        return player2Score;
    }

    /**
     * based on the turn number returns the current player
     *
     * @return the current player
     */
    public int getPlayerNum() {
        return turn % 2 == 0 ? playerNum[0] : playerNum[1];
    }

    /**
     * determines who won game and sets winner accordingly
     */
    public void setWinner() {

        if (player1Score > player2Score) {
            winner = playerNum[0];
            winMsg = "Player1 Wins!";
        } else if (player1Score > player2Score) {
            winner = playerNum[1];
            winMsg = "Player2 Wins!";
        } else if (player1Score == player2Score) {
            winner = 3;
            winMsg = "It's a tie!";
        }
    }

    /**
     * getWinner returns who won the game
     *
     * @return the winner of the game
     */
    public int getWinner() {
        return winner;
    }

    public String getWinMsg() {
        return winMsg;
    }

}
