import java.util.ArrayList;

class Auteur {
	private String nom;
	private boolean prime;
	
	public Auteur(String nom, boolean prime){
		this.nom = nom;
		this.prime = prime;
	}
	
	public String getNom(){
		return nom;
	}
	
	public boolean getPrix(){
		return prime;
	}
}

class Oeuvre{
	private Auteur auteur;
	private String titre;
	private String langue;
	
	public Oeuvre(String titre, Auteur auteur, String langue){
		this.auteur = auteur;
		this.titre = titre;
		this.langue = langue;
	}
	
	public Oeuvre(String titre, Auteur auteur){
		this(titre, auteur, "francais");
	}
	
	public String getTitre(){
		return titre;
	}
	public Auteur getAuteur(){
		return auteur;
	}
	public String getLangue(){
		return langue;
	}
	public void afficher(){
		System.out.println(titre + ", " + auteur.getNom() + ", en " + langue);
	}
}

class Exemplaire{
	private Oeuvre oeuvre;
	public Exemplaire(Oeuvre oeuvre){
		//this.oeuvre = new Oeuvre(oeuvre.getTitre(), oeuvre.getAuteur(), oeuvre.getLangue());
		this.oeuvre = oeuvre;
		System.out.println("Nouvel exemplaire -> " + oeuvre.getTitre() + ", " + oeuvre.getAuteur().getNom() + 
				", en " + oeuvre.getLangue());
	}
	
	public Exemplaire(Exemplaire copy){
		oeuvre = copy.oeuvre; // TODO
		System.out.print("Copie d'un exemplaire de -> ");
		oeuvre.afficher();
	}
	
	public Oeuvre getOeuvre() {
		return oeuvre;
	}
	
	public void afficher(){
		System.out.print("Un exemplaire de ");
		oeuvre.afficher();
	}
}

class Bibliotheque{
	private String nom;
	private ArrayList<Exemplaire> exemplaires;
	
	public Bibliotheque(String nom){
		this.nom = nom;
		exemplaires = new ArrayList<Exemplaire>();
		System.out.print("La bibliotheque " + nom + " est ouverte !");
	}
	
	public String getNom(){
		return nom;
	}
	
	public void stocker(Oeuvre oeuvre, int n){
		for (int i = 0; i < n; i++){
			exemplaires.add(new Exemplaire(oeuvre));
		}
	}
	
	public void stocker(Oeuvre oeuvre){
		stocker(oeuvre, 1);
	}
	
	public ArrayList<Exemplaire> listerExemplaires(String langue) {
		ArrayList<Exemplaire> res = new ArrayList<Exemplaire>();
		for (Exemplaire e: exemplaires){
			if(e.getOeuvre().getLangue() == langue){
				res.add(e);
			}
		}
		return res;
	}
	
	public ArrayList<Exemplaire> listerExemplaires() {
		return exemplaires;
	}
	
	public int compterExemplaires(Oeuvre oeuvre) {
		int n = 0;
		for (Exemplaire e: exemplaires){
			if (e.getOeuvre() == oeuvre){
				n++;
			}
		}
		return n;
	}
	
	public void afficherAuteur(boolean prix) {
		for(Exemplaire e: exemplaires){
			if (e.getOeuvre().getAuteur().getPrix() == prix){
				System.out.println(e.getOeuvre().getAuteur().getNom());
			}
		}
	}
	
	public void afficherAuteur() {
		afficherAuteur(true);
	}
}

public class _2_2_Biblio {

    public static void afficherExemplaires(ArrayList<Exemplaire> exemplaires) {
        for (Exemplaire exemplaire : exemplaires) {
            System.out.print("\t");
            exemplaire.afficher();
        }
    }

    public static void main(String[] args) {
        // create and store all the exemplaries
        Auteur a1 = new Auteur("Victor Hugo", false);
        Auteur a2 = new Auteur("Alexandre Dumas", false);
        Auteur a3 = new Auteur("Raymond Queneau", true);

        Oeuvre o1 = new Oeuvre("Les Miserables", a1, "francais");
        Oeuvre o2 = new Oeuvre("L\'Homme qui rit", a1, "francais");
        Oeuvre o3 = new Oeuvre("Le Comte de Monte-Cristo", a2, "francais");
        Oeuvre o4 = new Oeuvre("Zazie dans le metro", a3, "francais");
        Oeuvre o5 = new Oeuvre("The count of Monte-Cristo", a2, "anglais");

        Bibliotheque biblio = new Bibliotheque("municipale");
        biblio.stocker(o1, 2);
        biblio.stocker(o2);
        biblio.stocker(o3, 3);
        biblio.stocker(o4);
        biblio.stocker(o5);

        // ...
        System.out.println("La bibliotheque " + biblio.getNom() + " offre ");
        afficherExemplaires(biblio.listerExemplaires());
        String langue = "anglais";
        System.out.println("Les exemplaires en " + langue + " sont  ");
        afficherExemplaires(biblio.listerExemplaires(langue));
        System.out.println("Les auteurs a succes sont  ");
        biblio.afficherAuteur();
        System.out.print("Il y a " + biblio.compterExemplaires(o3) + " exemplaires");
        System.out.println(" de  " + o3.getTitre());
    }
}

