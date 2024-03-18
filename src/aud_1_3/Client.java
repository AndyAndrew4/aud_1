package aud_1_3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

enum ClientCategory {
    YOUNG, ELDERLY, BUSINESS
}

class Client implements Runnable {
    private static final Random random = new Random();
    private final ClientCategory category;
    private final ServiceWindow[] serviceWindows;
    private final AtomicInteger[] disappointedCounts;
    private final AtomicInteger[] totalCounts;

    public Client(ClientCategory category, ServiceWindow[] serviceWindows, AtomicInteger[] disappointedCounts, AtomicInteger[] totalCounts) {
        this.category = category;
        this.serviceWindows = serviceWindows;
        this.disappointedCounts = disappointedCounts;
        this.totalCounts = totalCounts;
    }

    @Override
    public void run() {
        int windowIndex = random.nextInt(serviceWindows.length);
        ServiceWindow window = serviceWindows[windowIndex];
        totalCounts[category.ordinal()].incrementAndGet();

        if (!window.tryServeClient(category)) {
            disappointedCounts[category.ordinal()].incrementAndGet();
            System.out.println("Client from category " + category + " left disappointed.");
        }
    }
}

