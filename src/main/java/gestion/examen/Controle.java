package gestion.examen;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
public class Controle extends ResultatExamens {

	private double noteControle;

	public double getNoteControle() {
		return noteControle;
	}

	public void setNoteControle(double noteControle) {
		this.noteControle = noteControle;
	}

	@Override
	public double getNote() {
		return noteControle;
	}
}
