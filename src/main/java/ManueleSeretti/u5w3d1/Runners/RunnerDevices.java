package ManueleSeretti.u5w3d1.Runners;

import ManueleSeretti.u5w3d1.Services.DeviceService;
import ManueleSeretti.u5w3d1.Services.UserService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
public class RunnerDevices implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private DeviceService deviceService;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker(Locale.ITALY);
        Random rndm = new Random();

//        for (int i = 0; i < 50; i++) {
//
//            long userId = userService.findById(rndm.nextInt(1, 31)).getId();
//            newDeviceDTO d = new newDeviceDTO(faker.commerce().productName(), StatoDevice.randomDeviceStatus(), userId);
//            deviceService.save(d);
//        }

    }
}
