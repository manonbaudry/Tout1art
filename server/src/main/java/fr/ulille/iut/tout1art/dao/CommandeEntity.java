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

import fr.ulille.iut.tout1art.dto.CommandeCreateDto;
import fr.ulille.iut.tout1art.dto.CommandeDto;

@Entity
@Table(name = "commande")

@NamedQueries({
    @NamedQuery(name="FindAllCommande", query="SELECT p from CommandeEntity p"),
    @NamedQuery(name="CheckCommandeName", query="SELECT count(p) from CommandeEntity p where p.nom = :pnom and p.id <> :pid"),
    @NamedQuery(name="FindCommandeByName", query="SELECT p from CommandeEntity p where p.nom = :pnom")
})

public class CommandeEntity {
    private final static Logger logger = LoggerFactory.getLogger(CommandeEntity.class);
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

    public static CommandeEntity convertFromCommandeCreateDto(CommandeCreateDto commande) {
        return modelMapper.map(commande, CommandeEntity.class);
    }

    public static CommandeDto convertToDto(CommandeEntity commande) {
		return  modelMapper.map(commande, CommandeDto.class);
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


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandeEntity that = (CommandeEntity) o;
        return id == that.id &&
                Objects.equals(nom, that.nom);
    }

    public int hashCode() {
        return Objects.hash(id, nom);
    }

	public Collection<CommandeDto> getCommandes() {
		// TODO Auto-generated method stub
		return null;
	}
}
