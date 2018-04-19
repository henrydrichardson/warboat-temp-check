package com.example.samanthamorris.warboat;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Random;

public class AI extends Player {

    public static final int GRIDLENGTH = 8;

    Deque<Integer> stack = new ArrayDeque<Integer>();



    public int Attack(Grid AiGrid, Grid human) {

        if (stack.isEmpty()) {
            Hunt(human);
            return 1;
        }
        else {
            Target(human);
        }
        return 0;
    }

    public void Hunt(Grid human) {
        int initial = generateEvenParity();
        while(human.getAttackPoints().contains(initial)) {
            initial = generateEvenParity();
        }

        recordShot(initial, human);
        for(int i = 0; i < 5; i++)
            human.setSunk(i);


        if(isHit(initial, human)) {

            int north = calcNorth(initial);
            int west = calcWest(initial);
            int south = calcSouth(initial);
            int east = calcEast(initial);


            if( ( !invalidShot(north) && !human.getAttackPoints().contains(north) )   ) {
                stack.push(north);
            }
            if( ( !invalidShot(west) && !human.getAttackPoints().contains(west) )   ) {
                stack.push(west);
            }
            if( ( !invalidShot(south) && !human.getAttackPoints().contains(south) )   ) {
                stack.push(south);
            }
            if( ( !invalidShot(east) && !human.getAttackPoints().contains(east) )   ) {
                stack.push(east);

            }
        }
    }

    public void Target(Grid human) {
         int initial = stack.pop();

         recordShot(initial, human);
        for(int i = 0; i < 5; i++)
            human.setSunk(i);


        if(isHit(initial, human)) {

                if(didShotSink(initial, human)) {
                    while(!stack.isEmpty()) {
                        stack.pop();
                    }
                    return;
                }

                int north = calcNorth(initial);
                int west = calcWest(initial);
                int south = calcSouth(initial);
                int east = calcEast(initial);

                if( ( !invalidShot(north) && !human.getAttackPoints().contains(north) )   ) {
                    stack.push(north);
                }
                if( ( !invalidShot(west) && !human.getAttackPoints().contains(west) )   ) {
                    stack.push(west);
                }
                if( ( !invalidShot(south) && !human.getAttackPoints().contains(south) )   ) {
                    stack.push(south);
                }
                if( ( !invalidShot(east) && !human.getAttackPoints().contains(east) )   ) {
                    stack.push(east);
                }

        }
    }

    public boolean isHit(int attackLocation, Grid HumanGrid) {

        if (HumanGrid.getSHIP_POINTS().contains(attackLocation))
            return true;
        else
            return false;
    }

    public void recordShot(int attackLocation, Grid HumanGrid) {

        if(HumanGrid.getSHIP_POINTS().contains(attackLocation))
        {
            HumanGrid.getAttackPoints().add(attackLocation);
            HumanGrid.getHitPoints().add(attackLocation);

        }
        else {
            HumanGrid.getAttackPoints().add(attackLocation);
            HumanGrid.getMissPoints().add(attackLocation);

        }
    }



    public boolean invalidShot(int attackLocation)
    {
        int row = attackLocation % GRIDLENGTH;
        if(row < 0 || row >= GRIDLENGTH)
            return true;
        if(attackLocation < 0 || attackLocation >= (GRIDLENGTH * GRIDLENGTH))
            return true;

        return false;
    }

    public boolean didShotSink(int attackLocation, Grid Human)
    {


        if(Human.getSunkPoints().contains(attackLocation))
            return true;
        else
            return false;
    }

    public int calcNorth(int attackLocation) {
        return attackLocation - GRIDLENGTH;

    }

    public int calcEast(int attackLocation) {
        return attackLocation + 1;
    }

    public int calcSouth(int attackLocation) {
        return attackLocation + GRIDLENGTH;
    }

    public int calcWest(int attackLocation) {
        return attackLocation - 1;
    }

    public int generateEvenNumber(int min, int max) {
        Random rand = new Random();
        min = min % 2 == 1 ? min + 1 : min; // If min is odd, add one to make sure the integer division can´t create a number smaller min;
        max = max % 2 == 1 ? max - 1 : max; // If max is odd, subtract one to make sure the integer division can´t create a number greater max;
        int randomNum = ((rand.nextInt((max - min)) + min) + 1) / 2; // Divide both by 2 to ensure the range
        return randomNum * 2; // multiply by 2 to make the number even
    }

    public int generateEvenParity() {
       int[] array = {1, 3, 5, 7,
                        8, 10, 12, 14,
                    17, 19, 21, 23,
                    24, 26, 28, 30,
                    33, 35, 37, 39,
                    40, 42, 44, 46,
                    49, 51, 53, 55,
                    56, 58, 60,  63};
        int random = (int)(Math.random() * (32) );

        return array[random];
    }



}