package fr.ulille.iut.tout1art.dto;

public class ProduitCreateDto {
    
    protected long id;
    protected String nom;
    protected String description;
    protected String srcImage;
    protected int idArtisan;
	protected double prix;
    protected int idProduit;
    protected String categorie;
    protected String sousCategorie;
    protected int commande;
    
    
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

	public String getSousCategorie() {
		return sousCategorie;
	}

	public void setSousCategorie(String categorie) {
		this.sousCategorie = sousCategorie;
	}
	
	public int getCommande() {
		return commande;
	}

	public void setCommande(int commande) {
		this.commande = commande;
	}
    
}
