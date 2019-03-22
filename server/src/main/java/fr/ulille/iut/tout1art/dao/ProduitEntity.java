package fr.ulille.iut.tout1art.dao;


import java.util.Collection;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ulille.iut.tout1art.dto.ProduitCreateDto;
import fr.ulille.iut.tout1art.dto.ProduitDto;

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
    private String srcImage;
    private double prix;
    private int idArtisan;
    private String categorie;
    private String sousCategorie;
    private int commande;
    private int delai;

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
	
    @Basic
    @Column(name = "description", nullable = false, length = -1)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @Basic
    @Column(name = "srcImage", nullable = false, length = -1)
	public String getSrcImage() {
		return srcImage;
	}

	public void setSrcImage(String srcImage) {
		this.srcImage = srcImage;
	}

    @Basic
    @Column(name = "prix", nullable = false, length = -1)
	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	@Basic
    @Column(name = "idArtisan", nullable = false, length = -1)
	public int getIdArtisan() {
		return idArtisan;
	}

	public void setIdArtisan(int idArtisan) {
		this.idArtisan = idArtisan;
	}

	@Basic
    @Column(name = "categorie", nullable = false, length = -1)
	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

    @Basic
    @Column(name = "sousCategorie", nullable = false, length = -1)
	public String getSousCategorie() {
		return sousCategorie;
	}

	public void setSousCategorie(String sousCategorie) {
		this.sousCategorie = sousCategorie;
	}
	
	@Basic
	@Column(name = "commande", nullable = false, length = -1)
	public int getCommande() {
		return commande;
	}

	public void setCommande(int commande) {
		this.commande = commande;
	}	
	
	
	
/*
    @ManyToMany /*(cascade = CascadeType.ALL)*/ /* (fetch = FetchType.EAGER)
    @JoinTable(name = "ingredientpizza",
            joinColumns = @JoinColumn(name = "idpizza", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "idingredient", referencedColumnName = "id", nullable = false))
	public Set<IngredientEntity> getIngredients() {
        return ingredients;
    }
*/


    /**
	 * @return the delai
	 */

	@Basic
	@Column(name = "delai", nullable = false, length = -1)
	public int getDelai() {
		return delai;
	}

	/**
	 * @param delai the delai to set
	 */
	public void setDelai(int delai) {
		this.delai = delai;
	}

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
