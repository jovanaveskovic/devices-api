package challenge.devices.service;

import challenge.devices.domain.Device;
import challenge.devices.dto.DeviceRequest;
import challenge.devices.dto.DeviceResponse;
import challenge.devices.exception.DeviceNotFoundException;
import challenge.devices.repository.DeviceRepository;
import challenge.devices.service.mapper.DeviceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultDeviceService implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @Override
    @Transactional
    public DeviceResponse createDevice(DeviceRequest deviceRequest) {
        log.debug("createDevice(request={})", deviceRequest);
        Device device = deviceMapper.toDevice(deviceRequest);
        return transformToResponse(deviceRepository.save(device));
    }

    @Override
    @Transactional
    public DeviceResponse updateDevice(Long id, DeviceRequest deviceRequest) {
        log.debug("updateDevice(id={},request={})", id, deviceRequest);
        Device device = getDeviceOrThrowException(id);
        if (device.getState() == Device.State.IN_USE) {
            throw new IllegalStateException("Cannot update device in use");
        }
        device.setBrand(deviceRequest.getBrand());
        device.setName(deviceRequest.getName());
        return transformToResponse(deviceRepository.save(device));
    }

    @Override
    @Transactional
    public DeviceResponse getDevice(Long id) {
        log.debug("getDevice(id={})", id);
        return transformToResponse(getDeviceOrThrowException(id));
    }

    @Override
    @Transactional
    public List<DeviceResponse> getAllDevices() {
        log.debug("getAllDevices()");
        return deviceRepository.findAll()
                .stream().map(this::transformToResponse).collect(toList());
    }

    @Override
    @Transactional
    public List<DeviceResponse> getAllDevicesByBrand(String brand) {
        log.debug("getAllDevicesByBrand(brand={})", brand);
        return deviceRepository.findByBrand(brand)
                .stream().map(this::transformToResponse).collect(toList());
    }

    @Override
    @Transactional
    public List<DeviceResponse> getAllDevicesByState(String state) {
        log.debug("getAllDevicesByState(state={})", state);
        return deviceRepository.findByState(Device.State.valueOf(state))
                .stream().map(this::transformToResponse).collect(toList());
    }

    @Override
    @Transactional
    public void deleteDevice(Long id) {
        log.debug("deleteDevice(id={})", id);
        Device device = getDeviceOrThrowException(id);
        if (device.getState() == Device.State.IN_USE) {
            throw new IllegalStateException("Cannot delete device in use");
        }
        deviceRepository.delete(device);
    }

    private DeviceResponse transformToResponse(Device device) {
        return deviceMapper.toResponse(device);
    }

    private Device getDeviceOrThrowException(Long deviceId) {
        return deviceRepository.findById(deviceId).orElseThrow(DeviceNotFoundException::new);
    }
}
