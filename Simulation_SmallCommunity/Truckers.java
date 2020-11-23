import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Truckers extends Thread{
    int truckId;
    int maxCapacity;
    Semaphore foodTruck;
    SuperStore s;
    Semaphore maskTruck;
    Semaphore truckCap;
    Semaphore deliver;
    Semaphore truckCap2;
    int countItem =0;
    int readCount = 0;
    boolean wantf = false;
    boolean wantm = false;
    public Truckers(int truckId, int maxCapacity, SuperStore s){
        this.truckId =truckId;
        this.maxCapacity = maxCapacity;
        foodTruck = new Semaphore(1);
        maskTruck = new Semaphore(1);
        truckCap = new Semaphore(this.maxCapacity);
        deliver = new Semaphore(1);
        this.s = s;
    }

    public void TakeFoodDelivery(int food) throws InterruptedException {
        System.out.println("Farmer are waiting for the Truck. Truck is currently busy");
        foodTruck.acquire();
        readCount++;
        if(readCount==1) {
            maskTruck.acquire();
        }
        System.out.println("Truck is stocking food now");
        countItem +=food;
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
        System.out.format("Currently Truck has %d food units\n",countItem);
        if(countItem >=maxCapacity){
            System.out.println("Truck has reached its maxCapacity, it is going to the SuperStore to deliver the food");
            Thread.sleep(ThreadLocalRandom.current().nextInt(3000,5000));
            s.FoodDelivered(food);
            readCount=0;
            countItem=0;
            maskTruck.release();
            Thread.sleep(1000);
        }
        foodTruck.release();
    }

    public void TakeMaskDelivery(int mask) throws InterruptedException {
        System.out.println("Maskers are waiting for the Truck. Truck is currently busy");
        maskTruck.acquire();
        readCount++;
        if(readCount==1) {
            foodTruck.acquire();
        }
        System.out.println("Truck is stocking masks now");
        countItem +=mask;
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
        System.out.format("Currently Truck has %d Mask\n",countItem);
        if(countItem >=maxCapacity) {
            System.out.println("Truck has reached its maxCapacity, it is going to the SuperStore to deliver the Masks");
            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 5000));
            s.MaskDelivered(mask);
            readCount=0;
            countItem=0;
            foodTruck.release();
            Thread.sleep(1000);
        }
        maskTruck.release();
    }
}
