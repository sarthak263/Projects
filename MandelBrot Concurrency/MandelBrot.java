import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;
import javax.imageio.ImageIO;
enum ParallelStrategy {
    Static,Dynamic
}

public class MandelBrot{
    protected BufferedImage I;
    protected int RED = new Color(204, 0, 0).getRGB();
    protected int YELLOW = new Color(255, 204, 0).getRGB();
    protected int GREEN = new Color(0, 153, 0).getRGB();
    protected int BLUE = new Color(0, 50, 204).getRGB();
    protected int PURPLE = new Color(102, 0, 140).getRGB();
    protected int BLACK = new Color(0, 0, 0).getRGB();

    public MandelBrot(ParallelStrategy choice, int NUMTHREADS, double XLO, double XHI, double YLO, double YHI, int THRESHOLD, int SIZE, File filename, String Name) throws IOException, InterruptedException {
        if (choice==ParallelStrategy.Static) {
            this.StaticMandelBrot(NUMTHREADS,XLO, XHI, YLO,  YHI,THRESHOLD,SIZE,filename,Name);
        }else{
            this.DynamicMandelBrot(NUMTHREADS,XLO, XHI, YLO,  YHI,THRESHOLD,SIZE,filename,Name);
        }
    }

    public void StaticMandelBrot(int NUMTHREADS, double XLO, double XHI, double YLO, double YHI, int THRESHOLD, int SIZE, File filename, String Name) throws IOException, InterruptedException {

        //Every Thread has its own Column + numThreads.
        //If thread num is 2 and size is 4x4 then T1 will have 0, 2, n+numThreads and t2 will have column 1 and 3, n+numThreads
        I = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        int[][] plots = new int[SIZE][SIZE];
        Thread[] workers = new Thread[NUMTHREADS];
        long start = System.currentTimeMillis();
        for (int threadNum = 0; threadNum < NUMTHREADS; threadNum++) {
            int finalThreadNum = threadNum;
            workers[threadNum] = new Thread(() -> {
                for (int x=0; x< SIZE; x++) {
                    for (int y = finalThreadNum; y < SIZE; y += NUMTHREADS) {

                        double xc = XLO + (XHI - XLO) * x / SIZE;
                        double yc = YLO + (YHI - YLO) * y / SIZE;
                        int iter = computeMandel(xc, yc, THRESHOLD);

                        if (iter < THRESHOLD) {
                            if (iter < THRESHOLD * .20)
                                plots[x][y] = YELLOW;
                            else if (iter < THRESHOLD * .40)
                                plots[x][y] = GREEN;
                            else if (iter < THRESHOLD * .60)
                                plots[x][y] = BLUE;
                            else if (iter < THRESHOLD * .80)
                                plots[x][y] = PURPLE;
                            else if (iter > THRESHOLD * .80)
                                plots[x][y] = RED;
                        } else
                            plots[x][y] = BLACK;
                    }
                }
            });
        }

        for(Thread worker: workers)
            worker.start();

        for(Thread worker: workers)
            worker.join();

        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        System.out.println("STATIC: Time it took: " + elapsedTime + " to run " + Name);

        for(int x =0; x< SIZE;x++){
            for(int y=0; y < SIZE;y++){
                I.setRGB(x,y,plots[x][y]);
            }
        }
        ImageIO.write(I, "jpg", filename);

    }

    public void DynamicMandelBrot(int NUMTHREADS, double XLO, double XHI, double YLO, double YHI, int THRESHOLD, int SIZE, File filename, String Name) throws InterruptedException, IOException {

        if (NUMTHREADS == 0) {
            System.out.println("Threads cannot be equal to 0.");
            System.exit(0);
        }
        int[][] plots = new int[SIZE][SIZE];
        I = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Thread[] workers = new Thread[NUMTHREADS];
        int sizeParts = 25;
        //used this DataStructures because it has better throughput
        LinkedBlockingQueue<MandelBrotSections> sections = new LinkedBlockingQueue<>();

        //adding the object mandelbrot sections to LinkedBlockingQueue
        //brilliant usage made by Nicholas in dividing sections using Math.min functions so used his version
        for (int x = 0; x < SIZE; x += sizeParts) {
            for (int y = 0; y < SIZE; y += sizeParts) {
                sections.put(new MandelBrotSections(x, Math.min(SIZE, x + sizeParts), y, Math.min(SIZE, y + sizeParts)));
            }
        }
        //mandelbrot creating with number of threads dynamically starts here
        long start = System.currentTimeMillis();
        for (int threadNum = 0; threadNum < NUMTHREADS; threadNum++) {
            workers[threadNum] = new Thread(() -> {
                do {
                    try {
                        MandelBrotSections sect = sections.poll();
                        assert sect != null;
                        for (int x = sect.minX; x < sect.maxX; x++) {
                            for (int y = sect.minY; y < sect.maxY; y++) {
                                double xc = XLO + (XHI - XLO) * x / SIZE;
                                double yc = YLO + (YHI - YLO) * y / SIZE;
                                int iter = computeMandel(xc, yc, THRESHOLD);

                                if (iter < THRESHOLD) {
                                    if (iter < THRESHOLD * .20)
                                        plots[x][y] = YELLOW;
                                    else if (iter < THRESHOLD * .40)
                                        plots[x][y] = GREEN;
                                    else if (iter < THRESHOLD * .60)
                                        plots[x][y] = BLUE;
                                    else if (iter < THRESHOLD * .80)
                                        plots[x][y] = PURPLE;
                                    else if (iter > THRESHOLD * .80)
                                        plots[x][y] = RED;
                                } else
                                    plots[x][y] = BLACK;
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Problem running Dynamic Thread");
                    }

                } while (sections.size() != 0);
            });
        }
        for(Thread worker: workers)
            worker.start();

        for(Thread worker: workers)
            worker.join();

        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        System.out.println("DYNAMIC: Time it took: " + elapsedTime + " to run " + Name);

        //creating the image
        for(int x =0; x< SIZE;x++){
            for(int y=0; y < SIZE;y++){
                I.setRGB(x,y,plots[x][y]);
            }
        }
        ImageIO.write(I, "jpg", filename);
    }

        public int computeMandel ( double xc, double yc, int threshold){
            int i = 0;
            double x = 0.0;
            double y = 0.0;

            while (x * x + y * y < 2 && i < threshold) {
                double xt = x * x - y * y + xc;
                double yt = 2.0 * x * y + yc;
                x = xt;
                y = yt;
                i += 1;
            }
            return i;

        }
}

class MandelBrotSections {
    int minX, maxX, minY, maxY;

    public MandelBrotSections(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

    }
}