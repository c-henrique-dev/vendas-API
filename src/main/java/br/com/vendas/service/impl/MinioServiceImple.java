package br.com.vendas.service.impl;

import br.com.vendas.service.interfaces.MinioService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MinioServiceImple implements MinioService {
    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    public String bucket;

    @Value("${minio.put-object-part-size}")
    private Long putObjectPartSize;

    public String enviarImagem(MultipartFile file) throws Exception {
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(generateFileName(file.getOriginalFilename()))
                        .stream(file.getInputStream(), file.getSize(), putObjectPartSize)
                        .build());

        return generateFileName(file.getOriginalFilename());
    }

    public String excluirImagem(String nome) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(nome)
                        .build());

        return nome;
    }

    public String recuperarImagem(String nome) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucket)
                        .expiry(86400)
                        .object(nome)
                        .build());
    }

    public static String generateFileName(String originalName) {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String formattedDate = sdf.format(new Date(currentTimeMillis));
        String fileName = formattedDate + "-" + originalName;
        return fileName;
    }

}