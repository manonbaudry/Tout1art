package fr.ulille.iut.tout1art.dto;

public class ComCreateDto {

    protected long id;
    protected int idPoduit;
    protected int idClient;
    protected int idArtisan;
    protected String statu;
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
	 * @return the idPoduit
	 */
	public int getIdPoduit() {
		return idPoduit;
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
	 * @param idPoduit the idPoduit to set
	 */
	public void setIdPoduit(int idPoduit) {
		this.idPoduit = idPoduit;
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
	 * @return the statu
	 */
	public String getStatu() {
		return statu;
	}

	/**
	 * @param statu the statu to set
	 */
	public void setStatu(String statu) {
		this.statu = statu;
	}
}
