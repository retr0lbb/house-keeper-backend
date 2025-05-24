package com.retr0lbb.housekeeper.deviceModule.service;

import com.retr0lbb.housekeeper.authservice.dto.CreateUserDTO;
import com.retr0lbb.housekeeper.deviceModule.dto.CreateDeviceDTO;
import com.retr0lbb.housekeeper.entitys.DeviceEntity;
import com.retr0lbb.housekeeper.repository.DeviceRepository;
import com.retr0lbb.housekeeper.repository.UserRepository;
import com.sun.tools.jconsole.JConsoleContext;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class DeviceServices {
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;


    public DeviceEntity createDevice(CreateDeviceDTO dto, UUID userId) throws BadRequestException {
        var user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new BadRequestException("User doesnot exists");
        }

        DeviceEntity newDevice = new DeviceEntity();
        newDevice.setDeviceSerial(dto.deviceSerial());
        newDevice.setUser(user.get());
        newDevice.setName(dto.name());

        return deviceRepository.save(newDevice);
    }

    public void removeDevice(UUID deviceId, UUID userId) throws Exception{
        var device = this.deviceRepository.findById(deviceId);
        if(device.isEmpty()){
            throw new Exception("Device not found");
        }
        if (!device.get().getUser().getUserId().equals(userId)) {
            throw new Exception("Cannot delete a device that's not yours");
        }
        var user = this.userRepository.findById(userId);

        if(user.isEmpty()){
            throw new Exception("User doesnot exits");
        }

        this.deviceRepository.deleteById(deviceId);
    }
}
