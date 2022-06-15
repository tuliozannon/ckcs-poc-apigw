package com.ckcspoc.ckcspocapigw.common.dto.event.collaboration;

import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSPayloadDocumentDto;
import com.ckcspoc.ckcspocapigw.common.dto.event.CKCSPayloadUserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class CKCSEventUserConnectedDto {
    public static final String EVENT_KEY = "collaboration.user.connected";

    private CKCSPayloadUserDto user;
    private CKCSPayloadDocumentDto document;
    private List<CKCSPayloadUserDto> connected_users;
}
