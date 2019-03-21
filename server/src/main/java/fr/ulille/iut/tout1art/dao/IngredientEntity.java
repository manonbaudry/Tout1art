package fr.ulille.iut.tout1art.dao;

import javax.persistence.*;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ulille.iut.tout1art.dto.IngredientDto;

import java.util.Objects;

@Entity
@Table(name = "ingredient")

@NamedQueries({
        @NamedQuery(name = "FindAllIngredients", query = "SELECT i from IngredientEntity i"),
        @NamedQuery(name = "FindIngredientByName", query = "SELECT i from IngredientEntity i where i.nom = :inom")
})

public class IngredientEntity extends IngredientDto {
	private final static Logger logger = LoggerFactory.getLogger(IngredientEntity.class);
	private static ModelMapper modelMapper = new ModelMapper();

	public IngredientEntity(IngredientDto ingredientDto) {
		modelMapper.map(ingredientDto, this.getClass());
	}

	public IngredientEntity() {
	}

	public static IngredientDto convertToDto(IngredientEntity ingredient) {
		return  modelMapper.map(ingredient, IngredientDto.class);
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
    @Column(name = "nom", nullable = false, length = -1, unique = true)
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IngredientEntity that = (IngredientEntity) o;
		return id == that.id &&
				Objects.equals(nom, that.nom);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nom);
	}

	@Override
	public String toString() {
		return "IngredientDto [id=" + id + ", nom=" + nom + "]";
	}
}
