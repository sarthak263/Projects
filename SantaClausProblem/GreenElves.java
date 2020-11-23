public class GreenElves implements Runnable{
    NorthPole n;
    public GreenElves(NorthPole n) {
        this.n =n;
    }


    @Override
    public void run() {
        while (true) {
            try {
                //call the NorthPole class
                this.n.GreenElvesHelp();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
