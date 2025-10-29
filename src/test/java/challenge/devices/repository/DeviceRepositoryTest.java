package challenge.devices.repository;

import challenge.devices.domain.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void shouldFindByBrand() {
        Device device = new Device();
        device.setName("Router");
        device.setBrand("Nokia");
        device.setState(Device.State.Available);
        device.setCreationTime(Instant.now());
        deviceRepository.save(device);

        List<Device> result = deviceRepository.getDevices("Nokia", null);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getBrand()).isEqualTo("Nokia");
    }

    @Test
    void shouldFindByState() {
        Device device = new Device();
        device.setName("Router");
        device.setBrand("Nokia");
        device.setState(Device.State.In_use);
        device.setCreationTime(Instant.now());
        deviceRepository.save(device);

        List<Device> result = deviceRepository.getDevices(null, Device.State.In_use);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getState()).isEqualTo(Device.State.In_use);
    }
}
