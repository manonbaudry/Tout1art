package fr.ulille.iut.tout1art.dao;


import javax.persistence.*;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ulille.iut.tout1art.dto.PizzaCreateDto;
import fr.ulille.iut.tout1art.dto.PizzaDto;
import fr.ulille.iut.tout1art.dto.PizzaShortDto;

import java.util.*;

@Entity
@Table(name = "pizza")

@NamedQueries({
    @NamedQuery(name="FindAllPizzas", query="SELECT p from PizzaEntity p"),
    @NamedQuery(name="CheckPizzaName", query="SELECT count(p) from PizzaEntity p where p.nom = :pnom and p.id <> :pid"),
    @NamedQuery(name="FindPizzaByName", query="SELECT p from PizzaEntity p where p.nom = :pnom")
})

public class PizzaEntity {
    private final static Logger logger = LoggerFactory.getLogger(PizzaEntity.class);
    private static ModelMapper modelMapper = new ModelMapper();

    private long id;
    private String nom;
    private String base; // crème, tomate
    private float prix_petite;
    private float prix_grande;
    private Set<IngredientEntity> ingredients = new HashSet<>();

    public static  PizzaEntity convertFromPizzaCreateDto(PizzaCreateDto pizza) {
        return modelMapper.map(pizza, PizzaEntity.class);
    }

    public static PizzaDto convertToDto(PizzaEntity pizza) {
		return  modelMapper.map(pizza, PizzaDto.class);
	}
	
	public static PizzaShortDto convertToSimpleDto(PizzaEntity pizza) {
        return  modelMapper.map(pizza, PizzaShortDto.class);
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

    @Basic
    @Column(name = "base", length = -1)
	public String getBase() {
        return base;
    }

	public void setBase(String base) {
        this.base = base;
    }

    @Basic
    @Column(name = "prix_petite", precision = 0)
	public float getPrix_petite() {
        return prix_petite;
    }

	public void setPrix_petite(float prix_petite) {
        this.prix_petite = prix_petite;
    }

    @Basic
    @Column(name = "prix_grande", nullable = true, precision = 0)
	public float getPrix_grande() {
        return prix_grande;
    }

	public void setPrix_grande(float prix_grande) {
        this.prix_grande = prix_grande;
    }

    @ManyToMany /*(cascade = CascadeType.ALL)*/ /* (fetch = FetchType.EAGER) */
    @JoinTable(name = "ingredientpizza",
            joinColumns = @JoinColumn(name = "idpizza", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "idingredient", referencedColumnName = "id", nullable = false))
	public Set<IngredientEntity> getIngredients() {
        return ingredients;
    }

	public void setIngredients(Set<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }

	public String toString() {
		return "PizzaDto [id=" + id + ", nom=" + nom + ", base=" + base + ", prix_petite=" + prix_petite
				+ ", prix_grande=" + prix_grande + ", ingredients=" + ingredients + "]";
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaEntity that = (PizzaEntity) o;
        return id == that.id &&
                Objects.equals(nom, that.nom);
    }

    public int hashCode() {
        return Objects.hash(id, nom);
    }
}
