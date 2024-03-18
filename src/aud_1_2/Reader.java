package aud_1_2;

import java.util.concurrent.CopyOnWriteArrayList;

public class Reader implements Runnable {
    private CopyOnWriteArrayList<Integer> listOfNumbers;

    public Reader(CopyOnWriteArrayList<Integer> listOfNumbers) {
        this.listOfNumbers = listOfNumbers;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000); // задержка
                if (!listOfNumbers.isEmpty()) {
                    Integer number = listOfNumbers.get(listOfNumbers.size() - 1);
                    System.out.println("Reading number: " + number);
                    listOfNumbers.remove(number);
                } else {
                    System.out.println("No numbers to read.");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

