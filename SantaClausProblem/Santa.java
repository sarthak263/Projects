public class Santa implements Runnable {
    NorthPole n;

    public Santa(NorthPole n) {
        this.n = n;
    }

    @Override
    public void run() {
        while(true) {
            try {
                //call the NorthPole class
                this.n.SantaGotoWork();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }


}
