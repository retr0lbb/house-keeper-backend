package com.retr0lbb.housekeeper.deviceModule;

import com.retr0lbb.housekeeper.deviceModule.dto.CreateDeviceDTO;
import com.retr0lbb.housekeeper.deviceModule.dto.UpdateDeviceDTO;
import com.retr0lbb.housekeeper.entitys.DeviceEntity;
import com.retr0lbb.housekeeper.entitys.RoomEntity;
import com.retr0lbb.housekeeper.repository.DeviceRepository;
import com.retr0lbb.housekeeper.repository.RoomRepository;
import com.retr0lbb.housekeeper.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    DeviceRepository deviceRepository;

    public DeviceEntity create(CreateDeviceDTO createDeviceDTO, UUID userId) throws BadRequestException {
        var user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new BadRequestException("User not found");
        }

        DeviceEntity device = new DeviceEntity();

        device.setDeviceSlug(createDeviceDTO.deviceSlug());
        device.setDeviceSerial(createDeviceDTO.deviceSerial());
        device.setUser(user.get());

        if (createDeviceDTO.roomId() != null) {
            Optional<RoomEntity> room = roomRepository.findById(createDeviceDTO.roomId());

            if (room.isEmpty()) {
                throw new BadRequestException("Room not found");
            }

            device.setRoom(room.get());
        }

        return deviceRepository.save(device);

    }

    public DeviceEntity updateDevice(UpdateDeviceDTO updateDeviceDTO, UUID deviceId, UUID userID) throws BadRequestException {
        Optional<DeviceEntity> deviceOpt = deviceRepository.findById(deviceId);

        if (deviceOpt.isEmpty()) {
            throw new BadRequestException("Device not found");
        }

        DeviceEntity device = deviceOpt.get();

        var requestedUserOpt = userRepository.findById(userID);

        if(requestedUserOpt.isEmpty()){
            throw new BadRequestException("User not found");
        }

        var requestedUser = requestedUserOpt.get();

        if(!device.getUser().getId().equals(requestedUser.getId()) && !requestedUser.getAccessLevel().getDescription().equals("admin")){
            throw new BadRequestException("You are not the owner of this device");
        }

        device.setDeviceSlug(updateDeviceDTO.deviceSlug());
        device.setDeviceSerial(updateDeviceDTO.deviceSerial());

        // 4. Atualizar Room (se tiver)
        if (updateDeviceDTO.roomId() != null) {
            Optional<RoomEntity> roomOpt = roomRepository.findById(updateDeviceDTO.roomId());
            if (roomOpt.isEmpty()) {
                throw new BadRequestException("Room not found");
            }
            device.setRoom(roomOpt.get());
        } else {
            device.setRoom(null); // Remove o vínculo caso o usuário queira tirar o device do quarto
        }

        // 5. Salvar no banco
        return deviceRepository.save(device);
    }

    public Page<DeviceEntity> readAllDevices(int page, int size, UUID userId, String query, String order) {
        // Definindo a ordenação
        Sort sort = "desc".equalsIgnoreCase(order) ?
                Sort.by("addedAt").descending() :
                Sort.by("addedAt").ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        boolean hasUserId = userId != null;
        boolean hasQuery = query != null && !query.isBlank();

        if (hasUserId && hasQuery) {
            return deviceRepository.findByUserIdAndDeviceSlugContainingIgnoreCase(userId, query, pageable);
        } else if (hasUserId) {
            return deviceRepository.findByUserId(userId, pageable);
        } else if (hasQuery) {
            return deviceRepository.findByDeviceSlugContainingIgnoreCase(query, pageable);
        } else {
            return deviceRepository.findAll(pageable);
        }
    }
}
