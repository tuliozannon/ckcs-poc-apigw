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
public class CKCSEventUserDisconnectedDto {
    public static final String EVENT_KEY = "collaboration.user.disconnected";

    private CKCSPayloadUserDto user;
    private CKCSPayloadDocumentDto document;
    private List<CKCSPayloadUserDto> connected_users;
}
