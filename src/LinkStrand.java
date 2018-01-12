import java.util.Iterator;

    public class LinkStrand implements IDnaStrand, Iterator<String> {
    	
    	private int myBreaks;
    	private long mySize;
    	private Node myFront, myBack, myCurrent;
    	
    	
    	public class Node{
    		String value;
    		Node next;
    		Node(String s){
    			value = s;
    			next = null;
    			
    		}
    		
    		public String getValue(){
    			return value;
    		}
    		
    		public Node getNext(){
    			return next;
    		}
    		
    		public void setValue(String s){
    			value = s;
    		}
    		
    		public void setNext(Node n){
    			next = n;
    		}
    	}

    	/**
    	 * Create a strand representing an empty DNA strand, length of zero.
    	 */
    	public LinkStrand() {
    		
    		initialize("");
    	}

    	/**
    	 * Create a strand representing s. No error checking is done to see if s
    	 * represents valid genomic/DNA data.
    	 * 
    	 * @param s
    	 *            is the source of cgat data for this strand
    	 */
    	public LinkStrand(String s) {
    		initialize(s);
    	}

    	/**
    	 * Initialize this strand so that it represents the value of source. No
    	 * error checking is performed.
    	 * 
    	 * @param source
    	 *            is the source of this enzyme
    	 */
    	@Override
    	public void initialize(String source) {
    		myFront = new Node(source);
    		myBack = myFront;
    		myCurrent = myFront;
    		mySize = source.length();
    		
    		// only create one node
    	}

    	/**
    	 * Cut this strand at every occurrence of enzyme, essentially replacing
    	 * every occurrence of enzyme with splicee.
    	 * 
    	 * @param enzyme
    	 *            is the pattern/strand searched for and replaced
    	 * @param splicee
    	 *            is the pattern/strand replacing each occurrence of enzyme
    	 * @return the new strand leaving the original strand unchanged.
    	 */
    	@Override
    	public IDnaStrand cutAndSplice(String enzyme, String splicee) {
    		
    		if (myBreaks != 0) {
    			throw new RuntimeException();
    			
    			// I hope this is right. This seems a little silly but whatever. 
    		}
			
			int position = 0;
			
			int start = 0;
			
			String search = this.toString();
			
			boolean first = true;
			
			LinkStrand output = null;
			
			while((position=search.indexOf(enzyme, position)) >= 0) {
			
				if (first == true) {
				
					output = new LinkStrand(search.substring(start, position));
					
					first = false;
			
				}
			
				else {
			
					output.append(search.substring(start,position));
			
				}
			
			start = position + enzyme.length();
			
			output.append(splicee);
			
			position ++;
			
			}
			
			if (start <search.length()) {
			
				if (output == null) {
			
					output = new LinkStrand("");
			
				}
			
				else {
				
					output.append(search.substring(start));
				
				}
			
			}
			
			return output;
    	}

    	/**
    	 * Returns the number of nucleotides/base-pairs in this strand.
    	 */
    	@Override
    	public long size() {
    		// TODO: Implement this method
    		return mySize;
    	}

    	/**
    	 * Return some string identifying this class.
    	 * 
    	 * @return a string representing this strand and its characteristics
    	 */
    	@Override
    	public String strandInfo() {
    		// TODO: Implement this method
    		return this.getClass().getName();
    	}

    	/**
    	 * Returns a string that can be printed to reveal information about what
    	 * this object has encountered as it is manipulated by append and
    	 * cutAndSplice.
    	 * 
    	 * @return
    	 */
    	@Override
    	public String getStats() {
    		// TODO: Implement this method
    		return String.format("# append calls = %d", myBreaks);
    	}

    	/**
    	 * Returns the sequence of DNA this object represents as a String
    	 * 
    	 * @return the sequence of DNA this represents
    	 */
    	@Override
    	public String toString() {
    		// TODO: Implement this method
    		StringBuilder output = new StringBuilder();
    		Node current = myFront;
    		while (current != null){
    			output.append(current.getValue());
    			current = current.next;
    		}
    		return output.toString();
    	}

    	/**
    	 * Append a strand of DNA to this strand. If the strand being appended is
    	 * represented by a LinkStrand object then an efficient append is performed.
    	 * 
    	 * @param dna
    	 *            is the strand being appended
    	 */
    	@Override
    	public void append(IDnaStrand dna) {
    		
    		
    		if (dna instanceof LinkStrand) {
    			myBreaks += 1;
    			mySize += dna.size();
    			
    			
    			myBack.next = ((LinkStrand) dna).myFront;
    			myBack = ((LinkStrand) dna).myBack;
    		}
    		else {
    			append(dna.toString());
    			//This should work
    		}
    	}

    	/**
    	 * Simply append a strand of dna data to this strand.
    	 * 
    	 * @param dna
    	 *            is the String appended to this strand
    	 */
    	@Override
    	public void append(String dna) {
    		myBreaks += 1;
    		mySize += dna.length();
    		Node temp = new Node(dna);
    		myBack.next = temp;
    		myBack = temp;
    	}

    	/**
    	 * Returns an IDnaStrand that is the reverse of this strand, e.g., for
    	 * "CGAT" returns "TAGC"
    	 * 
    	 * @return reverse strand
    	 */
    	@Override
    	public IDnaStrand reverse() {
    		StringBuilder reversal = new StringBuilder();

    		LinkStrand temp = this;

    		reversal.append(myFront.value);

    		reversal.reverse();

    		LinkStrand linkReverse = new LinkStrand(reversal.toString());

    		// I need an instance of the first nucleotide reversed

    		// stored as a node because then I won't need to append character

    		// by character

    		temp.myFront = temp.myFront.next;

    		while (temp.size() < mySize) {

    		Node temp1 = temp.myFront;

    		temp.myFront = temp.myFront.next;

    		temp1.next = null;

    		StringBuilder reversal1 = new StringBuilder();

    		reversal1.append(temp1.value);

    		reversal1.reverse();

    		linkReverse.append(reversal1.toString());

    		}

    		return linkReverse;

    	}

    	/**
    	 *	Returns the next element in the underlying LinkedList data structure
    	 */
    	@Override
    	public String next() {
    		// TODO: Implement this method
    		String temp = myCurrent.getValue();
    		myCurrent = myCurrent.next;
    		return temp;
    	}

    	/**
    	 *	True if next evaluates correctly
    	 *	False if next returns with an error
    	 */
    	@Override
    	public boolean hasNext() {
    		if (myCurrent == null){
    			return false;
    		}
    		else {
    			return true;
    		}
    	}
    }
    