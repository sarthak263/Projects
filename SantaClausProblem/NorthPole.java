public class NorthPole extends Thread{
    boolean ElvesDoor = true;
    boolean ReindeerDoor = true;
    int reindeerCount = 0;
    int RedElvesCount = 0;
    int GreenElvesCount =0;

    public NorthPole(){}

    //reindeer method
    public synchronized void reindeerWork() throws InterruptedException {
        //two doors if both are open then go in. if either one is false that means
        //Santa has to wake up and finish his tasks to make the condition to true.
        if(this.ReindeerDoor && this.ElvesDoor) {
            System.out.format("Reindeer %d came back from holiday\n", Thread.currentThread().getId());
            this.reindeerCount++;
            System.out.format("We have %d Reindeer waiting\n", this.reindeerCount);
            Thread.sleep(1000);

            if (this.reindeerCount == 9) {
                this.ReindeerDoor=false;
                notifyAll();
            }else {
                wait();
            }
        }
        notifyAll();
    }
    //Red Elves
    public synchronized void RedElvesHelp() throws InterruptedException {
        //two doors if both are open then go in. if either one is false that means
        //Santa has to wake up and finish his tasks to make the condition to true.
        if (this.ElvesDoor && this.ReindeerDoor) {
            System.out.format("Red Elf %d joined for help\n ", Thread.currentThread().getId());
            Thread.sleep(1000);
            this.RedElvesCount++;
            System.out.format("We have %d Red elves waiting\n",this.RedElvesCount);

            //if it equals to 3 make elves door to false so no other elves can acces this CS
            //even if its called notifyall
            if (this.RedElvesCount == 3) {
                System.out.println("We have three Red elves now");
                this.ElvesDoor = false;
                notifyAll();
                
            }else {
                wait();
            }
        }
        notifyAll();
    }
    //Green Elves Method
    public synchronized void GreenElvesHelp() throws InterruptedException {
        if (this.ElvesDoor && this.ReindeerDoor) {
            System.out.format("Green Elf %d joined for help\n ", Thread.currentThread().getId());
            Thread.sleep(1000);
            this.GreenElvesCount++;
            System.out.format("We have %d Green elves waiting\n",this.GreenElvesCount);

            //if it equals to 3 make elves door to false so no other elves can acces this CS
            //even if its called notifyall
            if (this.GreenElvesCount == 3) {
                System.out.println("We have three Green elves now");
                this.ElvesDoor = false;
                notifyAll();

            }else {
                wait();
            }
        }
        notifyAll();

    }

    public synchronized void SantaGotoWork() throws InterruptedException {
        System.out.println("Santa is sleeping...");
        wait();
        System.out.println("Santa woke up!");
        //if reindeercount is 9 then harness the reindeer and make the door to true
        if(this.reindeerCount==9) {
            System.out.println("We have 9 Reindeer, Santa will harness them.");
            System.out.println("Santa is going to delivery toys now.");
            Thread.sleep(1000);
            this.reindeerCount=0;
            this.ReindeerDoor=true;
            notifyAll();
        }
        //red elves is 3 then help each red elves and make the door to true
        if(this.RedElvesCount == 3) {
            System.out.println("We have 3 RedElves. Santa will help each of them now");
            System.out.println("Santa is helping RedElves");
            for (int i = 0; i < this.RedElvesCount; i++) {
                Thread.sleep(1000);
                System.out.println("Santa finished helping one of the Red elves");
            }
            this.RedElvesCount =0;
            this.ElvesDoor = true;
            notifyAll();
        }
        //Green elves is 3 then help each red elves and make the door to true
        if(this.GreenElvesCount == 3) {
            System.out.println("We have 3 Green Elves. Santa will help each of them now");
            System.out.println("Santa is helping Green Elves");
            for (int i = 0; i < this.GreenElvesCount; i++) {
                Thread.sleep(1000);
                System.out.println("Santa finished helping one of the Green elves");
            }
            this.GreenElvesCount =0;
            this.ElvesDoor = true;
            notifyAll();
        }
        System.out.println("Santa went back to Sleep");
    }

}


