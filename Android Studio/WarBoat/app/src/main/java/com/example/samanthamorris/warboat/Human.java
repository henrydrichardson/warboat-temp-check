package com.example.samanthamorris.warboat;

public class Human extends Player {
    protected int id;
    public String name;
    public Grid PlayerGrid;

    protected Human(){

        PlayerGrid = new Grid();
    }

    public int Attack(Grid PlayerGrid, Grid AIGrid)
    {
        boolean needInput = true;

        while (needInput) {

            //TODO Implement listener to touch grid and return index of array (grid location) clicked

            // If the Attack points contains, the index, the move is invalid
            if (PlayerGrid.getAttackPoints().contains(/*Button Chosen*/0))
            {
                continue;
            }

            // If move is valid, store index
            PlayerGrid.setAttackPoint(/*Button Chosen*/0);
        }

        return 0;
    }

    public int getWins()
    {
        return 0;
    }

    public int getLosses()
    {
        return 0;
    }


    public int getScore()
    {

        return getWins()/getLosses();
        //return 0;
    }

    public int getCurrency()
    {
        return 0;
    }
}
