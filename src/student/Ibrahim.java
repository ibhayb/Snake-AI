package student;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

import javax.naming.directory.DirContext;
import java.lang.reflect.Array;
import java.util.*;

public class Ibrahim implements Bot {


    private static final Direction[] DIRECTIONS = new Direction[] { Direction.RIGHT,
            Direction.DOWN, Direction.UP, Direction.LEFT};
    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple){
        Coordinate head = snake.getHead();
        Coordinate oppHead = opponent.getHead();
        Coordinate afterHead = null;
        Coordinate afterHeadOpp = null;

        if(snake.body.size() >= 2) {
            Iterator<Coordinate> it = snake.body.iterator();
            it.next(); // first element
            afterHead = it.next(); // second element
        }
        if(opponent.body.size() >= 2) {
            Iterator<Coordinate> it = opponent.body.iterator();
            it.next();
            afterHeadOpp = it.next();
        }
        final Coordinate afterHeadPos = afterHead;
        final Coordinate afterHeadPosOpp = afterHeadOpp;
        // avoids going backwards
        Direction[] validMoves = Arrays.stream(DIRECTIONS)
                .filter(d -> !head.moveTo(d).equals(afterHeadPos))
                .sorted()
                .toArray(Direction[]::new);
        // get validMoves of opponent for dodging them if the oppponent gets in range of 1 pixel
        Direction[] validMovesOpp = Arrays.stream(DIRECTIONS)
                .filter(d -> !oppHead.moveTo(d).equals(afterHeadPosOpp))
                .sorted()
                .toArray(Direction[]::new);

        // avoids hitting the mazebounds, opponent body, and yourself
        Direction[] notLosing = Arrays.stream(validMoves)
                .filter(d -> head.moveTo(d).inBounds(mazeSize))
                .filter(d -> !opponent.elements.contains(head.moveTo(d)))
                .filter(d -> !snake.elements.contains(head.moveTo(d)))
                .sorted()
                .toArray(Direction[]::new);


        ArrayList<Double> distances = new ArrayList<Double>();
        // mapping each direction of notLosing with the distance to the apple
        for (Direction dir: notLosing) {
                //calculate distance and put it in the arraylist
                int newHeadCoordinateX = snake.getHead().x + dir.dx;
                int newHeadCoordinateY = snake.getHead().y + dir.dy;
                double x = apple.x - newHeadCoordinateX;
                if(x < 0)
                    x= x*(-1);
                double y = (apple.y- newHeadCoordinateY);
                if(y < 0)
                    y = y*(-1);
                double distance =Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
                // test prints
                //   System.out.println("Apple: x = " + apple.x + " y = " + apple.y);
                //   System.out.println("Snakehead: x = " + dir.dx + " y = " + dir.dy);
                //   System.out.println("NewSnakeHeadCoords: x = " +newHeadCoordinateX + " y = " + newHeadCoordinateY);
                //   System.out.println("Current dir: x = " + x + " y = " + y + " distance = " + distance);
                // now add the direction and the distance value of it
                distances.add(distance);
        }
        double min;
        int indexOfMinDistance = 0;
        int indexOfNoOpponentHead = 0;
        min = distances.get(0);
        // calculate the distance to other snake
        double distanceToOtherSnake = 0;
        distanceToOtherSnake =  Math.sqrt(Math.pow((head.x - oppHead.x),2)+Math.pow((head.y-oppHead.y),2));
         System.out.println("Distance to other snake: " + distanceToOtherSnake);
        // select the index of smallest distance
        for(int i = 1; i < notLosing.length; i++){
           if(distances.get(i) < min) {
               min = distances.get(i);
                indexOfMinDistance = i;
               // System.out.println(min);
           }
           if(distanceToOtherSnake < 2){
               for (Direction dir: notLosing) {
                    // compare the coordinates for inequality
                   for (Direction oppDir: validMovesOpp) {
                       if (head.moveTo(dir) != oppHead.moveTo(oppDir)) {
                           indexOfNoOpponentHead = i;
                       }
                   }
               }
           }
        }
        if(distances.size() > 0 && indexOfNoOpponentHead == 0){
            System.out.println("using shortest path");
            return notLosing[indexOfMinDistance];
        }
        else if(distances.size() > 0 && indexOfNoOpponentHead != 0){
            System.out.println("Dodging enemies head!");
            return notLosing[indexOfNoOpponentHead];
        }
        else{
            System.out.println("using valid moves");
            return validMoves[0];
        }
    }


}


