package gestion.examen;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

public class GestionExamensDAO {

	public GestionExamensDAO() {

	}

	public void ajouterExamen(ResultatExamens examen) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionExamenDb");
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(examen);
			entityManager.getTransaction().commit();

		} finally {
			entityManager.close();
			emf.close();
		}
	}

	public List<ResultatExamens> recupererListeExamens() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionExamenDb");
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			List<ResultatExamens> examen = entityManager
					.createQuery("select r from ResultatExamens r", ResultatExamens.class).getResultList();
			return examen;
		} finally {
			entityManager.close();
			emf.close();
		}
	}

	public void ajouterDiplome(Diplome diplome) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionExamenDb");
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(diplome);
			entityManager.getTransaction().commit();

		} finally {
			entityManager.close();
			emf.close();
		}
	}

	public List<ResultatExamens> getResultatEtudiantPourPeriode(String nomEtudiant, Calendar dateDebut,
			Calendar dateFin) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionExamenDb");
		EntityManager entityManager = emf.createEntityManager();

		try {

			TypedQuery<ResultatExamens> query = entityManager.createQuery(
					"select r from ResultatExamens r where r.nomEtudiant = :nom and r.datePassageExamen BETWEEN :dateDebut and :dateFin",
					ResultatExamens.class).setParameter("nom", nomEtudiant)
					.setParameter("dateDebut", dateDebut, TemporalType.DATE)
					.setParameter("dateFin", dateFin, TemporalType.DATE);
			List<ResultatExamens> examens = query.getResultList();

			return examens;

		} finally {
			entityManager.close();
			emf.close();
		}
	}

	public double getMoyenneEtudiant(String nomEtudiant) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionExamenDb");
		EntityManager entityManager = emf.createEntityManager();

		try {

			double resultProjet = (double) entityManager.createNativeQuery(
					"select avg(noteOrale + noteEcrite) from projet as p inner join ResultatExamens as r on p.idExamen = r.idExamen where nomEtudiant = :nom ")
					.setParameter("nom", nomEtudiant).getSingleResult();

			double resultControle = (double) entityManager.createNativeQuery(
					"select avg(noteControle) from controle as c inner join ResultatExamens as r on c.idExamen = r.idExamen where nomEtudiant = :nom ")
					.setParameter("nom", nomEtudiant).getSingleResult();

			double moyenne = (resultControle + resultProjet) / 2;

			return moyenne;

		} finally {
			entityManager.close();
			emf.close();
		}

	}

	public double getMoyenneExamen(String codeExamen) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionExamenDb");
		EntityManager entityManager = emf.createEntityManager();

		try {

			@SuppressWarnings("unchecked")
			List<String> typeExamen = (List<String>) entityManager
					.createNativeQuery("select r.typeExamen from ResultatExamens as r where codeExamen = :codeExamen ")
					.setParameter("codeExamen", codeExamen).getResultList();

			double moyenne = 0;
			double diviseur = 0;
			double resultControle = -1;
			double resultProjet = -1;
			for (String typeExam : typeExamen) {
				if (typeExam.equals("C")) {

					resultControle = (double) entityManager.createNativeQuery(
							"select avg(noteControle) from controle as c inner join ResultatExamens as r on c.idExamen = r.idExamen where codeExamen = :codeExamen ")
							.setParameter("codeExamen", codeExamen).getSingleResult();
				}
				if (typeExam.equals("P")) {

					resultProjet = (double) entityManager.createNativeQuery(
							"select avg(noteOrale + noteEcrite) from projet as p inner join ResultatExamens as r on p.idExamen = r.idExamen where codeExamen = :codeExamen ")
							.setParameter("codeExamen", codeExamen).getSingleResult();
				}
			}
			if (resultControle > -1) {
				moyenne += resultControle;
				diviseur++;
			}
			if (resultProjet > -1) {
				diviseur++;
				moyenne += resultProjet;
			}
			if (diviseur != 0) {
				moyenne = moyenne / diviseur;
			}
			return moyenne;

		} finally {
			entityManager.close();
			emf.close();
		}

	}

}
