/**
 * @author Riabchenko Vlad
 */

import java.util.ArrayList;

class Timbre {

    public static final int ANNEE_COURANTE = 2014;
    public static final int VALEUR_TIMBRE_DEFAUT = 1;
    public static final String PAYS_DEFAUT = "Suisse";
    public static final String CODE_DEFAUT = "lambda";

    public static final int BASE_1_EXEMPLAIRES = 100;
    public static final int BASE_2_EXEMPLAIRES = 1000;
    public static final double PRIX_BASE_1 = 600;
    public static final double PRIX_BASE_2 = 400;
    public static final double PRIX_BASE_3 = 50;
    
    private String code;
    private int annee;
    private String pays;
    private double valeur;
    
    Timbre(String code, int annee, String pays, double valeur){
    	this.code = code;
    	this.annee = annee;
    	this.pays = pays;
    	this.valeur = valeur;
    }
    
    Timbre(String code, int annee, String pays){
    	this(code, annee, pays, VALEUR_TIMBRE_DEFAUT);
    }
    
    Timbre(String code, int annee){
    	this(code, annee, PAYS_DEFAUT, VALEUR_TIMBRE_DEFAUT);
    }
    
    Timbre(String code){
    	this(code, ANNEE_COURANTE, PAYS_DEFAUT, VALEUR_TIMBRE_DEFAUT);
    }
    
    Timbre(){
    	this(CODE_DEFAUT, ANNEE_COURANTE, PAYS_DEFAUT, VALEUR_TIMBRE_DEFAUT);
    }
    
    public double vente(){
    	if (age() > 5){
    		return 2.5 * valeur * age();
    	}
    	else{
    		return valeur;
    	}
    }
    
    public String toString() {
		return "Timbre de code " + code + " datant de " + annee + " (provenance " + pays 
				+ ") ayant pour valeur faciale " + valeur + " francs\n";
	}
    
    public int age() {
		return ANNEE_COURANTE - annee;
	}
    
    public String getCode(){
    	return code;
    }
    public int getAnnee() {
		return annee;
	}
    public String getPays(){
    	return pays;
    }
    public double getValeurFaciale() {
		return valeur;
	}
}

class Rare extends Timbre{
	private int nombre;
	
	Rare(String code, int annee, String pays, double valeur, int nombre){
		super(code, annee, pays, valeur);
		this.nombre = nombre;
	}
	
	Rare(String code, int annee, String pays, int nombre){
		super(code, annee, pays);
		this.nombre = nombre;
	}
	
	Rare(String code, int annee, int nombre){
		super(code, annee);
		this.nombre = nombre;
	}
	
	Rare(String code, int nombre){
		super(code);
		this.nombre = nombre;
	}
	
	Rare(int nombre){
		super();
		this.nombre = nombre;
	}
	
	public int getExemplaires(){
		return nombre;
	}
	
	public String toString(){
		return super.toString() + "Nombre d'exemplaires -> " + nombre;
	}
	
	@Override
	public double vente() {
		if (nombre < BASE_1_EXEMPLAIRES){
			return PRIX_BASE_1 * (age() / 10.0);
		}
		else{
			if (nombre < BASE_2_EXEMPLAIRES){
				return PRIX_BASE_2 * (age() / 10.0);
			}
			else{
				return PRIX_BASE_3 * (age() / 10.0);
			}
		}
	}
}

class Commemoratif extends Timbre{
	Commemoratif(String code, int annee, String pays, double valeur){
		super(code, annee, pays, valeur);
	}
	
	Commemoratif(String code, int annee, String pays){
		super(code, annee, pays);
	}
	
	Commemoratif(String code, int annee){
		super(code, annee);
	}
	
	Commemoratif(String code){
		super(code);
	}
	
	public String toString(){
		return super.toString() + "Timbre celebrant un evenement";
	}
	
	public double vente(){
		return 2 * super.vente();
	}
}

class _3_1_Philatelie {

    public static void main(String[] args) {

        // ordre des parametres: nom, annee d'emission, pays, valeur faciale,
        // nombre d'exemplaires
        Rare t1 = new Rare("Guarana-4574", 1960, "Mexique", 0.2, 98);

        // ordre des parametres: nom, annee d'emission, pays, valeur faciale
        Commemoratif t2 = new Commemoratif("700eme-501", 2002, "Suisse", 1.5);
        Timbre t3 = new Timbre("Setchuan-302", 2004, "Chine", 0.2);

        Rare t4 = new Rare("Yoddle-201", 1916, "Suisse", 0.8, 3);


        ArrayList<Timbre> collection = new ArrayList<Timbre>();

        collection.add(t1);
        collection.add(t2);
        collection.add(t3);
        collection.add(t4);

        for (Timbre timbre : collection) {
            System.out.println(timbre);
            System.out.println("Prix vente : " + timbre.vente() + " francs");
            System.out.println();
        }
    }
}

