package com.retr0lbb.housekeeper.deviceModule.controllers;

import com.retr0lbb.housekeeper.deviceModule.dto.CreateDeviceDTO;
import com.retr0lbb.housekeeper.deviceModule.service.DeviceServices;
import com.retr0lbb.housekeeper.entitys.DeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    DeviceServices deviceServices;

    @PostMapping("/add")
    public  ResponseEntity<DeviceEntity> createDevice(@RequestBody CreateDeviceDTO dto, JwtAuthenticationToken token){
        try {
            var device = this.deviceServices.createDevice(dto, UUID.fromString(token.getName()));

            return ResponseEntity.ok(device);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable UUID id, JwtAuthenticationToken token){
        try{
            this.deviceServices.removeDevice(id, UUID.fromString(token.getName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
}
