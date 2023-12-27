 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjonestictactoe;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.control.Label;

/**
 *
 * @author Jacob Jones
 * Date Completed - 9/15/2019
 * 
 */
public class JJonesTicTacToe extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        //Initializes the constant variables for the project
        final String player1Letter = "X";
        final String player2Letter = "O"; //By default this is the computer
        final int minWidth = 250;
        final int minHeight = 250;
        final int boardWidth = 3;
        final int boardHeight = 3;
        
        Random random = new Random();
        //Initializes the flags
        boolean[] isPlayer1Turn = {random.nextBoolean()}; //Randomizes the turn order
        boolean[] isTwoPlayerGame = {false}; //Gets around the anonymous classes not wanting to set regular variables
        
        //Initializes up the board
        String[][] board = new String[boardWidth][boardHeight];
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                board[r][c] = "#";
            }
        }
        
        //Arranges the layout
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        
        //Initializes the button board
        GridPane buttonBoard = new GridPane();
        buttonBoard.setAlignment(Pos.CENTER);
        layout.add(buttonBoard, 0, 0);
        
        //Arranges the scene
        Scene scene = new Scene(layout, minWidth, minHeight);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Initializes the label that outputs which player won
        Label winText = new Label("");
        layout.add(winText, 0, 1);
        
        //Creates the board of buttons
        for(int r = 0; r < boardWidth; r++){
            for(int c = 0; c < boardHeight; c++){
                
                //Initializes the button, with the number being determined by which row the button is on
                Button marker = new Button(1 + c + r*boardWidth + "");
                marker.setDisable(true);
                buttonBoard.add(marker, c, r);
                marker.setOnAction((ActionEvent event) -> {
                    
                    //Obtains the number from the button text, subtracting it to fit within bounds
                    int buttonNum = Integer.parseInt(marker.getText()) - 1; 
                    if(isTwoPlayerGame[0]){
                        if(isPlayer1Turn[0]){
                            //Changes the turn
                            isPlayer1Turn[0] = !isPlayer1Turn[0]; //Makes it player 2's turn
                            winText.setText("Player 2's Turn");
                            
                            //Sets the position the button is related to with player1Letter and configures the button
                            setPosition(board, buttonNum, player1Letter);
                            marker.setText(player1Letter);
                            marker.setDisable(true);
                            updateBoard(buttonBoard, board, player1Letter, player2Letter, isTwoPlayerGame[0], isPlayer1Turn[0]);
                            printBoard(board);
                            
                        } else {
                            //Changes the turn
                            isPlayer1Turn[0] = !isPlayer1Turn[0]; //Makes it player 1's turn
                            winText.setText("Player 1's Turn");
                            
                            //Sets the position the button is related to with player1Letter and configures the button
                            setPosition(board, buttonNum, player2Letter);
                            marker.setText(player2Letter);
                            marker.setDisable(true);
                            updateBoard(buttonBoard, board, player1Letter, player2Letter, isTwoPlayerGame[0], isPlayer1Turn[0]);
                            printBoard(board);
                            
                        }
                    } else{
                        //Updates the button board
                        setPosition(board, buttonNum, player1Letter);
                        marker.setText(player1Letter);
                        marker.setDisable(true);
                        //Updates and prints the board
                        updateBoard(buttonBoard, board, player1Letter, player2Letter, false, true);
                        printBoard(board);
                    }
                });
            }
        }
        
        
        //Initializes the button that begins a new game
        Button newGame = new Button("New Game");
        layout.add(newGame, 0, 2);
        newGame.setOnAction((ActionEvent event) -> {
            //Disables isTwoPlayerGame if it was enabled
            isTwoPlayerGame[0] = false;
            ObservableList<Node> buttons = buttonBoard.getChildren(); //The list of buttons
            winText.setText("");
            
            //Resets the button board
            for(int r = 0; r < boardWidth; r++){
                for(int c = 0; c < boardHeight; c++){
                    Button button = (Button)buttons.get(c + r*boardWidth);
                    button.setText(1 + c + r*boardWidth + "");
                    button.setDisable(false);
                    board[r][c] = "#";
                }
            }
        });
        
        //Initializes the button that begins a new game
        Button twoPlayerButton = new Button("Two Player Game");
        layout.add(twoPlayerButton, 0, 3);
        twoPlayerButton.setOnAction((ActionEvent event) -> {
            //Enables isTwoPlayerGame if it was disabled
            isTwoPlayerGame[0] = true;
            isPlayer1Turn[0] = random.nextBoolean(); //Randomizes the turn order
            ObservableList<Node> buttons = buttonBoard.getChildren(); //The list of buttons
            
            //Displays whose turn it is
            if(isPlayer1Turn[0]){
                winText.setText("Player 1's Turn");
            } else {
                winText.setText("Player 2's Turn");
            }
            
            //Resets the button board
            for(int r = 0; r < boardWidth; r++){
                for(int c = 0; c < boardHeight; c++){
                    Button button = (Button)buttons.get(c + r*boardWidth);
                    button.setText(1 + c + r*boardWidth + "");
                    button.setDisable(false);
                    board[r][c] = "#";
                }
            }
        });
    }
    
    /**
    *  This method will find the desired element from the matrix given it's list position
    * 
    *  @param board - The matrix
    *  @param listPosition - The list position
    *  @param letter - The letter that is being put into the board
    * 
    */
    public void setPosition(String[][] board, int listPosition, String letter){
        //Initializes the row and column variables
        int row = 0;
        int col = listPosition%board.length;
        
        //For each time the listPosition gets subtracted and doesn't equal 0, we know that it's at least One row up
        while(listPosition > board.length-1){
            listPosition-= board[0].length;
            row++;
        }
        board[row][col] = letter;
    }
    
    /**
    * 
    *   This method prints out the board
    * 
    *   @param board - The board the method is going to print
    * 
    */
    public void printBoard(String[][] board){
        //Prints out the board for debugging
        for (String[] row : board) {
            for (String col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
        System.out.println();
        //Plays the computer's turn
        
    }
    
    /**
    * 
    *   This method will perform the computer's turn, then update both board
    *   Once it is done, it returns an updated board
    * 
    *   @param pane - The button board
    *   @param board - The regular board
    *   @param player1 - The first player's letter
    *   @param player2 - The second player's letter
    *   @param twoPlayer - Will be true if it's a two player game
    *   @param player - Which player it's turn it is. Player 1 = True, Player 2 = False
    * 
    */
    public void updateBoard(GridPane pane, String[][] board, String player1, String player2, boolean twoPlayer, boolean player){
        //Initializes the regular variables
        int randNum = 0; //The random position on the board
        boolean isBoardFilled = false; //Tracks how many tiles are filled on the board
        boolean isPlayer1Winner = checkBoard(board, player1); //Tracks if X has won yet
        boolean isPlayer2Winner = false; //Tracks if O has won yet
        
        //Initializes the special variables
        Random random = new Random(); //The random class
        ObservableList<Node> buttons = pane.getChildren(); //The list of buttons
        Label winText = ((Label)((GridPane)pane.getParent()).getChildren().get(1));
        
        //Determines if the board is filled
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[r].length; c++){
                isBoardFilled = !board[r][c].equals("#");
                if(!isBoardFilled){ //Ends the loops if there is a single #
                    c = board[r].length - 1;
                    r = board.length - 1;
                }
            }
        }
        
        System.out.println("Is the board filled?: " + isBoardFilled);
        
        //Randomizes the number and checks if there are any free spaces
        if(twoPlayer){
            isPlayer2Winner = checkBoard(board, player2);
        }else {
            //Produces a random spot for the computer to fill
            if(!isPlayer1Winner && !isBoardFilled){
                do{
                    randNum = random.nextInt(buttons.size());
                }while(((Button)buttons.get(randNum)).isDisable());
            }

            //Executes if the board is not filled up
            if(!isBoardFilled && !isPlayer1Winner){
                ((Button)buttons.get(randNum)).setText(player2);
                ((Button)buttons.get(randNum)).setDisable(true);
                setPosition(board, randNum, player2);
                isPlayer2Winner = checkBoard(board, player2);
            }
        }
        
        //Checks who (if at all) won
        if(isPlayer1Winner){
            //Gives a different output depending on the type of game
            if(twoPlayer){
                winText.setText("Player 1 won!");
            } else {
                winText.setText("You win!");
            }
            //Disables all of the buttons after the game ends
            for(Node button : buttons){
                ((Button)button).setDisable(true);
            }
        } else if (isPlayer2Winner){
            //Gives a different output depending on the type of game
            if(twoPlayer){
                winText.setText("Player 2 won!");
            } else {
                winText.setText("You lose :( ");
            }
            //Disables all of the buttons after the game ends
            for(Node button : buttons){
                ((Button)button).setDisable(true);
            }
        } else if(isBoardFilled){
            winText.setText("It's a tie!");
        }
    }
    
    /**
    * 
    * This method will check the board for any winners either horizontally, vertically, or diagonally
    * 
    * @param board - The board to be checked
    * @param letter - The letter to check
    * @return Either the horizontal flag, vertical flag, or diagonal flag
    * 
    */
    public boolean checkBoard(String[][] board, String letter){
        //Initializes the flags
        boolean isHorizontal = false;
        boolean isVertical = false;
        boolean isDiagonal = false;
        
        //Checks the layout of the board to see if someone won
        for (int r = 0; r < board.length; r++){
            //Checks all of the rows if they're perfectly matched
            if(!isHorizontal){
                for(int c = 0; c < board[r].length; c++){
                    isHorizontal = board[r][c].equals(letter);
                    if(!isHorizontal){ //Ends the loop if the line breaks
                        c = board[r].length;
                    }
                }
            }
            //Checks all of the columns if they're perfectly matched
            if(!isVertical){
                for(int c = 0; c < board[r].length; c++){
                    isVertical = board[c][r].equals(letter);
                    if(!isVertical){ //Ends the loop if the line breaks
                        c = board[r].length;
                    }
                }
            }
            //Checks the first diagonal if they're prefectly matched
            if(!isDiagonal){
                for(int c = 0; c < board[r].length; c++){
                    isDiagonal = board[c][c].equals(letter);
                    if(!isDiagonal){
                        c = board[r].length;
                    }
                }
            }
            //Checks the other diagonal if they're perfectly matched
            //Done again to not interfere with the other diagonal check
            if(!isDiagonal){
                for(int c = 0; c < board[r].length; c++){
                    isDiagonal = board[board.length - 1 - c][c].equals(letter);
                    if(!isDiagonal){ //Ends the loop if the line breaks
                        c = board[r].length;
                    }
                }
            }
        }
        
        //Prints out the flags for debugging
        System.out.println("Horizontal: " + isHorizontal);
        System.out.println("Vertical: " + isVertical);
        System.out.println("Diagonal: " + isDiagonal);
        System.out.println();
        
        return (isHorizontal || isVertical || isDiagonal);
    }
    
}
