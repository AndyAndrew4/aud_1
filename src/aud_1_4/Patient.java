package aud_1_4;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

class Patient implements Runnable {
    private static final int MIN_ARRIVAL_TIME = 300;
    private static final int MAX_ARRIVAL_TIME = 600;

    private final String name;
    private final Random random = new Random();

    public Patient(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(MAX_ARRIVAL_TIME - MIN_ARRIVAL_TIME) + MIN_ARRIVAL_TIME);
            System.out.println(name + " приехал в поликлинику.");

            Clinic.getInstance().addPatientToQueue(this);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}