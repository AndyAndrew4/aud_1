package aud_1_4;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

class Clinic {
    private static final Clinic instance = new Clinic();
    private final BlockingQueue<Patient> queue = new LinkedBlockingQueue<>();
    private final AtomicBoolean processing = new AtomicBoolean(true);
    private int maxQueueLength = 0;
    private final Object lock = new Object();
    private final Random random = new Random();
    private final Thread processingThread;

    private Clinic() {
        processingThread = new Thread(() -> {
            while (processing.get() || !queue.isEmpty()) {
                processQueue();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        processingThread.start();
    }

    public static Clinic getInstance() {
        return instance;
    }

    public void addPatientToQueue(Patient patient) {
        synchronized (lock) {
            queue.add(patient);
            if (queue.size() > maxQueueLength) {
                maxQueueLength = queue.size();
            }
        }
    }

    private void processQueue() {
        Patient patient = queue.poll();
        if (patient != null) {
            System.out.println(patient.getName() + " у терапевта.");
            try {
                Thread.sleep(random.nextInt(500) + 500); // Random checkup time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(patient.getName() + " ушел на МРТ.");
            try {
                Thread.sleep(random.nextInt(500) + 500); // Random MRI time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(patient.getName() + " вышел с МРТ.");
        }
    }

    public void stopProcessing() {
        processing.set(false);
        try {
            processingThread.join(); // Ожидаем завершения потока обработки
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }
}
