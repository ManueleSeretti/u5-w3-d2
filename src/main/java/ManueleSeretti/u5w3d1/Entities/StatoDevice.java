package ManueleSeretti.u5w3d1.Entities;

import java.util.Random;

public enum StatoDevice {
    DISPONIBILE, ASSEGNATO, MANUTENIONE, DISMESSO;
    private static final Random rndm = new Random();

    public static StatoDevice randomDeviceStatus() {
        StatoDevice[] devices = values();
        return devices[rndm.nextInt(devices.length)];
    }
}
