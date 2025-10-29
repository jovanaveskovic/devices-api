package challenge.devices.service;

import challenge.devices.domain.Device;
import challenge.devices.dto.CreateDeviceRequest;
import challenge.devices.dto.DeviceResponse;
import challenge.devices.dto.UpdateDeviceRequest;
import challenge.devices.exception.DeviceInUseException;
import challenge.devices.exception.DeviceNotFoundException;
import challenge.devices.repository.DeviceRepository;
import challenge.devices.service.mapper.DeviceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class DefaultDeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceMapper deviceMapper;

    @InjectMocks
    private DefaultDeviceService deviceService;

    @Test
    void shouldCreateDevice() {
        CreateDeviceRequest deviceRequest = mock(CreateDeviceRequest.class);
        Device device = mock(Device.class);
        Device savedDevice = mock(Device.class);
        DeviceResponse deviceResponse = mock(DeviceResponse.class);
        doReturn(device).when(deviceMapper).toDevice(deviceRequest);
        when(deviceRepository.save(device)).thenReturn(savedDevice);
        doReturn(deviceResponse).when(deviceMapper).toResponse(savedDevice);

        DeviceResponse actualResponse = deviceService.createDevice(deviceRequest);

        assertThat(actualResponse).isEqualTo(deviceResponse);
    }

    @Test
    void shouldFullyUpdateDevice() {
        Long id = 123L;
        UpdateDeviceRequest deviceRequest = mock(UpdateDeviceRequest.class);
        Device device = mock(Device.class);
        Device savedDevice = mock(Device.class);
        DeviceResponse deviceResponse = mock(DeviceResponse.class);
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));
        doReturn(device).when(deviceMapper).toUpdatedDevice(device, deviceRequest);
        when(deviceRepository.save(device)).thenReturn(savedDevice);
        doReturn(deviceResponse).when(deviceMapper).toResponse(savedDevice);

        DeviceResponse actualResponse = deviceService.updateDevice(id, deviceRequest);

        assertThat(actualResponse).isEqualTo(deviceResponse);
    }

    @Test
    void shouldPartiallyUpdateDevice() {
        Long id = 123L;
        UpdateDeviceRequest deviceRequest = mock(UpdateDeviceRequest.class);
        Device device = mock(Device.class);
        Device savedDevice = mock(Device.class);
        DeviceResponse deviceResponse = mock(DeviceResponse.class);
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));
        doReturn(device).when(deviceMapper).toPartiallyUpdatedDevice(device, deviceRequest);
        when(deviceRepository.save(device)).thenReturn(savedDevice);
        doReturn(deviceResponse).when(deviceMapper).toResponse(savedDevice);

        DeviceResponse actualResponse = deviceService.partialUpdateDevice(id, deviceRequest);

        assertThat(actualResponse).isEqualTo(deviceResponse);
    }

    @Test
    void shouldNotUpdateDevice_deviceNotFound() {
        Long id = 123L;
        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> deviceService.getDevice(id));

        assertThat(thrown).isInstanceOf(DeviceNotFoundException.class);
    }

    @Test
    void shouldNotUpdateDevice_deviceInUse() {
        Long id = 123L;
        UpdateDeviceRequest deviceRequest = mock(UpdateDeviceRequest.class);
        Device device = mock(Device.class);
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));
        doReturn(device).when(deviceMapper).toUpdatedDevice(device, deviceRequest);

        doThrow(new DeviceInUseException("Cannot update name or brand of a device in use"))
                .when(deviceRepository).save(device);

        assertThrows(DeviceInUseException.class, () -> deviceService.updateDevice(id, deviceRequest));
    }

    @Test
    void shouldGetDevice() {
        Long id = 123L;
        Device device = mock(Device.class);
        DeviceResponse deviceResponse = mock(DeviceResponse.class);
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));
        doReturn(deviceResponse).when(deviceMapper).toResponse(device);

        DeviceResponse actualDevice = deviceService.getDevice(id);

        assertThat(actualDevice).isEqualTo(deviceResponse);
    }

    @Test
    void shouldNotGetDevice_deviceNotFound() {
        Long id = 123L;
        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> deviceService.getDevice(id));

        assertThat(thrown).isInstanceOf(DeviceNotFoundException.class);
    }

    @Test
    void shouldGetDevices() {
        Device device = mock(Device.class);
        DeviceResponse deviceResponse = mock(DeviceResponse.class);
        when(deviceRepository.getDevices(null, null)).thenReturn(List.of(device));
        when(deviceMapper.toResponse(device)).thenReturn(deviceResponse);

        List<DeviceResponse> actualDevices = deviceService.getDevices(null, null);

        assertThat(actualDevices).contains(deviceResponse);
    }

    @Test
    void shouldGetDevicesByBrand() {
        Device device = mock(Device.class);
        DeviceResponse deviceResponse = mock(DeviceResponse.class);
        when(deviceRepository.getDevices("Test brand", null)).thenReturn(List.of(device));
        when(deviceMapper.toResponse(device)).thenReturn(deviceResponse);

        List<DeviceResponse> actualDevices = deviceService.getDevices("Test brand", null);

        assertThat(actualDevices).contains(deviceResponse);
    }

    @Test
    void shouldGetDevicesByState() {
        Device device = mock(Device.class);
        DeviceResponse deviceResponse = mock(DeviceResponse.class);
        when(deviceRepository.getDevices("", Device.State.Available)).thenReturn(List.of(device));
        when(deviceMapper.toResponse(device)).thenReturn(deviceResponse);

        List<DeviceResponse> actualDevices = deviceService.getDevices("", "Available");

        assertThat(actualDevices).contains(deviceResponse);
    }

    @Test
    void shouldGetNoDevices() {
        when(deviceRepository.getDevices("Nokia", Device.State.Available)).thenReturn(List.of());

        List<DeviceResponse> actualDevices = deviceService.getDevices("Nokia", "Available");

        assertThat(actualDevices).isEmpty();
    }

    @Test
    void shouldDeleteDevice() {
        Long id = 123L;
        Device device = mock(Device.class);
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

        deviceService.deleteDevice(id);

        verify(deviceRepository).delete(device);
    }

    @Test
    void shouldNotDeleteDevice_deviceNotFound() {
        Long id = 123L;
        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> deviceService.deleteDevice(id));

        assertThat(thrown).isInstanceOf(DeviceNotFoundException.class);
    }

    @Test
    void shouldNotDeleteDevice_deviceInUse() {
        Long id = 123L;
        Device device = mock(Device.class);
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

        doThrow(new DeviceInUseException("Cannot update name or brand of a device in use"))
                .when(deviceRepository).delete(device);

        assertThrows(DeviceInUseException.class, () -> deviceService.deleteDevice(id));
    }
}
