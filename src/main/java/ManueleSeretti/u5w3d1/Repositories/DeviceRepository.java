package ManueleSeretti.u5w3d1.Repositories;

import ManueleSeretti.u5w3d1.Entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
