package fr.ulille.iut.tout1art.dao;


import javax.persistence.*;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ulille.iut.tout1art.dto.ArtisanCreateDto;
import fr.ulille.iut.tout1art.dto.ArtisanDto;
import fr.ulille.iut.tout1art.dto.PizzaCreateDto;
import fr.ulille.iut.tout1art.dto.PizzaDto;
import fr.ulille.iut.tout1art.dto.PizzaShortDto;

import java.util.*;

@Entity
@Table(name = "artisan")

@NamedQueries({
    @NamedQuery(name="FindAllArtisans", query="SELECT p from ArtisanEntity p"),
    @NamedQuery(name="FindArtisanByName", query="SELECT p from ArtisanEntity p where p.nom = :pnom")
})

public class ArtisanEntity {
    private final static Logger logger = LoggerFactory.getLogger(ArtisanEntity.class);
    private static ModelMapper modelMapper = new ModelMapper();

    private long id;
    private String nom;
    private String prenom; 
    private String adresse;
    private String mail;
    private String tel;

    public static  ArtisanEntity convertFromArtisanCreateDto(ArtisanCreateDto artisan) {
        return modelMapper.map(artisan, ArtisanEntity.class);
    }

    public static ArtisanDto convertToDto(ArtisanEntity artisan) {
		return  modelMapper.map(artisan, ArtisanDto.class);
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
        ArtisanEntity that = (ArtisanEntity) o;
        return id == that.id &&
                Objects.equals(nom, that.nom);
    }

    public int hashCode() {
        return Objects.hash(id, nom);
    }

	public Collection<ArtisanDto> getProduits() {
		// TODO Auto-generated method stub
		return null;
	}
}
