package com.example.validationexample.service;

import com.example.validationexample.configuration.AiriConfig;
import com.example.validationexample.entitiy.Product;
import com.example.validationexample.exception.FileException;
import com.example.validationexample.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AiriConfig airiConfig;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product createProductImage(Long id, MultipartFile file) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " not found."));
        String newFileName = saveImage(file);
        existingProduct.setImage_path(newFileName);

        return productRepository.save(existingProduct);
    }

    private void sampleMethod() {
        Integer port = airiConfig.getPort();
        String host = airiConfig.getHost();
    }


    public Product updateProductImage(Long id, MultipartFile file) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " not found."));

        deleteImage(existingProduct.getImage_path());

        String newFileName = saveImage(file);
        existingProduct.setImage_path(newFileName);

        return productRepository.save(existingProduct);
    }

    private String saveImage(MultipartFile file) {
        String fileRealName = file.getOriginalFilename(); //파일명을 얻어낼 수 있는 메서드!
        long kb = file.getSize() / 1024;
        System.out.println("ファイル名 " + fileRealName);
        System.out.println("容量サイズ(byte) : " + file.getSize());
        System.out.println("容量サイズ(kb) : " + kb);
        String fileExtension = fileRealName.substring(fileRealName.lastIndexOf(".") + 1);
        System.out.println("拡張子 : " + fileExtension);

        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());

        // 1. １００KB以上ならエラーを出す。
        if (kb > 100) {
            // TODO: 後でCustom Exceptionを作って、それをthrowするようにする。 (400 Error ?)
            System.out.println("100以上だよ？！危ない！");
            throw new FileException("100KB以上だよ？！危ない！");
        } else {
            System.out.println("危なくない！");
        }

//       2. 拡張子がjpeg, jpg, png, gif以外ならエラーを出す。
        if (fileExtension.equals("jpeg") || fileExtension.equals("jpg") || fileExtension.equals("png") || fileExtension.equals("gif")) {
            System.out.println("拡張子がjpeg, jpg, png, gifの一つだからおっけい！");
        } else {
            // TODO: 後でCustom Exceptionを作って、それをthrowするようにする。(400 Error ?)
            System.out.println("拡張子がjpeg, jpg, png, gifの一つじゃないからだめだよ！");
            throw new FileException("拡張子がjpeg, jpg, png, gifの一つじゃないからだめだよ！");
        }


        // 3. Folder があるかないか確認する処理
        Path imageFolder = Paths.get("images");
        if (Files.exists(imageFolder)) {
            System.out.println("images folderがすでに存在する");
        } else {
            System.out.println("images folderが存在しない");
            try {
                Files.createDirectories(imageFolder);
            } catch (IOException e) {
//                throw new RuntimeException(e);
                throw new FileException("Directoryの作成に失敗しました！");
            }

            System.out.println("images folderが存在しなかったからSpringBootで作成した");
        }

        // 4. File保存
        Path imageFile = imageFolder.resolve(uuid.toString() + "." + fileExtension);
        try {
            file.transferTo(imageFile);
        } catch (IOException e) {
            throw new FileException("ファイルの保存に失敗しました!");
        }

        return imageFile.toString();
    }

    private void deleteImage(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            if (Files.exists(path)) {
                Files.delete(path);
            } else {
                throw new RuntimeException("ファイルはこのパスで存在しません： " + imagePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("ファイル削除に失敗しました!" + imagePath, e);
        }
    }

}
