package com.clientui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {

	@Autowired
    private MicroserviceProduitsProxy ProduitsProxy;

    @RequestMapping("/")
    public String accueil(Model model){
    	List<ProductBean> produits = ProduitsProxy.listeDesProduits();
    	model.addAttribute("produits",produits);
        return "Accueil";
    }

}
