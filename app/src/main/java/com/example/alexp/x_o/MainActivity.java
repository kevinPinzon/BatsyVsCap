package com.example.alexp.x_o;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private String ticTacToe[][] = new String[3][3];
    private Boolean turn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout grid = (GridLayout) findViewById(R.id.button_container);
        if (grid != null) {
            int row = 0;
            int col = 0;
            for (int i = 0; i < grid.getChildCount(); i++) {

                //This is used to set column and row number
                if (i != 0 && i % 3 == 0) {
                    row++;
                    col = 0;
                }

                ImageButton currentButton = (ImageButton) grid.getChildAt(i);

                final int finalRow = row;
                final int finalCol = col;
                currentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ImageButton clickedButton = ((ImageButton) v);


                        if (clickedButton.getDrawable() == null) {
                            String player = getNextPLayer();
                            if (player=="X")
                                clickedButton.setImageDrawable(getResources().getDrawable(R.drawable.bats2));
                            else
                                clickedButton.setImageDrawable(getResources().getDrawable(R.drawable.cap2));
                            ticTacToe[finalRow][finalCol] = player;

                            String winner = checkSolved();
                            if (winner != null) {
                                if (winner == "X")
                                    winner="Batsy";
                                else
                                    winner="Cap";

                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Winner")

                                        .setMessage(winner)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create().show();
                            }
                        }

                    }
                });

                col++;
            }
        }

        Button restart = (Button) findViewById(R.id.btn_reiniciar);
        if (restart != null) {
            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.ticTacToe = new String[3][3];
                    GridLayout grid = (GridLayout) findViewById(R.id.button_container);
                    for (int i = 0; i < grid.getChildCount(); i++) {
                        ((ImageButton) grid.getChildAt(i)).setImageDrawable(null);
                    }
                }
            });
        }

    }


    private String getNextPLayer() {
        String nextPlayer = turn ? "X" : "O";
        turn = !turn;
        return nextPlayer;
    }


    //Solving
    private String checkRow(int row) {
        if (ticTacToe[row][0] == null) {
            return null;
        }

        if (ticTacToe[row][0] == ticTacToe[row][1] &&
                ticTacToe[row][1] == ticTacToe[row][2]) {
            return ticTacToe[row][0];
        }

        return null;
    }

    private String checkColumn(int column) {
        if (ticTacToe[0][column] == null) {
            return null;
        }
        if (ticTacToe[0][column] == ticTacToe[1][column] &&
                ticTacToe[1][column] == ticTacToe[2][column]) {
            return ticTacToe[0][column];
        }

        return null;
    }

    private String checkSolved() {
        for (int i = 0; i < ticTacToe.length; i++) {
            String winRow = checkRow(i);
            String winCol = checkColumn(i);

            if (winRow != null) {
                return winRow;
            } else if (winCol != null) {
                return winCol;
            }
        }


        //Need to check diagonals

        //Top left to bottom right
        if (ticTacToe[0][0] != null) {
            if (ticTacToe[0][0] == ticTacToe[1][1] &&
                    ticTacToe[1][1] == ticTacToe[2][2]) {
                return ticTacToe[0][0];
            }
        }

        //Top right to bottom left
        else if (ticTacToe[0][2] != null) {
            if (ticTacToe[0][2] == ticTacToe[1][1] &&
                    ticTacToe[1][1] == ticTacToe[2][0]) {
                return ticTacToe[0][2];
            }
        }

        return null;

    }


}
