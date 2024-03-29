package br.com.vendas.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String enviarImagem(MultipartFile file) throws Exception;

    String recuperarImagem(String nome) throws Exception;
}
