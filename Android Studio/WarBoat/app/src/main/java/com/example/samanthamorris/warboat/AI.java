package com.example.samanthamorris.warboat;
import java.util.ArrayList;


public class AI extends Player {

      public static final int GRIDLENGTH = 8;
      ArrayList<Integer> hit = new ArrayList<>();
      ArrayList<Integer> clear = new ArrayList<>(); //checking whether the direction is clear, ie. no ship there, East=0, South=1, West=2, North=3
      boolean progress = false;
      int  mostRecentInitial;
      boolean SunkMostRecent = false;
      boolean rotation = false; // FALSE IS HORIZONTAL

    public int Attack(Grid AI, Grid Human) {

        //Assume first shot is random unless proven otherwise
        int initial = 0;
        boolean randomShot = true;

        //If we haven;t started finding direction of anything
       if (( clear.isEmpty() && hit.isEmpty() ) && !(isHit(Human.getLastAttack(), Human)) );
            randomShot = true;

        // First shot will always be random
        if(Human.getAttackPoints().isEmpty()) {
            initial = (int) (Math.random() * (GRIDLENGTH * GRIDLENGTH));
            if (isHit(initial, Human)) {
                mostRecentInitial = initial;
                recordShot(initial, Human);
                return -1;
            }
            else
                recordShot(initial, Human);
        }

        //SUnk most recent
        if (SunkMostRecent) {
            initial = (int) (Math.random() * (GRIDLENGTH * GRIDLENGTH));
            if (isHit(initial, Human)) {
                mostRecentInitial = initial;
                recordShot(initial, Human);
                return -1;
            } else
                recordShot(initial, Human);
        }

        // If Last hit was still random
        if (randomShot) {
            initial = (int) (Math.random() * (GRIDLENGTH * GRIDLENGTH));
           while(Human.getAttackPoints().contains(initial)) {
               initial = (int) (Math.random() * (GRIDLENGTH * GRIDLENGTH));
           }
            if (isHit(initial, Human)) {
                mostRecentInitial = initial;
                recordShot(initial, Human);
                return -1;
            }
            else
                recordShot(initial, Human);
        }

        if(!randomShot) {
            initial = mostRecentInitial;
            if(progress == false) {
                findDirection(initial, Human);
            }
            else {
                tryToSink(initial, Human);
            }


        }





        return 0;
    }

    public void findDirection(int initial, Grid Human) {

        //Are we closer to finding the ship?

        int fire = initial;
        // the "hit" and "clear" lists, will be used later to determine
        // which direction the ship is oriented (i.e. East=0, South=1, West=2, North=3)
        if(!hit.contains(0) || !clear.contains(0)) {
            if (!Human.getAttackPoints().contains(fire + 1))
                // begin by checking the square to the east
                // make sure that square hasn't already been hit,
                // adjust x value by 1, moving the target to the east.
                fire++;
            // checking again to be sure that haven't already shot there
            // and that the new X coordinate isnt out of bounds
            if (!Human.getAttackPoints().contains(fire) && !invalidShot(fire)) {
                // fire with the adjusted coordinates and record it into shots
                progress = isHit(fire, Human);
                recordShot(fire, Human);
            }
                // If the coordinate would have been out of bounds, or if for some reason
                // east is doesn't have more ship, then we add it to clear
            else {
                clear.add(0);
                return;
            }
            // If the shot was successful, it adds the coordinates to hits and
            // Assigns 0 or "East" to the hit list, signifying the boat points that way.
            if (progress) {
                hit.add(0);
                rotation = false;
                return;
            }
            // shot fired but missed,East will be added to the clear list.
            else {
                clear.add(0);
                return;
            }
        }

        fire = initial;
        progress = false;

        if(!hit.contains(1) || !clear.contains(1)) {
            if (!Human.getAttackPoints().contains(fire + GRIDLENGTH))
                // begin by checking the square to the east
                // make sure that square hasn't already been hit,
                // adjust x value by 1, moving the target to the east.
                fire += GRIDLENGTH;
            // checking again to be sure that haven't already shot there
            // and that the new X coordinate isnt out of bounds
            if (!Human.getAttackPoints().contains(fire) && !invalidShot(fire)) {
                // fire with the adjusted coordinates and record it into shots
                progress = isHit(fire, Human);
                recordShot(fire, Human);
            }
            // If the coordinate would have been out of bounds, or if for some reason
            // east is doesn't have more ship, then we add it to clear
            else {
                clear.add(1);
                return;
            }
            // If the shot was successful, it adds the coordinates to hits and
            // Assigns 1 or "South" to the hit list, signifying the boat points that way.
            if (progress) {
                hit.add(1);
                rotation = true;
                return;
            }
            // shot fired but missed,South will be added to the clear list.
            else {
                clear.add(1);
                return;
            }
        }

        fire = initial;
        progress = false;
        // the "hit" and "clear" lists, will be used later to determine
        // which direction the ship is oriented (i.e. East=0, South=1, West=2, North=3)
        if(!hit.contains(2) || !clear.contains(2)) {
            if (!Human.getAttackPoints().contains(fire - 1))
                // begin by checking the square to the east
                // make sure that square hasn't already been hit,
                // adjust x value by 1, moving the target to the east.
                fire--;
            // checking again to be sure that haven't already shot there
            // and that the new X coordinate isnt out of bounds
            if (!Human.getAttackPoints().contains(fire) && !invalidShot(fire)) {
                // fire with the adjusted coordinates and record it into shots
                progress = isHit(fire, Human);
                recordShot(fire, Human);
            }
            // If the coordinate would have been out of bounds, or if for some reason
            // east is doesn't have more ship, then we add it to clear
            else {
                clear.add(2);
                return;
            }
            // If the shot was successful, it adds the coordinates to hits and
            // Assigns 12or "WEst" to the hit list, signifying the boat points that way.
            if (progress) {
                hit.add(2);
                rotation = false;
                return;
            }
            // shot fired but missed,East will be added to the clear list.
            else {
                clear.add(2);
                return;
            }
        }

        fire = initial;
        // the "hit" and "clear" lists, will be used later to determine
        // which direction the ship is oriented (i.e. East=0, South=1, West=2, North=3)
        if(!hit.contains(3) || !clear.contains(3)) {
            if (!Human.getAttackPoints().contains(fire - GRIDLENGTH))
                // begin by checking the square to the east
                // make sure that square hasn't already been hit,
                // adjust x value by 1, moving the target to the east.
                fire -= GRIDLENGTH;
            // checking again to be sure that haven't already shot there
            // and that the new X coordinate isnt out of bounds
            if (!Human.getAttackPoints().contains(fire) && !invalidShot(fire)) {
                // fire with the adjusted coordinates and record it into shots
                progress = isHit(fire, Human);
                recordShot(fire, Human);
            }
            // If the coordinate would have been out of bounds, or if for some reason
            // east is doesn't have more ship, then we add it to clear
            else {
                clear.add(3);
                return;
            }
            // If the shot was successful, it adds the coordinates to hits and
            // Assigns 3 or "North" to the hit list, signifying the boat points that way.
            if (progress) {
                hit.add(3);
                rotation = true;
                return;
            }
            // shot fired but missed,East will be added to the clear list.
            else {
                clear.add(3);
                return;
            }
        }

    }

    public void tryToSink(int initial, Grid Human) {
        // Now since we know where we going....
        int fire = initial;
        //boolean localProgress = false;

        // This will run if the East is a hit.
        if (hit.contains(0)) {
            // use this to shoot down the row until progress is false,
            // then add the east side to the clear list.
            if(!invalidShot(fire) && !Human.getAttackPoints().contains(fire) && !clear.contains(0)) {
                fire++;
                // If we havent shot here let's check whether we keep going
                if (!Human.getAttackPoints().contains(fire)) {
                    progress = isHit(fire, Human);
                    recordShot(fire, Human);
                    SunkMostRecent = didShotSink(fire, Human);
                    if (SunkMostRecent) {
                        clear.removeAll(clear);
                        hit.removeAll(hit);
                    }
                    // If progress is false, East is added to the clear list.
                    if (!progress) {
                        clear.add(0);
                        if(rotation == false)
                            hit.add(2);
                    }
                }
            }
        }

        if (hit.contains(1)) {
            // use this to shoot down the row until progress is false,
            // then add the east side to the clear list.
            if(!invalidShot(fire) && !Human.getAttackPoints().contains(fire) && !clear.contains(0)) {
                fire++;
                // If we havent shot here let's check whether we keep going
                if (!Human.getAttackPoints().contains(fire)) {
                    progress = isHit(fire, Human);
                    recordShot(fire, Human);
                    SunkMostRecent = didShotSink(fire, Human);
                    if (SunkMostRecent) {
                        clear.removeAll(clear);
                        hit.removeAll(hit);
                    }
                    // If progress is false, East is added to the clear list.
                    if (!progress) {
                        clear.add(1);
                        if(rotation == true)
                            hit.add(3);
                    }
                }
            }
        }

        if (hit.contains(2)) {
            // use this to shoot down the row until progress is false,
            // then add the east side to the clear list.
            if(!invalidShot(fire) && !Human.getAttackPoints().contains(fire) && !clear.contains(0)) {
                fire++;
                // If we havent shot here let's check whether we keep going
                if (!Human.getAttackPoints().contains(fire)) {
                    progress = isHit(fire, Human);
                    recordShot(fire, Human);
                    SunkMostRecent = didShotSink(fire, Human);
                    if (SunkMostRecent) {
                        clear.removeAll(clear);
                        hit.removeAll(hit);
                    }
                    // If progress is false, East is added to the clear list.
                    if (!progress) {
                        clear.add(2);
                        if(rotation == true)
                            hit.add(3);
                    }
                }
            }
        }

        if (hit.contains(3)) {
            // use this to shoot down the row until progress is false,
            // then add the east side to the clear list.
            if(!invalidShot(fire) && !Human.getAttackPoints().contains(fire) && !clear.contains(0)) {
                fire++;
                // If we havent shot here let's check whether we keep going
                if (!Human.getAttackPoints().contains(fire)) {
                    progress = isHit(fire, Human);
                    recordShot(fire, Human);
                    SunkMostRecent = didShotSink(fire, Human);
                    if (SunkMostRecent) {
                        clear.removeAll(clear);
                        hit.removeAll(hit);
                    }
                    // If progress is false, East is added to the clear list.
                    if (!progress) {
                        clear.add(3);
                        if(rotation == true)
                            hit.add(3);
                    }
                }
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
        boolean invalid = false;
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


}
