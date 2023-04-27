package com.tretsoft.spa.service.utility;

import com.tretsoft.spa.web.dto.content.IconDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class IconService {

    @Value("${app.iconsFolder}")
    private String iconsFolder;

    private List<IconDto> icons;

    @PostConstruct
    public void init() throws IOException {
        icons = new LinkedList<>();

        File folder = new File(iconsFolder);
        if (!folder.exists() || !folder.isDirectory())
            throw new RuntimeException("'" + iconsFolder + "' - is not directory or not exists");

        for (File f : Objects.requireNonNull(folder.listFiles())) {
            if (!f.getName().endsWith(".svg")) continue;

            String fileName = getFilenameWithoutExtension(f);

            icons.add(IconDto.builder()
                            .name(fileName)
                            .svg(Files.readString(f.toPath()))
                            .build());
        }
    }

    private static String getFilenameWithoutExtension(File file) {
        String filename = file.getName();
        int dotIndex = filename.lastIndexOf('.');

        if (dotIndex > 0) {
            return filename.substring(0, dotIndex);
        } else {
            return filename;
        }
    }

    public List<IconDto> getAll() {
        return icons;
    }

}
