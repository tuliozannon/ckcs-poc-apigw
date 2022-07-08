package com.ckcspoc.ckcspocapigw.common.dto.editor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditorBundleConfigDto {
    private EditorBundleCloudServicesDto cloudServices;
    private List<String> toolbar = new ArrayList<String>();
}
