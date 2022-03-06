/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.File;
import java.io.IOException;

/**
 * Example program using GraphPoet.
 * 
 * <p>PS2 instructions: you are free to change this example class.
 */
public class Main {
    
    /**
     * Generate example poetry.
     * 
     * @param args unused
     * @throws IOException if a poet corpus file cannot be found or read
     */
    public static void main(String[] args) throws IOException {
        final GraphPoet nimoy = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
        final String input1 = "Test the system.";
        System.out.println(input1 + "\n>>>\n" + nimoy.poem(input1));
        
        System.out.println();
        
        final GraphPoet example = new GraphPoet(new File("src/P1/poet/example.txt"));
        final String input2 = "Some like flowers, but some like leaves.";
        System.out.println(input2 + "\n>>>\n" + example.poem(input2));
    }
    
}
