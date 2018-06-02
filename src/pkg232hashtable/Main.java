/*
 * Author: Logan Caraway
 * Date Created: 5/31/2018
 * Purpose: It's a Hash Table that uses quadratic probing to resolve collisions
 */
package pkg232hashtable;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        HashTable<Character, Integer> table = new HashTable<>();
        String choice;
        while (true) {
            choice = JOptionPane.showInputDialog("Main Menu: <1> add, <2> delete, <3> to print, <4> search, <0> to quit");
            switch(choice.charAt(0)) {
                case ('1'): try {
                                char key = JOptionPane.showInputDialog("Enter key (character):").charAt(0);
                                int val = Integer.parseInt(JOptionPane.showInputDialog("Enter value (integer):"));
                                table.put(key, val);
                            } catch (Exception e)
                                {JOptionPane.showMessageDialog(null, "Incorrect input format, items not added to table");}
                            break;
                case ('2'): table.delete(JOptionPane.showInputDialog("Enter key to delete:").charAt(0));
                            break;
                case ('3'): table.printToConsole();
                            break;
                case ('4'): Integer val = table.get(JOptionPane.showInputDialog("Enter key to search for: ").charAt(0));
                            if (val != null)
                                JOptionPane.showMessageDialog(null, "The given key is found to have value: "+ val);
                            else
                                JOptionPane.showMessageDialog(null, "The given key was not found");
                            break;
                case ('0'): System.exit(0);
            }
        }
    }
    
}
