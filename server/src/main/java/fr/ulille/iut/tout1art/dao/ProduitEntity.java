package fr.ulille.iut.tout1art.dao;


import javax.persistence.*;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ulille.iut.tout1art.dto.ProduitCreateDto;
import fr.ulille.iut.tout1art.dto.ProduitDto;
import fr.ulille.iut.tout1art.dto.PizzaCreateDto;
import fr.ulille.iut.tout1art.dto.PizzaDto;
import fr.ulille.iut.tout1art.dto.PizzaShortDto;

import java.util.*;

@Entity
@Table(name = "produit")

@NamedQueries({
    @NamedQuery(name="FindAllProduits", query="SELECT p from ProduitEntity p"),
    @NamedQuery(name="CheckProduitName", query="SELECT count(p) from ProduitEntity p where p.nom = :pnom and p.id <> :pid"),
    @NamedQuery(name="FindProduitByName", query="SELECT p from ProduitEntity p where p.nom = :pnom")
})

public class ProduitEntity {
    private final static Logger logger = LoggerFactory.getLogger(ProduitEntity.class);
    private static ModelMapper modelMapper = new ModelMapper();

    private long id;
    private String nom;
    private String description; 
    private double prix;
    private int idProduit;

    public static ProduitEntity convertFromProduitCreateDto(ProduitCreateDto produit) {
        return modelMapper.map(produit, ProduitEntity.class);
    }

    public static ProduitDto convertToDto(ProduitEntity produit) {
		return  modelMapper.map(produit, ProduitDto.class);
	}
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
	public long getId() {
        return id;
    }

	public void setId(long id) {
        this.id = id;
    }
    
    @Basic
    @Column(name = "nom", nullable = false, length = -1)
	public String getNom() {
        return nom;
    }

	public void setNom(String nom) {
        this.nom = nom;
    }

	/*@Basic
    @Column(name = "base", length = -1)
	public String getBase() {
        return base;
    }*/

/*
    @ManyToMany /*(cascade = CascadeType.ALL)*/ /* (fetch = FetchType.EAGER)
    @JoinTable(name = "ingredientpizza",
            joinColumns = @JoinColumn(name = "idpizza", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "idingredient", referencedColumnName = "id", nullable = false))
	public Set<IngredientEntity> getIngredients() {
        return ingredients;
    }
*/


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitEntity that = (ProduitEntity) o;
        return id == that.id &&
                Objects.equals(nom, that.nom);
    }

    public int hashCode() {
        return Objects.hash(id, nom);
    }

	public Collection<ProduitDto> getProduits() {
		// TODO Auto-generated method stub
		return null;
	}
}
