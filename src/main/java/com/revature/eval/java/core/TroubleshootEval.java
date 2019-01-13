package com.revature.eval.java.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.revature.eval.java.core.EvaluationService.AtbashCipher;

public class TroubleshootEval {
	public static void main(String[] args) {
		EvaluationService service = new EvaluationService();
		String str;
		String str2;
		int cipher;
		Scanner sc = new Scanner(System.in);

//		while (true) {
//			System.out.println("Enter your process: ");
//			int i = Integer.parseInt(sc.nextLine());
//			System.out.println("Enter your string: ");
//			str = sc.nextLine();
			
//			if (i == 1) {
//				System.out.println("Encode: " + AtbashCipher.encode(str));
//			} else {
//				System.out.println("Decode: " + AtbashCipher.decode(str));
//			}
			System.out.println(service.isLuhnValid("046a 454 286"));
//			if (str == "exit") {
//				break;
//			}

//		}
//		sc.close();

	}

}
