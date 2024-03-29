package br.com.vendas.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.vendas.dtos.CredenciaisDto;
import br.com.vendas.dtos.TokenDto;
import br.com.vendas.dtos.UsuarioDto;
import br.com.vendas.dtos.UsuarioLogadoDto;
import br.com.vendas.entities.Usuario;
import br.com.vendas.security.jwt.JwtService;
import br.com.vendas.service.impl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuario")
@CrossOrigin
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid UsuarioDto usuarioDto) {
        String senhaCriptografada = passwordEncoder.encode(usuarioDto.getSenha());
        usuarioDto.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuarioDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody @Valid UsuarioDto usuarioDto) {
        this.usuarioService.update(id, usuarioDto);
    }

    @PostMapping("/auth")
    public TokenDto autenticar(@RequestBody CredenciaisDto credenciais) {
        UsuarioDto usuarioDto = new UsuarioDto();
        Usuario usuario = Usuario.builder()
                .login(credenciais.getLogin())
                .senha(credenciais.getSenha()).build();
        BeanUtils.copyProperties(usuario, usuarioDto);
        Usuario novoUsuario = this.usuarioService.listarPorLogin(credenciais.getLogin());
        this.usuarioService.autenticar(usuarioDto);
        String token = jwtService.gerarToken(novoUsuario);
        return new TokenDto(token);
    }

    @GetMapping("/usuarioLogado")
    public UsuarioLogadoDto obterUsuarioLogado() {
        return this.usuarioService.obterUsuarioLogado();
    }

}
