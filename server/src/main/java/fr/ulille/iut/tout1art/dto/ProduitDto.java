package fr.ulille.iut.tout1art.dto;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProduitDto {
	private final static Logger logger = LoggerFactory.getLogger(ArtisanCreateDto.class);

    protected long id;
    protected String nom;
    protected String description; 
    protected String srcImage;
    protected int idArtisan;
    protected String categorie;
	protected double prix;
    protected int idProduit;
    
    public String getSrcImage() {
		return srcImage;
	}

	public void setSrcImage(String srcImage) {
		this.srcImage = srcImage;
	}
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getIdArtisan() {
		return idArtisan;
	}

	public void setIdArtisan(int idArtisan) {
		this.idArtisan = idArtisan;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

    
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtisanCreateDto that = (ArtisanCreateDto) o;
        return id == that.id &&
                Objects.equals(nom, that.nom);
    }
}
