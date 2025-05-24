package com.retr0lbb.housekeeper.authservice.dto;

public record LoginResponse(String accessToken, long expiresIn) {

}
