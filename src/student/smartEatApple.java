package student;

/*
This Bot  "MyBot2" is an extension of "MyBot". It does everything that "MyBot" does, and additionally it uses the same
structures of "MyBot" to calculate the possible moves of the opponent Snake. The possible new positions of the opponent
are stored in a HashSet "possiblePositionsOpponent". Its elements are filtered from "validMoves" Array of our snake in the structure
that was already used in "MyBot". The result is a simple "avoidance behavior" of our snake. A collision becomes a lot less
likely.

 */

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

import java.util.Arrays;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class smartEatApple implements Bot {
    private static final Direction[] DIRECTIONS = new Direction[]{Direction.RIGHT,
            Direction.DOWN, Direction.UP, Direction.LEFT};
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







        Coordinate afterHeadNotFinal = null;
        if(snake.body.size()>=2){
            Iterator<Coordinate> it = snake.body.iterator();
            it.next();
            afterHeadNotFinal = it.next();
        }

        final Coordinate afterHead = afterHeadNotFinal;

        Direction[] validMoves = Arrays.stream(DIRECTIONS)
                .filter(d -> !head.moveTo(d).equals(afterHead))
                .sorted()
                .toArray(Direction[]::new);



        Direction[] notLosing = Arrays.stream(validMoves)
                .filter(d -> head.moveTo(d).inBounds(mazeSize))
                .filter(d -> !opponent.elements.contains(head.moveTo(d)))
                .filter(d -> !snake.elements.contains(head.moveTo(d)))
                .filter(d -> !possiblePositionsOpponent.contains(head.moveTo(d)))
                .sorted()
                .toArray(Direction[]::new);

        // sort array with starting with smallest distance to apple
        double length = 0.0;


        if (notLosing.length > 0){
            return notLosing[0];
        }
        else{
            return validMoves[0];
        }

    }
}
