package com.ecommerce.microcommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.microcommerce.model.Product;

public interface IProductDao extends JpaRepository<Product, Integer> {
	/*
	 * public List<Product> findAll();
	 * 
	 * public Product findById(int id);
	 * 
	 * public Product save(Product product);
	 */
	public Product findById(int id);

	List<Product> findByPrixGreaterThan(int prixLimit);

	List<Product> findByNomLike(String recherche);

	// @Query("SELECT id, nom, prix, prix_Achat FROM Product p WHERE p.prix >
	// :prixLimit")
	@Query(value = "SELECT id, nom, prix, prix_Achat FROM Product p WHERE p.prix > :prixLimit", nativeQuery = true)
	List<Product> chercherUnProduitCher(@Param("prixLimit") int prix);
}
