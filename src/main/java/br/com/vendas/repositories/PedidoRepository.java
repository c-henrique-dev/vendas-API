package br.com.vendas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vendas.entities.Pedido;
import br.com.vendas.entities.Usuario;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByUsuario(Usuario usuario);

    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.usuario.login = :login")
    List<Pedido> findByIdFetchItens(@Param("login") String login);
}
