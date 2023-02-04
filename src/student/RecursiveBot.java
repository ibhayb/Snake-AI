package student;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

public class RecursiveBot implements Bot {
    private int oracle(Snake snake, Snake opponent, Coordinate apple, int simSteps) {
        // Base cases

        if (snake.headCollidesWith(opponent)) {

            return Integer.MAX_VALUE;
        }
        if (simSteps == 0) {

            return Math.abs(apple.x - snake.getHead().x) + Math.abs(apple.y - snake.getHead().y);
        }

        // recurse calls in four directions
        Snake opponent_clone = opponent.clone();
        if (!opponent_clone.body.isEmpty()) {
            opponent_clone.elements.remove(opponent_clone.body.removeLast());
        }
        Snake snake_left = snake.clone();
        Snake snake_right = snake.clone();
        Snake snake_up = snake.clone();
        Snake snake_down = snake.clone();

        int val_left = Integer.MAX_VALUE;
        if (snake_left.moveTo(Direction.LEFT, false)) {
            val_left = oracle(snake_left, opponent_clone, apple, simSteps - 1);
        }

        int val_right = Integer.MAX_VALUE;
        if (snake_right.moveTo(Direction.RIGHT, false)) {
            val_right = oracle(snake_right, opponent_clone, apple, simSteps - 1);
        }

        int val_up = Integer.MAX_VALUE;
        if (snake_up.moveTo(Direction.UP, false)) {
            val_up = oracle(snake_up, opponent_clone, apple, simSteps - 1);
        }

        int val_down = Integer.MAX_VALUE;
        if (snake_down.moveTo(Direction.DOWN, false)) {
            val_down = oracle(snake_down, opponent_clone, apple, simSteps - 1);
        }

        int best_val = Math.min(Math.min(val_down, val_up), Math.min(val_left, val_right));
        if (snake.getHead().x == apple.x && snake.getHead().y == apple.y && best_val < Integer.MAX_VALUE) { // Objektvergleich
                                                                                                            // daher x
                                                                                                            // und y
                                                                                                            // getrennt
            return 0;
        } else {
            if (best_val == Integer.MAX_VALUE) {
                return best_val;
            }
            return best_val + 1;
        }
    }

    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        int simSteps = 10;
        Snake opponent_clone = opponent.clone();

        Snake snake_left = snake.clone();
        Snake snake_right = snake.clone();
        Snake snake_up = snake.clone();
        Snake snake_down = snake.clone();

        int val_left = Integer.MAX_VALUE;
        if (snake_left.moveTo(Direction.LEFT, false)) {
            val_left = oracle(snake_left, opponent_clone, apple, simSteps);
        }

        int val_right = Integer.MAX_VALUE;
        if (snake_right.moveTo(Direction.RIGHT, false)) {
            val_right = oracle(snake_right, opponent_clone, apple, simSteps);
        }

        int val_up = Integer.MAX_VALUE;
        if (snake_up.moveTo(Direction.UP, false)) {
            val_up = oracle(snake_up, opponent_clone, apple, simSteps);
        }

        int val_down = Integer.MAX_VALUE;
        if (snake_down.moveTo(Direction.DOWN, false)) {
            val_down = oracle(snake_down, opponent_clone, apple, simSteps);
        }

        int best_val = Math.min(Math.min(val_down, val_up), Math.min(val_left, val_right));

        if (val_left == best_val) {
            return Direction.LEFT;
        }
        if (val_down == best_val) {
            return Direction.DOWN;
        }
        if (val_right == best_val) {
            return Direction.RIGHT;
        } else {
            return Direction.UP;
        }
    }
}