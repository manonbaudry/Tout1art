package fr.ulille.iut.tout1art.dto;

public class ComCreateDto {

    private long id;
    private int idProduit;
    private int idClient;
    private int idArtisan;
    private String statutt;
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
	 * @return the idProduit
	 */
	public int getIdProduit() {
		return idProduit;
	}

	
	/**
	 * @return the idArtisan
	 */
	public int getIdArtisan() {
		return idArtisan;
	}

	/**
	 * @param idArtisan the idArtisan to set
	 */
	public void setIdArtisan(int idArtisan) {
		this.idArtisan = idArtisan;
	}

	/**
	 * @param idProduit the idProduit to set
	 */
	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	/**
	 * @return the idClient
	 */
	public int getIdClient() {
		return idClient;
	}

	/**
	 * @param idClient the idClient to set
	 */
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	/**
	 * @return the statutt
	 */
	public String getStatut() {
		return statutt;
	}

	/**
	 * @param statut the statutt to set
	 */
	public void setStatut(String statut) {
		this.statutt = statut;
	}
}
