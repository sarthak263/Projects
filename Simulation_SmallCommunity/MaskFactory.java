import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class MaskFactory extends Thread {
    //Threshold
    int min_maskeE = 3; //min masks employees
    int max_masksE = 6;
    int masks_min = 3;//min masks created per day
    int masks_max = 10;//max masks per day

    int maskersId;
    int masksMadePerDay;
    SuperStore s;
    Truckers t;
    int day = 1;
    Semaphore maskers;
    Semaphore availableDelivery;
    public MaskFactory(int maskersId, Truckers t) {
        this.maskersId = maskersId;
        this.t = t;
        maskers = new Semaphore(max_masksE);
        availableDelivery = new Semaphore(1);
    }

    public void MakingMasks() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000,3000));
        //adding min masks workers needed to make the masks
        masksMadePerDay = ThreadLocalRandom.current().nextInt(masks_min,masks_max);
        System.out.format("Maskers created %d Masks on Day %d\n",this.masksMadePerDay,this.day);
    }
    public void AwaitTruckerToTakeMask() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000,3000));
        int takeMask = masksMadePerDay;
        System.out.println("Maskers waiting for the Truck on day "+ this.day);
        availableDelivery.acquire();
        t.TakeMaskDelivery(takeMask);
        availableDelivery.release();
    }

    public void MaskerComingToWork() throws InterruptedException {
        System.out.println("Maskers are coming to work");
        maskers.acquire();
    }

    public void MaskerGoingHome() throws InterruptedException {
        System.out.println("Maskers going home and sleeping");
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000,4000));
        maskers.release();
    }

    public void nextDay() {
        this.day++;
    }
    @Override
    public void run() {
        while(true) {
            try {
                MaskerComingToWork();
                MakingMasks();
                AwaitTruckerToTakeMask();
                MaskerGoingHome();
                nextDay();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }

        }
    }
}
