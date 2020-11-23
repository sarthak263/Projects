import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
public class SuperStore extends Thread {
    int Smax = 200; //store cap
    int currentFoodCap = 0;
    int currentMaskAvailable = 0;

    int sc = 3; //customer cap
    int min_sse = 3; //min employees
    int max_sse =5; //max employees

    int ss_min = 5; //min amount of customers per day
    int ss_max = 10; //max amount of customers per day

    Semaphore food;
    Semaphore mask;
    Semaphore truck;
    Semaphore maxCustomerPerDay;
    Semaphore maxCustomerCap;
    Semaphore workers;
    Semaphore enter;
    public SuperStore(){
        food = new Semaphore(currentFoodCap);
        mask = new Semaphore(currentMaskAvailable);
        truck = new Semaphore(Smax);
        maxCustomerCap = new Semaphore(sc);
        workers  = new Semaphore(min_sse);
        enter = new Semaphore(1);
        //maxCustomerCap = new Semaphore(ss_max);
    }

    public void FoodDelivered(int foodUnits) throws InterruptedException {
        //trucks wait if food capacity reaches the max
        truck.acquire(foodUnits);
        currentFoodCap+=foodUnits;
        System.out.println("Food delivered to the SuperStore");
        food.release(foodUnits);
    }

    public void MaskDelivered(int maskUnits) {
        currentMaskAvailable+=maskUnits;
        System.out.println("Masks delivered to the SuperStore");
        mask.release(currentMaskAvailable);
    }

    public int FarmerVisitFoodStore(int min, int max, int farmerId) throws InterruptedException {
        maxCustomerCap.acquire();
        int foodBought = ThreadLocalRandom.current().nextInt(min,max);
        System.out.format("Farmer %d is buying %d food units\n", farmerId,foodBought);
        food.acquire(foodBought);
        maxCustomerCap.release();
        return foodBought;
    }
    public int FarmerVisitMaskStore(int min, int max) throws InterruptedException {
        maxCustomerCap.acquire();
        int maskBought = ThreadLocalRandom.current().nextInt(min,max);
        mask.acquire(maskBought);
        maxCustomerCap.release();
        return maskBought;
    }
}
