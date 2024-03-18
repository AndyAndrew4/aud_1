package aud_1_2;

import java.util.concurrent.CopyOnWriteArrayList;

public class Writer implements Runnable {
    private CopyOnWriteArrayList<Integer> listOfNumbers;

    public Writer(CopyOnWriteArrayList<Integer> listOfNumbers) {
        this.listOfNumbers = listOfNumbers;
    }

    @Override
    public void run() {
        try {
            int count = 1;
            while (true) {
                Thread.sleep(1000); // задержка
                System.out.println("Writing number: " + count);
                listOfNumbers.add(count++);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


