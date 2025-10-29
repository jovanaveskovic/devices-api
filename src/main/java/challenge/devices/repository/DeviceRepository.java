package challenge.devices.repository;

import challenge.devices.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query("""
        SELECT d FROM Device d
        WHERE (:brand IS NULL OR :brand = '' OR d.brand = :brand)
          AND (:state IS NULL OR d.state = :state)
    """)
    List<Device> getDevices(@Param("brand") String brand, @Param("state") Device.State state);
}
