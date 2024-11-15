import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650; //Allocate 50px for the text panel on top


    JFrame frame = new JFrame("Tic-Tac-Toe"); //Title for our window
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3]; //Use 2d array to keep track of buttons, and keep track of placement of the buttons, and to check if there is a winner
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX; //Which text to use to whichever player is up

    boolean gameOver = false;
    int turns = 0; //If number of turns equals 9, all 9 turns have passed can't play game anymore

    TicTacToe() {
        // Set Look-and-Feel to Metal to ensure custom colors are applied consistently
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); //Opens up our window at the center of our screen
        frame.setResizable(false); //Can't resize window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //When clicking X program terminates
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray); //Background to gray
        textLabel.setForeground(Color.white); //Font color to white
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER); //Makes the text centered, instead of starting on the left
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel); //Taking the label, adding it to our panel
        frame.add(textPanel, BorderLayout.NORTH); //Takes the panel and adds it to the frame, BorderLayout pushes it upwards

        boardPanel.setLayout(new GridLayout(3, 3)); //Creates 3 by 3 grid layout
        boardPanel.setBackground(Color.darkGray); //Color of boardPanel
        frame.add(boardPanel); //Adds the board panel to the frame

        for (int r = 0; r < 3; r++) { //Rows
            for (int c = 0; c < 3; c++) { //Columns
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.setContentAreaFilled(false);
                tile.setOpaque(true);
                //tile.setText(currentPlayer);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return; //If the game is over, we don't want to update the tiles,

                        JButton tile = (JButton) e.getSource(); //Case to be JButton since we know the source can only come from a button

                        //If text is empty string, set the text to the player
                        if(tile.getText() == "") {
                            tile.setText(currentPlayer); 
                            turns++;
                            checkWinner(); //Checks if there is a winner

                            //Means if still false, then enter
                            if (!gameOver) {
                                currentPlayer = currentPlayer == playerX ? playerO : playerX; //If currentPlayer is playerX, make it playerO, else make it playerX
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }                       
                    }
                });
            }
        }
    }
    void checkWinner() {
        //note the array swaps in the horizontal and vertical check
        //horizontal win, check each horizontal array
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") {
                continue;
            }
            if (board[r][0].getText() == board[r][1].getText() &&
                board[r][1].getText() == board[r][2].getText()) { //Checks if all tiles are equal
                    for (int i = 0; i < 3; i++) {
                        setWinner(board[r][i]); //Call setWinner on the row and the column
                    }
                    gameOver = true;
                    return;
            }
        }

        //vertical win, check each vertical array
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") { //Checks if the first tile in the column is empty, if it is no winner
                continue;
            }
            if (board[0][c].getText() == board[1][c].getText() &&
                board[1][c].getText() == board[2][c].getText()) { //Checks if all tiles are equal
                    for (int i = 0; i < 3; i++) {
                        setWinner(board[i][c]); //Call setWinner on the row and the column
                    }
                    gameOver = true;
                    return;
            }
        }

        //diagonal win
        if (board[0][0].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][2].getText() &&
            board[0][0].getText() != "") {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][i]);
                }
                gameOver = true;
                return;
        }
        if (board[0][2].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][0].getText() &&
            board[0][2].getText() != "") {
                setWinner(board[0][2]);
                setWinner(board[1][1]);
                setWinner(board[2][0]);
                gameOver = true;
                return;
        }

        //tie???
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
            return;
        }
    }

    //For the winner
    void setWinner(JButton tile) { 
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }
}


