package com.ckcspoc.ckcspocapigw.common.dto.editor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditorBundleDto {
    private String bundle;
    private EditorBundleConfigDto config;
}
