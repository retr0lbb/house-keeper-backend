package com.retr0lbb.housekeeper.deviceModule.dto;

import java.util.UUID;

public record UpdateDeviceDTO(String deviceSlug, String deviceSerial, UUID roomId) {
}
