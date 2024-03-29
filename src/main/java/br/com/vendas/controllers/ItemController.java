package br.com.vendas.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import br.com.vendas.entities.Item;
import br.com.vendas.service.interfaces.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/itens")
@Tag(name = "Item")
@CrossOrigin
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public List<Item> obterItem(@PathVariable Integer id) throws Exception {
        return this.itemService.obterItens(id);
    }

}
