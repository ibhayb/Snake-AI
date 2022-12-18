package student;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;
//import sun.font.CoreMetrics;

import java.util.Random;

public class Rana implements Bot {

    boolean isValidMove(Snake snake,Coordinate mazeSize,Direction d,Snake opponent){
        if(snake.getHead().moveTo(d).inBounds(mazeSize) && !snake.elements.contains(snake.getHead().moveTo(d)) && !opponent.elements.contains(snake.getHead().moveTo(d))){

            return true;
        }
        return  false;

    }
    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {


        int disFromLeft = Integer.MAX_VALUE, disFromRight=Integer.MAX_VALUE,disFromUp=Integer.MAX_VALUE,disFromDown=Integer.MAX_VALUE;

        if (isValidMove(snake, mazeSize, Direction.UP,opponent)) {

            Coordinate toUp = snake.getHead().moveTo(Direction.UP);
            disFromUp = Math.abs((toUp.x- apple.x)+(toUp.y- apple.y));

        }
        if (isValidMove(snake, mazeSize, Direction.LEFT,opponent)) {

            Coordinate toLeft = snake.getHead().moveTo(Direction.LEFT);
            disFromLeft = Math.abs((toLeft.x- apple.x)+(toLeft.y- apple.y));

        }
        if (isValidMove(snake, mazeSize, Direction.DOWN,opponent)) {

            Coordinate toDown = snake.getHead().moveTo(Direction.DOWN);
            disFromDown = Math.abs((toDown.x- apple.x)+(toDown.y- apple.y));

        }
        if (isValidMove(snake, mazeSize, Direction.RIGHT,opponent)) {

            Coordinate toRight = snake.getHead().moveTo(Direction.RIGHT);
            disFromRight = Math.abs((toRight.x- apple.x)+(toRight.y- apple.y));

        }

        int minDis = Math.min(Math.min(disFromRight,disFromDown),Math.min(disFromUp,disFromLeft));

        if(snake.elements.contains(opponent.getHead())){

            System.out.println("colision");
        }

        if(minDis==disFromRight){
            return Direction.RIGHT;
        }
        else if(minDis==disFromDown){

            return Direction.DOWN;
        }
        else if(minDis==disFromLeft){

            return  Direction.LEFT;
        }
        return  Direction.UP;

    }
}
