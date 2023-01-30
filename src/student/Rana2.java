package student;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

import java.util.*;

public class Rana2 implements Bot {

    static final Direction[] DIRECTIONS = new Direction[]{Direction.RIGHT,
            Direction.DOWN, Direction.UP, Direction.LEFT};

    boolean isValidMove(Snake snake,Coordinate mazeSize,Direction d,HashSet<Coordinate>opponentPos,Snake opponent){
        if(snake.getHead().moveTo(d).inBounds(mazeSize) && !snake.elements.contains(snake.getHead().moveTo(d)) &&
                !opponentPos.contains(snake.getHead().moveTo(d)) && !opponent.elements.contains(snake.getHead().moveTo(d))){
            //&& !opponent.elements.contains(snake.getHead().moveTo(d)
            return true;

        }
        return  false;

    }
    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate head = snake.getHead();
        Coordinate head2 = opponent.getHead();

        //Position of second Element of opponent
        Coordinate afterHeadNotFinal2 = null;
        if(opponent.body.size()>=2){
            Iterator<Coordinate> it = opponent.body.iterator();
            it.next();
            afterHeadNotFinal2 = it.next();
        }

        final Coordinate afterHead2 = afterHeadNotFinal2;

        Direction[] validMovesOpponent = Arrays.stream(DIRECTIONS)
                .filter(d -> !head2.moveTo(d).equals(afterHead2))
                .sorted()
                .toArray(Direction[]::new);

        Set<Coordinate> possiblePositionsOpponent = new HashSet<>();
        for(int i=0; i< validMovesOpponent.length; i++){
            possiblePositionsOpponent.add(head2.moveTo(validMovesOpponent[i]));
        }



        int disFromLeft = Integer.MAX_VALUE, disFromRight=Integer.MAX_VALUE,disFromUp=Integer.MAX_VALUE,disFromDown=Integer.MAX_VALUE;

        if (isValidMove(snake, mazeSize, Direction.UP, (HashSet<Coordinate>) possiblePositionsOpponent,opponent)) {

            Coordinate toUp = snake.getHead().moveTo(Direction.UP);
            disFromUp =(int) Math.sqrt(Math.pow(toUp.x- apple.x,2)+Math.pow(toUp.y- apple.y,2));

        }
        if (isValidMove(snake, mazeSize, Direction.LEFT,(HashSet<Coordinate>) possiblePositionsOpponent,opponent)) {

            Coordinate toLeft = snake.getHead().moveTo(Direction.LEFT);
            //disFromLeft = Math.abs((toLeft.x- apple.x)+(toLeft.y- apple.y));
            disFromLeft =(int) Math.sqrt(Math.pow(toLeft.x- apple.x,2)+Math.pow(toLeft.y- apple.y,2));

        }
        if (isValidMove(snake, mazeSize, Direction.DOWN,(HashSet<Coordinate>) possiblePositionsOpponent,opponent)) {

            Coordinate toDown = snake.getHead().moveTo(Direction.DOWN);
            disFromDown = Math.abs((toDown.x- apple.x)+(toDown.y- apple.y));
            disFromDown =(int) Math.sqrt(Math.pow(toDown.x- apple.x,2)+Math.pow(toDown.y- apple.y,2));

        }
        if (isValidMove(snake, mazeSize, Direction.RIGHT,(HashSet<Coordinate>) possiblePositionsOpponent,opponent)) {

            Coordinate toRight = snake.getHead().moveTo(Direction.RIGHT);
            //disFromRight = Math.abs((toRight.x- apple.x)+(toRight.y- apple.y));
            disFromRight =(int) Math.sqrt(Math.pow(toRight.x- apple.x,2)+Math.pow(toRight.y- apple.y,2));
        }

        int minDis = Math.min(Math.min(disFromRight,disFromDown),Math.min(disFromUp,disFromLeft));


        if(minDis==disFromRight){
            return Direction.RIGHT;
        }
        else if(minDis==disFromDown){

            return Direction.DOWN;
        }
        else if(minDis==disFromLeft){

            return  Direction.LEFT;
        }
        else if(minDis==disFromUp) {
            return Direction.UP;
        }
        else{

            Random rn = new Random();
            int pos = rn.nextInt(3);
            switch (pos){

                case 0:
                    return Direction.RIGHT;
                case 1:
                    return Direction.LEFT;
                case 2:
                    return Direction.UP;
                default:
                    return Direction.DOWN;
            }
        }

    }
}

