package snakes;

public class MyBotUp implements Bot{

    @Override
    public Direction chooseDirection(final Snake snake, final Snake opponent, final Coordinate mazeSize, final Coordinate apple){
        return Direction.UP;

    }

}
