public class Main {
	
	public static void main (String[] args){
		Entropy e = new Entropy ("Exemplo.txt");
		
		System.out.println("Por simbolo: " + e.getSingleEntropy());
		System.out.println("Por par: " + e.getPairEntropy());
		System.out.println("Por palavra: " + e.getWordEntropy());
	}
	
}
