/**
 * @author Riabchenko Vlad
 */

abstract class Navire{
	private int x;
	private int y;
	private int drapeau;
	private boolean detruit;
	
	public Navire(int px, int py, int drapeau){
		x = px;
		if (x < 0){
			x = 0;
		}
		if (x > _4_2_Piraterie.MAX_X){
			x = _4_2_Piraterie.MAX_X;
		}
		y = py;
		if (y < 0){
			y = 0;
		}
		if (y > _4_2_Piraterie.MAX_Y){
			y = _4_2_Piraterie.MAX_Y;
		}
		this.drapeau = drapeau;
		this.detruit = false;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getDrapeau(){
		return drapeau;
	}
	public boolean estDetruit(){
		return detruit;
	}
	public String getEtat(){
		if (detruit){
			return "detruit";
		}
		else{
			return "intact";
		}
	}
	
	public String toString(){
		return getNom() + " avec drapeau " + drapeau + " en (" + x + "," + y + ") -> " + getEtat();
	}
	
	public double distance(Navire autre) {
		return Math.sqrt((x - autre.x) * (x - autre.x) + (y - autre.y) * (y - autre.y));
	}
	
	public void avance(int unitsX, int unitsY){
		x += unitsX;
		if (x < 0){
			x = 0;
		}
		if (x > _4_2_Piraterie.MAX_X){
			x = _4_2_Piraterie.MAX_X;
		}
		y += unitsY;
		if (y < 0){
			y = 0;
		}
		if (y > _4_2_Piraterie.MAX_Y){
			y = _4_2_Piraterie.MAX_Y;
		}
	}
	
	public void coule() {
		detruit = true;
	}
	
	public String getNom(){
		return "Bateau";
	}
	
	public void rencontre(Navire autre){
		if (distance(autre) < _4_2_Piraterie.RAYON_RENCONTRE && drapeau != autre.drapeau){
			combat(autre);
		}
	}
	
	public abstract void combat(Navire autre);
	
	public abstract boolean estPacifique();
	
	public abstract void recoitBoulet();
}

class Pirate extends Navire{
	
	private boolean endommage;
	
	public Pirate(int x, int y, int drapeau, boolean endommage){
		super(x, y, drapeau);
		this.endommage = endommage;
	};
	
	public boolean estEndommage(){
		return endommage;
	}
	
	public String getNom(){
		return "Bateau pirate";
	}
	
	public String getEtat(){
		if (estDetruit()){
			return "detruit";
		}
		else{
			if (endommage){
				return "ayant subi des dommages";
			}
			else{
				return "intact";
			}
		}
	}

	@Override
	public boolean estPacifique() {
		return false;
	}

	@Override
	public void recoitBoulet() {
		if (endommage){
			coule();
		}
		else{
			endommage = true;
		}
	}
	
	public String toString(){
		return getNom() + " avec drapeau " + getDrapeau() + " en (" + getX() + "," + getY() + ") -> " + getEtat();
	}

	@Override
	public void combat(Navire autre) {
		autre.recoitBoulet();
		if (!autre.estPacifique()){
			recoitBoulet();
		}
	}
}

class Marchand extends Navire{
	
	public Marchand(int x, int y, int drapeau) {
		super(x, y, drapeau);
	}

	public String getNom(){
		return "Bateau marchand";
	}

	@Override
	public boolean estPacifique() {
		return true;
	}

	@Override
	public void recoitBoulet() {
		coule();
	}
	
	public String toString(){
		return getNom() + " avec drapeau " + getDrapeau() + " en (" + getX() + "," + getY() + ") -> " + getEtat();
	}
	
	@Override
	public void combat(Navire autre) {
		if (!autre.estPacifique()){
			recoitBoulet();
		}
	}
}

class _4_2_Piraterie {

    static public final int MAX_X = 500;
    static public final int MAX_Y = 500;
    static public final double RAYON_RENCONTRE = 10;

    static public void main(String[] args) {
        // Test de la partie 1
        System.out.println("***Test de la partie 1***");
        System.out.println();
        // un bateau pirate 0,0 avec le drapeau 1 et avec dommages
        Navire ship1 = new Pirate(0, 0, 1, true);
        // un bateau marchand en 25,0 avec le drapeau 2
        Navire ship2 = new Marchand(25, 0, 2);
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println("Distance: " + ship1.distance(ship2));
        System.out.println("Quelques deplacements horizontaux et verticaux");
        // se deplace de 75 unites a droite et 100 en haut
        ship1.avance(75, 100);
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println("Un deplacement en bas:");
        ship1.avance(0, -5);
        System.out.println(ship1);
        ship1.coule();
        ship2.coule();
        System.out.println("Apres destruction:");
        System.out.println(ship1);
        System.out.println(ship2);

        // Test de la partie 2
        System.out.println();
        System.out.println("***Test de la partie 2***");
        System.out.println();

        // deux vaisseaux sont enemis s'ils ont des drapeaux differents

        System.out.println("Bateau pirate et marchand ennemis (trop loins):");
        // bateau pirate intact
        ship1 = new Pirate(0, 0, 1, false);
        ship2 = new Marchand(0, 25, 2);
        System.out.println(ship1);
        System.out.println(ship2);
        ship1.rencontre(ship2);
        System.out.println("Apres la rencontre:");
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println();

        System.out.println("Bateau pirate et marchand ennemis (proches):");
        // bateau pirate intact
        ship1 = new Pirate(0, 0, 1, false);
        ship2 = new Marchand(2, 0, 2);
        System.out.println(ship1);
        System.out.println(ship2);
        ship1.rencontre(ship2);
        System.out.println("Apres la rencontre:");
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println();

        System.out.println("Bateau pirate et marchand amis (proches):");
        // bateau pirate intact
        ship1 = new Pirate(0, 0, 1, false);
        ship2 = new Marchand(2, 0, 1);
        System.out.println(ship1);
        System.out.println(ship2);
        ship1.rencontre(ship2);
        System.out.println("Apres la rencontre:");
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println();

        System.out.println("Deux bateaux pirates ennemis intacts (proches):");
        // bateaux pirates intacts
        ship1 = new Pirate(0, 0, 1, false);
        ship2 = new Pirate(2, 0, 2, false);
        System.out.println(ship1);
        System.out.println(ship2);
        ship1.rencontre(ship2);
        System.out.println("Apres la rencontre:");
        System.out.println(ship1);
        System.out.println(ship2);
        System.out.println();

        System.out.println("Un bateau pirate intact et un avec dommages, ennemis:");
        // bateau pirate intact
        Navire ship3 = new Pirate(0, 2, 3, false);
        System.out.println(ship1);
        System.out.println(ship3);
        ship3.rencontre(ship1);
        System.out.println("Apres la rencontre:");
        System.out.println(ship1);
        System.out.println(ship3);
        System.out.println();

        System.out.println("Deux bateaux pirates ennemis avec dommages:");
        System.out.println(ship2);
        System.out.println(ship3);
        ship3.rencontre(ship2);
        System.out.println("Apres la rencontre:");
        System.out.println(ship2);
        System.out.println(ship3);
        System.out.println();
    }
}
