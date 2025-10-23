public class Point {

    private double rho;
    private double theta;

    public Point() {
        rho = 0.0;
        theta = 0.0;
    }

    /**
     * PRE: -
     * POST:
     * getRho() == sqrt(x*x + y*y)
     * getTheta() == atan2(y, x)
     */
    public Point(double x, double y) {
        setFromXY(x, y);
    }

    private void setFromXY(double x, double y) {
        rho = Math.sqrt(x * x + y * y);
        theta = Math.atan2(y, x);
    }

    /**
     * Abscissa
     * PRE: -
     * POST:
     * Result == getRho() * cos(getTheta())
     */
    public double getX() {
        return getRho() * Math.cos(getTheta());
    }

    /**
     * Ordinate
     * PRE: -
     * POST:
     * Result == getRho() * sin(getTheta())
     */
    public double getY() {
        return getRho() * Math.sin(getTheta());
    }

    /**
     * Distance to origin (0, 0)
     * PRE: -
     * POST:
     * Result == sqrt(x**2+y**2)
     */
    public double getRho() {
        return rho;
    }

    /**
     * Angle to horizontal axis
     * PRE: -
     * POST:
     * Result = atan2(y, x)
     */
    public double getTheta() {
        return theta;
    }

    /**
     * Returns the Point representing the vector from self to other Point
     * PRE: -
     * POST:
     * Result.getX() == p.getX() - getX()
     * Result.getY() == p.getY() - getY()
     */
    public Point vectorTo(Point p) {
        return new Point(p.getX() - getX(), p.getY() - getY());
    }

    /**
     * Distance to other
     * PRE: -
     * POST:
     * Result = sqrt((p.getX() - getX())^2 + (p.getY() - getY())^2)
     */
    public double distance(Point p) {
        return vectorTo(p).getRho();
    }

    /**
     * Move by dx horizontally, dy vertically
     * PRE: -
     * POST:
     * getX() == old getX + dx
     * getY() == old getY + dy
     */
    public void translate(double dx, double dy) {
        double x = getX() + dx;
        double y = getY() + dy;
        setFromXY(x, y);
    }

    /**
     * Scale by factor
     * PRE: -
     * POST:
     * getRho() == old getRho() * abs(factor)
     *     if factor >= 0:
     *          getTheta() == old getTheta()
     *     if factor < 0:
     *          getTheta() == old getTheta() + PI
     */
    public void scale(double factor) {
        rho *= Math.abs(factor);
        if (factor < 0) {
            increment_theta(Math.PI);
        }
    }

    private void increment_theta(double angle) {
        theta += angle;
        theta = theta % (2 * Math.PI);
    }

    /**
     * Rotate around origin (0, 0) by angle
     * PRE: -
     * POST:
     * getTheta() == old getTheta() + angle (NB! Nurkade võrdlus)
     */
    public void centre_rotate(double angle) {
        increment_theta(angle);
    }

    /**
     * Rotate around p by angle
     * PRE: -
     * POST:
     * p.vectorTo(this).getTheta() == p.vectorTo(old this).getTheta() + angle (NB! Nurkade võrdlus)
    */
    public void rotate(Point p, double angle) {
        translate(-p.getX(), -p.getY());
        centre_rotate(angle);
        translate(p.getX(), p.getY());
    }
}
