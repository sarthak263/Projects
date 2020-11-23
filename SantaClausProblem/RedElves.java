public class RedElves implements Runnable {
    NorthPole n;
    public RedElves(NorthPole n) {
        this.n =n;
    }


    @Override
    public void run() {
        while (true) {
            try {
                //call the NorthPole class
                this.n.RedElvesHelp();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
