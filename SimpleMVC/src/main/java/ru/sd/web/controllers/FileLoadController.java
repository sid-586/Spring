package ru.sd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.sd.app.exceptions.EmptyFileLoadException;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = "/books/load")
public class FileLoadController {
    private final Logger logger = Logger.getLogger(FileLoadController.class);
    private static final Path ROOT = Path.of(System.getProperty("catalina" +
            ".home"));
    private static final String STORAGE = ROOT + File.separator + "external_uploads";

    @GetMapping
    public String load(Model model) throws IOException {
        logger.info("Load page");
        model.addAttribute("filelist", getFileList());
        return "load_page";
    }

    @PostMapping("/upload_files")
    public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception, EmptyFileLoadException {

        if (file.isEmpty()) {
            throw new EmptyFileLoadException("No such file");
        }
        String name = file.getOriginalFilename();
        byte[] fileContent = file.getBytes();

        if (!Files.exists(ROOT.resolve(STORAGE))) {
            try {
                logger.info("Try to create directory in " + STORAGE);
                Files.createDirectory(ROOT.resolve(STORAGE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try (OutputStream os =
                     Files.newOutputStream(Files.createFile(
                             Path.of(ROOT.resolve(STORAGE) + File.separator + name)))) {
            os.write(fileContent);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        logger.info("New file was saved at " + STORAGE);

        return "redirect:/books/load";
    }

    @GetMapping(value = "/download_files")
    public void downloadFiles(@RequestParam String fileName,
                              HttpServletResponse response) throws IOException, EmptyFileLoadException {
        logger.info("Download file " + fileName + " started");

        File downloadFile = new File(STORAGE + File.separator + fileName);

        if(!Files.exists(downloadFile.toPath())) {
            throw new EmptyFileLoadException("This file has been already " +
                    "deleted (:");
        }
        String mimeType = URLConnection.guessContentTypeFromName(fileName);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        response.setHeader("Content-Disposition", String.format("inline; " +
                "filename=\"%s\"", fileName));
        logger.info("File to download - " + fileName + " type - " + mimeType);

        try (InputStream is =
                     Files.newInputStream(ROOT.resolve(STORAGE + File.separator + fileName));
             OutputStream os = response.getOutputStream()) {
            FileCopyUtils.copy(is, os);
            logger.info("Download completed");
            os.flush();
        }

    }

    @PostMapping(value = "/back")
    public String backToPrevious() {
        return "redirect:/books/shelf";
    }

    public List<String> getFileList() throws IOException {
        List<String> fileList;
        try (Stream<Path> input =
                     Files
                             .list(ROOT.resolve(STORAGE))) {
            fileList = input
                    .map(m -> m.getFileName().toFile().getName())
                    .collect(Collectors.toList());
        }

        fileList.forEach(logger::info);
        return fileList;
    }
}
