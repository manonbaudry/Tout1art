package fr.ulille.iut.tout1art.dto;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import fr.ulille.iut.tout1art.dao.NotificationEntity;

public class NotificationDto {
  	protected long id;
    protected String conserne;
    protected String type;
    protected String contenu;
    protected int lu;

	public long getId() {
        return id;
    }

	public void setId(long id) {
        this.id = id;
    }
    
	/**
	 * @return the conserne
	 */
	public String getConserne() {
		return conserne;
	}

	/**
	 * @param conserne the conserne to set
	 */
	public void setConserne(String conserne) {
		this.conserne = conserne;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the contenu
	 */

	public String getContenu() {
		return contenu;
	}

	/**
	 * @param contenu the contenu to set
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	/**
	 * @return the lu
	 */

	public int getLu() {
		return lu;
	}

	/**
	 * @param lu the lu to set
	 */
	public void setLu(int lu) {
		this.lu = lu;
	}

	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationEntity that = (NotificationEntity) o;
        return id == that.getId();
       }
}
