package student;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

public class dumbEatApple implements Bot {
    private static final Direction DIRECTIONS[] = new Direction[]{ Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN};
    /*
    This bot just  goes left, right, down or up while checking in which direction the apple is.
    It does not matter about dying of the following cases:
        - hitting itself
        - hitting the enemy
        - hitting the mazeborder
     */
    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate head = snake.getHead();
        return directionOfApple(head, apple);
    }

    public Direction directionOfApple(Coordinate head, Coordinate apple){
        if( apple.x < head.x)
            return  Direction.LEFT;
        if ( apple.y < head.y)
            return Direction.DOWN;
        if (apple.y > head.y)
            return Direction.UP;
        if (apple.x > head.x)
            return Direction.RIGHT;

        return null;
    }
}
