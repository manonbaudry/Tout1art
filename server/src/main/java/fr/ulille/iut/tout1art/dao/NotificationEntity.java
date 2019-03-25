package fr.ulille.iut.tout1art.dao;



import javax.persistence.*;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ulille.iut.tout1art.dto.NotificationCreateDto;
import fr.ulille.iut.tout1art.dto.NotificationDto;

import java.util.*;

@Entity
@Table(name = "notification")

@NamedQueries({
    @NamedQuery(name="FindAllNotifications", query="SELECT p from NotificationEntity p"),
   // @NamedQuery(name="FindNotificationByName", query="SELECT p from NotificationEntity p where p.nom = :pnom")
})

public class NotificationEntity {
    private final static Logger logger = LoggerFactory.getLogger(NotificationEntity.class);
    private static ModelMapper modelMapper = new ModelMapper();

    private long id;
    private String conserne;
    private String type;
    private String contenu;
    private int lu;

    public static  NotificationEntity convertFromNotificationCreateDto(NotificationCreateDto notification) {
        return modelMapper.map(notification, NotificationEntity.class);
    }

    public static NotificationDto convertToDto(NotificationEntity notification) {
		return  modelMapper.map(notification, NotificationDto.class);
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
    
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationEntity that = (NotificationEntity) o;
        return id == that.id &&
                Objects.equals(nom, that.nom);
    }

	/**
	 * @return the conserne
	 */
    @Basic
    @Column(name = "conserne", nullable = false, length = -1)
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

    @Basic
    @Column(name = "type", nullable = false, length = -1)
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

    @Basic
    @Column(name = "contenu", nullable = false, length = -1)
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

    @Basic
    @Column(name = "lu", nullable = false, length = -1)
	public int getLu() {
		return lu;
	}

	/**
	 * @param lu the lu to set
	 */
	public void setLu(int lu) {
		this.lu = lu;
	}

}
