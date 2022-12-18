/*
This is an extension of "MyBot2". It has the same capacity to take evasive  as "MyBot2", but instead of choosing a
direction of the "NotLosing"-Array randomly, possible coordinates are calculated and the resulting distance between the
head of the snake and the apple is analyzed. A simple BubbleSort-Algorithm is used to sort the Array of Directions, so
that the snake immediately goes for the apple (if possible). Note that if the opponent does not do anything, like the
"SampleBot" there is the danger of losing because the snake grows very fast and frequently traps itself.
in the evolution of a simple Bot would be to detect a possible self entrapment.

This bot looses most of the time against simpler bots because of self entrapment!
 */


package student;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Max implements Bot {
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

        //Bubble Sort; Sorting Elements of "NotLosing"
        double distance1, distance2;
        int a1, a2, h11, h12, h21, h22, d1, d2;
        Coordinate test1, test2;
        Direction temp;
        if (notLosing.length > 0){
            a1=apple.x;
            a2=apple.y;
            for(int i=0; i< notLosing.length; i++){
                for(int a= i+1; a< notLosing.length; a++){
                    test1 = head.moveTo(notLosing[i]);
                    test2 = head.moveTo(notLosing[a]);
                    h11= test1.x;
                    h12 = test1.y;
                    d1 = (h11-a1)*(h11-a1)+(h12-a2)*(h12-a2);
                    distance1=Math.sqrt((double)d1);

                    h21= test2.x;
                    h22 = test2.y;
                    d2 = (h21-a1)*(h21-a1)+(h22-a2)*(h22-a2);
                    distance2=Math.sqrt((double)d2);

                    if(distance2<distance1){
                        temp=notLosing[i];
                        notLosing[i]=notLosing[a];
                        notLosing[a]=temp;

                    }

                }
            }
            return notLosing[0];


        }
        else{
            return validMoves[0];
        }
    }
}

