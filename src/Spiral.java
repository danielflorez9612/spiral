import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class Spiral {
    private static final Character ASTERISK = '*';
    private Map<Direction, Direction> directionsMap;
    private Character[][] spiralMatrix;
    private int n;

    private Spiral(int n) {
        this.n = n;
        this.directionsMap = new EnumMap<>(Direction.class);
        this.directionsMap.put(Direction.UP, Direction.RIGHT);
        this.directionsMap.put(Direction.RIGHT, Direction.DOWN);
        this.directionsMap.put(Direction.DOWN, Direction.LEFT);
        this.directionsMap.put(Direction.LEFT, Direction.UP);
        this.spiralMatrix = new Character[n][n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Spiral spiral = new Spiral(n);
        spiral.printSpiral();
        scanner.close();
    }

    private void print() {
        for (Character[] matrix1 : spiralMatrix) {
            for (Character character : matrix1) {
                Character c = character == null ? ' ' : character;
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private Integer[] fillNInDirection(Integer[] startingPositions, int n, Direction direction) {
        int transformedI = startingPositions[0];
        int transformedJ = startingPositions[1];
        while (n > 0) {
            transformedI = direction.transformI.apply(transformedI);
            transformedJ = direction.transformJ.apply(transformedJ);
            spiralMatrix[transformedI][transformedJ] = ASTERISK;
            n--;
        }
        return new Integer[]{transformedI, transformedJ};
    }

    private Direction nextDirection(Direction direction) {
        return directionsMap.get(direction);
    }

    public void printSpiral() {
        int currentLineSize = n;
        Direction direction = Direction.RIGHT;
        Integer[] startingPositions = new Integer[]{0, -1};
        while (currentLineSize > 0) {
            startingPositions = fillNInDirection(startingPositions, currentLineSize, direction);
            direction = nextDirection(direction);
            currentLineSize--;
        }
        print();
    }

    public enum Direction {
        LEFT(x -> x, y -> y - 1),
        RIGHT(x -> x, y -> y + 1),
        UP(x -> x - 1, y -> y),
        DOWN(x -> x + 1, y -> y);
        Function<Integer, Integer> transformI;
        Function<Integer, Integer> transformJ;

        Direction(Function<Integer, Integer> transformI, Function<Integer, Integer> transformJ) {
            this.transformI = transformI;
            this.transformJ = transformJ;
        }
    }
}
