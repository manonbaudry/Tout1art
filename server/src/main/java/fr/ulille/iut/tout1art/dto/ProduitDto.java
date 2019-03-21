package fr.ulille.iut.tout1art.dto;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProduitDto {
	private final static Logger logger = LoggerFactory.getLogger(ArtisanCreateDto.class);

    private long id;
    private String nom;
    private String description; 
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

	public int getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
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
