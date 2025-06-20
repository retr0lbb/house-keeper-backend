package com.retr0lbb.housekeeper.entitys;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "devices")
public class DeviceEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column (unique = true, name = "device_slug")
    private String deviceSlug;

    @Column(unique = true)
    private String deviceSerial;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Column(name = "added_at")
    private LocalDateTime addedAt = LocalDateTime.now();

    @Column(name = "last_change_at")
    private LocalDateTime lastChangeAt;

    private String status;

    public DeviceEntity(UUID id, String deviceSerial, UserEntity user, RoomEntity room, LocalDateTime addedAt, LocalDateTime lastChangeAt, String status, String deviceSlug) {
        this.id = id;
        this.deviceSerial = deviceSerial;
        this.user = user;
        this.room = room;
        this.addedAt = addedAt;
        this.lastChangeAt = lastChangeAt;
        this.status = status;
        this.deviceSlug = deviceSlug;
    }

    public DeviceEntity(){
        super();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDateTime getLastChangeAt() {
        return lastChangeAt;
    }

    public void setLastChangeAt(LocalDateTime lastChangeAt) {
        this.lastChangeAt = lastChangeAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeviceSlug() {
        return deviceSlug;
    }

    public void setDeviceSlug(String deviceSlug) {
        this.deviceSlug = deviceSlug;
    }
}