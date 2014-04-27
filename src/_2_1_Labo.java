/**
 * @author Riabchenko Vlad
 */

class Souris {

    public static final int ESPERANCE_VIE_DEFAUT = 36;
    private int poids; 
    private String couleur;
    private int age;
    private int esperanceVie;
    private boolean clonee;
    
    public Souris(int poids, String couleur, int age, int esperanceVie){
    	this.poids = poids;
    	this.couleur = couleur;
    	this.age = age;
    	this.esperanceVie = esperanceVie;
    	this.clonee = false;
    	System.out.println("Une nouvelle souris !");
    }
    
    public Souris(int poids, String couleur, int age){
    	this(poids, couleur, age, ESPERANCE_VIE_DEFAUT);
    }
    
    public Souris(int poids, String couleur){
    	this(poids, couleur, 0);
    }
    
    public Souris(Souris copy){
    	this.poids = copy.poids;
    	this.couleur = copy.couleur;
    	this.age = copy.age;
    	this.esperanceVie = copy.esperanceVie * 4 / 5;
    	this.clonee = true;
    	System.out.println("Clonage d'une souris !");
    }
    
    @Override
    public String toString(){
    	String res =  "Une souris " + couleur;
    	res += clonee ? ", clonee," : "";
    	res += " de " + age + " mois et pesant " + poids + " grammes";
    	return res;
    }
    
    public void vieillir(){
    	age = esperanceVie;
    	if (clonee == true && age > esperanceVie / 2){
    		couleur = "verte";
    	}
    }
    
    public void evolue(){
    	vieillir();
    }
}

public class _2_1_Labo {

    public static void main(String[] args) {
        Souris s1 = new Souris(50, "blanche", 2);
        Souris s2 = new Souris(45, "grise");
        Souris s3 = new Souris(s2);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        s1.evolue();
        s2.evolue();
        s3.evolue();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
    }
}
