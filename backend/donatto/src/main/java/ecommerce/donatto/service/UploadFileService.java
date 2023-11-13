package ecommerce.donatto.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileService {
    private String rutaAbs = "C:\\Users\\Usuario\\Desktop\\e-commerce\\backend\\donatto\\uploads";

    public String saveImage(MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            byte [] bytes=image.getBytes();
            Path rutaA = Paths.get(rutaAbs+image.getOriginalFilename());
            Files.write(rutaA, bytes);
            return image.getOriginalFilename();
        }
        return "default.jpg";
    }



    public void deleteImage(String name) {
        String ruta="C:\\Users\\Usuario\\Desktop\\e-commerce\\backend\\donatto\\uploads";
        File file = new File(ruta+name);
        file.delete();
    }
}
