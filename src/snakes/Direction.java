package snakes;

/**
 * This enum class implements a direction vector
 * that shows staring coordinate and direction of movement
 * Size of step for movement is restricted to adjacent cells
 */
public enum Direction {

    UP(0, 1),
    DOWN(0, -1),
    RIGHT(1, 0),
    LEFT(-1, 0);

    // x and y coordinate
    public final int dx, dy;
    // vector of movement
    public final Coordinate v;
    /**
     * Construct the direction of movement
     *
     * @param dx row coordinate
     * @param dy column coordinate
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        this.v = new Coordinate(dx, dy);
    }

    public String directionToWord(){
        // due to implementation of organizers
        // we are printing here instead of UP, DOWN
        // and vice versa because looking on to the display
        // when printing out DOWN the snake was going up
        // do not get irritated because in enum its other way around
        if(dx == 0 && dy == 1) return "DOWN";
        if(dx == 0 && dy == -1) return "UP";
        if(dx == 1 && dy == 0) return "RIGHT";
        if(dx == -1 && dy == 0) return "LEFT";
        return "error turning directionToWord";
    }
}
