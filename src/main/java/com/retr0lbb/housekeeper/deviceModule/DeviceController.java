package com.retr0lbb.housekeeper.deviceModule;

import com.retr0lbb.housekeeper.authservice.controllers.TokenController;
import com.retr0lbb.housekeeper.authservice.dto.CreateUserDTO;
import com.retr0lbb.housekeeper.deviceModule.dto.CreateDeviceDTO;
import com.retr0lbb.housekeeper.deviceModule.dto.UpdateDeviceDTO;
import com.retr0lbb.housekeeper.entitys.DeviceEntity;
import com.retr0lbb.housekeeper.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final JwtEncoder jwtEncoder;

    @Autowired
    DeviceService deviceService;

    public DeviceController(JwtEncoder jwtEncoder){
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping
    public ResponseEntity<DeviceEntity> create(@RequestBody CreateDeviceDTO dto, JwtAuthenticationToken token){
        UUID userId = UUID.fromString(token.getName());

        try{
            var device = deviceService.create(dto, userId);
            return ResponseEntity.ok(device);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<DeviceEntity> update(@RequestBody UpdateDeviceDTO dto, JwtAuthenticationToken token, @PathVariable("deviceId") UUID deviceId){
        UUID userId = UUID.fromString(token.getName());

        try{
            var device = deviceService.updateDevice(dto, deviceId, userId);
            return ResponseEntity.ok(device);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DeviceEntity>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "asc") String order
    ) {
        try {
            // Validação simples pro parâmetro order
            if (!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
                return ResponseEntity.badRequest().build();
            }

            Page<DeviceEntity> devices = deviceService.readAllDevices(page, size, userId, query, order);
            return ResponseEntity.ok(devices);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity deleteDevice(@PathVariable(name = "deviceId") UUID deviceId, JwtAuthenticationToken token){
        UUID userId = UUID.fromString(token.getName());
        try{
            deviceService.deleteDevice(deviceId, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
