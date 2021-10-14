package com.ecommerce.microcommerce.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.microcommerce.dao.IProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class ProductController {

	@Autowired
	IProductDao productDao;

	// Un produit

	@GetMapping(value = "/Produits/{id}")
	public Product afficherUnProduit(@PathVariable int id) {
		Product product = productDao.findById(id);
		return product;
	}

	// Tous les produits
	/*
	 * @GetMapping(value = "/Produits")
	 * 
	 * public List<Product> listeProduits() { return productDao.findAll(); }
	 */
	// @RequestMapping(value = "/Produits", method = RequestMethod.GET)
	@GetMapping(value = "/Produits")
	public MappingJacksonValue listeProduits() {
		Iterable<Product> produits = productDao.findAll();

		SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

		FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

		MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

		produitsFiltres.setFilters(listDeNosFiltres);

		return produitsFiltres;
	}

	@GetMapping(value = "test/produits/{prixLimit}")
	public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
		return productDao.findByPrixGreaterThan(prixLimit);
	}

	@GetMapping(value = "test2/produits/{recherche}")
	public List<Product> testeDeRequetes(@PathVariable String recherche) {
		return productDao.findByNomLike("%" + recherche + "%");
	}

	@GetMapping(value = "test3/produits/{prix}")
	public List<Product> testeDeRequetes2(@PathVariable int prix) {
		return productDao.chercherUnProduitCher(prix);
	}

	// Ajouter un produit
	/*
	 * @PostMapping(value = "/Produits") public Product ajouterProduit(@RequestBody
	 * Product product) { Product addProduct = productDao.save(product); return
	 * addProduct; }
	 */

	@PostMapping(value = "/Produits")
	public ResponseEntity<Void> ajouterProduit(@RequestBody Product product) {
		Product addProduct = productDao.save(product);
		if (addProduct == null)
			return ResponseEntity.noContent().build();
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addProduct.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(value = "/Produits/{id}")
	public void supprimerProduit(@PathVariable int id) {

		productDao.delete(productDao.findById(id));
	}

	@PutMapping(value = "/Produits")
	public void updateProduit(@RequestBody Product product) {

		productDao.save(product);
	}
}
