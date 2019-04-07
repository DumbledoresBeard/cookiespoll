package net.cookiespoll.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class Cookie {
    private int id;
    private String name;
    private String description;
    private File file;
}
