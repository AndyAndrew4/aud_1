package aud_1_3;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicBoolean;

class ServiceWindow {
    private final AtomicBoolean isBusy = new AtomicBoolean(false);
    private final EnumSet<ClientCategory> supportedCategories;

    public ServiceWindow(EnumSet<ClientCategory> supportedCategories) {
        this.supportedCategories = supportedCategories;
    }

    public boolean tryServeClient(ClientCategory category) {
        if (supportedCategories.contains(category) && !isBusy.getAndSet(true)) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                isBusy.set(false);
            }
            return true;
        }
        return false;
    }
}
