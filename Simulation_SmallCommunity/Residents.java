import java.util.Random;

public class Residents {
    Farmers[] farmers;
    MaskFactory[] maskMakers;
    SuperStore s;
    Truckers t;
    public Residents() {
        farmers = new Farmers[3];
        maskMakers = new MaskFactory[3];
        s = new SuperStore();
        Random rand = new Random();
        t = new Truckers(1,20,s);
        for(int i=0; i<farmers.length;i++) {
            farmers[i] = new Farmers(i + 1, 1, rand.nextInt(3) + 1,s,t);
        }

        for(int i = 0; i < maskMakers.length;i++) {
            maskMakers[i] = new MaskFactory(i+1,t);
        }

    }
    public void run() {
        for (Farmers farmer : farmers) {
            farmer.start();
        }
        for (MaskFactory maskers: maskMakers) {
            maskers.start();
        }
    }
}
