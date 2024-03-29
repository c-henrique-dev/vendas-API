package br.com.vendas.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.vendas.entities.Usuario;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        try {
            long expString = Long.valueOf(expiracao);
            LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
            Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
            Date data = Date.from(instant);
            Algorithm algorithm = Algorithm.HMAC512(chaveAssinatura);
            String token = JWT.create()
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(data)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token");
        }
    }

    public String tokenValido(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(chaveAssinatura);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

}
