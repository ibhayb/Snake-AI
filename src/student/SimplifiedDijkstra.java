package student;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

import java.util.*;

public class SimplifiedDjikstra implements Bot {


    private static final Direction[] DIRECTIONS = new Direction[] { Direction.RIGHT,
            Direction.DOWN, Direction.UP, Direction.LEFT};
    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple){
        Coordinate head = snake.getHead();
        Coordinate oppHead = opponent.getHead();
        Coordinate afterHead = null;

        if(snake.body.size() >= 2) {
            Iterator<Coordinate> it = snake.body.iterator();
            it.next(); // first element
            afterHead = it.next(); // second element
        }

        final Coordinate afterHeadPos = afterHead;
        // avoids going backwards
        Direction[] validMoves = Arrays.stream(DIRECTIONS)
                .filter(d -> !head.moveTo(d).equals(afterHeadPos))
                .sorted()
                .toArray(Direction[]::new);

        // avoids hitting the mazebounds, opponent body, and yourself
        Direction[] notLosing = Arrays.stream(validMoves)
                .filter(d -> head.moveTo(d).inBounds(mazeSize))
                .filter(d -> !opponent.elements.contains(head.moveTo(d)))
                .filter(d -> !snake.elements.contains(head.moveTo(d)))
                .sorted()
                .toArray(Direction[]::new);


        // calculate the distance to other snake
        double distanceToOtherSnake = 0;
        distanceToOtherSnake =  Math.sqrt(Math.pow((head.x - oppHead.x),2)+Math.pow((head.y-oppHead.y),2));
        System.out.println("Distance to other snake: " + distanceToOtherSnake);

        ArrayList<Double> distancesToApple = new ArrayList<Double>();
        ArrayList<Double> distancesToOpp = new ArrayList<Double>();
        // mapping each direction of notLosing with the distance to the apple
        for (Direction dir: notLosing) {
                //calculate distances and put it in the arraylist
                int newHeadCoordinateX = snake.getHead().x + dir.dx;
                int newHeadCoordinateY = snake.getHead().y + dir.dy;
                double x = apple.x - newHeadCoordinateX;
                double y = apple.y - newHeadCoordinateY;
                double distance =Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
                distancesToApple.add(distance);
                x = oppHead.x - newHeadCoordinateX;
                y = oppHead.y- newHeadCoordinateY;
                distance = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
                distancesToOpp.add((distance));
                // test prints
                //   System.out.println("Apple: x = " + apple.x + " y = " + apple.y);
                //   System.out.println("Snakehead: x = " + dir.dx + " y = " + dir.dy);
                //   System.out.println("NewSnakeHeadCoords: x = " +newHeadCoordinateX + " y = " + newHeadCoordinateY);
                //   System.out.println("Current dir: x = " + x + " y = " + y + " distance = " + distance);
        }
        double max;
        double min;
        int indexOfMinDistance = 0;
        int indexOfMaxDistanceToOpp = 0;

        min = 0;
        max = 0;
        if(!distancesToApple.isEmpty())
            min = distancesToApple.get(0);
        if(!distancesToOpp.isEmpty())
            max = distancesToOpp.get(0);


         // choose the index of smallest distance
         // choose the index of furthest distance to opponents head
        if(min != 0 && max != 0) {
            for (int i = 1; i < notLosing.length; i++) {
                if (distancesToApple.get(i) < min) {
                    min = distancesToApple.get(i);
                    indexOfMinDistance = i;
                    // System.out.println(min);
                }
                if (distancesToOpp.get(i) > max) {
                    max = distancesToOpp.get(i);
                    indexOfMaxDistanceToOpp = i;
                }
            }
        }

        if(distancesToApple.size() > 0 && distanceToOtherSnake > 2){
            System.out.println("using shortest path");
            return notLosing[indexOfMinDistance];
        } else if ( distancesToOpp.size() > 0 && distanceToOtherSnake <= 2) {
            System.out.println("dodging opponents head");
           return  notLosing[indexOfMaxDistanceToOpp];
        } else{
            System.out.println("using valid moves");
            return validMoves[0];
        }
    }


}
/*
pseudocode:
chooseDistance(snake, opponent,  apple):
    distancesToApple[]
    distancesToEnemyHead[]
    for each direction i
        distancesToApple[i] = distance(apple)
        distancesToOpponentHead[i] = distance(opponent.Head)
    if(distance(enemy) < 2)
        return max(distancesToOpponentHead[]) // dodge opponents head
    else
        return min(distancesToApple[]) // go for apple
 */


