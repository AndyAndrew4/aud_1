package aud_1_3;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class MFC {
    private static final int NUM_CLIENTS = 100;
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        ServiceWindow[] serviceWindows = new ServiceWindow[]{
                new ServiceWindow(EnumSet.allOf(ClientCategory.class)),
                new ServiceWindow(EnumSet.of(ClientCategory.ELDERLY)),
                new ServiceWindow(EnumSet.of(ClientCategory.BUSINESS))
        };

        AtomicInteger[] disappointedCounts = new AtomicInteger[]{
                new AtomicInteger(0),
                new AtomicInteger(0),
                new AtomicInteger(0)
        };

        AtomicInteger[] totalCounts = new AtomicInteger[]{
                new AtomicInteger(0),
                new AtomicInteger(0),
                new AtomicInteger(0)
        };

        for (int i = 0; i < NUM_CLIENTS; i++) {
            ClientCategory category = ClientCategory.values()[random.nextInt(ClientCategory.values().length)];
            new Thread(new Client(category, serviceWindows, disappointedCounts, totalCounts)).start();
        }

        Thread.sleep(10000); // Обработка клиентов

        // Выводим результаты
        for (ClientCategory category : ClientCategory.values()) {
            int categoryIndex = category.ordinal();
            int total = totalCounts[categoryIndex].get();
            int disappointed = disappointedCounts[categoryIndex].get();
            double percentage = (double) disappointed / total * 100;
            System.out.printf("Percentage of disappointed %s clients: %.2f%% (%d out of %d)%n", category, percentage, disappointed, total);
        }
    }
}