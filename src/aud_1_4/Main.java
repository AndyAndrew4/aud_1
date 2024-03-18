package aud_1_4;

public class Main {
    public static void main(String[] args) {
        int numPatients = 10;
        Thread[] threads = new Thread[numPatients];

        for (int i = 0; i < numPatients; i++) {
            threads[i] = new Thread(new Patient("Больной " + (i + 1)));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // стоп потока информации
        Clinic.getInstance().stopProcessing();

        // нужный вывод
        System.out.println("Максимально в очереди: " + Clinic.getInstance().getMaxQueueLength());
    }
}
