package com.retr0lbb.housekeeper.entitys;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_devices")
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "device_id")
    private UUID deviceID;

    @Column(unique = true)
    private Long deviceSerial;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id") // define a FK para o usuário
    private UserModel user;

    public DeviceEntity(){

    }

    public DeviceEntity(UUID deviceID, Long deviceSerial, LocalDateTime addedAt, String name, UserModel user) {
        super();
        this.deviceID = deviceID;
        this.deviceSerial = deviceSerial;
        this.addedAt = addedAt;
        this.name = name;
        this.user = user;
    }

    public UUID getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(UUID deviceID) {
        this.deviceID = deviceID;
    }

    public Long getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(Long deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
