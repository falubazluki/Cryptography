import java.util.Scanner;
import java.math.BigInteger;

public class Zadanie1{

	public static int generatorLCG(int a, int b, int m, int x){
		return x = (a * x + b) % m;
	}

	public static int modInv(int value1, int value2){
		String x = "" + value1;
		String y = "" + value2;
		BigInteger a, b, c;

		a = new BigInteger(x);
		b = new BigInteger(y);
		c = a.modInverse(b);
		System.out.println(c);
		int result = c.intValue();
		return result;
	}

	public static void main(String[] args){
		//variables to generate values
		int a = 14; // multiplier
		int m = 17; // modulus
		int b = 2;  // increment
		int howMany; // how many numbers generate
		int x = 5; // seed

		//variables to tests
		int[] generated_values = new int[7];

		System.out.println("Predicting rest of values will start after first 7 generated values...");

		Scanner write = new Scanner(System.in);
		System.out.println("How many pseudo-random numbers do you want to generate? ");
		howMany = write.nextInt();

		int count = 0;
		while (count < howMany){
			x = generatorLCG(a, b, m, x);
			if (count < 7){
				generated_values[count] = x;
			}							
			System.out.println("Pseudo-random generated number is: " + x);	
			count++;
			if (count == generated_values.length){
				int[] valuesToGCD = new int[4];
				valuesToGCD = findValuesToGCD(generated_values);
				int modulus = calculateGCDofGivenArray(valuesToGCD);
				int multiplier = findMultiplier(generated_values, modulus);
				int increment = findIncrement(generated_values, modulus, multiplier);
				int nextValue = calculateNextValues(generated_values, modulus, multiplier, increment, howMany);
			}
			//count++;
		}
	}

	public static int[] findValuesToGCD(int[] table){
		int[] t_values = new int[6];
		int[] z_values = new int[4];
		for (int t = 0; t < t_values.length; t++){
			t_values[t] = table[t+1] - table[t];
		}
		for (int z = 0; z < z_values.length; z++){
			z_values[z] = Math.abs((t_values[z+2] * t_values[z]) - (t_values[z+1] * t_values[z+1]));
		}
		return z_values;
	}

	static int gcd(int a, int b){
        if (a == 0)
            return b;
        return gcd(b%a, a);
    }
     
    static int calculateGCDofGivenArray(int[] table){
        int modulus = table[0];
        for (int i = 1; i < table.length; i++){
            modulus = gcd(table[i], modulus);
        }        
        return modulus;
    }

    static int findMultiplier(int[] valuesGeneratedByLCG, int modulus){
    	int valueToInvertedModulo = valuesGeneratedByLCG[1] - valuesGeneratedByLCG[0];
    	int multiplier = ((valuesGeneratedByLCG[2] - valuesGeneratedByLCG[1]) * modInv(valueToInvertedModulo, modulus) % modulus);
    	return multiplier;
    }

    static int findIncrement(int[] valuesGeneratedByLCG, int modulus, int multiplier){
    	int increment = (valuesGeneratedByLCG[1] - ((valuesGeneratedByLCG[0] * multiplier) % modulus));
    	return increment;
    }

    static int calculateNextValues(int[] valuesGeneratedByLCG, int modulus, int multiplier, int increment, int howMany){
    	int nextValue = valuesGeneratedByLCG[valuesGeneratedByLCG.length - 1];
    	System.out.println("Nastepna liczba to: " + nextValue);
    	for (int i = 7; i < howMany; i++){
    		nextValue = (multiplier * nextValue + increment) % modulus;
    		System.out.println("Next values will be: " + nextValue);
    	}
    	return 1;
    }
}