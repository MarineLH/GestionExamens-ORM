package gestion.examen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		GestionExamensDAO examenDAO = new GestionExamensDAO();

		/** INIT DATES **/
		Calendar dateExam = Calendar.getInstance();
		dateExam.set(2018, Calendar.APRIL, 18);
		Calendar dateDebutPeriode = Calendar.getInstance();
		dateDebutPeriode.set(2018, Calendar.JANUARY, 01);
		Calendar dateFinPeriode = Calendar.getInstance();
		dateFinPeriode.set(2018, Calendar.JUNE, 30);

		/** INIT CONTROLE **/
		Controle unControle = new Controle();
		unControle.setCodeExamen("EXAM1");
		unControle.setDatePassageExamen(dateExam);
		unControle.setNomEtudiant("Marine");
		unControle.setNoteControle(15);

		/** INIT PROJET **/
		Projet unProjet = new Projet();
		unProjet.setCodeExamen("PROJET_TEST");
		unProjet.setDatePassageExamen(dateExam);
		unProjet.setNomEtudiant("Marine");
		unProjet.setNoteEcrite(10);
		unProjet.setNoteOrale(9);

		/** ADD CONTROLE & PROJET **/
		examenDAO.ajouterExamen(unControle);
		examenDAO.ajouterExamen(unProjet);

		/** INIT ETUDIANTS **/
		String Marine = "Marine";

		/** GET LISTE EXAMENS POUR UN ETUDIANT SUR PERIODE DONNEE **/
		List<ResultatExamens> lesExamens = examenDAO.getResultatEtudiantPourPeriode(Marine, dateDebutPeriode,
				dateFinPeriode);
		for (ResultatExamens exam : lesExamens) {
			System.out.println("Code examen : " + exam.getCodeExamen() + " - Nom etudiant : " + exam.getNomEtudiant()
					+ " - Note : " + exam.getNote() + " - Date : " + exam.getDateFormated(exam.getDatePassageExamen()));
		}

		/** GET MOYENNE D'UN ETUDIANT **/
		double laMoyenne = examenDAO.getMoyenneEtudiant(Marine);
		System.out.println("La moyenne de " + Marine + " est de : " + laMoyenne);

		/** GET MOYENNE D'UN EXAMEN **/
		String examen = "PROJET_TEST";
		double laMoyenneExamen = examenDAO.getMoyenneExamen(examen);
		System.out.println("La moyenne de l'examen : " + examen + " est de : " + laMoyenneExamen);

		/** GET LISTE EXAMENS **/
		List<ResultatExamens> listeExamens = examenDAO.recupererListeExamens();
		for (ResultatExamens unExamen : listeExamens) {
			System.out.println(unExamen.toString());
		}

		/** ADD EXAMENS IN LISTEEXAMENS **/
		List<ResultatExamens> listeExamensPourDiplome = new ArrayList<>();
		listeExamensPourDiplome.add(listeExamens.get(0));
		listeExamensPourDiplome.add(listeExamens.get(2));
		listeExamensPourDiplome.add(listeExamens.get(5));

		/** ADD DIPLOME **/
		Diplome diplome = new Diplome();
		diplome.setNom("Diplome1");
		diplome.setExamens(listeExamensPourDiplome);
		examenDAO.ajouterDiplome(diplome);

	}

}
