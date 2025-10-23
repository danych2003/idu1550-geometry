import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    Random rnd = new Random();

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void getX(double x, double y) {
        Point point = new Point(x, y);

        double expected = point.getRho() * Math.cos(point.getTheta());
        assertEquals(expected, point.getX());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void getY(double x, double y) {
        Point point = new Point(x, y);

        double expected = point.getRho() * Math.sin(point.getTheta());
        assertEquals(expected, point.getY());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void getRho(double x, double y) {
        Point point = new Point(x, y);

        double expected = Math.sqrt(x * x + y * y);
        assertEquals(expected, point.getRho());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void getTheta(double x, double y) {
        Point point = new Point(x, y);

        double expected = Math.atan2(y, x);
        assertEquals(expected, point.getTheta());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void vectorTo(double x, double y) {
        Point point1 = new Point(x, y);
        Point point2 = new Point(x, y);
        Point vector = point1.vectorTo(point2);

        assertEquals(vector.getX(), point2.getX() - point1.getX());
        assertEquals(vector.getY(), point2.getY() - point1.getY());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void distance(double x, double y) {
        Point point1 = new Point(x, y);
        Point point2 = new Point(x, y);

        double expected = Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2) + Math.pow(point2.getY() - point1.getY(), 2));
        assertEquals(expected, point1.distance(point2));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void translate(double x, double y) {
        Point point = new Point(x, y);

        double dx = rnd.nextDouble();
        double dy = rnd.nextDouble();

        point.translate(dx, dy);

        double delta = 1e-10;

        assertEquals(point.getX(), x + dx, delta);
        assertEquals(point.getY(), y + dy, delta);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void scale(double x, double y) {
        double negativeFactor = -rnd.nextDouble(0.1, 10);
        double positiveFactor = rnd.nextDouble(0.1, 10);
        for (int i = 0; i < 2; i++) {
            double factor = i == 0 ? positiveFactor : negativeFactor;
            Point point = new Point(x, y);

            double oldRho = point.getRho();

            point.scale(factor);
            double expected = oldRho * Math.abs(factor);
            assertEquals(expected, point.getRho());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void centre_rotate(double x, double y) {
        Point point = new Point(x, y);
        double oldTheta = point.getTheta();
        double angle = rnd.nextDouble();

        point.centre_rotate(angle);

        double expected = (oldTheta + angle) % (2 * Math.PI);

        assertEquals(expected, point.getTheta());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void rotate(double x, double y) {
        Point center = new Point(0, 0); // точка вращения
        Point point = new Point(x, y);

        if (x == center.getX() && y == center.getY()) return;

        double angle = rnd.nextDouble();

        double oldTheta = normalize(point.vectorTo(center).getTheta());

        point.rotate(center, angle);

        double newTheta = normalize(point.vectorTo(center).getTheta());

        double expected = normalize(oldTheta + angle);

        double delta = 1e-10;
        assertEquals(expected, newTheta, delta);
    }

    private double normalize(double theta) {
        theta = theta % (2 * Math.PI);
        if (theta < 0) theta += 2 * Math.PI;
        return theta;
    }
}