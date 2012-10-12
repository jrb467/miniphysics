
public class teasste {
	
	public static void main(String[] args){
		System.out.print("private static final float[] SINES = {");
		for(int i = 0; i <= 90; i++){
			System.out.print((float)Math.sin(Math.toRadians(i))+"f,");
			if(i%10 == 9 && i != 89) {
				System.out.println();
			}
		}
		System.out.print("};");
	}
}
