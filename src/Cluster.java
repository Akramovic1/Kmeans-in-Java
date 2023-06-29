import java.util.ArrayList;

public class Cluster {
    private int id;
    private ArrayList<Point> points;
    private Point centroid;

    public Cluster(int id, Point centroid) {
        this.id = id;
        this.centroid = centroid;
        this.points = new ArrayList<Point>();
    }

    public int getId() {
        return id;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void removePoint(Point point) {
        points.remove(point);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public Point getCentroid() {
        return centroid;
    }

    public void updateCentroid() {
        double[] newValues = new double[centroid.getValues().length];
        int numPoints = points.size();
        for (Point point : points) {
            double[] values = point.getValues();
            for (int i = 0; i < newValues.length; i++) {
                if(values.length == 0)
                    break;
                newValues[i] += values[i];
            }
        }
        for (int i = 0; i < newValues.length; i++) {
            newValues[i] /= numPoints;
        }
        centroid = new Point(newValues, centroid.getLabel());
    }
}