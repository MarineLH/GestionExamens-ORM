package gestion.examen;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Diplome {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Basic
	@Column(nullable = false)
	private String nom;

	@ManyToMany
	private List<ResultatExamens> examens = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public List<ResultatExamens> getExamens() {
		return examens;
	}

	public void setExamens(List<ResultatExamens> examens) {
		this.examens = examens;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
