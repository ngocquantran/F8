package com.example.myvocab.service;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileService {
//    Path folder asset
    private Path assetPath;
    public FileService(){
      getAssetFolder();
    }

    public void getAssetFolder(){
        String dir=Paths.get("").toAbsolutePath().toString();
        Path curDir=Paths.get(dir);
        Path parent=curDir.getParent().getParent();
        assetPath=parent.resolve("front_end/asset");
    }

//    Xem File
    public byte[] readFile(String fileName){
//        Lấy đường dẫn file tương ứng
        Path imgPath=assetPath.resolve("img/word_thumb");
        if (!Files.exists(imgPath)){
            throw new RuntimeException("Lỗi khi đọc file"+fileName);
        }
        try {
            Path file=imgPath.resolve(fileName);
            Resource resource=new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                InputStream inputStream=resource.getInputStream();
                byte[] byArr= StreamUtils.copyToByteArray(inputStream);
                inputStream.close();
                return byArr;
            }else {
                throw new RuntimeException("Lỗi khi đọc file "+fileName);
            }
        }catch (Exception e){
            throw new RuntimeException("Lỗi khi đọc file "+fileName);
        }

    }

    public List<String> getFiles(){
        Path imgPath=assetPath.resolve("img/word_thumb");
        if (!Files.exists(imgPath)){
            return new ArrayList<>();
        }
//        Lấy danh sách file ảnh
        File[] files=imgPath.toFile().listFiles();
        return Arrays.stream(files)
                .map(file -> file.getName())
                .map(name-> "api/v1/imgs/"+name).collect(Collectors.toList());
    }


}
