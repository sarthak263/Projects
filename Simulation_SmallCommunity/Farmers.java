import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Farmers extends Thread{
    int nfood =10; //food at home
    int nmask = 10;//masks at home

    int cf_min = 3; //food eat min
    int cf_max = 5; //food eat max

    int bf_min= 5; //food buy min
    int bf_max = 10; //food buy max

    int bmask_min = 5;
    int bmask_max = 10;

    int Cfp_min = 1; //min masks use
    int Cfp_max = 3; //max masks use

    //Triggers
    int f_min =  8; //food
    int mask_min = 8;//masks

    int farmerId;
    int foodGrownPerDay;
    int foodEatenPerDay;
    int maskUsedPerDay;
    int day;
    int houseHoldSize;
    SuperStore s;
    Truckers t;
    Semaphore FarmerWorkers;

    public Farmers(int farmerId, int day, int houseHoldSize,SuperStore s,Truckers t){
        this.farmerId= farmerId;
        this.day =day;
        this.houseHoldSize = houseHoldSize;
        this.s = s;
        this.t = t;
        FarmerWorkers = new Semaphore(3);
    }

    public void Farm() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000,3000));
        foodGrownPerDay = ThreadLocalRandom.current().nextInt(3,10);
        System.out.format("Farmer %d grew %d food units on day %d.\n",this.farmerId,this.foodGrownPerDay,this.day);
        this.nfood+=foodGrownPerDay;

        maskUsedPerDay = ThreadLocalRandom.current().nextInt(Cfp_min,Cfp_max)*3;
        System.out.format("Farmer %d used %d mask on day %d\n", this.farmerId,maskUsedPerDay,this.day);
    }

    public void EatFood() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000,3000));
        foodEatenPerDay = ThreadLocalRandom.current().nextInt(cf_min,cf_max)*houseHoldSize;
        System.out.format("Farmer's %d family with houseHold size %d ate %d food units on day %d.\n",this.farmerId,this.houseHoldSize,this.foodEatenPerDay,this.day);
        this.nfood-=foodEatenPerDay;

    }

    public void AwaitTruckerToTakeFood() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000,3000));
        int takeFood = this.foodGrownPerDay-this.foodEatenPerDay;

        if(takeFood<0) {
            System.out.format("Farmer %d ate more food than grown, Truck will take it from pantry on day %d\n",this.farmerId,this.day);
            this.nfood+=takeFood;
        }
        else{
            this.nfood-=takeFood;
        }
        System.out.format("Farmer %d is waiting for a Truck\n",this.farmerId);
        FarmerWorkers.acquire();
        t.TakeFoodDelivery(Math.abs(takeFood));
        FarmerWorkers.release();
    }

    public void ShopForFood() throws InterruptedException {

        if(this.nfood<f_min){
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000,3000));
            System.out.format("Farmer %d shopping for Food on day %d\n",farmerId,this.day);
            int boughtFood = s.FarmerVisitFoodStore(bf_min,bf_max,this.farmerId);
            System.out.format("Farmer %d bought %d food units from the store on day %d\n",this.farmerId,boughtFood,this.day);
            this.nfood+=boughtFood;
        }
    }

    public void ShopForMask() throws InterruptedException {

        if(nmask<mask_min) {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000,3000));
            System.out.format("Farmer %d shopping for mask on day %d\n",farmerId,this.day);
            int boughtMasks = s.FarmerVisitMaskStore(bmask_min,bmask_min);
            System.out.format("Farmer %d bought %d masks from the store on day %d\n",this.farmerId,boughtMasks,this.day);
            this.nmask+=boughtMasks;
        }
    }

    public void FarmerSleep() throws InterruptedException {

        System.out.format("Farmer %d is going to sleep\n",farmerId);
        Thread.sleep(ThreadLocalRandom.current().nextInt(3000,6000));
        System.out.format("Farmer %d has %d food and %d masks at his house on day %d\n", this.farmerId,this.nfood,this.nmask, this.day);

    }

    public void nextDay() {
        day++;
    }
    @Override
    public void run() {
        while(true) {
            try {
                Farm();
                EatFood();
                //ac
                AwaitTruckerToTakeFood();
                //release
                ShopForFood();
                ShopForMask();
                FarmerSleep();
                nextDay();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }

        }

    }

}
