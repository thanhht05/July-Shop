package Spring_MVC.JulyShop.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadService {

    private final ServletContext servletContext;

    public UploadService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String handleUploadFile(MultipartFile file, String tagetFolder) throws IOException {
        if (file.isEmpty()) {
            return "";
        }

        // get url from project
        // this is return D:/JulyShop/src...
        String rootPath = this.servletContext.getRealPath("/resources/img");
        byte[] bytes = file.getBytes();

        String fileName = "";

        File dir = new File(rootPath + File.separator + tagetFolder);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // save file with unique with ms
        fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

        // save file
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        return fileName;

    }

}
