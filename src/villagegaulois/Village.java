package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Marche marche;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalMaximum);
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal < etals.length) {
				Etal etal = etals[indiceEtal];
				if (!etal.isEtalOccupe()) {
					etal.occuperEtal(vendeur, produit, nbProduit);
				}
			}
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtalAvecProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].contientProduit(produit)) {
					nbEtalAvecProduit++;
				}
			}
			Etal ListeEtalAvecProduit[] = new Etal[nbEtalAvecProduit];
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].contientProduit(produit)) {
					ListeEtalAvecProduit[i] = etals[i];
				}
			}
			return ListeEtalAvecProduit;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			int nbEtalLibre = 0;
			StringBuilder chaine = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					nbEtalLibre++;
				}
			}
			if (nbEtalLibre != 0) {
				chaine.append("Il reste " + nbEtalLibre + "étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder(vendeur + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int numEtal = this.marche.trouverEtalLibre();
		this.marche.utiliserEtal(numEtal, vendeur, produit, nbProduit);
		chaine.append("Le vendeur "+ vendeur +" vend des "+ produit +" à l'étal n°" + numEtal +".\n");
		return chaine.toString();
	}
}