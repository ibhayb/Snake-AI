package snakes;

public class MyBotDown implements Bot{

    @Override
    public Direction chooseDirection(final Snake snake, final Snake opponent, final Coordinate mazeSize, final Coordinate apple){
        return Direction.DOWN;
    }

}
