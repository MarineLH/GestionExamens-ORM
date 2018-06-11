package gestion.examen;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "typeExamen")
public abstract class ResultatExamens implements ExamenNotable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idExamen")
	private Long id;

	@Basic
	@Column(length = 30, nullable = false)
	private String codeExamen;

	@Basic
	@Column(length = 50, nullable = false)
	private String nomEtudiant;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Calendar datePassageExamen;

	public Long getId() {
		return id;
	}

	public String getCodeExamen() {
		return codeExamen;
	}

	public void setCodeExamen(String codeExamen) {
		this.codeExamen = codeExamen;
	}

	public String getNomEtudiant() {
		return nomEtudiant;
	}

	public void setNomEtudiant(String nomEtudiant) {
		this.nomEtudiant = nomEtudiant;
	}

	public Calendar getDatePassageExamen() {
		return datePassageExamen;
	}

	public void setDatePassageExamen(Calendar datePassageExamen) {
		this.datePassageExamen = datePassageExamen;
	}

	public String getDateFormated(Calendar calendar) {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
		String date = formatDate.format(calendar.getTime());

		return date;
	}

	public String toString() {

		return this.codeExamen + " - " + this.nomEtudiant + " - " + getDateFormated(this.datePassageExamen) + " - "
				+ this.getNote();
	}
}
