public class BST<K extends Comparable<K>, T> implements Map<K, T> {
	public class BSTNode <K extends Comparable<K>, T> {
		public K key;
		public T data;
		public BSTNode<K,T> left, right;
		
		public BSTNode(K k, T val) {
			key = k;
			data = val;
			left = right = null;
		}
		
		public BSTNode(K k, T val, BSTNode<K,T> l, BSTNode<K,T> r) {
			key = k;
			data = val;
			left = l;
			right = r;
		}
	}

	BSTNode<K,T> root, current, q;
	
	public BST() {
		root = current = null;
	}

	@Override
	public boolean empty() {
		return root == null;
	}

	@Override
	public boolean full() {
		return false;
	}

	@Override
	public T retrieve() {
		return current.data;
	}

	@Override
	public void update(T e) {
		current.data = e;
	}

	@Override
	
	public Pair<Boolean, Integer> find(K key) {
		int KeyComparisons = 0;
		boolean exists = false;
		
		if(empty()) {
			return new Pair<Boolean ,Integer>(exists , KeyComparisons);
		}
		
			current = root;
			q = null;
			
		while(current!= null) {
			q = current;
			 KeyComparisons++;
			 
			if((key.compareTo(current.key)) > 0)
				current = current.right;
			else if((key.compareTo(current.key)) < 0)
				current = current.left;
			else {
				exists = true;
				break;
				}
		}
		current = q;
		return new Pair<Boolean ,Integer>(exists , KeyComparisons);
	}

	@Override
	public Pair<Boolean, Integer> insert(K key, T data) {
		BSTNode<K,T> Newnode, q = current;
		
		Pair<Boolean ,Integer> newpair = find(key);
		if(newpair.first == true) {
			current = q;
			newpair.first = false;
			return newpair;
		}
		
		Newnode = new BSTNode<K,T>(key, data);
		if (empty()) {
			root = current = Newnode;
		}
		else {
			
			if((key.compareTo(current.key)) > 0)
				current.right = Newnode;
			else
				current.left = Newnode;
			
			current = Newnode;
			newpair.first = true;	
		}

		return newpair;
	}

	@Override
	public Pair<Boolean, Integer> remove(K key) {
		   BSTNode<K,T>  LastCurrent = current;
	        Pair < Boolean , Integer > pair = find (key);
	        if (pair.first == false)  {
	        
	            current = LastCurrent;
	            pair.first = false;
	        } else {
	            K k1 = key;
	            BSTNode<K,T> p = root;
	            BSTNode<K,T> q = null;
	            while (p != null) {
	            
	                    if (k1.compareTo(p.key) < 0) {
	                        q =p;
	                        p = p.left;
	                    } 
	                    else if (k1.compareTo(p.key) > 0) {    
	                        q = p;
	                        p = p.right;
	                    }
	                    else { 	                      
	                        if ((p.left != null) && (p.right != null)) { 
	                            BSTNode<K,T> min = p.right;
	                            q = p;
	                            while (min.left != null) {
	                                q = min;
	                                min = min.left;
	                            }
	                            p.key = min.key;
	                            p.data = min.data;
	                            k1 = min.key;
	                            p = min;
	                        }
	                        if (p.left != null) {
	                            p = p.left;
	                        } 
	                        else {
	                            p = p.right;
	                        }
	                        if (q == null) {
	                            root = p;
	                        } 
	                        else {
	                        
	                            if (k1.compareTo(q.key) > 0) 
	                                q.right = p;
	                            else 
	                                q.left = p;
	                        }
	                    current = root;
	                } 
	            }  
	            pair.first = true;
	        }
	        return pair;
	}

	@Override
	public List<K> getAll() {
		List <K> list = new LinkedList<K>();
		printAscending(root, list); 
        return list;
	}
	
	private void printAscending(BSTNode<K,T> q, List <K> list) {
    
        if (q != null) {
        	printAscending(q.left ,list); 
            list.insert(q.key);
            printAscending(q.right ,list); 
        }
        else return;
    }
    
}
