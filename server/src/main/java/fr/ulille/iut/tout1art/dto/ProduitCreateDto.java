package fr.ulille.iut.tout1art.dto;

import java.util.Objects;

public class ProduitCreateDto {
    
    private long id;
    private String nom;
    private String description;
    private String srcImage;
    private int idArtisan;
    private String categorie;
    
    public String getSrcImage() {
		return srcImage;
	}

	public void setSrcImage(String srcImage) {
		this.srcImage = srcImage;
	}

	private double prix;
    private int idProduit;
    
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
