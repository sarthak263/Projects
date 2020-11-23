public class Simulation {

    public static void main(String[] args) {
        //create all the classes and threads
        NorthPole n = new NorthPole();
        Thread[] reindeer = new Thread[9];
        Thread[] RedElves = new Thread[10];
        Thread[] GreenElves = new Thread[10];
        Santa s = new Santa(n);
        Thread santa  = new Thread(s);

        //initialize all the threads
        for(int i =0; i < 9; i++) {
            reindeer[i] = new Thread(new Reindeer(n));
        }
        for(int i =0; i < 10; i++) {
            RedElves[i] = new Thread(new RedElves(n));
            GreenElves[i] = new Thread(new GreenElves(n));
        }

        //starting all the threads
        santa.start();

        for(Thread rein: reindeer){
            rein.start();
        }

        for(Thread rE: RedElves){
            rE.start();
        }

        for(Thread gE: GreenElves) {
            gE.start();
        }
    }
}
