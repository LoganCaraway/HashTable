/*
 * Author: Logan Caraway
 * Date Created: 5/31/2018
 * Purpose: It's a Hash Table that uses quadratic probing to resolve collisions
 */
package pkg232hashtable;

import java.util.Arrays;

public class HashTable <Key, Value> {
    private Key keys[];
    private Value vals[];
    private int size;
    private int num;
    
    /*Constructor*/
    public HashTable() {
        num = 0;
        size = 16;
        keys = (Key[]) new Object[size];
        vals = (Value[]) new Object[size];
    }
    /*Constructor*/
    public HashTable(int length) {
        num = 0;
        size = length;
        keys = (Key[]) new Object[size];
        vals = (Value[]) new Object[size];
    }
    
    private int hash(Key key)
    {return (key.hashCode() & 0x7fffffff) % size;}
    
    /* Adds the Key/Value pair to the table if not already present.
     * If the key is already in the table, update its Value to the new one.
     */
    public void put(Key key, Value val) {
        if (num >= size * 4 / 5)
            resize(2 * size);
        
        //try to place in regular location; if a conflict occurs, use quadratic probing
        int i;
        int j = 0;
        for (i = hash(key); keys[i] != null; i = (i + (j * j)) % size) {
            //if the table already contains given key, update its value
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
            j++;
        }
        //we found an empty slot
        keys[i] = key;
        vals[i] = val;
        num++;
    }
    
    /*Returns the value paired with the given key*/
    public Value get(Key key) {
        int j = 0;
        for (int i = hash(key); keys[i] != null; i = (i + (j * j)) % size) {
            if (keys[i].equals(key))
                return vals[i];
            j++;
        }
        return null;
    }
    
    /*Deletes the Key/Value pair for the given Key*/
    public void delete(Key key) {
        if (!contains(key))
            return;
        int i = hash(key);
        int j = 0;
        while (!keys[i].equals(key)) {
            i = (i + (j * j)) % size;
            j++;
        }
        keys[i] = null;
        vals[i] = null;
        i = (i + (j * j)) % size;
        while (keys[i] != null) {
            j++;
            Key key_to_redo = keys[i];
            Value val_to_redo = vals[i];
            keys[i] = null;
            vals[i] = null;
            num--;
            put(key_to_redo, val_to_redo);
            i = (i + (j * j)) % size;
        }
        num--;
        if (num > 0 && num <= size/8)
            resize(size/2);
    }
    
    /*Resizes the array to the given size*/
    private void resize(int new_size) {
        HashTable<Key, Value> new_table = new HashTable<>(new_size);
        for (int i = 0; i < size; i++)
            if (keys[i] != null)
                new_table.put(keys[i], vals[i]);
        keys = new_table.getKeyArray();
        vals = new_table.getValueArray();
        size = new_table.getSize();
    }
    
    /*Returns true if this hash table contains the given key; false otherwise*/
    private boolean contains(Key key) {
        //iterate through keys array
        for (int i = 0; i < size; i++)
            if (key.equals(keys[i]))
                return true;
        return false;
    }
    
    /*Prints contents of table to console*/
    public void printToConsole() {
        Key ks[] = getKeyArray();
        Value vs[] = getValueArray();
        
        System.out.println("Keys: Values");
        //print every non-null key-value pair
        for (int i = 0; i < size; i++)
            if (ks[i] != null)
                System.out.println(ks[i]+"["+i+"]: "+vs[i]);
        System.out.println();
    }
    
    /*Returns an array of the keys in this hashtable*/
    public Key[] getKeyArray()
        {return Arrays.copyOf(keys, keys.length);}
    
    /*Returns an array of the values in this hashtable*/
    public Value[] getValueArray()
        {return Arrays.copyOf(vals, vals.length);}
    
    /*Returns the size of this hashtable*/
    public int getSize()
        {return size;}
}
