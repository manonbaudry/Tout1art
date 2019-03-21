package fr.ulille.iut.tout1art.dao;


import javax.persistence.*;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ulille.iut.tout1art.dto.ArtisanCreateDto;
import fr.ulille.iut.tout1art.dto.ArtisanDto;

import java.util.*;

@Entity
@Table(name = "artisan")

@NamedQueries({
    @NamedQuery(name="FindAllArtisans", query="SELECT p from ArtisanEntity p"),
   // @NamedQuery(name="FindArtisanByName", query="SELECT p from ArtisanEntity p where p.nom = :pnom")
})

public class ArtisanEntity {
    private final static Logger logger = LoggerFactory.getLogger(ArtisanEntity.class);
    private static ModelMapper modelMapper = new ModelMapper();

    private long id;
    private String nom;
    private String prenom; 
    private String adresse;
    private String mail;
    private String tel;
    private String login;
    private String mdp;

    public static  ArtisanEntity convertFromArtisanCreateDto(ArtisanCreateDto artisan) {
        return modelMapper.map(artisan, ArtisanEntity.class);
    }

    public static ArtisanDto convertToDto(ArtisanEntity artisan) {
		return  modelMapper.map(artisan, ArtisanDto.class);
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

    @Basic
    @Column(name = "prenom", nullable = false, length = -1)
	public String getPrenom() {
		return prenom;
	}

    @Basic
    @Column(name = "adresse", nullable = false, length = -1)
	public String getAdresse() {
		return adresse;
	}

    @Basic
    @Column(name = "mail", nullable = false, length = -1)
	public String getMail() {
		return mail;
	}

    @Basic
    @Column(name = "tel", nullable = false, length = -1)
	public String getTel() {
		return tel;
	}

	public void setNom(String nom) {
        this.nom = nom;
    }
	

    /**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

		
    @Basic
    @Column(name = "login", nullable = false, length = -1)
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

    @Basic
    @Column(name = "mdp", nullable = false, length = -1)
	public String getMdp() {
		return mdp;
	}

	/**
	 * @param mdp the mdp to set
	 */
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtisanEntity that = (ArtisanEntity) o;
        return id == that.id &&
                Objects.equals(nom, that.nom);
    }

}
