package com.abcg.controller;

import com.abcg.model.Product;
import com.abcg.model.User;
import com.abcg.service.ProductService;
import com.abcg.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private UploadFileService upload;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("products", productService.findAll());
        return "products/show";
    }
    @GetMapping("/crear")
    public String create(){
        return "products/create";
    }

    @PostMapping("/guardar")
    public String save(Product product, @RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("Este es el objeto producto {}", product);
        User u = new User(1,"","","","","","","");
        product.setUser(u);

        // Imagen
        if (product.getId() == null){ // Cuando se crea un producto

            String nameImage = upload.saveImage(file);
            LOGGER.info("Nombre de la imagen: {}",nameImage);
            product.setImage(nameImage);
        }

        productService.save(product);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Product product = new Product();
        Optional<Product> optionalProduct = productService.get(id);
        product = optionalProduct.get();

        LOGGER.info("Producto buscado: {}", product);

        model.addAttribute("product", product);

        return "products/edit";
    }

    @PostMapping("/actualizar")
    public String update(Product product, @RequestParam("img") MultipartFile file) throws IOException {
        Product p = new Product();
        p = productService.get(product.getId()).get();

        if(file.isEmpty()){ // Editamos el producto pero no cambiamos la imagen

            product.setImage(p.getImage());
        }else{
            //Eliminar cuando no sea la imagen por defecto
            if(p.getImage().equals("default.jpg")){
                upload.deleteImage(p.getImage());
            }
            String nameImage = upload.saveImage(file);
            product.setImage(nameImage);
        }
        product.setUser(p.getUser());
        productService.update(product);
        return "redirect:/productos";
    }
    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable Integer id){
        Product p = new Product();
        p = productService.get(id).get();

        //Eliminar cuando no sea la imagen por defecto
        if(p.getImage().equals("default.jpg")){
            upload.deleteImage(p.getImage());
        }
        productService.delete(id);
        return "redirect:/productos";
    }
}
