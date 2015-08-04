package game.geom;

import android.graphics.Path;

/**
 * Path of a polygon
 * Can get the Path to draw with android
 * Can also obtain if a point is within the polygon.
 * <p/>
 * Created by Steven on 8/4/2015.
 */
public class PolygonPath implements Shape {
    // Path of the Polygon.
    protected Path p;

    // Polygon coordinates.
    protected int[] polyY, polyX;

    // Number of sides in the polygon.
    protected int polySides;

    public PolygonPath(int[] xPoints, int[] yPoints) throws Exception {
        this.polyX = xPoints;
        this.polyY = yPoints;
        this.polySides = xPoints.length;

        p = this.formPath();
    }

    protected Path formPath() throws Exception {
        p = new Path();

        // First draw point is the initial polygon point.
        p.moveTo(this.polyX[0], this.polyY[0]);

        // Protect from bad polygon position counts.
        if (this.polyX.length != this.polyY.length) {
            throw new Exception("Created " + PolygonPath.class.getSimpleName() + " with unequal number of x and y coordinates.");
        }

        // Loop the polygon points adding lines between them.
        for (int i = 1; i < this.polyX.length && i < this.polyY.length; i++) {
            p.lineTo(this.polyX[i], this.polyY[i]);
        }

        // Connect it back to the original point.
        p.lineTo(this.polyX[0], this.polyY[0]);

        return p;
    }

    /**
     * Obtain the path
     *
     * @return Path of the polygon
     */
    public Path getPath() {
        return this.p;
    }

    /**
     * Checks if the PolygonPath contains a point.
     *
     * @param x Point horizontal pos.
     * @param y Point vertical pos.
     * @return Point is in Poly flag.
     * @see "http://alienryderflex.com/polygon/"
     */
    @Override
    public boolean contains(int x, int y) {
        boolean c = false;
        int i, j = 0;
        for (i = 0, j = polySides - 1; i < polySides; j = i++) {
            if (((polyY[i] > y) != (polyY[j] > y))
                    && (x < (polyX[j] - polyX[i]) * (y - polyY[i]) / (polyY[j] - polyY[i]) + polyX[i]))
                c = !c;
        }
        return c;
    }
}