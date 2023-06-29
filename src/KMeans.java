import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class KMeans {

    public static void main(String[] args) throws IOException {
        // File path
        String filePath = new File("").getAbsolutePath() + "/src/iris.data";

        // Load dataset
        ArrayList<Point> dataset = loadDataset(filePath);

        int K = 4;

        // Initialize clusters
        ArrayList<Cluster> clusters = initializeClusters(K, dataset);

        long startTime = System.nanoTime();


        // Run K-means algorithm
        int iterations = 0;
        boolean changed = true;
        while (changed && iterations < 100) {
            changed = false;
            iterations++;
            // Assign points to clusters
            for (Point p : dataset) {
                Cluster closest = getClosestCluster(p, clusters);
                if (closest != p.getCluster()) {
                    if (p.getCluster() != null) {
                        p.getCluster().removePoint(p);
                    }
                    closest.addPoint(p);
                    p.setCluster(closest);
                    changed = true;
                }
            }
            // Update cluster centroids
            for (Cluster c : clusters) {
                c.updateCentroid();
            }
        }

        long endTime = System.nanoTime();


        // Print results
        for (Cluster c : clusters) {
            System.out.println("Cluster " + c.getId() + " (size = " + c.getPoints().size() + "): " + c.getCentroid().toString());
        }

        System.out.println("Iterations: " + iterations);

        long timeElapsed = (endTime - startTime) / 1000;
        System.out.println("Time elapsed: " + timeElapsed + " micro seconds");

    }

    public static ArrayList<Point> loadDataset(String filename) throws IOException {
        ArrayList<Point> dataset = new ArrayList<Point>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            double[] values = new double[fields.length - 1];
            for (int i = 0; i < values.length; i++) {
                values[i] = Double.parseDouble(fields[i]);
            }
            dataset.add(new Point(values, fields[fields.length - 1]));
        }
        br.close();
        return dataset;
    }

    public static ArrayList<Cluster> initializeClusters(int K, ArrayList<Point> dataset) {
        ArrayList<Cluster> clusters = new ArrayList<Cluster>();
        Random random = new Random();
        double[][] centroids = {{4.6,3.4,1.4,0.3}, {6.8,2.8,4.8,1.4}, {6.3,2.8,5.1,1.5}, {5.9,3,5.1,1.8}};
        String[] labels = {"Iris-setosa", "Iris-versicolor", "Iris-virginica", "Iris-virginica"};
        for (int i = 0; i < K; i++) {
//            Point centroid = dataset.get(random.nextInt(dataset.size()));
            Point centroid = new Point(centroids[i], labels[i]);
            clusters.add(new Cluster(i, centroid));
        }
        return clusters;
    }

    public static Cluster getClosestCluster(Point p, ArrayList<Cluster> clusters) {
        Cluster closest = null;
        double minDistance = Double.MAX_VALUE;
        for (Cluster c : clusters) {
            double distance = Point.distance(p, c.getCentroid());
            if (distance < minDistance) {
                closest = c;
                minDistance = distance;
            }
        }
        return closest;
    }
}