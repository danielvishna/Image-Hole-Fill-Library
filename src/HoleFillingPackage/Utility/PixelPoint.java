package HoleFillingPackage.Utility;

import java.awt.*;

/**
 * Represents a pixel in the image with its coordinates and grayscale value.
 */
public class PixelPoint extends Point {
    private double color;

    public PixelPoint(int x, int y) {
        super(x, y);
    }




    public PixelPoint(int x, int y, double color) {
        super(x, y);
        this.color = color;
    }

    /**
     * Retrieves the grayscale color of the pixel.
     * @return The grayscale color value.
     */
    public double getColor() {
        return color;
    }

    /**
     * Sets the grayscale color of the pixel.
     * @param color The grayscale color value.
     */
    public void setColor(double color) {
        this.color = color;
    }
}
