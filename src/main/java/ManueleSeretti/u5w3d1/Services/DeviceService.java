package ManueleSeretti.u5w3d1.Services;

import ManueleSeretti.u5w3d1.Entities.Device;
import ManueleSeretti.u5w3d1.Entities.StatoDevice;
import ManueleSeretti.u5w3d1.Entities.User;
import ManueleSeretti.u5w3d1.Payloads.newDeviceDTO;
import ManueleSeretti.u5w3d1.Repositories.DeviceRepository;
import ManueleSeretti.u5w3d1.exceptions.BadRequestException;
import ManueleSeretti.u5w3d1.exceptions.NotFoundException;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    public Device save(newDeviceDTO body) throws IOException {
        User u = userService.findById(body.userID());
        Device d = new Device();
        d.setName(body.name());
        d.setStato(body.stato());
        if (d.getStato() == StatoDevice.ASSEGNATO) {
            d.setUser(u);
        } else {
            d.setUser(null);
        }
        return deviceRepository.save(d);


    }

    public Device assegnaDevice(long id, newDeviceDTO body) throws IOException {

        User u = userService.findById(body.userID());
        Device deviceFound = this.findById(id);
        Device d = new Device();
        d.setName(body.name());
        d.setStato(body.stato());
        d.setUser(u);
        if (deviceFound.getStato() != StatoDevice.DISPONIBILE || d.getUser() == null) {

            throw new BadRequestException("non è possibile assegnare questo dispositivo!!! ");
        }
        return deviceRepository.save(d);
    }

    public Page<Device> getDevices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return deviceRepository.findAll(pageable);
    }

    public Device findById(long id) {
        return deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) {
        Device d = this.findById(id);
        deviceRepository.delete(d);
    }

    public Device findByIdAndUpdate(long id, newDeviceDTO body) {
        Device found = this.findById(id);
        User u = userService.findById(body.userID());
        found.setName(body.name());
        found.setStato(body.stato());
        found.setUser(u);


        return deviceRepository.save(found);

    }

}



