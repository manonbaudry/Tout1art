package fr.ulille.iut.tout1art.dao;


import java.util.Collection;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ulille.iut.tout1art.dto.ComCreateDto;
import fr.ulille.iut.tout1art.dto.ComDto;

@Entity
@Table(name = "com")

@NamedQueries({
    @NamedQuery(name="FindAllComs", query="SELECT p from ComEntity p")
    //@NamedQuery(name="GetProduitByArtisan", query="SELECT * FROM ProduitEntity p WHERE p.idArtisan = :pid AND p.id IN (SELECT c.idProduit FROM ComEntity c)")
})

public class ComEntity {
    private final static Logger logger = LoggerFactory.getLogger(ComEntity.class);
    private static ModelMapper modelMapper = new ModelMapper();

    private long id;
    private int idPoduit;
    private int idClient;
    private int idArtisan;
    private String statu;
    
    public static  ComEntity convertFromComCreateDto(ComCreateDto commander) {
        return modelMapper.map(commander, ComEntity.class);
    }

    public static ComDto convertToDto(ComEntity commander) {
		return  modelMapper.map(commander, ComDto.class);
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
    @Column(name = "idProduit", nullable = false, length = -1)
	public int getIdPoduit() {
		return idPoduit;
	}

    /**
	 * @param idPoduit the idPoduit to set
	 */
	public void setIdPoduit(int idPoduit) {
		this.idPoduit = idPoduit;
	}

	@Basic
	@Column(name = "idClient", nullable = false, length = -1)
	public int getIdClient() {
		return idClient;
	}

	/**
	 * @param idPoduit the idPoduit to set
	 */
	public void setIdArtisan(int idArtisan) {
	this.idArtisan = idArtisan;
	}

	@Basic
	@Column(name = "idArtisan", nullable = false, length = -1)
	public int getIdArtisan() {
		return idArtisan;
	}

	/**
	 * @param idClient the idClient to set
	 */
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	@Basic
	@Column(name = "statu", nullable = false, length = -1)
	public String getStatu() {
		return statu;
	}

	/**
	 * @param statu the statu to set
	 */
	public void setStatu(String statu) {
		this.statu = statu;
	}

/*
    @ManyToMany /*(cascade = CascadeType.ALL)*/ /* (fetch = FetchType.EAGER)
    @JoinTable(name = "ingredientpizza",
            joinColumns = @JoinColumn(name = "idpizza", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "idingredient", referencedColumnName = "id", nullable = false))
	public Set<IngredientEntity> getIngredients() {
        return ingredients;
    }
*/


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComEntity that = (ComEntity) o;
        return id == that.id &&
                Objects.equals(id, that.id);
    }

	public Collection<ComDto> getCommandes() {
		// TODO Auto-generated method stub
		return null;
	}
}
