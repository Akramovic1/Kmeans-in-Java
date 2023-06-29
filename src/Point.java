public class Point {
    private double[] values;
    private String label;
    private Cluster cluster;

    public Point(double[] values, String label) {
        this.values = values;
        this.label = label;
    }

    public double[] getValues() {
        return values;
    }

    public String getLabel() {
        return label;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public static double distance(Point a, Point b) {
        double[] aValues = a.getValues();
        double[] bValues = b.getValues();
        double sumOfSquares = 0.0;
        for (int i = 0; i < aValues.length; i++) {
            sumOfSquares += Math.pow(aValues[i] - bValues[i], 2);
        }
        return Math.sqrt(sumOfSquares);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i != values.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(") [").append(label).append("]");
        return sb.toString();
    }
}