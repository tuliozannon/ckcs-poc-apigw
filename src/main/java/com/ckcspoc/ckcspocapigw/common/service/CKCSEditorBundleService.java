package com.ckcspoc.ckcspocapigw.common.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CKCSEditorBundleService {
    public String getEditorBundleFromResource(String editorBundleName){
        return this.loadEditorBundlesFromResources().get(editorBundleName);
    }

    private Map<String, String> loadEditorBundlesFromResources() {
        Map<String, String> bundlesMap = new HashMap<String, String>();
        String CLASSPATH_BUNDLES_PATTERN = "classpath:bundles/*/*.js";
        String BUNDLES_PATH = "bundles/";

        ClassLoader classLoader = this.getClass().getClassLoader();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
        try {
            Resource[] resources = resolver.getResources(CLASSPATH_BUNDLES_PATTERN);
            Arrays.stream(resources)
                    .map(resource -> {
                        try {
                            InputStream inputStream = resource.getInputStream();
                            byte[] data = new byte[inputStream.available()];
                            inputStream.read(data, 0, data.length);
                            String bundleStr = new String(data, StandardCharsets. UTF_8);
                            String bundleName = FilenameUtils.removeExtension(resource.getFile().getName());
                            bundlesMap.put(bundleName, bundleStr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return resource;
                    })
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            log.error("Failed to get resources from " + CLASSPATH_BUNDLES_PATTERN, ex);
        }
        log.info("Bundles map = " + bundlesMap);
        return bundlesMap;
    }


}
