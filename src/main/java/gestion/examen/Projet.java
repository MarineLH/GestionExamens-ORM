package gestion.examen;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
public class Projet extends ResultatExamens {

	private double noteOrale;
	private double noteEcrite;

	public double getNoteOrale() {
		return noteOrale;
	}

	public void setNoteOrale(double noteOrale) {
		this.noteOrale = noteOrale;
	}

	public double getNoteEcrite() {
		return noteEcrite;
	}

	public void setNoteEcrite(double noteEcrite) {
		this.noteEcrite = noteEcrite;
	}

	@Override
	public double getNote() {
		return noteEcrite + noteOrale;
	}

}
