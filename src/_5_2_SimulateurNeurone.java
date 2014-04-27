/**
 * @author Riabchenko Vlad
 */

import java.util.ArrayList;

class Position{
	private double x;
	private double y;
	
	public Position(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Position(){
		this(0, 0);
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	};
	
	public String toString(){
		return String.format("%.1f, %.1f)", x, y);
	}
}

class Neurone{
	private Position position;
	private ArrayList<Neurone> connexions;
	private double signal;
	private double attenuation;
	
	public Neurone(Position position, double attenuation){
		this.position = position;
		this.attenuation = attenuation;
		signal = 0;
		connexions = new ArrayList<Neurone>();
	}
	
	public void connexion(Neurone n){
		connexions.add(n);
	}
	
	public int getNbConnexions(){
		return connexions.size();
	}
	public Neurone getConnexion(int index){
		return connexions.get(index);
	}
	public Position getPosition(){
		return position;
	}
	public double getAttenuation(){
		return attenuation;
	}
	public double getSignal(){
		return signal;
	}
	
	public void recoitStimulus(double stimulus){
		signal = attenuation * stimulus;
		for(Neurone n: connexions){
			n.recoitStimulus(signal);
		}
	}
	
	public String toString (){
		String res = "Le neurone en position " + position 
				+ " avec attenuation " + String.format("%.1f", attenuation);
		if (getNbConnexions() > 0){
			res += " en connexion avec\n";
			for(Neurone n: connexions){
				res += "  - un neurone en position " + n.position + "\n";
			}
		}
		else{
			res += " sans connexion\n";
		}
		return res;
	}
}

class NeuroneCumulatif extends Neurone{

	public NeuroneCumulatif(Position position, double attenuation) {
		super(position, attenuation);
	}
	
	public void recoitStimulus(double stimulus){
		super.recoitStimulus(getSignal()/getAttenuation() + stimulus);
	}
}

class Cerveau{
	private ArrayList<Neurone> neurones;
	
	public Cerveau(){
		neurones = new ArrayList<Neurone>();
	}
	
	public int getNbNeurones(){
		return neurones.size();
	}
	
	public Neurone getNeurone(int index){
		return neurones.get(index);
	}
	
	public void ajouterNeurone(Position position, double attenuation){
		neurones.add(new Neurone(position, attenuation));
	}
	
	public void ajouterNeuroneCumulatif(Position position, double attenuation){
		neurones.add(new NeuroneCumulatif(position, attenuation));
	}
	
	public void stimuler(int index, double stimulus){
		neurones.get(index).recoitStimulus(stimulus);
	}
	
	public double sonder(int index){
		return neurones.get(index).getSignal();
	}
	
	public void creerConnexions(){
		if (neurones.size() > 1){
			neurones.get(0).connexion(neurones.get(1));
		}
		if (neurones.size() > 2){
			neurones.get(0).connexion(neurones.get(2));
		}
		
		for (int i = 1; i < neurones.size() - 2; i+=2){
			neurones.get(i).connexion(neurones.get(i + 1));
			neurones.get(i + 1).connexion(neurones.get(i + 2));
		}
	}
	
	public String toString(){
		String res = "Le cerveau contient " + neurones.size() + " neurone(s)\n";
		for (Neurone n: neurones){
			res += n + "\n";
		}
		return res + "*----------*\n\n";
	}
}

public class _5_2_SimulateurNeurone {

    public static void main(String[] args) {
        // TEST DE LA PARTIE 1
        System.out.println("Test de la partie 1:");
        System.out.println("--------------------");

        Position position1 = new Position(0, 1);
        Position position2 = new Position(1, 0);
        Position position3 = new Position(1, 1);

        Neurone neuron1 = new Neurone(position1, 0.5);
        Neurone neuron2 = new Neurone(position2, 1.0);
        Neurone neuron3 = new Neurone(position3, 2.0);

        neuron1.connexion(neuron2);
        neuron2.connexion(neuron3);
        neuron1.recoitStimulus(10);

        System.out.println("Signaux : ");
        System.out.println(neuron1.getSignal());
        System.out.println(neuron2.getSignal());
        System.out.println(neuron3.getSignal());

        System.out.println();
        System.out.println("Premiere connexion du neurone 1");
        System.out.println(neuron1.getConnexion(0));


        // FIN TEST DE LA PARTIE 1

        // TEST DE LA PARTIE 2
        System.out.println("Test de la partie 2:");
        System.out.println("--------------------");

        Position position5 = new Position(0, 0);
        NeuroneCumulatif neuron5 = new NeuroneCumulatif(position5, 0.5);
        neuron5.recoitStimulus(10);
        neuron5.recoitStimulus(10);
        System.out.println("Signal du neurone cumulatif  -> " + neuron5.getSignal());

        // FIN TEST DE LA PARTIE 2

        // TEST DE LA PARTIE 3
        System.out.println();
        System.out.println("Test de la partie 3:");
        System.out.println("--------------------");
        Cerveau cerveau = new Cerveau();

        // parametres de construction du neurone:
        // la  position et le facteur d'attenuation
        cerveau.ajouterNeurone(new Position(0,0), 0.5);
        cerveau.ajouterNeurone(new Position(0,1), 0.2);
        cerveau.ajouterNeurone(new Position(1,0), 1.0);

        // parametres de construction du neurone cumulatif:
        // la  position et le facteur d'attenuation
        cerveau.ajouterNeuroneCumulatif(new Position(1,1), 0.8);
        cerveau.creerConnexions();
        cerveau.stimuler(0, 10);

        System.out.println("Signal du 3eme neurone -> " + cerveau.sonder(3));
        System.out.println(cerveau);
        // FIN TEST DE LA PARTIE 3
    }
}
