import java.util.ArrayList;

class Vehicule{
	private String nom;
	private double vitesse; 
	private int poids;
	private int carburant;
	
	public Vehicule(){
		nom = "Anonyme";
		vitesse = 130;
		poids = 1000;
		carburant = 0;
	}
	
	public Vehicule(String nom, double vitesse, int poids, int carburant){
		this.nom = nom;
		this.vitesse = vitesse;
		this.poids = poids;
		this.carburant = carburant;
	}
	
	public String toString(){
		return nom + " -> vitesse max = " + vitesse + " km/h, poids = " + poids + " kg";
	}
	
	public double performance(){
		return vitesse / poids;
	}
	
	public boolean meilleur(Vehicule autreVehicule) {
		return performance() >= autreVehicule.performance();
	}
	
	public boolean estDeuxRoues(){
		return false;
	};
	
	public String getNom(){
		return nom;
	}
	public double getVitesseMax(){
		return vitesse;
	}
	public int getPoids(){
		return poids;
	}
	public int getCarburant(){
		return carburant;
	}
	
	public void deduireCarburant(int tours) {
		carburant -= tours;
		if (carburant < 0){
			carburant = 0;
		}
	}
};

class Voiture extends Vehicule{
	private String categorie;
	
	Voiture(String nom, double vitesse, int poids, int carburant, String categorie){
		super(nom, vitesse, poids, carburant);
		this.categorie = categorie;
	}
	
	public String getCategorie(){
		return categorie;
	}
	
	public String toString() {
		return super.toString() + ", Voiture de " + categorie;
	}
};

class Moto extends Vehicule{
	private boolean sidecar;
	
	public Moto(String nom, double vitesse, int poids, int carburant){
		super(nom, vitesse, poids, carburant);
		this.sidecar = false;
	}
	
	public Moto(String nom, double vitesse, int poids, int carburant, boolean sidecar){
		super(nom, vitesse, poids, carburant);
		this.sidecar = sidecar;
	}
	
	public String toString() {
		return super.toString() + ", Moto" 
			+ (sidecar == true ? ", avec sidecar" : "");
	}
	
	public boolean estDeuxRoues(){
		return !sidecar;
	};
}

abstract class Rallye{
	public abstract boolean check();
}

class GrandPrix extends Rallye{
	
	private ArrayList<Vehicule> vehicules;

	public GrandPrix(){
		vehicules = new ArrayList<Vehicule>();
	}
	
	public void ajouter(Vehicule vehicule){
		vehicules.add(vehicule);
	}
	
	@Override
	public boolean check() {
		boolean ilyadeuxroues = false;
		boolean ilnyapasdeuxroues = false;
		for (Vehicule v: vehicules){
			if (v.estDeuxRoues()){
				ilyadeuxroues = true;
			}
			else{
				ilnyapasdeuxroues = true;
			}
		}
		return ilyadeuxroues != ilnyapasdeuxroues;
	}
	
	public void run(int tours){
		if (check()){
			Vehicule gagnant = null;
			for (Vehicule v: vehicules){
				v.deduireCarburant(tours);
				if (gagnant == null && v.getCarburant() > 0 
						|| gagnant != null && v.getCarburant() > 0 && v.meilleur(gagnant)){
					gagnant = v;
				}
			}
			if (gagnant != null){
				System.out.println("Le gagnant du grand prix est -> " + gagnant);
			}
			else {
				System.out.println("Elimination de tous les vehicules");
			}
		}
		else {
			System.out.println("Pas de Grand Prix");
		}
	}
	
}

public class Course {

    public static void main(String[] args) {

        // PARTIE 1
        System.out.println("Test partie 1 : ");
        System.out.println("----------------");
        Vehicule v0 = new Vehicule();
        System.out.println(v0);

        // les arguments du constructeurs sont dans l'ordre:
        // le nom, la vitesse, le poids, le carburant
        Vehicule v1 = new Vehicule("Ferrari", 300.0, 800, 30);
        Vehicule v2 = new Vehicule("Renault Clio", 180.0, 1000, 20);
        System.out.println();
        System.out.println(v1);
        System.out.println();
        System.out.println(v2);

        if (v1.meilleur(v2)) {
            System.out.println("Le premier vehicule est meilleur que le second");
        } else {
            System.out.println("Le second vehicule est meilleur que le premier");
        }
        // FIN PARTIE 1

        // PARTIE2
        System.out.println();
        System.out.println("Test partie 2 : ");
        System.out.println("----------------");

        // les trois premiers arguments sont dans l'ordre:
        // le nom, la vitesse, le poids, le carburant
        // le dernier argument indique la presence d'un sidecar
        Moto m1 = new Moto("Honda", 200.0, 250, 15, true);
        Moto m2 = new Moto("Kawasaki", 280.0, 180, 10);
        System.out.println(m1);
        System.out.println();
        System.out.println(m2);
        System.out.println();

        // les trois premiers arguments sont dans l'ordre:
        // le nom, la vitesse, le poids, le carburant
        // le dernier argument indique la categorie
        Voiture vt1 = new Voiture("Lamborghini", 320, 1200, 40, "course");
        Voiture vt2 = new Voiture("BMW", 190, 2000, 35, "tourisme");
        System.out.println(vt1);
        System.out.println();
        System.out.println(vt2);
        System.out.println();
        // FIN PARTIE 2

        // PARTIE 3
        System.out.println();
        System.out.println("Test partie 3 : ");
        System.out.println("----------------");

        GrandPrix gp1 = new GrandPrix();
        gp1.ajouter(v1);
        gp1.ajouter(v2);
        System.out.println(gp1.check());

        GrandPrix gp2 = new GrandPrix();
        gp2.ajouter(vt1);
        gp2.ajouter(vt2);
        gp2.ajouter(m2);
        System.out.println(gp2.check());

        GrandPrix gp3 = new GrandPrix();
        gp3.ajouter(vt1);
        gp3.ajouter(vt2);
        gp3.ajouter(m1);
        System.out.println(gp3.check());

        GrandPrix gp4 = new GrandPrix();
        gp4.ajouter(m1);
        gp4.ajouter(m2);
        System.out.println(gp4.check());
        // FIN PARTIE 3

        // PARTIE 4
        System.out.println();
        System.out.println("Test partie 4 : ");
        System.out.println("----------------");
        GrandPrix gp5 = new GrandPrix();
        gp5.ajouter(vt1);
        gp5.ajouter(vt2);

        System.out.println("Premiere course :");
        gp5.run(11);
        System.out.println();

        System.out.println("Deuxieme  course :");
        gp3.run(40);
        System.out.println();

        System.out.println("Troisieme  course :");
        gp2.run(11);
        // FIN PARTIE 4
    }

}
