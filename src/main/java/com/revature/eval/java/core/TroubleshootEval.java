package com.revature.eval.java.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TroubleshootEval {
	public static void main(String[] args) {
		EvaluationService service = new EvaluationService();
		String str;
		int cipher;
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Enter your nth: ");
			int nth = Integer.parseInt(sc.nextLine());
//			System.out.println("Enter your string: ");
//			str = sc.nextLine();
			
			System.out.println("Nth Prime: " + service.calculateNthPrime(nth));
			 if(nth == -1){
		            break;
		        }
			
		}
		sc.close();

	}

}
