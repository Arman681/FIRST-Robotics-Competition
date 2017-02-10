package org.usfirst.frc.team5872.robot;

import java.util.*;

public class EthanMario {
 
	public static void main(String[] args) {
		
		int a = 0;
		int b; 
		int y = 0;
		int x;
		Scanner num = new Scanner(System.in);
		System.out.println("Please input a number from 1-9");
		x = num.nextInt();
		b = x;
		for (a=0;a<b;a++){
			System.out.println("*");
			for (y=0;y<x-1;y++){
				System.out.print("*");
			}
		x--;
		}

	}

}
