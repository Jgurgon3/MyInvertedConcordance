






/*************************************************************************
 *Jim Gurgone
 *Assignment 3
 *3.5.21
 *CSC 403-510
 *************************************************************************/

public class MyInvertedConcordance {

    // write the concordance to a file
    private static void serialize (String fileName, ST<String, SET<Integer>> concordance) {
        Out out = new Out (fileName);
        out.println (concordance.size ());
        for (String s : concordance.keys ()) {
            SET<Integer> set = concordance.get(s);
            if (set == null) set = new SET<Integer>();

            out.println (s);            
            out.println (set.size ());       
            for (int k : set)
                out.println (k);
        }
        out.close ();
    }
    // read the concordance from a file
    private static ST<String, SET<Integer>> deserialize (String fileName) {
        ST<String, SET<Integer>> concordance = new ST<String, SET<Integer>> ();
        In in = new In (fileName);
        int sizeConcordance = in.readInt ();
        for (int iConcordance = 0; iConcordance < sizeConcordance; iConcordance++) {
            String key = in.readString ();
            int sizeSet = in.readInt ();
            SET<Integer> set = new SET<Integer>();
            for (int iSet = 0; iSet < sizeSet; iSet++) 
                set.add (in.readInt ());
            concordance.put (key, set);
        }
        in.close ();
        return concordance;
    }
    public static ST<String, SET<Integer>> createConcordance (String[] words) {
        ST<String, SET<Integer>> st = new ST<String, SET<Integer>>();
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            if (!st.contains(s)) {
                st.put(s, new SET<Integer>());
            }
            SET<Integer> set = st.get(s);
            set.add(i);
        }
        return st;
    }
    public static String[] invertConcordance (ST<String, SET<Integer>> st) 
    {
    	int k = 0;
    	for(String key : st.keys())
    	{
    	  for(Integer i : st.get(key))
    	  {
    	    if(i > k)
    	    { 
    	    	k = i;
    	    }
    	  }
    	} 
    	String[] newWords =  new String[k+1];
    	for(String key : st.keys())
    	{
    	  for(Integer i : st.get(key))
    	  {
    	    newWords[i] = key;
    	  }
    	}
    	return newWords;
    }
    
    
    private static void saveWords (String fileName, String[] words) {
        int MAX_LENGTH = 70;
        Out out = new Out (fileName);
        int length = 0;
        for (String word : words) {
            length += word.length ();
            if (length > MAX_LENGTH) {
                out.println ();
                length = word.length ();
            }
            out.print (word);
            out.print (" ");
            length++;
        }
        out.close ();
    }
    public static void main(String[] args) {
        String fileName = "tale";
        In in = new In (fileName);
        String[] words = in.readAll().split("\\s+");
        
        ST<String, SET<Integer>> st = createConcordance (words);
        StdOut.println("Finished building concordance");

        // write to a file and read back in (to check that serialization works)
        serialize ("concordance-tale.txt", st);
        st = deserialize ("concordance-tale.txt");
        
        words = invertConcordance (st);
        saveWords ("reconstructed-tale.txt", words);
    }
}

