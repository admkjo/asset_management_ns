package com.gh.gov.ns.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class CredentialsGenerator {
	
	public String generateUsername(String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        
        
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        
        return output.toString();
    }
	
	
	public String generatePassword(String username) {
		Random rand = new Random();
		int num=rand.nextInt(10000);
		String password=generateUsername(username) + String.valueOf(num).toString();
		return password;
	}

}
