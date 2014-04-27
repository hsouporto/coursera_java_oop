import java.util.ArrayList;
import java.util.Random;

class Postulant{
	private String nom;
	private int votes;
	
	public Postulant(String nom){
		this.nom = nom;
		votes = 0;
	}
	
	public void elect(){
		votes++;
	}
	
	public void init(){
		votes = 0;
	}
	
	public int getVotes(){
		return votes;
	}
	
	public String getNom(){
		return nom;
	}
}

class Scrutin{
	private ArrayList<Postulant> postulants;
	private ArrayList<Vote> votes;
	private int maxVotants;
	private int votants;
	private int date;
	
	Scrutin(ArrayList<Postulant> postulants, int maxVotants, int date){
		this.postulants = postulants;
		this.maxVotants = maxVotants;
		this.date = date;
		votants = 0;
		votes = new ArrayList<Vote>();
	}
	
	public void compterVotes(){
		for (Vote v: votes){
			System.out.println(v);
			if (!v.estInvalide()){
				v.getPostulant().elect();
			}
		}
	}
	
	public void simuler(double pourcentage, int jour){
		votants = (int) (pourcentage * maxVotants);
		for (int i = 0; i < votants; i++){
			int candNum = Utils.randomInt(postulants.size());
			switch (i % 3){
			case 0:
				votes.add(new BulletinElectronique(postulants.get(candNum), jour, date));
				break;
			case 1:
				votes.add(new BulletinPapier(postulants.get(candNum), jour, date, i % 2 == 1));
				break;
			default:
				votes.add(new BulletinCourrier(postulants.get(candNum), jour, date, i % 2 == 1));
			}
		}
	}
	
	public void calculerVotants(){
		votants = 0;
		for(Postulant p: postulants){
			votants += p.getVotes();
		}
	}
	
	public void init(){
		for(Postulant p: postulants){
			p.init();
		}
	}
	
	public Postulant gagnant(){
		Postulant g = postulants.get(0);
		for (Postulant p: postulants){
			if (p.getVotes() >= g.getVotes()){
				g = p;
			}
		}
		return g;
	}
	
	public void resultats(){
		if (votants == 0){
			System.out.println("Scrutin annule, pas de votants");
		}
		else{
			calculerVotants();
			Postulant g = gagnant();
			System.out.format("Taux de participation -> %.1f pour cent\n", votants * 100 / (double)maxVotants);
			System.out.println("Nombre effectif de votants -> " + votants);
			System.out.println("Le chef choisi est -> " + g.getNom());
			System.out.println("Repartition des electeurs");
			for(Postulant p: postulants){
				System.out.format(p.getNom() + " ->  + %.1f pour cent des electeurs\n", p.getVotes() * 100 / (double)votants);
			}
		}
	}
}

interface CheckBulletin {
	public boolean checkDate();
}

abstract class Vote{
	private Postulant postulant;
	private int date;
	private int dateLimite;
	
	public Vote(Postulant postulant, int date, int dateLimite){
		this.postulant = postulant;
		this.date = date;
		this.dateLimite = dateLimite;
	};
	
	public Postulant getPostulant(){
		return postulant;
	}
	
	public int getDate(){
		return date;
	}
	
	public int getDateLimite(){
		return dateLimite;
	}
	
	public String toString(){
		return "pour " + postulant.getNom() + " -> " + (estInvalide() ? "invalide": "valide");
	};
		
	public abstract boolean estInvalide();
}

class BulletinPapier extends Vote{
	private boolean signe;

	public BulletinPapier(Postulant postulant, int date, int dateLimite, boolean signe) {
		super(postulant, date, dateLimite);
		this.signe = signe;
	}

	@Override
	public boolean estInvalide() {
		return !signe ;
	}

	public boolean checkDate() {
		return getDate() <= getDateLimite();
	}
	
	public String toString(){
		return "vote par bulletin papier pour " + getPostulant().getNom() + " -> " 
			+ (estInvalide() ? "invalide" : "valide");
	}
}

class BulletinCourrier extends BulletinPapier implements CheckBulletin{

	public BulletinCourrier(Postulant postulant, int date, int dateLimite, boolean signe) {
		super(postulant, date, dateLimite, signe);
	}
	
	
	@Override
	public boolean estInvalide() {
		return super.estInvalide() || !checkDate();
	}
	
	public String toString(){
		return "envoi par courrier d'un vote par bulletin papier pour " + getPostulant().getNom() + " -> " 
			+ (estInvalide() ? "invalide" : "valide");
	}
	
}

class BulletinElectronique extends Vote implements CheckBulletin{

	public BulletinElectronique(Postulant postulant, int date, int dateLimite) {
		super(postulant, date, dateLimite);
	}

	@Override
	public boolean estInvalide() {
		return !checkDate();
	}
	
	@Override
	public boolean checkDate() {
		return getDate() <= getDateLimite() - 2;
	}
	
	public String toString(){
		return "vote electronique pour " + getPostulant().getNom() + " -> " + (estInvalide() ? "invalide" : "valide");
	}
}

class Utils {

    private static final Random RANDOM = new Random();

    // NE PAS UTILISER CETTE METHODE DANS LES PARTIES A COMPLETER
    public static void setSeed(long seed) {
        RANDOM.setSeed(seed);
    }

    // génère un entier entre 0 et max (max non compris)
    public static int randomInt(int max) {
        return RANDOM.nextInt(max);
    }
}

/**
 * Classe pour tester la simulation
 */

class Votation {

    public static void main(String args[]) {
        // TEST 1
        System.out.println("Test partie I:");
        System.out.println("--------------");

        ArrayList<Postulant> postulants = new ArrayList<Postulant>();
        postulants.add(new Postulant("Tarek Oxlama"));
        postulants.add(new Postulant("Nicolai Tarcozi"));
        postulants.add(new Postulant("Vlad Imirboutine"));
        postulants.add(new Postulant("Angel Anerckjel"));

        postulants.get(0).elect();
        postulants.get(0).elect();

        postulants.get(1).elect();
        postulants.get(1).elect();
        postulants.get(1).elect();

        postulants.get(2).elect();

        postulants.get(3).elect();
        postulants.get(3).elect();
        postulants.get(3).elect();
        postulants.get(3).elect();

        // 30 -> nombre maximal de votants
        // 15 jour du scrutin
        Scrutin scrutin = new Scrutin(postulants, 30, 15);
        scrutin.calculerVotants();
        scrutin.resultats();

        // FIN TEST 1

        // TEST 2
        System.out.println("Test partie II:");
        System.out.println("---------------");

        scrutin = new Scrutin(postulants, 30, 15);
        scrutin.init();
        // tous les bulletins passent le check de la date
        // les parametres de simuler sont dans l'ordre:
        // le pourcentage de votants et le jour du vote
        scrutin.simuler(0.75, 12);
        scrutin.compterVotes();
        scrutin.resultats();

        scrutin = new Scrutin(postulants, 30, 15);
        scrutin.init();
        // seuls les bulletins papier passent
        scrutin.simuler(0.75, 15);
        scrutin.compterVotes();
        scrutin.resultats();

        scrutin = new Scrutin(postulants, 30, 15);
        scrutin.init();
        // les bulletins electroniques ne passent pas
        scrutin.simuler(0.75, 15);
        scrutin.compterVotes();
        scrutin.resultats();
        //FIN TEST 2

    }
}
