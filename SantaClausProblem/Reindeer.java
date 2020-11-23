public class Reindeer implements Runnable{
    NorthPole n;

    public Reindeer(NorthPole n) {
        this.n =n;
    }

    @Override
    public void run() {
        while(true) {
            try {
                //call the NorthPole class
                this.n.reindeerWork();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
