package fr.ulille.iut.tout1art.dto;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComDto {
	private final static Logger logger = LoggerFactory.getLogger(ArtisanCreateDto.class);

    protected long id;
    protected int idProduit;
    protected int idClient;
    protected int idArtisan;
    protected String statut;
    

    
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
	 * @return the statut
	 */
	public String getStatut() {
		return statut;
	}

	/**
	 * @param statut the statut to set
	 */
	public void setStatut(String statut) {
		this.statut = statut;
	}


}
