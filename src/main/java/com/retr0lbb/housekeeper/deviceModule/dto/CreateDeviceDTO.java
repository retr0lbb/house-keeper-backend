package com.retr0lbb.housekeeper.deviceModule.dto;

import java.util.UUID;

public record CreateDeviceDTO(String deviceSlug, String deviceSerial, UUID roomId) {
}
