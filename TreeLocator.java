 public class TreeLocator<T> implements Locator<T> {
	 
	class TreeOf4 <T>{
		 
		 Location loc;
		 List<T> info = new LinkedList<T>();
		 
		 TreeOf4<T> ch1,ch2,ch3,ch4;
		 
		  public TreeOf4(Location loc, T data) {
              this.loc = loc;
              ch1 = ch2 = ch3 = ch4 = null;
              this.info.insert(data);
		  }  
	 }
	 
	 TreeOf4<T> root , current;
	 
	 public TreeLocator() {
	        root = current = null;
	 }    
	 
	@Override
	public int add(T e, Location loc) {
        Pair < Boolean , Integer > newpair = findLoc(loc);
        
        if (!newpair.first) {
        	TreeOf4<T> newNode = new TreeOf4<> (loc,e);
        	
           if (root != null) {
        	   
                if ((loc.x < current.loc.x) && (loc.y <= current.loc.y))
                	current.ch1 = newNode;
                else if ((loc.x  <= current.loc.x) && (loc.y > current.loc.y))
                	current.ch2 = newNode;
                else if ((loc.x  > current.loc.x) && (loc.y >= current.loc.y))
                	current.ch3 = newNode;
                else if ((loc.x  >= current.loc.x) && (loc.y < current.loc.y))
                	current.ch4 = newNode;
                current = newNode;
                
           } else { root = current = newNode; }
           
        } else { current.info.insert(e); }
        
        return newpair.second;
	}

	@Override
	public Pair<List<T>, Integer> get(Location loc) {
        Pair < Boolean , Integer> p = findLoc(loc);
        List<T> C = new LinkedList<T>() ;
    
        if (!p.first) {
        	return new Pair<List<T> , Integer>(C, p.second);
        } else {
        	 if (!current.info.empty()) {
                 current.info.findFirst();
                 while (current.info.last() == false) {
                	 C.insert(current.info.retrieve());
                     current.info.findNext();
                 }
                 C.insert(current.info.retrieve());
             }
        }
        return new Pair<List<T>, Integer>(C, p.second);
	}

	
	@Override
	public Pair<Boolean, Integer> remove(T e, Location loc) {
	        
			boolean removed = false;
	        Pair< Boolean , Integer > p =findLoc(loc);
	        
	       if (p.first == false) {
	    	   return new Pair<> (removed, p.second);
	       } else {
	            if (current.info.empty() == false) {
	                current.info.findFirst();
	                while (current.info.last() == false) {
	                    if (e.equals(current.info.retrieve())) {
	                        current.info.remove();
	                        removed = true;
	                    } else
	                        current.info.findNext(); }
	                
	                if (e.equals(current.info.retrieve())) {
	                    current.info.remove();
	                    removed = true;
	                }
	            }
	        }
	        return new Pair<> (removed, p.second);
	}

	@Override
	public List<Pair<Location, List<T>>> getAll() {
		List<Pair<Location, List<T>>> list = new LinkedList<Pair<Location, List<T>>>();
		if (null != root)
			getlist(root , list);
		return list;
		
	}
	
	private void getlist (TreeOf4<T> P , List<Pair<Location, List<T>>> list) {
		if(P != null) {
			list.insert(new Pair<Location, List<T>>(P.loc, P.info));
			getlist(P.ch1 , list);
			getlist(P.ch2 , list);
			getlist(P.ch3 , list);
			getlist(P.ch4 , list);
		} else
			return;
	}

	@Override
	 public Pair<List<Pair<Location, List<T>>>, Integer> inRange(Location lowerLeft, Location upperRight) {
	        Pair<List<Pair<Location, List<T>>>, Integer> list = new Pair<List<Pair<Location, List<T>>>, Integer>(new LinkedList<Pair<Location, List<T>>>(), 0);
	        getRange(list, root, lowerLeft, upperRight , new Location (upperRight.x, lowerLeft.y), new Location (lowerLeft.x, upperRight.y)) ;

	        return list;
	    }
	    
	    private void getRange(Pair<List<Pair<Location, List<T>>>, Integer> list ,  TreeOf4<T> r , Location LL, Location UR , Location LL2, Location UR2 )
	    {
	        if (r==null)
	            return;
	        else {
	            list.second++;
	            if (LL.x<=r.loc.x  && LL.y<=r.loc.y &&  r.loc.x<=UR.x  && r.loc.y<= UR.y)
	            	list.first.insert(new Pair<Location,List<T>>(r.loc,r.info));
	            
	           if ((LL2.x<r.loc.x && LL2.y<=r.loc.y) || (UR2.x<r.loc.x && UR2.y<=r.loc.y) || (LL.x<r.loc.x && LL.y<=r.loc.y) || (UR.x<r.loc.x && UR.y<=r.loc.y))
	        	   getRange(list, r.ch1, LL, UR, LL2, UR2);
	          
	           if ((UR2.x<=r.loc.x && UR2.y>r.loc.y) || (UR.x<=r.loc.x && UR.y>r.loc.y) || (LL2.x<=r.loc.x && LL2.y>r.loc.y) || (LL.x<=r.loc.x && LL.y>r.loc.y))
	        	   getRange(list, r.ch2 , LL, UR, LL2, UR2);
	          
	           if ((LL.x>r.loc.x && LL.y>=r.loc.y) || (LL2.x>r.loc.x && LL2.y>=r.loc.y) ||  (UR.x >r.loc.x && UR.y>=r.loc.y) || (UR2.x>r.loc.x && UR2.y>=r.loc.y))
	        	   getRange(list, r.ch3 , LL, UR, LL2, UR2);
	        
	           if ( (LL2.x>=r.loc.x && LL2.y<r.loc.y) || (UR.x>=r.loc.x && UR.y<r.loc.y) ||    (LL.x>=r.loc.x && LL.y<r.loc.y) || (UR2.x>=r.loc.x && UR2.y<r.loc.y))
	        	   getRange(list, r.ch4 , LL, UR, LL2, UR2); 
	        }
	    }
	    
	private Pair<Boolean, Integer> findLoc(Location loc) {
		int locComparisons = 0;
		boolean exists = false;
		
		if(root != null) {
			current = root;
			TreeOf4<T> q = null;
			
			while(current!= null) {
				q = current;
				 locComparisons++;
				 
				 if ((current.loc.x == loc.x) && (current.loc.y == loc.y)) {
						exists = true;
						break; 
				 }
				 if ((loc.x  < current.loc.x) && (loc.y <= current.loc.y))
					 current = current.ch1;
				 else if ((loc.x  <= current.loc.x) && (loc.y > current.loc.y))
					 current = current.ch2;
				 else if ((loc.x  > current.loc.x) && (loc.y >= current.loc.y))
					 current = current.ch3;
				 else if ((loc.x  >= current.loc.x) && (loc.y < current.loc.y))
					 current = current.ch4;	 
			}
			current = q;
		}
		return new Pair<Boolean ,Integer>(exists , locComparisons);
	}
}
