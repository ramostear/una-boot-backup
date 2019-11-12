package com.ramostear.unaboot.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface UploaderService {

    String upload(MultipartFile file);

    void delete(String url);

    void delete(Collection<String> urls);
}
